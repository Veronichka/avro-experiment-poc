
package com.vherasymenko.avro

import com.vherasymenko.avro.core.AvroEncoderPort
import com.vherasymenko.avro.core.AvroEncoderService
import com.vherasymenko.avro.outbound.MessageProducer
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

/**
 * The entry point into the system. Runs as a standalone web server.
 */
@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

    @Bean
    RestOperations restOperations( ApplicationProperties configuration ) {
        def factory = new HttpComponentsClientHttpRequestFactory()
        factory.connectTimeout = configuration.rest.connectTimeout
        factory.readTimeout = configuration.rest.readTimeout
        new RestTemplate( factory )
    }

    @Bean
    RabbitTemplate rabbitTemplate( CachingConnectionFactory connectionFactory ) {
        def bean = new RabbitTemplate( connectionFactory )
        bean
    }

    @Bean
    AvroEncoderPort avroEncoderPort(MessageProducer eventProducer ) {
        new AvroEncoderService( eventProducer )
    }

}
