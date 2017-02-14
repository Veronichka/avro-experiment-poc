package com.vherasymenko.avro.decoder.core

import org.springframework.messaging.Message

/**
 * The router port.
 */
interface MessageRouterPort {

    void routeEvent( Message eventMessage )
}