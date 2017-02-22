package com.vherasymenko.avro.decoder.core

/**
 * Port to the course install decoder.
 */
interface CourseInstallDecoderPort {

    void decodeCourseInstallV1ToV1( String event, int schemaId )

    void decodeCourseInstallV1ToV2( String event, int schemaId )

    void decodeCourseInstallV1ToV3( String event, int schemaId )
}