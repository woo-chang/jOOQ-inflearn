package com.example.jooq

import org.jooq.DSLContext
import org.jooq.generated.tables.JActor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class FirstLookjOOQTest {
    @Autowired
    private lateinit var dslContext: DSLContext

    @Test
    fun test() {
        dslContext.selectFrom(JActor.ACTOR)
            .limit(10)
            .fetch()
    }
}
