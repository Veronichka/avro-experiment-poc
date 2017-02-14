package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.MessageHeaders
import org.springframework.messaging.Message

/**
 * Routes messages from messaging system to different decoders.
 */
class MessageRouter implements MessageRouterPort {

    private final CourseInstallDecoderPort courseInstallDecoder

    MessageRouter( CourseInstallDecoderPort aCourseInstallDecoder ) {
        courseInstallDecoder = aCourseInstallDecoder
    }

    @Override
    void routeEvent( Message eventMessage ) {
        def eventContentType = eventMessage.headers.get( MessageHeaders.CONTENT_TYPE )
        def eventPayload = eventMessage.payload as String

        if ( eventContentType == AvroConstants.COURSE_INSTALL_CHANNEL ) {
            courseInstallDecoder.decodeCourseInstallEvent( eventPayload )
        }
//        else if ( eventContentType == AvroConstants.USER_CREATE_CHANNEL ) {
//
//        }
//        else if ( eventContentType == AvroConstants.LESSON_INSTALL_CHANNEL ) {
//
//        }
    }
}
