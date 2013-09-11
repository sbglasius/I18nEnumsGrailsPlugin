package dk.glasius.transformation

import static org.codehaus.groovy.ast.expr.MethodCallExpression.NO_ARGUMENTS
import static org.codehaus.groovy.ast.expr.VariableExpression.THIS_EXPRESSION

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.springframework.context.MessageSourceResolvable

import dk.glasius.annotations.EnumMessageSourceResolvable

@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class EnumMessageSourceResolvableTransformation extends AbstractASTTransformation {
	static final Class MY_CLASS = EnumMessageSourceResolvable
	static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS)
	static final String MY_TYPE_NAME = '@' + MY_TYPE.nameWithoutPackage

	void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
		if(!(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
			throw new RuntimeException("Internal error: wrong types: ${nodes[0].class} / ${nodes[1].class}")
		}
		AnnotationNode annotationNode = nodes[0] as AnnotationNode
		def annotatedNode = nodes[1]

		String prefix = getMemberStringValue(annotationNode, 'prefix')
		String postfix = getMemberStringValue(annotationNode, 'postfix')
		boolean shortName = memberHasValue(annotationNode, 'shortName', true)
		def defaultNameCase = getEnumAnnotationParam(annotationNode, 'defaultNameCase', DefaultNameCase, DefaultNameCase.UNCHANGED)

		if(annotatedNode instanceof ClassNode) {
			ClassNode classNode = annotatedNode
			addInterface(classNode)
			addGetDefaultMessageMetod(classNode, defaultNameCase)
			addGetCodesMetod(classNode, prefix, postfix, shortName)
			addGetArgumentsMetod(classNode)
		}
	}


	private addInterface(ClassNode classNode) {
		def clazz = ClassHelper.make(MessageSourceResolvable)
		classNode.addInterface(clazz)
	}


	private addGetDefaultMessageMetod(ClassNode source, final defaultNameCase) {
        if(source.getMethods('getDefaultMessage')) return
		def block = new BlockStatement()
		def expression
		switch(defaultNameCase) {
			case DefaultNameCase.CAPITALIZE:
				expression = makeMethods(THIS_EXPRESSION, ['name','toLowerCase','capitalize'])
				break
			case DefaultNameCase.LOWER_CASE:
				expression = makeMethods(THIS_EXPRESSION, ['name','toLowerCase'])
				break
			case DefaultNameCase.UPPER_CASE:
				expression = makeMethods(THIS_EXPRESSION, ['name','toUpperCase'])
				break
			default:
				expression = makeMethods(THIS_EXPRESSION, 'name')
		}

		block.addStatement(new ReturnStatement(expression))

		def method = new MethodNode('getDefaultMessage', ACC_PUBLIC, ClassHelper.STRING_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, block)
		source.addMethod(method)
	}


	private addGetArgumentsMetod(ClassNode source) {
        if(source.getMethods('getArguments')) return
		def block = new BlockStatement()
		def arrayExpression = new ArrayExpression(ClassHelper.make(Object), [])
		block.addStatement(new ReturnStatement(arrayExpression))

		def method = new MethodNode('getArguments', ACC_PUBLIC, arrayExpression.type, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, block)
		source.addMethod(method)
	}


	private addGetCodesMetod(ClassNode source, String prefix, String postfix, boolean shortName) {
        if(source.getMethods('getCodes')) return
		def block = new BlockStatement()
		def enumName = makeMethods(THIS_EXPRESSION, 'name')
		def enumNameLowerCase = makeMethods(enumName, 'toLowerCase')
		def enumNameUpperCase = makeMethods(enumName, 'toUpperCase')
		def className = makeMethods(THIS_EXPRESSION, ['getClass', shortName ? 'getSimpleName' : 'getName'])

		if(prefix && !prefix.endsWith('.')) {
			prefix += '.'
		}
		if(postfix && !postfix.startsWith('.')) {
			postfix = '.' + postfix
		}
		def prefixExpression = new ConstantExpression(prefix ?: '')
		def dotExpression = new ConstantExpression('.')
		def postfixExpression = new ConstantExpression(postfix ?: '')
		def upperCase = makeGString(prefixExpression, dotExpression, postfixExpression, className, enumNameUpperCase)
		def unchangedCase = makeGString(prefixExpression, dotExpression, postfixExpression, className, enumName)
		def lowerCase = makeGString(prefixExpression, dotExpression, postfixExpression, className, enumNameLowerCase)

		def arrayExpression = new ArrayExpression(new ClassNode(String), [upperCase, unchangedCase, lowerCase])
		block.addStatement(new ReturnStatement(arrayExpression))

		def method = new MethodNode('getCodes', ACC_PUBLIC, arrayExpression.type, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, block)
		source.addMethod(method)
	}


	private GStringExpression makeGString(ConstantExpression prefixExpression, ConstantExpression dotExpression, ConstantExpression postfixExpression, MethodCallExpression className, MethodCallExpression enumNameUpperCase) {
		def upperCase = new GStringExpression('', [prefixExpression, dotExpression, postfixExpression], [className, enumNameUpperCase])
		upperCase
	}


	private getEnumAnnotationParam(AnnotationNode node, String parameterName, Class type, defaultValue) {
		def member = node.getMember(parameterName)
		if(member) {
			if(member instanceof PropertyExpression) {
				try {
					return Enum.valueOf(type, ((PropertyExpression) member).propertyAsString)
				} catch(e) {
					throw new RuntimeException("Expecting ${type.name} value for ${parameterName} annotation parameter. Found $member",e)
				}
			} else {
				throw new RuntimeException("Expecting ${type.name} value for ${parameterName} annotation parameter. Found $member")
			}
		}
		return defaultValue
	}


	private MethodCallExpression makeMethods(Expression expression, def method) {
		if(method instanceof List) {
			return method.inject(expression) { Expression expr, String name ->
				return new MethodCallExpression(expr, name, NO_ARGUMENTS)
			}
		}
		return new MethodCallExpression(expression, (String)method, NO_ARGUMENTS)
	}
}
