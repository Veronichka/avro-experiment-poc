package com.vherasymenko.avro.decoder.inbound

import com.vherasymenko.avro.decoder.core.MessageRouterPort
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

/**
 * The gateway to the messaging system.
 */
@Slf4j
@Component
@EnableBinding( Sink )
class MessageListener {

    @Autowired
    MessageRouterPort router

    @StreamListener( Sink.INPUT )
    void handleMessage( final Message message ) {
        log.info( 'The stream listener received a message : ' + message.toString() )
        router.routeEvent( message )
    }
}
