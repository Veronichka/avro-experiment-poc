package com.vherasymenko.avro

import com.vherasymenko.avro.schemaLoader.AvroSchemasLoaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import spock.lang.Ignore
import spock.lang.Specification

/**
 * The integration test for the AvroSchemasScanService.
 */
class AvroSchemasScanServiceIntegrationTest extends Specification {

    @Autowired
    SchemaRegistryClient registryClient

    @Ignore
    def 'exercise application schemas scanning and registering to the schema registry'() {
        given: 'valid subject under test'
        def resolver = new PathMatchingResourcePatternResolver()
        def sut = new AvroSchemasLoaderService( resolver, registryClient )

        when: 'the exercised method is called'
        sut.findAndRegisterSchemas( '/**/v100/*.avsc' )

        then:
        true
    }
}
