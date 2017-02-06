
package com.vherasymenko.avro.inbound

import com.vherasymenko.avro.ApplicationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

/**
 * We need just enough Spring to parse the application.yml file for us.
 */
@EnableConfigurationProperties( ApplicationProperties )
class AcceptanceTestConfiguration {
    // acceptance test specific beans go here
}
