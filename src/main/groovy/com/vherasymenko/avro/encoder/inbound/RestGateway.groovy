package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.encoder.core.CourseInstallEncoderPort
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
     * The encoder for the course install document.
     */
    private final CourseInstallEncoderPort courseInstallEncoder

    @Autowired
    RestGateway( CourseInstallEncoderPort aCourseInstallEncoder ) {
        courseInstallEncoder = aCourseInstallEncoder
    }

    @RequestMapping( path = 'course/install', method = [RequestMethod.POST] )
    ResponseEntity<String> serializeCourseInstall( @RequestBody String requestEvent ) {
        courseInstallEncoder.encodeEvent( requestEvent )
        ResponseEntity.ok( 'The encoded event is sent to the messaging system.' )
    }
}
