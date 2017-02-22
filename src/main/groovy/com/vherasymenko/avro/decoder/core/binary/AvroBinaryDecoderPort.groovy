package com.vherasymenko.avro.decoder.core.binary

import org.apache.avro.Schema

/**
 * The avro binary decoder port.
 */
interface AvroBinaryDecoderPort {

    void decodeEvent( Schema schema, byte[] event )
}