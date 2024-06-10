package jooq.custom.generator

import org.jooq.codegen.DefaultGeneratorStrategy
import org.jooq.codegen.GeneratorStrategy.Mode
import org.jooq.meta.Definition

class JPrefixGeneratorStrategy : DefaultGeneratorStrategy() {
    override fun getJavaClassName(definition: Definition?, mode: Mode?): String {
        if (mode == Mode.DEFAULT) {
            return "J" + super.getJavaClassName(definition, mode)
            // 강의자는 아래와 같이 _를 붙이는 방식을 사용
            // return super.getJavaClassName(definition, mode) + "_"
        }
        return super.getJavaClassName(definition, mode)
    }
}
