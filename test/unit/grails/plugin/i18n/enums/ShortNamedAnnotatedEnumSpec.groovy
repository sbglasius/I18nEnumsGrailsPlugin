package grails.plugin.i18n.enums

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class ShortNamedAnnotatedEnumSpec extends EnumSpecification {

    private static final CN = 'ShortNamedAnnotatedEnum' 
	def theEnum = newEnum().with {
        name = CN
        useShortName = true
        values = [ 'ONE', 'two', 'Three', 'FOUR_Five']
        it
    }

	@Unroll
	def "test that the default annotated enum default message returns correct values"() {

		when:
		def clazz = theEnum.clazz()

		then:
		clazz.ONE.codes == ["${CN}.ONE", "${CN}.ONE", "${CN}.one"]
		clazz.two.codes == ["${CN}.TWO", "${CN}.two", "${CN}.two"]
		clazz.Three.codes == ["${CN}.THREE", "${CN}.Three", "${CN}.three"]
		clazz.FOUR_Five.codes == ["${CN}.FOUR_FIVE", "${CN}.FOUR_Five", "${CN}.four_five"]

	}
}
