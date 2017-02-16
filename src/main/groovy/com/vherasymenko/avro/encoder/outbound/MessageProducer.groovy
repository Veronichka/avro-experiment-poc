package com.vherasymenko.avro.encoder.outbound

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

/**
 * The gateway to the messaging system.
 */
@Component
@EnableBinding( Source.class )
class MessageProducer {

    private Source source

    @Autowired
    MessageProducer( Source aSource ) {
        source = aSource
    }

    void sendMessage( Message message ) {
        source.output().send( message )
    }
}
