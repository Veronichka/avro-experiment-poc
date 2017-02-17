package com.vherasymenko.avro.encoder.core

import com.vherasymenko.avro.encoder.core.json.AvroJsonEncoderPort
import com.vherasymenko.avro.encoder.outbound.MessageProducer
import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.MessageHeaders
import event.course_install.Course
import event.course_install.CourseInstall
import event.course_install.Lesson
import event.course_install.Unit
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.messaging.support.MessageBuilder

/**
 * Service that encodes course install document.
 */
@Slf4j
class CourseInstallService implements CourseInstallPort {

    /**
     * The gateway to the messaging system.
     */
    private final MessageProducer producer

    /**
     * The avro json encoder.
     */
    private final AvroJsonEncoderPort encoder

    CourseInstallService( MessageProducer aProducer, AvroJsonEncoderPort anEncoder ) {
        producer = aProducer
        encoder = anEncoder
    }

    @Override
    void handleEvent( String event ) {
        def schema = CourseInstall.classSchema
        log.info( 'The avro schema for event encoding : ' + schema.toString( true ) )

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

        // encode document using avro json encoding
        def encodedDocument = encoder.encodeEvent( schema, avroData, CourseInstall.class )

        // send encoded document to the messaging system
        def message = MessageBuilder.withPayload( encodedDocument )
                .setHeader( MessageHeaders.CONTENT_TYPE,  AvroConstants.COURSE_INSTALL_CHANNEL )
                .build()
        producer.sendMessage( message )
    }
}
