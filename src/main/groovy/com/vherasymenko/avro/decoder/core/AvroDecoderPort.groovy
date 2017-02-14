package com.vherasymenko.avro.decoder.core

import org.apache.avro.Schema

/**
 * The avro decoder port.
 */
interface AvroDecoderPort {

    void decodeEvent( Schema schema, String event )
}