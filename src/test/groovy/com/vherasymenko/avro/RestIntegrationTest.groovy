package com.vherasymenko.avro

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder

/**
 * The integration test to the full encoder work flow.
 */
class RestIntegrationTest extends BaseIntegrationTest {

    def 'exercise full workflow'() {
        given: 'valid rest operations'
        assert restOperations

        and: 'valid json event'
        def requestEvent = '{"name": "Ben", "favoriteNumber": 7, "favoriteColor": "red"}'

        when: 'the valid request event is sent through rest'
        def reportURI = restBaseURI.build().toUri()
        def entity = new HttpEntity( requestEvent )
        def createResult = restOperations.postForEntity( reportURI, entity, String )

        then: 'the valid response is returned'
        createResult.statusCode == HttpStatus.OK
    }

    private UriComponentsBuilder getRestBaseURI() {
        UriComponentsBuilder.newInstance().
                scheme( 'http' ).
                host( 'localhost' ).
                port( portListener.serverPort ).
                path( '/user' )
    }
}
