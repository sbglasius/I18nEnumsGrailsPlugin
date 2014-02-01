package grails.plugin.i18nEnums

import org.codehaus.groovy.grails.compiler.i18nEnum.I18nEnumTransformer
import org.codehaus.groovy.grails.compiler.injection.ClassInjector
import org.codehaus.groovy.grails.compiler.injection.GrailsAwareClassLoader
import spock.lang.Specification
import spock.lang.Unroll

class NameCaseAnnotatedEnumSpec extends Specification {
    GrailsAwareClassLoader gcl
    def setup() {
        gcl = new GrailsAwareClassLoader()
        def transformer = new I18nEnumTransformer()
        gcl.classInjectors = [transformer] as ClassInjector[]
    }

	def source ={nameCase ->
        """
				package dk.glasius
				import grails.plugin.i18nEnums.annotations.I18nEnum
				import grails.plugin.i18nEnums.transformation.DefaultNameCase

				@I18nEnum(defaultNameCase = DefaultNameCase.${nameCase})
				enum NameCasedAnnotatedEnum {
					ONE,
					two,
					Three,
					FOUR_FIVE
				}
			""".stripIndent()
    }


	@Unroll
	def "test that the default annotated enum default message returns correct values"() {

		when:
		def clazz = gcl.parseClass(source(enumName))

		then:
		clazz.ONE.defaultMessage == one
		clazz.two.defaultMessage == two
		clazz.Three.defaultMessage == three
		clazz.FOUR_FIVE.defaultMessage == four

		where:
		enumName     | one   | two   | three   | four
		'UNCHANGED'  | 'ONE' | 'two' | 'Three' | 'FOUR_FIVE'
		'UPPER_CASE' | 'ONE' | 'TWO' | 'THREE' | 'FOUR_FIVE'
		'LOWER_CASE' | 'one' | 'two' | 'three' | 'four_five'
		'CAPITALIZE' | 'One' | 'Two' | 'Three' | 'Four_five'
		'ALL_CAPS' | 'One' | 'Two' | 'Three' | 'Four Five'
	}
}

