
package com.vherasymenko.avro.spring

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

/**
 * Event listener that can detect what port the embedded container chose to bind to.
 */
@Component
class DynamicPortListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    /**
     * When we get the event from the embedded server, we will change this to the port it is actually listening on.
     */
    int serverPort = 8080

    @Override
    void onApplicationEvent( EmbeddedServletContainerInitializedEvent event ) {
        serverPort = event.embeddedServletContainer.port
    }
}
