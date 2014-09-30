package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class PrefixEnumSpec extends Specification {
    def "test if PostfixEnum implements MessageSourceResolvable"() {
        expect:
        PostfixEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that PostfixEnum getCodes() return the correct values"() {
        expect:
        PostfixEnum.ONE.codes == ["test.PostfixEnum.ONE.postfix", "test.PostfixEnum.ONE.postfix", "test.PostfixEnum.one.postfix"]
    }

    def "test that PostfixEnum getDefault() returns the correct value"(){
        expect:
        PostfixEnum.ONE.defaultMessage == "ONE"
    }
}
