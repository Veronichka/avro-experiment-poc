package com.vherasymenko.avro.inbound

import com.vherasymenko.avro.core.AvroEncoderPort
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
     * Tha avro encoder.
     */
    private final AvroEncoderPort avroEncoder

    @Autowired
    RestGateway( AvroEncoderPort anAvroEncoder ) {
        avroEncoder = anAvroEncoder
    }

    @RequestMapping( path = 'user', method = [RequestMethod.POST] )
    ResponseEntity<String> fetchApplicationList( @RequestBody String reportRequest ) {
        avroEncoder.encodeEvent( reportRequest )
        ResponseEntity.ok( 'The encoded event is sent to the messaging system.' )
    }
}
