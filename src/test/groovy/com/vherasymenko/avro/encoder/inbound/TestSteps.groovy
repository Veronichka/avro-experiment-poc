
package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.Application
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

import java.util.regex.Pattern

/**
 * Step definitions geared towards Enchantment's acceptance test but remember, all steps are used
 * by Cucumber unless special care is taken. If you word your features in a consistent manner, then
 * steps will automatically get reused and you won't have to keep writing the same test code.
 */
@Slf4j
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = [Application, AcceptanceTestConfiguration], loader = SpringApplicationContextLoader )
class TestSteps {

    /**
     * Knows how to determine the service that the application is listening on.
     */
    @Autowired
    private HttpServiceResolver theServiceResolver

    @Autowired
    private RestOperations restOperations

    /**
     * This is state shared between steps and can be setup and torn down by the hooks.
     */
    class MyWorld {
        URI uri
        String requestEvent
        String eventPath
        ResponseEntity response
    }

    /**
     * Shared between hooks and steps.
     */
    MyWorld sharedState

    @Before
    void assembleSharedState() {
        log.info( 'Creating shared state' )
        sharedState = new MyWorld()
        sharedState.uri = theServiceResolver.resolveURI()
    }

    @After
    void destroySharedState() {
        log.info( 'Destroying shared state' )
        sharedState = null
    }

    @Given( '^valid event$' )
    void 'valid event'() {
        sharedState.requestEvent = '''
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
    }

    @Given( '^valid lesson status event$' )
    void 'valid lesson status event'() {
        sharedState.requestEvent = '''
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
    }

    @Given( '^valid path (.*)$' )
    void 'valid path'( String restPath ) {
        sharedState.eventPath = restPath
    }

    @When( '^the request is sent$' )
    void 'the request is sent'() {
        def reportURI = UriComponentsBuilder.newInstance().uri( sharedState.uri )
                .path( sharedState.eventPath ).build().toUri()
        def entity = new HttpEntity( sharedState.requestEvent )
        sharedState.response = restOperations.postForEntity( reportURI, entity, String )
    }

    @Then( '^the response returned with status (\\d+)$' )
    void 'the response returned with status (\\d+)'( int responseCode ) {
        assert sharedState.response.statusCode.value() == responseCode
    }

    @Then( '^the success is logged$' )
    void 'the success is logged'() {
        sleep( 300 )
        def fileStream = new FileInputStream('logs/logfile.txt')
        def buffer = new BufferedReader(new InputStreamReader(fileStream))
        def pattern = Pattern.compile('Successfully decoded document with json avro decoder')
        assert findExpectedMessage( buffer, pattern )
    }

    @Then( '^the failure is logged$' )
    void 'the failure is logged'() {
        sleep( 300 )
        def fileStream = new FileInputStream('logs/logfile.txt')
        def buffer = new BufferedReader(new InputStreamReader(fileStream))
        def pattern = Pattern.compile( 'The decoding failed' )
        assert findExpectedMessage( buffer, pattern )
    }

    private boolean findExpectedMessage( BufferedReader buffer, Pattern pattern ) {
        String strLine
        def messageMatched = false
        while ( ( strLine = buffer.readLine() ) != null && !messageMatched ) {
            messageMatched = pattern.matcher( strLine ).find()
        }
        messageMatched
    }
}
