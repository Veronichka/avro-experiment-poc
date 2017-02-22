package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.MessageHeaders
import groovy.util.logging.Slf4j
import org.springframework.messaging.Message

/**
 * Routes messages from messaging system to different decoders.
 */
@Slf4j
class MessageRouter implements MessageRouterPort {

    /**
     * Handles course install document decoding.
     */
    private final CourseInstallDecoderPort courseInstallDecoder

    /**
     * Handles lesson install document decoding.
     */
    private final LessonStatusDecoderPort lessonStatusDecoder

    MessageRouter( CourseInstallDecoderPort aCourseInstallDecoder, LessonStatusDecoderPort aLessonStatusDecoder ) {
        courseInstallDecoder = aCourseInstallDecoder
        lessonStatusDecoder = aLessonStatusDecoder
    }

    @Override
    void routeEvent( Message eventMessage ) {
        def eventContentType = eventMessage.headers.get( MessageHeaders.CONTENT_TYPE )
        def schemaId = eventMessage.headers.get( MessageHeaders.SCHEMA_ID ) as int
        def eventPayload = eventMessage.payload

        if ( eventContentType == AvroConstants.COURSE_INSTALL_V1_TO_V1_CHANNEL ) {
            log.info( 'The message is sent to the ' + AvroConstants.COURSE_INSTALL_V1_TO_V1_CHANNEL )
            courseInstallDecoder.decodeCourseInstallV1ToV1( eventPayload as String, schemaId )
        }
        else if ( eventContentType == AvroConstants.COURSE_INSTALL_V1_TO_V2_CHANNEL ) {
            log.info( 'The message is sent to the ' + AvroConstants.COURSE_INSTALL_V1_TO_V2_CHANNEL )
            courseInstallDecoder.decodeCourseInstallV1ToV2( eventPayload as String, schemaId )
        }
        else if ( eventContentType == AvroConstants.COURSE_INSTALL_V1_TO_V3_CHANNEL ) {
            log.info( 'The message is sent to the ' + AvroConstants.COURSE_INSTALL_V1_TO_V3_CHANNEL )
            courseInstallDecoder.decodeCourseInstallV1ToV3( eventPayload as String, schemaId )
        }
        else if ( eventContentType == AvroConstants.LESSON_STATUS_CHANNEL ) {
            log.info( 'The message is sent to the ' + AvroConstants.LESSON_STATUS_CHANNEL )
            lessonStatusDecoder.decodeLessonInstallEvent( eventPayload as byte[], schemaId )
        }
        else {
            log.warn( "The subscribed channel ${eventContentType} was not specified in the message router. The message was dropped." )
        }
    }
}
