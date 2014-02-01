package grails.plugin.i18nEnums

import org.codehaus.groovy.grails.compiler.i18nEnum.I18nEnumTransformer
import org.codehaus.groovy.grails.compiler.injection.ClassInjector
import org.codehaus.groovy.grails.compiler.injection.GrailsAwareClassLoader
import spock.lang.Specification

class AlreadyImplementedMethodAnnotatedEnumSpec extends Specification {

    GrailsAwareClassLoader gcl
    def setup() {
        gcl = new GrailsAwareClassLoader()
        def transformer = new I18nEnumTransformer()
        gcl.classInjectors = [transformer] as ClassInjector[]
    }

	def source = {"""
            import grails.plugin.i18nEnums.annotations.I18nEnum
            // import org.springframework.context.MessageSourceResolvable

            @I18nEnum
            public enum Test {
                ONE,
                two,
                Three

                // Where the method goes
                ${it}

            }
			""".stripIndent()}


	def "test that if the getCodes is already implemented, it is not being implemented again"() {

		when:
		def clazz = gcl.parseClass(source("""
                @Override
                String[] getCodes() {
                    return ['some.other.code'] as String[]
                }"""))

		then:
		clazz.ONE.codes == ['some.other.code']
		clazz.two.codes == ['some.other.code']
		clazz.Three.codes == ['some.other.code']
	}

    def "test that if the getArguments is already implemented, it is not being implemented again"() {

		when:
		def clazz = gcl.parseClass(source("""
                @Override
                Object[] getArguments() {
                    return [1,'A'] as Object[]
                }"""))

		then:
		clazz.ONE.arguments == [1,'A']
		clazz.two.arguments == [1,'A']
		clazz.Three.arguments == [1,'A']
	}

    def "test that if the getDefaultMessage is already implemented, it is not being implemented again"() {

		when:
		def clazz = gcl.parseClass(source("""
                @Override
                String getDefaultMessage() {
                    return "Another default message"
                }"""))

		then:
		clazz.ONE.defaultMessage == "Another default message"
		clazz.two.defaultMessage == "Another default message"
		clazz.Three.defaultMessage == "Another default message"
	}
}
