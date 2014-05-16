package grails.plugin.i18n.enums

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
class Transformation extends AbstractASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        init(nodes, sourceUnit)
        def ano = nodes[0] as AnnotationNode
        def cls = nodes[1] as AnnotatedNode
        if (cls instanceof ClassNode && cls.enum) {
            addInterfaces(cls, MessageSourceResolvable)
            installHelper(cls, ano)
        }
    }

    private addInterfaces(ClassNode cls, Class<?> ... ifaces) { ifaces.each { cls.addInterface(ClassHelper.make(it)) } }

    private installHelper(ClassNode cls, AnnotationNode ano) {
        Expression constructor = new ConstructorCallExpression(
                ClassHelper.make(EnumDelegate),
                new ArgumentListExpression(
                    new VariableExpression("this", cls),
                    createConfigExpr(ano)
            )
        )

        FieldNode $delegation = new FieldNode('$delegation', Modifier.PRIVATE, ClassHelper.make(EnumDelegate), cls, constructor)
        AnnotationNode delegateAno = new AnnotationNode(ClassHelper.make(Delegate))
        $delegation.addAnnotation(delegateAno)
        cls.addTransform(DelegateASTTransformation, delegateAno)

        cls.addField($delegation)
    }

    private MapExpression createConfigExpr(AnnotationNode ano) {
        MapExpression map = new MapExpression()
        ['prefix', 'postfix', 'shortName', 'defaultNameCase'].each {
            Expression expr = ano.getMember(it)
            if(expr) map.addMapEntryExpression(createConfigEntryExpr(it, expr))
        }
        map
    }

    private MapEntryExpression createConfigEntryExpr(String k, Expression v) { new MapEntryExpression(new ConstantExpression(k), v) }

}
