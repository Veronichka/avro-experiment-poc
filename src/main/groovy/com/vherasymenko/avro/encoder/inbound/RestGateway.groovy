package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.encoder.core.CourseInstallEncoderPort
import com.vherasymenko.avro.encoder.core.LessonStatusEncoderPort
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
@RequestMapping( '/' )
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

    @RequestMapping( path = 'course/install', method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstall( @RequestBody String requestEvent ) {
        log.info( 'Received POST request to course/install with content: ' + requestEvent )
        courseInstallEncoder.handleEvent( requestEvent )
        ResponseEntity.ok( 'The encoded course install event is sent to the messaging system.' )
    }

    @RequestMapping( path = 'lesson/status', method = [RequestMethod.POST] )
    ResponseEntity<String> serializeLessonInstall( @RequestBody String requestEvent ) {
        log.info( 'Received POST request to status/lesson with content: ' + requestEvent )
        lessonInstallEncoder.handleEvent( requestEvent )
        ResponseEntity.ok( 'The encoded lesson install event is sent to the messaging system.' )
    }
}
