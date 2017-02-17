package com.vherasymenko.avro.decoder.core.binary

import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro binary decoder.
 */
@Slf4j
class AvroBinaryDecoderService implements AvroBinaryDecoderPort {

    @Override
    void decodeEvent( Schema schema, byte[] event ) {
        def decoder = new DecoderFactory().binaryDecoder( event, null )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        def document = reader.read( null, decoder )
        log.info( 'Decoded document with binary avro decoder: ' + document )
    }
}
