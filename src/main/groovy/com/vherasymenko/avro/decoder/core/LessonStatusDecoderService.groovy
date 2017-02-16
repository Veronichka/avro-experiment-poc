package com.vherasymenko.avro.decoder.core

import com.vherasymenko.avro.decoder.core.binary.AvroBinaryDecoderPort
import org.apache.avro.Schema

/**
 * Decoder for the lesson status document.
 */
class LessonStatusDecoderService implements LessonStatusDecoderPort {

    private final AvroBinaryDecoderPort decoder

    LessonStatusDecoderService( AvroBinaryDecoderPort aDecoder ) {
        decoder = aDecoder
    }

    @Override
    void decodeLessonInstallEvent( byte[] event ) {
        def parser = new Schema.Parser()
        parser.parse( new File( 'src/main/avro/lessonStatusItem.avsc' ) )
        def schema = parser.parse( new File( 'src/main/avro/lesson_status.avsc' ) )
        decoder.decodeEvent( schema, event )
    }
}
