package com.vherasymenko.avro.schemaLoader

import groovy.util.logging.Slf4j
import org.apache.avro.Schema
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient
import org.springframework.core.io.support.ResourcePatternResolver

import javax.annotation.PostConstruct

/**
 * Scans the application for avro schemas and registers them in the schema registry.
 */
@Slf4j
class AvroSchemasLoaderService implements AvroSchemasLoaderPort {

    /**
     * The list with the schema locations per versions. Keep this in version increasing order.
     */
    public static final List<String> schemasLocations = ['/**/avro/v100/*.avsc', '/**/avro/v200/*.avsc']

    private final ResourcePatternResolver resolver

    private final SchemaRegistryClient registryClient

    AvroSchemasLoaderService( ResourcePatternResolver aResolver, SchemaRegistryClient aRegistryClient ) {
        resolver = aResolver
        registryClient = aRegistryClient
    }

    @PostConstruct
    void firstMethod() {
        scanApplicationForSchemas( schemasLocations )
    }

    void scanApplicationForSchemas( List<String> schemasLocations ) {
        schemasLocations.each { path ->
            findAndRegisterSchemas( path )
        }
    }

    void findAndRegisterSchemas( String schemaLocation ) {
        log.info( "Scanning avro schema resources on classpath ${schemaLocation}." )
        def parser = new Schema.Parser()
        try {
            def resources = resolver.getResources( "classpath*:${schemaLocation}" )
            log.info( "Found ${resources.length} schemas on classpath" )
            resources.each { r ->
                //we parse all schemas using one parser to parse nested schemas right because schema registry does not support this
                def s = parser.parse( r.getInputStream() )
                log.info( "Resource ${r.getFilename()} parsed into schema ${s.namespace}.${s.name}" )
            }
        }
        catch ( IOException e ) {
            e.printStackTrace()
        }
        // we do this checking to not save to the schema registry all enums and subclasses
        def schemas = parser.getTypes().findAll { namespace, schema -> namespace in SchemasNamespaceRegistry.values().fullNamespace }
        schemas.each { namespace, Schema schema ->
            def id = registryClient.register( schema.name, 'avro', schema.toString() ).id
            log.info( "Schema ${schema.name} registered with id ${id}" )
        }
    }
}
