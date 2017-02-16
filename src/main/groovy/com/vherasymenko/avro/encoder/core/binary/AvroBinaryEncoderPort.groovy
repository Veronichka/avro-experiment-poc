package com.vherasymenko.avro.encoder.core.binary

import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord

/**
 * The avro binary encoder port.
 */
interface AvroBinaryEncoderPort {

    byte[] encodeEvent(Schema schema, GenericRecord avroData )
}