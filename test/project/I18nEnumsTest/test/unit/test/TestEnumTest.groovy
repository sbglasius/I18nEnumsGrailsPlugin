package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class TestEnumTest extends Specification {
    def "test if TestEnum implements MessageSourceResolvable"() {
        expect:
        TestEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that TestEnum getCodes() return the correct values"() {
        expect:
        TestEnum.ONE.codes == ["test.TestEnum.ONE", "test.TestEnum.ONE", "test.TestEnum.one"]
    }

    def "test that TestEnum getDefault() returns the correct value"(){
        expect:
        TestEnum.ONE.defaultMessage == "One"
    }
}
