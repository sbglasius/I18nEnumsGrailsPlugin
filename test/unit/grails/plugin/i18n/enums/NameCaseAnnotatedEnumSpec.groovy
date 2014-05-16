package grails.plugin.i18n.enums

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class NameCaseAnnotatedEnumSpec extends EnumSpecification {

	def theEnumTpl = newEnum().with {
        values = ['ONE', 'two', 'Three', 'FOUR_FIVE']
        it
    }

	@Unroll
	def "test that the default annotated enum default message returns correct values"() {

		when:
		def clazz = theEnumTpl.with {
            nameCase = DefaultNameCase.valueOf(enumName)
            it
        }.newClazz()

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
		'ALL_CAPS'   | 'One' | 'Two' | 'Three' | 'Four Five'
	}
}

