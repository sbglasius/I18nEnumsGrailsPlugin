package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class PostfixAllCapsEnumSpec extends Specification {
    def "test if PostfixAllCapsEnum implements MessageSourceResolvable"() {
        expect:
        PostfixAllCapsEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that PostfixAllCapsEnum getCodes() return the correct values"() {
        expect:
        PostfixAllCapsEnum.ONE.codes == ["prefix.test.PostfixAllCapsEnum.ONE", "prefix.test.PostfixAllCapsEnum.ONE", "prefix.test.PostfixAllCapsEnum.one"]
    }

    def "test that PostfixAllCapsEnum getDefault() returns the correct value"(){
        expect:
        PostfixAllCapsEnum.THREE_OR_MORE.defaultMessage == "Three Or More"
    }
}
