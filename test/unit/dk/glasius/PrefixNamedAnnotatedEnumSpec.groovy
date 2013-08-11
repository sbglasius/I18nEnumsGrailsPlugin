package dk.glasius

import spock.lang.Specification
import spock.lang.Unroll

@Mixin(AnnotationTestHelper)
class PrefixNamedAnnotatedEnumSpec extends Specification {

	def source = '''
				package dk.glasius
				import dk.glasius.annotations.EnumMessageSourceResolvable
				import dk.glasius.transformation.DefaultNameCase

				@EnumMessageSourceResolvable(prefix = '${prefix}', postfix = '${postfix}')
				enum PrefixNameCasedAnnotatedEnum {
					ONE,
					two,
					Three,
					FOUR_Five
				}
			'''


	@Unroll
	def "test that the default annotated enum default message returns correct values"() {
		when:
		def args = []
		if(prefix) args << "prefix = ${prefix}"
		if(postfix) args << "postfix = ${postfix}'"
		def clazz = add_class_to_classpath(createSourceCodeForTemplate(source, [prefix: prefix, postfix: postfix]))

		then:
		clazz.ONE.codes == createCodeList('ONE', expectedPrefix, expectedPostfix)
		clazz.two.codes == createCodeList('two', expectedPrefix, expectedPostfix)
		clazz.Three.codes == createCodeList('Three', expectedPrefix, expectedPostfix)
		clazz.FOUR_Five.codes == createCodeList('FOUR_Five', expectedPrefix, expectedPostfix)

		where:
		prefix | postfix | expectedPrefix | expectedPostfix
		''     | ''      | ''             | ''
		'pre'  | ''      | 'pre.'         | ''
		'pre.' | ''      | 'pre.'         | ''
		''     | 'post'  | ''             | '.post'
		''     | '.post' | ''             | '.post'
		'pre'  | 'post'  | 'pre.'         | '.post'
		'pre.' | '.post' | 'pre.'         | '.post'
	}


	private createCodeList(name, prefix, postfix) {
		[
				createEnumName(name.toUpperCase(), prefix, postfix),
				createEnumName(name, prefix, postfix),
				createEnumName(name.toLowerCase(), prefix, postfix)
		]
	}


	private String createEnumName(name, prefix, postfix) {
		"${prefix}dk.glasius.PrefixNameCasedAnnotatedEnum.${name}${postfix}".toString()
	}
}
