package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.encoder.core.CourseInstallEncoderPort
import com.vherasymenko.avro.encoder.core.LessonStatusEncoderPort
import com.vherasymenko.avro.schared.AvroConstants
import com.vherasymenko.avro.schared.RestConstants
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Inbound HTTP gateway.
 */
@Slf4j
@RestController
@RequestMapping( RestConstants.REPORT )
class RestGateway {

    /**
     * The handler for the course install event.
     */
    private final CourseInstallEncoderPort courseInstallEncoder

    /**
     * The handler for the lesson status event.
     */
    private final LessonStatusEncoderPort lessonInstallEncoder

    @Autowired
    RestGateway(CourseInstallEncoderPort aCourseInstallEncoder, LessonStatusEncoderPort aLessonInstallEncoder ) {
        courseInstallEncoder = aCourseInstallEncoder
        lessonInstallEncoder = aLessonInstallEncoder
    }

    @RequestMapping( path = RestConstants.COURSE_INSTALL_V1_TO_V1, method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstallV1ToV1( @RequestBody String requestEvent ) {
        log.info( "Received POST request to the ${RestConstants.COURSE_INSTALL_V1_TO_V1} with content: " + requestEvent )
        courseInstallEncoder.handleEvent( requestEvent, AvroConstants.COURSE_INSTALL_V1_TO_V1_CHANNEL )
        ResponseEntity.ok( 'The encoded course install event is sent to the messaging system.' )
    }

    @RequestMapping( path = RestConstants.COURSE_INSTALL_V1_TO_V2, method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstallV1ToV2( @RequestBody String requestEvent ) {
        log.info( "Received POST request to the ${RestConstants.COURSE_INSTALL_V1_TO_V2} with content: " + requestEvent )
        courseInstallEncoder.handleEvent( requestEvent, AvroConstants.COURSE_INSTALL_V1_TO_V2_CHANNEL )
        ResponseEntity.ok( 'The encoded course install event is sent to the messaging system.' )
    }

    @RequestMapping( path = RestConstants.COURSE_INSTAL_V1_TO_V3, method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstallV1ToV3( @RequestBody String requestEvent ) {
        log.info( "Received POST request to the ${RestConstants.COURSE_INSTAL_V1_TO_V3} with content: " + requestEvent )
        courseInstallEncoder.handleEvent( requestEvent, AvroConstants.COURSE_INSTALL_V1_TO_V3_CHANNEL )
        ResponseEntity.ok( 'The encoded course install event is sent to the messaging system.' )
    }

    @RequestMapping( path = RestConstants.LESSON_STATUS, method = [RequestMethod.POST] )
    ResponseEntity<String> serializeLessonInstall( @RequestBody String requestEvent ) {
        log.info( 'Received POST request to status/lesson with content: ' + requestEvent )
        lessonInstallEncoder.handleEvent( requestEvent )
        ResponseEntity.ok( 'The encoded lesson install event is sent to the messaging system.' )
    }
}
