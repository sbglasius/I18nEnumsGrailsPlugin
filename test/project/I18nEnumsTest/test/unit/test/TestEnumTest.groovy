package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class TestEnumTest extends Specification {
    def "test if TestEnum implements MessageSourceResolvable"() {
        expect: TestEnum.ONE instanceof MessageSourceResolvable
    }
}
