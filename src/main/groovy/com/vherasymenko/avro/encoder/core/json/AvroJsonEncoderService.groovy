package com.vherasymenko.avro.encoder.core.json

import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter

/**
 * The avro json encoder.
 */
@Slf4j
class AvroJsonEncoderService implements AvroJsonEncoderPort {

    @Override
    String encodeEvent( Schema schema, def avroData, Class classType ) {
        try {
            // from doc: We create a DatumWriter, which converts Java objects into an in-memory serialized format.
            // The SpecificDatumWriter class is used with generated classes and extracts the schema from the specified generated type.
            def datumWriter = new SpecificDatumWriter(classType)

            def output = new ByteArrayOutputStream()
            def encoder = EncoderFactory.get().jsonEncoder(schema, output, true)
            log.info('Avro json encoding.')
            datumWriter.write(avroData, encoder)
            encoder.flush()

            output.toString('UTF-8')
        }
        catch ( Exception e ) {
            log.error( 'The encoding failed: ' + e.message )
        }
    }
}
