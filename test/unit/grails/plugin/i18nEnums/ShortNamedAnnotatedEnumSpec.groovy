package grails.plugin.i18nEnums

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class ShortNamedAnnotatedEnumSpec extends Specification {

	def source = """
				package dk.glasius
				import grails.plugin.i18nEnums.annotations.I18nEnum
				import grails.plugin.i18nEnums.transformation.DefaultNameCase

				@I18nEnum(shortName = true)
				enum ShortNamedAnnotatedEnum {
					ONE,
					two,
					Three,
					FOUR_Five
				}
			"""


	@Unroll
	def "test that the default annotated enum default message returns correct values"() {

		when:
		def clazz = add_class_to_classpath(source)

		then:
		clazz.ONE.codes == ['ShortNamedAnnotatedEnum.ONE', 'ShortNamedAnnotatedEnum.ONE', 'ShortNamedAnnotatedEnum.one']
		clazz.two.codes == ['ShortNamedAnnotatedEnum.TWO', 'ShortNamedAnnotatedEnum.two', 'ShortNamedAnnotatedEnum.two']
		clazz.Three.codes == ['ShortNamedAnnotatedEnum.THREE', '''ShortNamedAnnotatedEnum.Three''', 'ShortNamedAnnotatedEnum.three']
		clazz.FOUR_Five.codes == ['ShortNamedAnnotatedEnum.FOUR_FIVE', 'ShortNamedAnnotatedEnum.FOUR_Five', 'ShortNamedAnnotatedEnum.four_five']
	}
}
