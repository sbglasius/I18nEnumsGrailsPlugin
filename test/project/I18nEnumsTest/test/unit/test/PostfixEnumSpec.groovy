package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class PostfixEnumSpec extends Specification {
    def "test if PrefixEnum implements MessageSourceResolvable"() {
        expect:
        PrefixEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that PrefixEnum getCodes() return the correct values"() {
        expect:
        PrefixEnum.ONE.codes == ["prefix.test.PrefixEnum.ONE", "prefix.test.PrefixEnum.ONE", "prefix.test.PrefixEnum.one"]
    }

    def "test that PrefixEnum getDefault() returns the correct value"(){
        expect:
        PrefixEnum.ONE.defaultMessage == "ONE"
    }
}
