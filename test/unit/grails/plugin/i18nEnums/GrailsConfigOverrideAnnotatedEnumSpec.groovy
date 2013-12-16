package grails.plugin.i18nEnums
import grails.plugin.i18nEnums.transformation.DefaultNameCase
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders
import spock.lang.Specification

@Mixin([AnnotationTestHelper, GrailsUnitTestMixin])
class GrailsConfigOverrideAnnotatedEnumSpec extends Specification {

    def source = """
				package dk.glasius
				import grails.plugin.i18nEnums.annotations.I18nEnum
                import grails.plugin.i18nEnums.transformation.DefaultNameCase

				@I18nEnum(prefix = 'overridepre', postfix = 'overridepost', shortName = false, defaultNameCase = DefaultNameCase.UPPER_CASE)
				enum DefaultAnnotatedEnum {
					ONE,
					Two,
					three
				}
			"""

    def setup() {
        Holders.config = [ grails: [plugin: [i18nEnum: [
                prefix:'pre',
                postfix: 'post',
                defaultNameCase: DefaultNameCase.LOWER_CASE,
                shortName: true
        ]]]]

    }

    def cleanup() {
        Holders.config = null
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
