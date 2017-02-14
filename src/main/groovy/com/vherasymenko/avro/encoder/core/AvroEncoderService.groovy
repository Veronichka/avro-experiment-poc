package com.vherasymenko.avro.encoder.core

import org.apache.avro.Schema
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter

/**
 * The avro encoder.
 */
class AvroEncoderService implements AvroEncoderPort {

    @Override
    String encodeEvent( Schema schema, def avroData, Class classType ) {

        def datumWriter = new SpecificDatumWriter( classType )

        def output = new ByteArrayOutputStream()
        def encoder = EncoderFactory.get().jsonEncoder( schema, output, true )

        datumWriter.write( avroData, encoder )
        encoder.flush()

        new String( output.toByteArray() )
    }
}
