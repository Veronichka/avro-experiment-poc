package com.vherasymenko.avro.encoder.core.json

import org.apache.avro.Schema

/**
 * The avro json encoder port.
 */
interface AvroJsonEncoderPort {

    String encodeEvent(Schema schema, def avroData, Class classType )
}