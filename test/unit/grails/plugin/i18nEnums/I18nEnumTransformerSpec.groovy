package grails.plugin.i18nEnums
import org.codehaus.groovy.grails.compiler.i18nEnum.I18nEnumTransformer
import org.codehaus.groovy.grails.compiler.injection.ClassInjector
import org.codehaus.groovy.grails.compiler.injection.GrailsAwareClassLoader
import org.springframework.context.MessageSourceResolvable
import spock.lang.Specification

class I18nEnumTransformerSpec extends Specification{

    GrailsAwareClassLoader gcl
    def setup() {
        gcl = new GrailsAwareClassLoader()
        def transformer = new I18nEnumTransformer()
        gcl.classInjectors = [transformer] as ClassInjector[]
    }

    def "Test that transformer adds interface and methods to enum and that the methods return the expected output"() {
        when:

        def sut = gcl.parseClass("""
        				import grails.plugin.i18nEnums.annotations.I18nEnum

        				@I18nEnum
        				enum TestEnum {
        					ONE,
        					Two,
        					three
        				}
        			""")

        then:
        sut.ONE instanceof MessageSourceResolvable

        and:
        sut.ONE.codes == ['TestEnum.ONE', 'TestEnum.ONE', 'TestEnum.one']
        sut.ONE.arguments == []
        sut.ONE.defaultMessage == 'ONE'

        and:
        sut.Two.codes == ['TestEnum.TWO', 'TestEnum.Two', 'TestEnum.two']
        sut.Two.arguments == []
        sut.Two.defaultMessage == 'Two'

        and:
        sut.three.codes == ['TestEnum.THREE', 'TestEnum.three', 'TestEnum.three']
        sut.three.arguments == []
        sut.three.defaultMessage == 'three'
    }

    def "Test that transformer adds interface and methods to enum in a package and that the methods return the expected output"() {
        when:

        def sut = gcl.parseClass("""
        				package test
        				import grails.plugin.i18nEnums.annotations.I18nEnum

        				@I18nEnum
        				enum TestEnum {
        					ONE,
        					Two,
        					three
        				}
        			""")

        then:
        sut.ONE instanceof MessageSourceResolvable

        and:
        sut.ONE.codes == ['test.TestEnum.ONE', 'test.TestEnum.ONE', 'test.TestEnum.one']
        sut.ONE.arguments == []
        sut.ONE.defaultMessage == 'ONE'

        and:
        sut.Two.codes == ['test.TestEnum.TWO', 'test.TestEnum.Two', 'test.TestEnum.two']
        sut.Two.arguments == []
        sut.Two.defaultMessage == 'Two'

        and:
        sut.three.codes == ['test.TestEnum.THREE', 'test.TestEnum.three', 'test.TestEnum.three']
        sut.three.arguments == []
        sut.three.defaultMessage == 'three'
    }
}
