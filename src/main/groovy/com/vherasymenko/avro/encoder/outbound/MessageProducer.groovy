package com.vherasymenko.avro.encoder.outbound

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

/**
 * The gateway to the messaging system.
 */
@Slf4j
@Component
@EnableBinding( Source.class )
class MessageProducer {

    private Source source

    @Autowired
    MessageProducer( Source aSource ) {
        source = aSource
    }

    void sendMessage( Message message ) {
        log.info( 'Sending message to spring cloud stream.' )
        source.output().send( message )
    }
}
