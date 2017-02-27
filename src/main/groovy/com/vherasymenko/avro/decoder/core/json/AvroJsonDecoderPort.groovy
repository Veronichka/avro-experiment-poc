package com.vherasymenko.avro.decoder.core.json

import org.apache.avro.Schema

/**
 * The avro json decoder port.
 */
interface AvroJsonDecoderPort {

    void decodeEventWithNewSchema( Schema schema, Schema newSchema, String event )

    void decodeEventWithOldSchema( Schema schema, String event )
}