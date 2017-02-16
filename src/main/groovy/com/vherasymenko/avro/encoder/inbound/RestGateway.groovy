package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.encoder.core.CourseInstallPort
import com.vherasymenko.avro.encoder.core.LessonStatusPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Inbound HTTP gateway.
 */
@RestController
@RequestMapping( '/' )
class RestGateway {

    /**
     * The handler for the course install event.
     */
    private final CourseInstallPort courseInstallEncoder

    /**
     * The handler for the lesson status event.
     */
    private final LessonStatusPort lessonInstallEncoder

    @Autowired
    RestGateway(CourseInstallPort aCourseInstallEncoder, LessonStatusPort aLessonInstallEncoder ) {
        courseInstallEncoder = aCourseInstallEncoder
        lessonInstallEncoder = aLessonInstallEncoder
    }

    @RequestMapping( path = 'course/install', method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstall( @RequestBody String requestEvent ) {
        courseInstallEncoder.handleEvent( requestEvent )
        ResponseEntity.ok( 'The encoded course install event is sent to the messaging system.' )
    }

    @RequestMapping( path = 'lesson/status', method = [RequestMethod.POST] )
    ResponseEntity<String> serializeLessonInstall( @RequestBody String requestEvent ) {
        lessonInstallEncoder.handleEvent( requestEvent )
        ResponseEntity.ok( 'The encoded lesson install event is sent to the messaging system.' )
    }
}
