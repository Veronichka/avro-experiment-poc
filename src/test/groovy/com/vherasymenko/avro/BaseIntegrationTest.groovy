package com.vherasymenko.avro

import com.vherasymenko.avro.spring.DynamicPortListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestOperations
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

/**
 * Base configurations for the test side.
 */
@SpringBootTest( webEnvironment = RANDOM_PORT )
@ContextConfiguration( classes = Application, loader = SpringBootContextLoader )
class BaseIntegrationTest extends Specification {

    @Autowired
    RestOperations restOperations

    @Autowired
    RabbitTemplate template

    @Autowired
    DynamicPortListener portListener
}
