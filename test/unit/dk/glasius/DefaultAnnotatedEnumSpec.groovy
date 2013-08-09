package dk.glasius

import spock.lang.Specification
import org.springframework.context.MessageSourceResolvable


@Mixin(AnnotationTestHelper)
class DefaultAnnotatedEnumSpec extends Specification {
	def setup() {
		setUp()
	}


	def source = """
				package dk.glasius
				import dk.glasius.annotations.EnumMessageSourceResolvable

				@EnumMessageSourceResolvable
				public enum DefaultAnnotatedEnum {
					ONE,
					TWO,
					Three
				}
			"""


	def "test that the enum has an interface of MessageSourceResolvable"() {
		when:
		def clazz = add_class_to_classpath(source)

		then:
		MessageSourceResolvable in clazz.interfaces
	}


	def "test that the default annotated enum default message returns correct values"() {
		when:
		def clazz = add_class_to_classpath(source)

		then:
		clazz.ONE.defaultMessage == 'ONE'
		clazz.TWO.defaultMessage == 'TWO'
		clazz.Three.defaultMessage == 'Three'
	}



	def "test that the default annotated enum arguments returns correct values"() {
		when:
		def clazz = add_class_to_classpath(source)

		then:
		clazz.ONE.arguments == []
		clazz.TWO.arguments == []
	}

	def "test that the default annotated enum codes returns correct values"() {
		when:
		def clazz = add_class_to_classpath(source)

		then:
		clazz.ONE.codes == ['dk.glasius.DefaultAnnotatedEnum.ONE','dk.glasius.DefaultAnnotatedEnum.ONE', 'dk.glasius.DefaultAnnotatedEnum.one']
		clazz.TWO.codes == ['dk.glasius.DefaultAnnotatedEnum.TWO','dk.glasius.DefaultAnnotatedEnum.TWO', 'dk.glasius.DefaultAnnotatedEnum.two']
	}


}
