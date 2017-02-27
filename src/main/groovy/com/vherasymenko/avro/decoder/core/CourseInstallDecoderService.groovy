package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.decoder.core.json.AvroJsonDecoderPort
import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient

/**
 * Decoder for the course install document.
 */
@Slf4j
class CourseInstallDecoderService implements CourseInstallDecoderPort {

    private final AvroJsonDecoderPort decoder

    private final SchemaRegistryClient registryClient

    CourseInstallDecoderService( AvroJsonDecoderPort aDecoder, SchemaRegistryClient aRegistryClient ) {
        decoder = aDecoder
        registryClient = aRegistryClient
    }

    @Override
    void decodeCourseInstallV1ToV1( String event, int schemaId ) {
        def schema = new Schema.Parser().parse( registryClient.fetch( schemaId ) )
        log.info( 'The avro schema for event decoding : ' + schema.toString( true ) )

        decoder.decodeEventWithOldSchema( schema, event )
    }

    @Override
    void decodeCourseInstallV1ToV2( String event, int schemaId ) {
        def writeSchema = new Schema.Parser().parse( registryClient.fetch( schemaId ) )
        def readSchema = new Schema.Parser().parse( new File( 'src/main/resources/avro/course_install_2.avsc' ) )
        log.info( 'The avro schema for event decoding : ' + readSchema.toString( true ) )

        decoder.decodeEventWithNewSchema( writeSchema, readSchema, event )
    }

    @Override
    void decodeCourseInstallV1ToV3( String event, int schemaId ) {
        def writeSchema = new Schema.Parser().parse( registryClient.fetch( schemaId ) )
        def readSchema = new Schema.Parser().parse( new File( 'src/main/resources/avro/course_install_3.avsc' ) )
        log.info( 'The avro schema for event decoding : ' + readSchema.toString( true ) )

        decoder.decodeEventWithNewSchema( writeSchema, readSchema, event )

    }
}
