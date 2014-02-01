package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification

class TestEnumSpec extends Specification {

    def "test that enums annotated are decorated with correct interface"() {
        expect:
        TestEnum.ONE instanceof MessageSourceResolvable
    }
}
