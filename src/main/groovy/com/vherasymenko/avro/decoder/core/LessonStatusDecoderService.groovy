package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.decoder.core.binary.AvroBinaryDecoderPort
import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient

/**
 * Decoder for the lesson status document.
 */
@Slf4j
class LessonStatusDecoderService implements LessonStatusDecoderPort {

    private final AvroBinaryDecoderPort decoder

    private final SchemaRegistryClient registryClient

    LessonStatusDecoderService( AvroBinaryDecoderPort aDecoder, SchemaRegistryClient aRegistryClient ) {
        decoder = aDecoder
        registryClient = aRegistryClient
    }

    @Override
    void decodeLessonInstallEvent( byte[] event, int schemaId ) {
        def schema = new Schema.Parser().parse( registryClient.fetch( schemaId ) )
        log.info( 'The avro schema for event decoding : ' + schema.toString( true ) )

        decoder.decodeEvent( schema, event )
    }
}
