
package com.vherasymenko.avro

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

/**
 * The entry point into the system. Runs as a standalone web server.
 */
@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

}
