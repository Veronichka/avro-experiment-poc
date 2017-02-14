package com.vherasymenko.avro.decoder.core

/**
 * The avro decoder port.
 */
interface AvroDecoderPort {

    void decodeEvent( String event )
}