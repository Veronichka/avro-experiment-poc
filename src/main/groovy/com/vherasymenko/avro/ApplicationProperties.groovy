
package com.vherasymenko.avro

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Custom configuration properties that are driven by Spring Boot and its application.yml file.
 */
@ConfigurationProperties( value = 'avro', ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * Contains the logging related properties.
     */
    Logging logging

    /**
     * The Rest API configuration.
     */
    RestProperties rest

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

    static class RestProperties {
        /**
         * The number of milliseconds the REST template should wait to establish a connection with the service it is calling.
         */
        int connectTimeout

        /**
         * The number of milliseconds the REST template should wait for a response from the service it is calling.
         */
        int readTimeout
    }
}
