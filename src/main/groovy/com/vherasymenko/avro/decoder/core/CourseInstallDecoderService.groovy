package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.decoder.core.json.AvroJsonDecoderPort
import event.course_install.CourseInstall
import groovy.util.logging.Slf4j
import org.apache.avro.Schema

/**
 * Decoder for the course install document.
 */
@Slf4j
class CourseInstallDecoderService implements CourseInstallDecoderPort {

    private final AvroJsonDecoderPort decoder

    CourseInstallDecoderService( AvroJsonDecoderPort aDecoder ) {
        decoder = aDecoder
    }

    @Override
    void decodeCourseInstallEvent( String event ) {
        def schema = CourseInstall.classSchema
        def newSchema = new Schema.Parser().parse( new File( 'src/main/resources/avro/course_install_2.avsc' ) )
        log.info( 'The avro schema for event decoding : ' + newSchema.toString( true ) )

        decoder.decodeEvent( schema, newSchema, event )
    }
}
