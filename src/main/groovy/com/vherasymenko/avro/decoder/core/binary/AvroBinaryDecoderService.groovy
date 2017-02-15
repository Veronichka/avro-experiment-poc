package com.vherasymenko.avro.decoder.core.binary

import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro binary decoder.
 */
class AvroBinaryDecoderService implements AvroBinaryDecoderPort {

    @Override
    void decodeEvent( Schema schema, byte[] event ) {
        def decoder = new DecoderFactory().binaryDecoder( event, null )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        def document = reader.read( null, decoder )
        println( 'Decoded document: ' + document )
        document
    }
}
