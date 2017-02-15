package com.vherasymenko.avro.decoder.core.json

import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro json decoder.
 */
class AvroJsonDecoderService implements AvroJsonDecoderPort {

    @Override
    void decodeEvent( Schema schema, String event ) {
        def decoder = new DecoderFactory().jsonDecoder( schema, event )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        def document = reader.read( null, decoder )
        println( 'Decoded document: ' + document )
        document
    }
}
