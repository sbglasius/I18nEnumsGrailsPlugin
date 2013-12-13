package grails.plugin.enummessagesourceresolvable
import grails.plugin.enummessagesourceresolvable.transformation.DefaultNameCase
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders
import spock.lang.Specification

@Mixin([AnnotationTestHelper, GrailsUnitTestMixin])
class GrailsConfigOverrideAnnotatedEnumSpec extends Specification {

    def source = """
				package dk.glasius
				import grails.plugin.enummessagesourceresolvable.annotations.EnumMessageSourceResolvable
                import grails.plugin.enummessagesourceresolvable.transformation.DefaultNameCase

				@EnumMessageSourceResolvable(prefix = 'overridepre', postfix = 'overridepost', shortName = false, defaultNameCase = DefaultNameCase.UPPER_CASE)
				enum DefaultAnnotatedEnum {
					ONE,
					Two,
					three
				}
			"""

    def setup() {
        Holders.config = [ grails: [plugin: [enummessagesourceresolvable: [
                prefix:'pre',
                postfix: 'post',
                defaultNameCase: DefaultNameCase.LOWER_CASE,
                shortName: true
        ]]]]
        println Holders.config

    }


    def "test that the annotated enum default message returns correct values"() {
        when:
        def clazz = add_class_to_classpath(source)

        then:
        clazz.ONE.defaultMessage == 'ONE'
        clazz.Two.defaultMessage == 'TWO'
        clazz.three.defaultMessage == 'THREE'
    }



    def "test that the annotated enum arguments returns correct values"() {
        when:
        def clazz = add_class_to_classpath(source)

        then:
        clazz.ONE.arguments == []
        clazz.Two.arguments == []
        clazz.three.arguments == []
    }

    def "test that the annotated enum codes returns correct values"() {
        when:
        def clazz = add_class_to_classpath(source)

        then:
        clazz.ONE.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.ONE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.ONE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.one.overridepost']
        clazz.Two.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.TWO.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.Two.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.two.overridepost']
        clazz.three.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.THREE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.three.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.three.overridepost']
    }


}
