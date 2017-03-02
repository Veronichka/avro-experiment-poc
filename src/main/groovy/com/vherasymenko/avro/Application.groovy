
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
import com.vherasymenko.avro.encoder.core.CourseInstallEncoderPort
import com.vherasymenko.avro.encoder.core.CourseInstallEncoderService
import com.vherasymenko.avro.encoder.core.LessonStatusEncoderPort
import com.vherasymenko.avro.encoder.core.LessonStatusEncoderService
import com.vherasymenko.avro.encoder.outbound.MessageProducer
import com.vherasymenko.avro.schemaLoader.AvroSchemasLoaderPort
import com.vherasymenko.avro.schemaLoader.AvroSchemasLoaderService
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient
import org.springframework.context.annotation.Bean
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
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
    SchemaRegistryClient registryClient() {
        new ConfluentSchemaRegistryClient()
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
    AvroSchemasLoaderPort avroSchemasScanPort( SchemaRegistryClient aRegistryClient ) {
        def resolver = new PathMatchingResourcePatternResolver()
        new AvroSchemasLoaderService( resolver, aRegistryClient )
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
    CourseInstallEncoderPort courseInstallEncoder( MessageProducer producer, AvroJsonEncoderPort encoder, SchemaRegistryClient registryClient ) {
        new CourseInstallEncoderService( producer, encoder, registryClient )
    }

    @Bean
    LessonStatusEncoderPort lessonStatusEncoder( MessageProducer producer, AvroBinaryEncoderPort encoder, SchemaRegistryClient registryClient ) {
        new LessonStatusEncoderService( producer, encoder, registryClient )
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
     CourseInstallDecoderPort courseInstallDecoder( AvroJsonDecoderPort decoder, SchemaRegistryClient registryClient ) {
        new CourseInstallDecoderService( decoder, registryClient )
    }

    @Bean
    LessonStatusDecoderPort lessonStatusDecoder( AvroBinaryDecoderPort decoder, SchemaRegistryClient registryClient ) {
        new LessonStatusDecoderService( decoder, registryClient )
    }

    @Bean
    MessageRouterPort messageRouter( CourseInstallDecoderPort courseInstallDecoder, LessonStatusDecoderPort lessonStatusDecoder ) {
        new MessageRouter( courseInstallDecoder, lessonStatusDecoder )
    }
}
