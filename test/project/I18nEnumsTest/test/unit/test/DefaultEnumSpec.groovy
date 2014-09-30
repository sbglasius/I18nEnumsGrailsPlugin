package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class DefaultEnumSpec extends Specification {
    def "test if DefaultEnum implements MessageSourceResolvable"() {
        expect:
        DefaultEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that DefaultEnum getCodes() return the correct values"() {
        expect:
        DefaultEnum.ONE.codes == ["test.DefaultEnum.ONE", "test.DefaultEnum.ONE", "test.DefaultEnum.one"]
    }

    def "test that DefaultEnum getDefault() returns the correct value"(){
        expect:
        DefaultEnum.ONE.defaultMessage == "ONE"
    }
}
