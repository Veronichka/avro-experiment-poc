package com.vherasymenko.avro.core

import com.vherasymenko.avro.outbound.MessageProducer

import event.avro.User
import groovy.json.JsonSlurper
import org.apache.avro.Schema
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter

/**
 * The avro encoder.
 */
class AvroEncoderService implements AvroEncoderPort {

    /**
     * The gateway to the messaging system.
     */
    private final MessageProducer producer

    AvroEncoderService(MessageProducer aProducer ) {
        producer = aProducer
    }

    @Override
    void encodeEvent( String event ) {
        Schema schema = new Schema.Parser().parse( new File( 'src/main/avro/user.avsc' ) )

        def jsonParser = new JsonSlurper()
        def jsonData = jsonParser.parseText( event )

        // map the request data to the schema generated avro object
        def avroData = new User()
        avroData.setName( jsonData.name )
        avroData.setFavoriteNumber( jsonData.favoriteNumber )
        avroData.setFavoriteColor( jsonData.favoriteColor )

        def datumWriter = new SpecificDatumWriter( User.class )

        def output = new ByteArrayOutputStream()
        def encoder = EncoderFactory.get().jsonEncoder( schema, output, true )

        datumWriter.write( avroData, encoder )
        encoder.flush()

        def encodedJson = new String( output.toByteArray() )
        encodedJson
        producer.sendMessage( encodedJson )
    }
}
