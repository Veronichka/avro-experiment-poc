package com.vherasymenko.avro.encoder.core

/**
 * Port to the lesson status document encoder service.
 */
interface LessonStatusEncoderPort {

    void handleEvent( String event )
}