package grails.plugin.i18nEnums
import grails.plugin.i18nEnums.transformation.DefaultNameCase
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders
import org.codehaus.groovy.grails.compiler.i18nEnum.I18nEnumTransformer
import org.codehaus.groovy.grails.compiler.injection.ClassInjector
import org.codehaus.groovy.grails.compiler.injection.GrailsAwareClassLoader
import spock.lang.Specification

@Mixin([GrailsUnitTestMixin])
class GrailsConfigOverrideAnnotatedEnumSpec extends Specification {
    GrailsAwareClassLoader gcl
    def setup() {
        gcl = new GrailsAwareClassLoader()
        def transformer = new I18nEnumTransformer()
        gcl.classInjectors = [transformer] as ClassInjector[]

        Holders.config = [ grails: [plugin: [i18nEnum: [
                prefix:'pre',
                postfix: 'post',
                defaultNameCase: DefaultNameCase.LOWER_CASE,
                shortName: true
        ]]]]
    }

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

    def cleanup() {
        Holders.config = null
    }


    def "test that the annotated enum default message returns correct values"() {
        when:
        def clazz = gcl.parseClass(source)

        then:
        clazz.ONE.defaultMessage == 'ONE'
        clazz.Two.defaultMessage == 'TWO'
        clazz.three.defaultMessage == 'THREE'
    }

    def "test that the annotated enum arguments returns correct values"() {
        when:
        def clazz = gcl.parseClass(source)

        then:
        clazz.ONE.arguments == []
        clazz.Two.arguments == []
        clazz.three.arguments == []
    }

    def "test that the annotated enum codes returns correct values"() {
        when:
        def clazz = gcl.parseClass(source)

        then:
        clazz.ONE.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.ONE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.ONE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.one.overridepost']
        clazz.Two.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.TWO.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.Two.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.two.overridepost']
        clazz.three.codes == ['overridepre.dk.glasius.DefaultAnnotatedEnum.THREE.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.three.overridepost', 'overridepre.dk.glasius.DefaultAnnotatedEnum.three.overridepost']
    }


}
