package com.vherasymenko.avro.decoder.core.json

import org.apache.avro.Schema

/**
 * The avro json decoder port.
 */
interface AvroJsonDecoderPort {

    void decodeEvent( Schema schema, Schema newSchema, String event )
}