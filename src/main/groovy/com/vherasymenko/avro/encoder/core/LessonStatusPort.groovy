package com.vherasymenko.avro.encoder.core

/**
 * Port to the lesson status document encoder service.
 */
interface LessonStatusPort {

    void handleEvent( String event )
}