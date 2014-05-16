package grails.plugin.i18n.enums

import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders

@Mixin([AnnotationTestHelper, GrailsUnitTestMixin])
class GrailsConfigOverrideAnnotatedEnumSpec extends EnumSpecification {

    def theEnum = newEnum().with {
        prefix = 'overridepre'
        postfix = 'overridepost'
        useShortName = false
        nameCase = DefaultNameCase.UPPER_CASE
        values = [ 'ONE', 'Two', 'three' ]
        it
    }

    def setup() {
        Holders.config = createConfig([
            prefix:'pre',
            postfix: 'post',
            defaultNameCase: DefaultNameCase.LOWER_CASE,
            shortName: true
        ])
    }

    def cleanup() {
        Holders.config = null
    }

    def "test that the annotated enum default message returns correct values"() {
        when:
        def clazz = theEnum.newClazz()

        then:
        clazz.ONE.defaultMessage == 'ONE'
        clazz.Two.defaultMessage == 'TWO'
        clazz.three.defaultMessage == 'THREE'
    }



    def "test that the annotated enum arguments returns correct values"() {
        when:
        def clazz = theEnum.newClazz()

        then:
        clazz.ONE.arguments == []
        clazz.Two.arguments == []
        clazz.three.arguments == []
    }

    def "test that the annotated enum codes returns correct values"() {
        when:
        def clazz = theEnum.newClazz()

        then:
        clazz.ONE.codes == ["overridepre.dk.glasius.${CN}.ONE.overridepost", "overridepre.dk.glasius.${CN}.ONE.overridepost", "overridepre.dk.glasius.${CN}.one.overridepost"]
        clazz.Two.codes == ["overridepre.dk.glasius.${CN}.TWO.overridepost", "overridepre.dk.glasius.${CN}.Two.overridepost", "overridepre.dk.glasius.${CN}.two.overridepost"]
        clazz.three.codes == ["overridepre.dk.glasius.${CN}.THREE.overridepost", "overridepre.dk.glasius.${CN}.three.overridepost", "overridepre.dk.glasius.${CN}.three.overridepost"]
    }


}
