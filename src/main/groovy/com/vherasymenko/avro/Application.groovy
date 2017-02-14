
package com.vherasymenko.avro

import com.vherasymenko.avro.decoder.core.AvroDecoderPort
import com.vherasymenko.avro.decoder.core.AvroDecoderService
import com.vherasymenko.avro.decoder.core.CourseInstallDecoderPort
import com.vherasymenko.avro.decoder.core.CourseInstallDecoderService
import com.vherasymenko.avro.decoder.core.MessageRouter
import com.vherasymenko.avro.decoder.core.MessageRouterPort
import com.vherasymenko.avro.encoder.core.AvroEncoderPort
import com.vherasymenko.avro.encoder.core.AvroEncoderService
import com.vherasymenko.avro.encoder.core.CourseInstallEncoderPort
import com.vherasymenko.avro.encoder.core.CourseInstallEncoderService
import com.vherasymenko.avro.encoder.outbound.MessageProducer
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

    // Encoder

    @Bean
    AvroEncoderPort avroEncoderPort() {
        new AvroEncoderService()
    }

    @Bean
    CourseInstallEncoderPort courseInstallEncoder( MessageProducer producer, AvroEncoderPort encoder ) {
        new CourseInstallEncoderService( producer, encoder )
    }

    // Decoder

    @Bean
    AvroDecoderPort avroDecoderPort() {
        new AvroDecoderService()
    }

    @Bean
     CourseInstallDecoderPort courseInstallDecoder( AvroDecoderPort decoder ) {
        new CourseInstallDecoderService( decoder )
    }

    @Bean
    MessageRouterPort messageRouter( CourseInstallDecoderPort courseInstallDecoder ) {
        new MessageRouter( courseInstallDecoder )
    }
}
