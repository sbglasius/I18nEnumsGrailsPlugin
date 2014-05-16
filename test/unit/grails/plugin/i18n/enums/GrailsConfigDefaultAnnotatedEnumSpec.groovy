package grails.plugin.i18n.enums

import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders

@Mixin([AnnotationTestHelper, GrailsUnitTestMixin])
class GrailsConfigDefaultAnnotatedEnumSpec extends EnumSpecification {

    def theEnum = newEnum().with {
        values = ['ONE', 'Two', 'three']
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
        clazz.ONE.defaultMessage == 'one'
        clazz.Two.defaultMessage == 'two'
        clazz.three.defaultMessage == 'three'
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
        clazz.ONE.codes == ["pre.${CN}.ONE.post", "pre.${CN}.ONE.post", "pre.${CN}.one.post"]
        clazz.Two.codes == ["pre.${CN}.TWO.post", "pre.${CN}.Two.post", "pre.${CN}.two.post"]
        clazz.three.codes == ["pre.${CN}.THREE.post", "pre.${CN}.three.post", "pre.${CN}.three.post"]
    }


}
