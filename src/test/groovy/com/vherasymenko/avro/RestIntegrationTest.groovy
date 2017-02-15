package com.vherasymenko.avro

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder

/**
 * The integration test to the full encoder work flow.
 */
class RestIntegrationTest extends BaseIntegrationTest {

    def 'exercise full workflow for the course install'() {
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
        def reportURI = getRestBaseURI( '/course/install' ).build().toUri()
        def entity = new HttpEntity( requestEvent )
        def createResult = restOperations.postForEntity( reportURI, entity, String )

        then: 'the valid response is returned'
        createResult.statusCode == HttpStatus.OK
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
        def reportURI = getRestBaseURI( '/lesson/status' ).build().toUri()
        def entity = new HttpEntity( requestEvent )
        def createResult = restOperations.postForEntity( reportURI, entity, String )

        then: 'the valid response is returned'
        createResult.statusCode == HttpStatus.OK
    }

    private UriComponentsBuilder getRestBaseURI( String path ) {
        UriComponentsBuilder.newInstance().
                scheme( 'http' ).
                host( 'localhost' ).
                port( portListener.serverPort ).
                path( path )
    }
}
