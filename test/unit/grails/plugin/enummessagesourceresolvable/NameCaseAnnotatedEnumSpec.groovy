package grails.plugin.enummessagesourceresolvable

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class NameCaseAnnotatedEnumSpec extends Specification {

	def source = '''
				package dk.glasius
				import grails.plugin.enummessagesourceresolvable.annotations.EnumMessageSourceResolvable
				import grails.plugin.enummessagesourceresolvable.transformation.DefaultNameCase

				@EnumMessageSourceResolvable(defaultNameCase = DefaultNameCase.${nameCase})
				enum NameCasedAnnotatedEnum {
					ONE,
					two,
					Three,
					FOUR_FIVE
				}
			'''


	@Unroll
	def "test that the default annotated enum default message returns correct values"() {

		when:
		def clazz = add_class_to_classpath(createSourceCodeForTemplate(source, [nameCase: enumName]))

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

