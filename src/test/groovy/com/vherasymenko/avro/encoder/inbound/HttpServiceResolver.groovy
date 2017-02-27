
package com.vherasymenko.avro.encoder.inbound

/**
 * Strategy for determining the HTTP coordinates the tests should connect to.
 */
interface HttpServiceResolver {

    /**
     * Resolves the URI that should be used when connecting to the application.
     * @return URI to use.
     */
    URI resolveURI()
}
