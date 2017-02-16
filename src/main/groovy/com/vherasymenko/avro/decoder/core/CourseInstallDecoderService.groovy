package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.decoder.core.json.AvroJsonDecoderPort
import event.course_install.CourseInstall

/**
 * Decoder for the course install document.
 */
class CourseInstallDecoderService implements CourseInstallDecoderPort {


    private final AvroJsonDecoderPort decoder

    CourseInstallDecoderService( AvroJsonDecoderPort aDecoder ) {
        decoder = aDecoder
    }

    @Override
    void decodeCourseInstallEvent( String event ) {
        def schema = CourseInstall.classSchema
        decoder.decodeEvent( schema, event )
    }
}
