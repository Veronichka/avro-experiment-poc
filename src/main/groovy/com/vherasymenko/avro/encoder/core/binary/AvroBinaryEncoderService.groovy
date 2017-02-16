package com.vherasymenko.avro.encoder.core.binary

import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.EncoderFactory

/**
 * The avro binary encoder.
 */
class AvroBinaryEncoderService implements AvroBinaryEncoderPort {

    @Override
    byte[] encodeEvent( Schema schema, GenericRecord avroData ) {

        // from doc: Since we are not using code generation, we create a GenericDatumWriter.
        // It requires the schema both to determine how to write the GenericRecords and to verify that all non-nullable fields are present.
        def datumWriter = new GenericDatumWriter( schema )

        def output = new ByteArrayOutputStream()
        def encoder = EncoderFactory.get().binaryEncoder( output, null )

        datumWriter.write( avroData, encoder )
        encoder.flush()

        output.toByteArray()

    }
}
