package grails.plugin.i18n.enums

import spock.lang.Specification
import org.springframework.context.MessageSourceResolvable


@Mixin(AnnotationTestHelper)
class DefaultAnnotatedEnumSpec extends EnumSpecification {

    private String className = CN
	def theEnum = newEnum().with {
        values = ['ONE', 'Two', 'three']
        it
    }

	def "test that the enum has an interface of MessageSourceResolvable"() {
		when:
		def clazz = theEnum.newClazz()

		then:
		MessageSourceResolvable in clazz.interfaces
	}


	def "test that the default annotated enum default message returns correct values"() {
		when:
        def clazz = theEnum.newClazz()

        then:
		clazz.ONE.defaultMessage == 'ONE'
		clazz.Two.defaultMessage == 'Two'
		clazz.three.defaultMessage == 'three'
	}



	def "test that the default annotated enum arguments returns correct values"() {
		when:
        def clazz = theEnum.newClazz()

        then:
		clazz.ONE.arguments == []
		clazz.Two.arguments == []
		clazz.three.arguments == []
	}

	def "test that the default annotated enum codes returns correct values"() {
		when:
        def clazz = theEnum.newClazz()

        then:
		clazz.ONE.codes == ["dk.glasius.${CN}.ONE","dk.glasius.${CN}.ONE", "dk.glasius.${CN}.one"]
		clazz.Two.codes == ["dk.glasius.${CN}.TWO","dk.glasius.${CN}.Two", "dk.glasius.${CN}.two"]
		clazz.three.codes == ["dk.glasius.${CN}.THREE","dk.glasius.${CN}.three", "dk.glasius.${CN}.three"]
	}


}
