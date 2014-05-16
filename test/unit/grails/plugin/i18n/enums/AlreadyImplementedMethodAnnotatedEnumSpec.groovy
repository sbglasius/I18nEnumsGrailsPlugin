package grails.plugin.i18n.enums

@Mixin(AnnotationTestHelper)
class AlreadyImplementedMethodAnnotatedEnumSpec extends EnumSpecification {

	def "test that if the getCodes is already implemented, it is not being implemented again"() {

		when:
		def clazz = newEnum().with {
            implementz << org.springframework.context.MessageSourceResolvable
            methods << '@Override String[] getCodes() { return [\'some.other.code\'] as String[] }'
            it
        }.clazz()

		then:
		clazz.ONE.codes == ['some.other.code']
		clazz.two.codes == ['some.other.code']
		clazz.Three.codes == ['some.other.code']
	}

    def "test that if the getArguments is already implemented, it is not being implemented again"() {

		when:
        def clazz = newEnum().with {
            implementz << org.springframework.context.MessageSourceResolvable
            methods << '@Override Object[] getArguments() { return [1,\'A\'] as Object[] }'
            it
        }.clazz()

		then:
		clazz.ONE.arguments == [1,'A']
		clazz.two.arguments == [1,'A']
		clazz.Three.arguments == [1,'A']
	}

    def "test that if the getDefaultMessage is already implemented, it is not being implemented again"() {

		when:
        def clazz = newEnum().with {
            implementz << org.springframework.context.MessageSourceResolvable
            methods << '@Override String getDefaultMessage() { return "Another default message" }'
            it
        }.clazz()


        then:
		clazz.ONE.defaultMessage == "Another default message"
		clazz.two.defaultMessage == "Another default message"
		clazz.Three.defaultMessage == "Another default message"
	}
}
