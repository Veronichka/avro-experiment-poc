package com.vherasymenko.avro.decoder.core

/**
 * Port to the lesson status decoder.
 */
interface LessonStatusDecoderPort {

    void decodeLessonInstallEvent( byte[] event )
}