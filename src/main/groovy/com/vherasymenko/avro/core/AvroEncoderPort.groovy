package com.vherasymenko.avro.core

/**
 * The avro encoder port.
 */
interface AvroEncoderPort {

    void encodeEvent( String event )
}