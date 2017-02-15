package com.vherasymenko.avro.encoder.core.json

import org.apache.avro.Schema
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter

/**
 * The avro json encoder.
 */
class AvroJsonEncoderService implements AvroJsonEncoderPort {

    @Override
    String encodeEvent( Schema schema, def avroData, Class classType ) {

        // from doc: We create a DatumWriter, which converts Java objects into an in-memory serialized format.
        // The SpecificDatumWriter class is used with generated classes and extracts the schema from the specified generated type.
        def datumWriter = new SpecificDatumWriter( classType )

        def output = new ByteArrayOutputStream()
        def encoder = EncoderFactory.get().jsonEncoder( schema, output, true )

        datumWriter.write( avroData, encoder )
        encoder.flush()

        output.toString( 'UTF-8' )
    }
}
