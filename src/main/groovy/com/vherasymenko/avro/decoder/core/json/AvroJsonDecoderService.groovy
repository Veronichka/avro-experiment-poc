package com.vherasymenko.avro.decoder.core.json

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
    void decodeEvent( Schema schema, Schema newSchema, String event ) {
        def decoder = new DecoderFactory().jsonDecoder( schema, event )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema, newSchema )
        def document = reader.read( null, decoder )
        log.info( 'Decoded document with json avro decoder: ' + document )
    }
}
