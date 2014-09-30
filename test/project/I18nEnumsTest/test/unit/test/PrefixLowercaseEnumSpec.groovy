package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class PrefixLowercaseEnumSpec extends Specification {
    def "test if PrefixLowercaseEnum implements MessageSourceResolvable"() {
        expect:
        PrefixLowercaseEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that PrefixLowercaseEnum getCodes() return the correct values"() {
        expect:
        PrefixLowercaseEnum.ONE.codes == ["test.PrefixLowercaseEnum.ONE.postfix", "test.PrefixLowercaseEnum.ONE.postfix", "test.PrefixLowercaseEnum.one.postfix"]
    }

    def "test that PrefixLowercaseEnum getDefault() returns the correct value"(){
        expect:
        PrefixLowercaseEnum.ONE.defaultMessage == "one"
    }
}
