package com.vherasymenko.avro.encoder.core

import com.vherasymenko.avro.encoder.core.binary.AvroBinaryEncoderPort
import com.vherasymenko.avro.encoder.outbound.MessageProducer
import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.MessageHeaders
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.springframework.messaging.support.MessageBuilder

/**
 * Service that encodes lesson status document. Also uses GenericRecord that works without code generation.
 * This service uses nested schemas as well.
 */
@Slf4j
class LessonStatusEncoderService implements LessonStatusEncoderPort {

    /**
     * The gateway to the messaging system.
     */
    private final MessageProducer producer

    /**
     * The binary encoder.
     */
    private final AvroBinaryEncoderPort encoder

    LessonStatusEncoderService(MessageProducer aProducer, AvroBinaryEncoderPort anEncoder ) {
        producer = aProducer
        encoder = anEncoder
    }

    @Override
    void handleEvent( String event ) {
        def parser = new Schema.Parser()
        def innerSchema = parser.parse( new File( 'src/main/avro/lessonStatusItem.avsc' ) )
        def mainSchema = parser.parse( new File( 'src/main/avro/lesson_status.avsc' ) )
        log.info( 'The avro schema for event encoding : ' + mainSchema.toString( true ) )

        def jsonParser = new JsonSlurper()
        def jsonData = jsonParser.parseText( event )

        def statusList = jsonData.status.collect { statusItem ->
            GenericRecord lessonStatusItem = new GenericData.Record( innerSchema )
            lessonStatusItem.put( 'userUuid', statusItem.userUuid )
            lessonStatusItem.put( 'courseUuid', statusItem.courseUuid )
            lessonStatusItem.put( 'unitId', statusItem.unitId )
            lessonStatusItem.put( 'lessonUuid', statusItem.lessonUuid )
            lessonStatusItem.put( 'completionStatus',
                    new GenericData.EnumSymbol( innerSchema.getField( 'completionStatus' ).schema(), statusItem.completionStatus as String ) )
            lessonStatusItem.put( 'score', statusItem.score )
            lessonStatusItem.put( 'time', statusItem.time )
            lessonStatusItem.put( 'markedLearned', statusItem.markedLearned )
            lessonStatusItem.put( 'testedOut', statusItem.testedOut )
            lessonStatusItem.put( 'unitAssessmentStatus',
                    new GenericData.EnumSymbol( innerSchema.getField( 'unitAssessmentStatus' ).schema().getTypes().get( 1 ), statusItem.unitAssessmentStatus as String ) )
            lessonStatusItem.put( 'unitAssessmentScore', statusItem.unitAssessmentScore )
            lessonStatusItem
        }

        GenericRecord lessonStatus = new GenericData.Record( mainSchema )
        lessonStatus.put( 'agent', new GenericData.EnumSymbol( mainSchema.getField( 'agent' ).schema(), jsonData.agent as String ) )

        lessonStatus.put( 'status', statusList )

        // encode document using avro binary encoding
        def encodedDocument = encoder.encodeEvent( mainSchema, lessonStatus )

        // send encoded document to the messaging system
        def message = MessageBuilder.withPayload( encodedDocument )
                .setHeader( MessageHeaders.CONTENT_TYPE, AvroConstants.LESSON_STATUS_CHANNEL )
                .build()
        producer.sendMessage( message )
    }
}
