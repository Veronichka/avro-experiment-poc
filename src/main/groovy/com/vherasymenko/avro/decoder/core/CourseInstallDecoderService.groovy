package com.vherasymenko.avro.decoder.core

import event.course_install.CourseInstall
import org.springframework.beans.factory.annotation.Autowired

/**
 * Decoder for the course install document.
 */
class CourseInstallDecoderService implements CourseInstallDecoderPort {

    @Autowired
    AvroDecoderPort decoder

    CourseInstallDecoderService( AvroDecoderPort aDecoder ) {
        decoder = aDecoder
    }

    @Override
    void decodeCourseInstallEvent( String event ) {
        def schema = CourseInstall.classSchema
        decoder.decodeEvent( schema, event )
    }
}
