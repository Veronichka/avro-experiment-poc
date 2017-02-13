
package com.vherasymenko.avro.encoder.inbound

import com.vherasymenko.avro.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration

/**
 * Step definitions geared towards Enchantment's acceptance test but remember, all steps are used
 * by Cucumber unless special care is taken. If you word your features in a consistent manner, then
 * steps will automatically get reused and you won't have to keep writing the same test code.
 */
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = [Application, AcceptanceTestConfiguration], loader = SpringApplicationContextLoader )
class TestSteps {

    /**
     * This is state shared between steps and can be setup and torn down by the hooks.
     */
    class MyWorld { }

    /**
     * Shared between hooks and steps.
     */
    MyWorld sharedState
}
