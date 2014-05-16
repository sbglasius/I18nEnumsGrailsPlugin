package grails.plugin.i18n.enums

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class PrefixNamedAnnotatedEnumSpec extends EnumSpecification {

    private static final String CN = 'PrefixNameCasedAnnotatedEnum'
	def theEnum = newEnum().with {
        name = CN
        values = [ 'ONE', 'two', 'Three', 'FOUR_Five' ]
        it
    }

	@Unroll
	def "test that the default annotated enum default message returns correct values"() {
		when:
        def clazz = theEnum.with {
            if(_prefix) prefix = _prefix
            if(_postfix) postfix = _postfix
            it
        }.clazz()

		then:
		clazz.ONE.codes == createCodeList('ONE', expectedPrefix, expectedPostfix)
		clazz.two.codes == createCodeList('two', expectedPrefix, expectedPostfix)
		clazz.Three.codes == createCodeList('Three', expectedPrefix, expectedPostfix)
		clazz.FOUR_Five.codes == createCodeList('FOUR_Five', expectedPrefix, expectedPostfix)

		where:
		_prefix | _postfix | expectedPrefix | expectedPostfix
		''      | ''       | ''             | ''
		'pre'   | ''       | 'pre.'         | ''
		'pre.'  | ''       | 'pre.'         | ''
		''      | 'post'   | ''             | '.post'
		''      | '.post'  | ''             | '.post'
		'pre'   | 'post'   | 'pre.'         | '.post'
		'pre.'  | '.post'  | 'pre.'         | '.post'
	}


	private createCodeList(name, prefix, postfix) {[
        createEnumName(name.toUpperCase(), prefix, postfix),
        createEnumName(name, prefix, postfix),
        createEnumName(name.toLowerCase(), prefix, postfix)
	]}


	private String createEnumName(name, prefix, postfix) { "${prefix}dk.glasius.$CN.$name$postfix".toString() }
}
