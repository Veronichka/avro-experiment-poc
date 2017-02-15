
package com.vherasymenko.avro

import com.vherasymenko.avro.decoder.core.CourseInstallDecoderPort
import com.vherasymenko.avro.decoder.core.CourseInstallDecoderService
import com.vherasymenko.avro.decoder.core.LessonStatusDecoderPort
import com.vherasymenko.avro.decoder.core.LessonStatusDecoderService
import com.vherasymenko.avro.decoder.core.binary.AvroBinaryDecoderPort
import com.vherasymenko.avro.decoder.core.binary.AvroBinaryDecoderService
import com.vherasymenko.avro.decoder.core.json.AvroJsonDecoderPort
import com.vherasymenko.avro.decoder.core.json.AvroJsonDecoderService
import com.vherasymenko.avro.decoder.core.MessageRouter
import com.vherasymenko.avro.decoder.core.MessageRouterPort
import com.vherasymenko.avro.encoder.core.binary.AvroBinaryEncoderPort
import com.vherasymenko.avro.encoder.core.binary.AvroBinaryEncoderService
import com.vherasymenko.avro.encoder.core.json.AvroJsonEncoderService
import com.vherasymenko.avro.encoder.core.json.AvroJsonEncoderPort
import com.vherasymenko.avro.encoder.core.CourseInstallPort
import com.vherasymenko.avro.encoder.core.CourseInstallService
import com.vherasymenko.avro.encoder.core.LessonStatusPort
import com.vherasymenko.avro.encoder.core.LessonStatusService
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
    AvroJsonEncoderPort avroEncoderPort() {
        new AvroJsonEncoderService()
    }

    @Bean
    AvroBinaryEncoderPort avroBinaryEncoder() {
        new AvroBinaryEncoderService()
    }

    @Bean
    CourseInstallPort courseInstallEncoder( MessageProducer producer, AvroJsonEncoderPort encoder ) {
        new CourseInstallService( producer, encoder )
    }

    @Bean
    LessonStatusPort lessonStatusEncoder( MessageProducer producer, AvroBinaryEncoderPort encoder ) {
        new LessonStatusService( producer, encoder )
    }

    // Decoder

    @Bean
    AvroJsonDecoderPort avroDecoderPort() {
        new AvroJsonDecoderService()
    }

    @Bean
    AvroBinaryDecoderPort avroBinaryDecoder() {
        new AvroBinaryDecoderService()
    }

    @Bean
     CourseInstallDecoderPort courseInstallDecoder( AvroJsonDecoderPort decoder ) {
        new CourseInstallDecoderService( decoder )
    }

    @Bean
    LessonStatusDecoderPort lessonStatusDecoder( AvroBinaryDecoderPort decoder ) {
        new LessonStatusDecoderService( decoder )
    }

    @Bean
    MessageRouterPort messageRouter( CourseInstallDecoderPort courseInstallDecoder, LessonStatusDecoderPort lessonStatusDecoder ) {
        new MessageRouter( courseInstallDecoder, lessonStatusDecoder )
    }
}
