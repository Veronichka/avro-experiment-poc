package com.vherasymenko.avro.decoder.core

import event.course_install.CourseInstall
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DecoderFactory

/**
 * The avro decoder.
 */
class AvroDecoderService implements AvroDecoderPort {

    @Override
    void decodeEvent( String event ) {
        def inputStream = new ByteArrayInputStream( event.bytes )
        def schema = CourseInstall.classSchema

        def decoder = new DecoderFactory().jsonDecoder( schema, inputStream )
        DatumReader<GenericRecord> reader = new GenericDatumReader<>( schema )
        def document = reader.read( null, decoder )
        println( 'Decoded document: ' + document )
        document
    }
}
