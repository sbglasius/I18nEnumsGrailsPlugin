package test

import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification


class CapitalizeEnumSpec extends Specification {
    def "test if CapitalizeEnum implements MessageSourceResolvable"() {
        expect:
        CapitalizeEnum.ONE instanceof MessageSourceResolvable
    }

    def "test that CapitalizeEnum getCodes() return the correct values"() {
        expect:
        CapitalizeEnum.ONE.codes == ["test.CapitalizeEnum.ONE", "test.CapitalizeEnum.ONE", "test.CapitalizeEnum.one"]
    }

    def "test that CapitalizeEnum getDefault() returns the correct value"(){
        expect:
        CapitalizeEnum.ONE.defaultMessage == "One"
    }
}
