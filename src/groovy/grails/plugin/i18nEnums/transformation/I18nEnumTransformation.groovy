package grails.plugin.i18nEnums.transformation

import grails.plugin.i18nEnums.helper.I18nEnumHelper
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.DelegateASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.springframework.context.MessageSourceResolvable

import java.lang.reflect.Modifier

@SuppressWarnings("GroovyUnusedDeclaration")
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class I18nEnumTransformation extends AbstractASTTransformation {

    static Set<Class<? extends Enum>> enums = new HashSet<Class<? extends Enum>>()

    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        init(nodes, sourceUnit)
        def ano = nodes[0] as AnnotationNode
        def cls = nodes[1] as AnnotatedNode
        if (cls instanceof ClassNode && cls.enum) {
            enums << cls.typeClass

            addInterfaces(cls, MessageSourceResolvable)
            installHelper(cls, ano)
        }
    }

    private addInterfaces(ClassNode cls, Class<?> ... ifaces) { cls.addInterface(ClassHelper.make(ifaces)) }

    private installHelper(ClassNode cls, AnnotationNode ano) {
        Expression constructor = new ConstructorCallExpression(
                ClassHelper.make(I18nEnumHelper),
                new ArgumentListExpression(
                    new VariableExpression("this", cls),
                    createConfigExpr(ano)
            )
        )

        FieldNode $helper = new FieldNode('$helper', Modifier.PRIVATE, ClassHelper.make(I18nEnumHelper), cls, constructor)
        AnnotationNode delegateAno = new AnnotationNode(ClassHelper.make(Delegate))
        $helper.addAnnotation(delegateAno)
        cls.addTransform(DelegateASTTransformation, delegateAno)

        cls.addField($helper)
    }

    private MapExpression createConfigExpr(AnnotationNode ano) {
        MapExpression map = new MapExpression()
        ['prefix', 'postfix', 'shortName', 'defaultNameCase'].each {
            Expression expr = ano.getMember(it)
            if(expr) {
                map.addMapEntryExpression(createConfigEntryExpr(it, expr))
            }
        }
        map
    }

    private MapEntryExpression createConfigEntryExpr(String k, Expression v) { new MapEntryExpression(new ConstantExpression(k), v) }

}
