package com.vherasymenko.avro.encoder.core

import com.vherasymenko.avro.encoder.outbound.MessageProducer
import event.course_install.Course
import event.course_install.CourseInstall
import event.course_install.Lesson
import event.course_install.Unit
import groovy.json.JsonSlurper

/**
 * Service that encodes course install document.
 */
class CourseInstallEncoderService implements CourseInstallEncoderPort {

    /**
     * The gateway to the messaging system.
     */
    private final MessageProducer producer

    /**
     * The avro json encoder.
     */
    private final AvroEncoderPort encoder

    CourseInstallEncoderService( MessageProducer aProducer, AvroEncoderPort anEncoder ) {
        producer = aProducer
        encoder = anEncoder
    }

    @Override
    void encodeEvent( String event ) {
        def schema = CourseInstall.classSchema

        def jsonParser = new JsonSlurper()
        def jsonData = jsonParser.parseText( event )

        // map the request data to the schema generated avro object
        def avroData = new CourseInstall()
        def course = new Course()
        course.setUuid( jsonData.course.uuid )
        course.setName( jsonData.course.name )
        course.setKnownLanguage( jsonData.course.knownLanguage )
        course.setTargetLanguage( jsonData.course.targetLanguage )
        course.setProduct( jsonData.course.product )

        def units = jsonData.units.collect { item ->
            def lessons = item.lessons.collect { lessonItem ->
                new Lesson().with {
                    it.setId( lessonItem.id )
                    it.setUuid( lessonItem.uuid )
                    it.setName( lessonItem.name )
                    it.setPosition( lessonItem.position )
                    it.setIsAssessment( lessonItem.isAssessment )
                    it
                }
            }
            new Unit().with {
                it.setId( item.id )
                it.setUuid( item.uuid )
                it.setName( item.name )
                it.setMaxScore( item.maxScore )
                it.setMinScore( item.minScore )
                it.setHasAssessment( item.hasAssessment )
                it.setLessons( lessons )
                it
            }
        }

        avroData.setCourse( course )
        avroData.setUnits( units )

        // encode document using avro
        def encodedDocument = encoder.encodeEvent( schema, avroData, CourseInstall.class )

        // send encoded document to the messaging system
        producer.sendMessage( encodedDocument )
    }
}
