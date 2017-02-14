package com.vherasymenko.avro.encoder.core

import org.apache.avro.Schema

/**
 * The avro encoder port.
 */
interface AvroEncoderPort {

    String encodeEvent(Schema schema, def avroData, Class classType )
}