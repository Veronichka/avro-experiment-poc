package com.vherasymenko.avro.decoder.core.json

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro json decoder.
 */
@Slf4j
class AvroJsonDecoderService implements AvroJsonDecoderPort {

    @Override
    void decodeEventWithNewSchema( Schema schema, Schema newSchema, String event ) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema, newSchema )
        decodeEvent( schema, reader, event )
    }

    @Override
    void decodeEventWithOldSchema( Schema schema, String event ) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        decodeEvent( schema, reader, event )
    }

    private static void decodeEvent( Schema schema, DatumReader<GenericRecord> reader, String event ) {
        try {
            def decoder = new DecoderFactory().jsonDecoder( schema, event )
            def document = reader.read( null, decoder )
            log.info( 'Successfully decoded document with json avro decoder: ' + JsonOutput.prettyPrint( document.toString() ) )
        }
        catch ( Exception e ) {
            log.error( 'The decoding failed: ' + e.message )
        }
    }
}
