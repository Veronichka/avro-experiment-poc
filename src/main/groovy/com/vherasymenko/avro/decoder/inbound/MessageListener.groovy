package com.vherasymenko.avro.decoder.inbound

import com.vherasymenko.avro.decoder.core.AvroDecoderPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

/**
 * The gateway to the messaging system.
 */
@Component
@EnableBinding( Sink )
class MessageListener {

    @Autowired
    AvroDecoderPort decoder

    @StreamListener( Sink.INPUT )
    void handleMessage( final String message ) {
        decoder.decodeEvent( message )
    }
}
