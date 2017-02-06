
package com.vherasymenko.avro

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Custom configuration properties that are driven by Spring Boot and its application.yml file.
 */
@ConfigurationProperties( value = 'example', ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * Contains the logging related properties.
     */
    Logging logging

    static class Logging {
        /**
         * Identifies this type of service. Used in logging.
         */
        String serviceCode

        /**
         * Identifies this instance of the service. Used in logging.
         */
        String serviceInstance

        /**
         * Logically groups a collection of services. Used in logging.
         */
        String realm
    }

    /**
     * This property controls...
     */
    String foo

}
