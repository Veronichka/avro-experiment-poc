package com.vherasymenko.avro.encoder.core

/**
 * Port to the course install document encoder service.
 */
interface CourseInstallEncoderPort {

    void handleEvent( String event, String processChannelName )
}