package dk.glasius

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class ShortNamedAnnotatedEnumSpec extends Specification {
	def setup() {
		setUp()
	}


	def source = """
				package dk.glasius
				import dk.glasius.annotations.EnumMessageSourceResolvable
	            import dk.glasius.transformation.DefaultNameCase

				@EnumMessageSourceResolvable(shortName = true)
				public enum ShortNamedAnnotatedEnum {
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

