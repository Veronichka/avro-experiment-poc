package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.MessageHeaders
import org.springframework.messaging.Message

/**
 * Routes messages from messaging system to different decoders.
 */
class MessageRouter implements MessageRouterPort {

    private final CourseInstallDecoderPort courseInstallDecoder

    private final LessonStatusDecoderPort lessonStatusDecoder

    MessageRouter( CourseInstallDecoderPort aCourseInstallDecoder, LessonStatusDecoderPort aLessonStatusDecoder ) {
        courseInstallDecoder = aCourseInstallDecoder
        lessonStatusDecoder = aLessonStatusDecoder
    }

    @Override
    void routeEvent( Message eventMessage ) {
        def eventContentType = eventMessage.headers.get( MessageHeaders.CONTENT_TYPE )
        def eventPayload = eventMessage.payload

        if ( eventContentType == AvroConstants.COURSE_INSTALL_CHANNEL ) {
            courseInstallDecoder.decodeCourseInstallEvent( eventPayload as String )
        }
        else if ( eventContentType == AvroConstants.LESSON_STATUS_CHANNEL ) {
            lessonStatusDecoder.decodeLessonInstallEvent( eventPayload as byte[] )
        }
    }
}
