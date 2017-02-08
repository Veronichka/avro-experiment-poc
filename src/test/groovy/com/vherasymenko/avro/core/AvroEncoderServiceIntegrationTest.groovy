package com.vherasymenko.avro.core

import com.vherasymenko.avro.BaseIntegrationTest
import com.vherasymenko.avro.outbound.MessageProducer
import org.springframework.beans.factory.annotation.Autowired

/**
 * The integration-level testing of AvroEncoderService.
 */
class AvroEncoderServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MessageProducer messageProducer

    def 'exercise json encoder'() {
        given: 'valid json input'
        def input = '{"name": "Ben", "favoriteNumber": 7, "favoriteColor": "red"}'

        and: 'valid subject under test'
        def sut = new AvroEncoderService( messageProducer )

        when: 'the input event is sent to encoder'
        sut.encodeEvent( input )

        then: 'the encoded event is sent to the messaging system'
        //TODO: listen messages from the rabbitmq and check here.
    }
}
