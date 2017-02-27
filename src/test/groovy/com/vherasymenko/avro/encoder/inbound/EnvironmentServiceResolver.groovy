
package com.vherasymenko.avro.encoder.inbound

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.web.util.UriComponentsBuilder

/**
 * Uses the environment to obtain the port that a standalone server is running on.
 */
class EnvironmentServiceResolver implements HttpServiceResolver, ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    /**
     * The port the service is listening on.
     */
    int port = Integer.MIN_VALUE

    @Override
    void onApplicationEvent( final EmbeddedServletContainerInitializedEvent event ) {
        port = event.embeddedServletContainer.port
    }

    @Override
    URI resolveURI() {
        UriComponentsBuilder.newInstance()
                .scheme( 'http' )
                .host( 'localhost' )
                .port( port )
                .path( '/' )
                .build()
                .toUri()
    }
}
