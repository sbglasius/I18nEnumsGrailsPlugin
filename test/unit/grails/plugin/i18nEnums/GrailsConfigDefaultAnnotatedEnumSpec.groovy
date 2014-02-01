package grails.plugin.i18nEnums
import grails.plugin.i18nEnums.transformation.DefaultNameCase
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders
import org.codehaus.groovy.grails.compiler.i18nEnum.I18nEnumTransformer
import org.codehaus.groovy.grails.compiler.injection.ClassInjector
import org.codehaus.groovy.grails.compiler.injection.GrailsAwareClassLoader
import spock.lang.Specification

@Mixin([GrailsUnitTestMixin])
class GrailsConfigDefaultAnnotatedEnumSpec extends Specification {
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

				@I18nEnum
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
        clazz.ONE.defaultMessage == 'one'
        clazz.Two.defaultMessage == 'two'
        clazz.three.defaultMessage == 'three'
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
        clazz.ONE.codes == ['pre.DefaultAnnotatedEnum.ONE.post', 'pre.DefaultAnnotatedEnum.ONE.post', 'pre.DefaultAnnotatedEnum.one.post']
        clazz.Two.codes == ['pre.DefaultAnnotatedEnum.TWO.post', 'pre.DefaultAnnotatedEnum.Two.post', 'pre.DefaultAnnotatedEnum.two.post']
        clazz.three.codes == ['pre.DefaultAnnotatedEnum.THREE.post', 'pre.DefaultAnnotatedEnum.three.post', 'pre.DefaultAnnotatedEnum.three.post']
    }


}
