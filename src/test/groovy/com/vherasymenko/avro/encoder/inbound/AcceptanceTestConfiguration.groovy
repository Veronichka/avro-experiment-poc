
package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestOperations

/**
 * We need just enough Spring to parse the application.yml file for us.
 */
@EnableConfigurationProperties( ApplicationProperties )
class AcceptanceTestConfiguration {

    @Bean
    EnvironmentServiceResolver environmentServiceResolver() {
        new EnvironmentServiceResolver()
    }

    @Autowired
    RestOperations restOperations
}
