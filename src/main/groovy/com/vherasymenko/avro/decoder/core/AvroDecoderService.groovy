package com.vherasymenko.avro.decoder.core

import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro decoder.
 */
class AvroDecoderService implements AvroDecoderPort {

    @Override
    void decodeEvent( Schema schema, String event ) {
        def decoder = new DecoderFactory().jsonDecoder( schema, event )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        def document = reader.read( null, decoder )
        println( 'Decoded document: ' + document )
        document
    }
}
