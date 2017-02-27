package com.vherasymenko.avro

import com.vherasymenko.avro.schared.RestConstants
import org.apache.avro.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Unroll
import java.util.regex.Pattern

/**
 * The integration test to the full encoder work flow.
 */
class RestIntegrationTest extends BaseIntegrationTest {

    @Autowired
    SchemaRegistryClient registryClient

    @Unroll
    def 'exercise full workflow for the course install decoding from V1 to #description'() {
        given: 'valid rest operations'
        assert restOperations

        and: 'valid json event'
        def requestEvent = '''
            { 
                "course" : {                                                      
                    "uuid" : "fb88ad20-1702-11e3-a517-50e5494249f7",              
                    "name" : "Russian Essentials Ver.2",                          
                    "knownLanguage" : "RUSSIAN",                                  
                    "targetLanguage" : "ENGLISH",                                 
                    "product" : "LessonGin"                                       
                },
                "units" : [
                    {
                        "id" : 81,                                                
                        "uuid" : "7764b0f0-9d28-4c2a-82b9-3cb5daba3335",          
                        "name" : "Unit 1",                                        
                        "maxScore" : 8,                                           
                        "minScore" : 4,                                           
                        "hasAssessment" : true,                                   
                        "lessons" : [
                            {
                                "id" : 229,                                       
                                "uuid" : "e7594991-0d76-4f88-b265-ca7235364338",  
                                "name" : "Lesson 1",                              
                                "position" : 1,                                   
                                "isAssessment" : false                            
                            }
                        ]
                    }
                ]
            }
        '''

        when: 'the valid request event is sent through rest'
        def reportURI = getRestBaseURI( restPath ).build().toUri()
        def entity = new HttpEntity( requestEvent )
        def createResult = restOperations.postForEntity( reportURI, entity, String )

        then: 'the valid response is returned'
        createResult.statusCode == HttpStatus.OK

        and: 'the success is logged'
        sleep( 300 )
        def fileStream = new FileInputStream('logs/logfile.txt')
        def buffer = new BufferedReader(new InputStreamReader(fileStream))

        if ( restPath == RestConstants.COURSE_INSTAL_V1_TO_V3 ) {
            def pattern = Pattern.compile( 'The decoding failed' )
            assert findExpectedMessage( buffer, pattern )
        }
        else {
            def pattern = Pattern.compile('Successfully decoded document with json avro decoder')
            assert findExpectedMessage( buffer, pattern )
        }

        where:
        restPath                                | description
        RestConstants.COURSE_INSTALL_V1_TO_V1   | 'V1'
        RestConstants.COURSE_INSTALL_V1_TO_V2   | 'V2'
        RestConstants.COURSE_INSTAL_V1_TO_V3    | 'V3'
    }

    private boolean findExpectedMessage( BufferedReader buffer, Pattern pattern ) {
        String strLine
        def messageMatched = false
        while ( ( strLine = buffer.readLine() ) != null && !messageMatched ) {
            messageMatched = pattern.matcher( strLine ).find()
        }
        messageMatched
    }

    def 'exercise full workflow for the lesson status'() {
        given: 'valid rest operations'
        assert restOperations

        and: 'valid json event'
        def requestEvent = '''
            { 
                "agent": "WEB",                                                       
                "status" : [
                    {
                        "userUuid" : "ccd41244-241c-11e3-8fcc-00ffb08bd008",         
                        "courseUuid" : "aac41231-241e-11e3-8fbb-00ffb08bd174",        
                        "unitId" : 80,                                                
                        "lessonUuid" : "ccd41244-241c-11e3-8fcc-00ffb08bd008",        
                        "completionStatus" : "IN_PROGRESS",                           
                        "score": 0,                                                   
                        "time": 5,                                                    
                        "markedLearned" : true,                                      
                        "testedOut" : true,                                           
                        "unitAssessmentStatus" : "FAILED",                            
                        "unitAssessmentScore" : 8                                    
                    }
                ]
            }
        '''

        when: 'the valid request event is sent through rest'
        def reportURI = getRestBaseURI( RestConstants.LESSON_STATUS ).build().toUri()
        def entity = new HttpEntity( requestEvent )
        def createResult = restOperations.postForEntity( reportURI, entity, String )

        then: 'the valid response is returned'
        createResult.statusCode == HttpStatus.OK

        and: 'the success is logged'
        sleep( 300 )
        def fileStream = new FileInputStream('logs/logfile.txt')
        def buffer = new BufferedReader(new InputStreamReader(fileStream))
        def pattern = Pattern.compile('Successfully decoded document with json avro decoder')
        assert findExpectedMessage( buffer, pattern )
    }

    def 'exercise schema registry client'() {
        given: 'valid schema'
        def schema = new Schema.Parser().parse( new File( 'src/main/avro/course_install.avsc' ) )

        when: 'register schema in the schema registry'
        def schemaId = registryClient.register( schema.name, 'avro', schema.toString() ).id

        then: 'the schema is successfully retrieved from the registry server'
        def fetchedSchema = registryClient.fetch( schemaId )
        fetchedSchema == schema.toString()
    }

    private UriComponentsBuilder getRestBaseURI( String path ) {
        UriComponentsBuilder.newInstance().
                scheme( 'http' ).
                host( 'localhost' ).
                port( portListener.serverPort ).
                path( RestConstants.REPORT ).
                path( path )
    }
}
