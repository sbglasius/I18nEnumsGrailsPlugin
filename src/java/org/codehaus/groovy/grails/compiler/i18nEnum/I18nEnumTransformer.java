package org.codehaus.groovy.grails.compiler.i18nEnum;

import grails.plugin.i18nEnums.annotations.I18nEnum;
import grails.plugin.i18nEnums.helper.I18nEnumHelper;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.grails.compiler.injection.AllArtefactClassInjector;
import org.codehaus.groovy.grails.compiler.injection.AstTransformer;
import org.codehaus.groovy.grails.compiler.injection.GrailsASTUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.context.MessageSourceResolvable;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.List;

@AstTransformer
public class I18nEnumTransformer implements AllArtefactClassInjector {
    private static final String I18N_ENUM_HELPER = "$$helper";

    @Override
    public void performInjection(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        List<AnnotationNode> i18nEnumAnnotations = classNode.getAnnotations(new ClassNode(I18nEnum.class));
        if (classNode.isEnum() && !i18nEnumAnnotations.isEmpty()) {
            FieldNode helper = addHelper(classNode, i18nEnumAnnotations.get(0));
            addMethod(classNode, helper, "getCodes", ClassHelper.STRING_TYPE.makeArray());
            addMethod(classNode, helper, "getArguments", ClassHelper.OBJECT_TYPE.makeArray());
            addMethod(classNode, helper, "getDefaultMessage", ClassHelper.STRING_TYPE);
            addInterface(classNode);
        }
    }

    private void addMethod(ClassNode classNode, FieldNode helper, String methodName, ClassNode returnType) {
        final boolean hasMethod = GrailsASTUtils.implementsZeroArgMethod(classNode, methodName);
        if (!hasMethod) {
            FieldExpression methodHelperVariable = new FieldExpression(classNode.getField(I18N_ENUM_HELPER));

            MethodCallExpression methodCallExpression = new MethodCallExpression(methodHelperVariable, methodName, MethodCallExpression.NO_ARGUMENTS);
            ReturnStatement returnStatement = new ReturnStatement(methodCallExpression);

            BlockStatement blockStatement = new BlockStatement();
            blockStatement.addStatement(returnStatement);
            MethodNode methodNode = new MethodNode(methodName, Modifier.PUBLIC, returnType, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, blockStatement);
            classNode.addMethod(methodNode);
        }
    }

    @Override
    public void performInjection(SourceUnit source, ClassNode classNode) {
        performInjection(source, null, classNode);
    }

    @Override
    public void performInjectionOnAnnotatedClass(SourceUnit source, ClassNode classNode) {
        performInjection(source, null, classNode);
    }

    @Override
    public boolean shouldInject(URL url) {
        return true;
    }

    private void addInterface(ClassNode classNode) {
        ClassNode clazz = ClassHelper.make(MessageSourceResolvable.class);
        classNode.addInterface(clazz);
    }

    private FieldNode addHelper(ClassNode classNode, AnnotationNode annotationNode) {
        MapExpression mapExpression = createMapExpression(annotationNode);

        Expression thisExpression = new VariableExpression("this", classNode);
        ArgumentListExpression helperArguments = new ArgumentListExpression(thisExpression, mapExpression);
        Expression helperValue = new ConstructorCallExpression(ClassHelper.make(I18nEnumHelper.class), helperArguments);
        FieldNode helperField = new FieldNode(I18N_ENUM_HELPER, Modifier.PRIVATE, ClassHelper.make(I18nEnumHelper.class), classNode, helperValue);
        classNode.addField(helperField);
        return helperField;
    }

    private MapExpression createMapExpression(AnnotationNode annotationNode) {
        Expression prefix = annotationNode.getMember("prefix");
        Expression postfix = annotationNode.getMember("postfix");
        Expression shortName = annotationNode.getMember("shortName");
        Expression defaultNameCase = annotationNode.getMember("defaultNameCase");

        Expression mapExpression = new MapExpression();
        if (DefaultGroovyMethods.asBoolean(prefix)) {
            ((MapExpression) mapExpression).addMapEntryExpression(createMapEntryExpression("prefix", prefix));
        }

        if (DefaultGroovyMethods.asBoolean(postfix)) {
            ((MapExpression) mapExpression).addMapEntryExpression(createMapEntryExpression("postfix", postfix));
        }

        if (DefaultGroovyMethods.asBoolean(shortName)) {
            ((MapExpression) mapExpression).addMapEntryExpression(createMapEntryExpression("shortName", shortName));
        }

        if (DefaultGroovyMethods.asBoolean(defaultNameCase)) {
            ((MapExpression) mapExpression).addMapEntryExpression(createMapEntryExpression("defaultNameCase", defaultNameCase));
        }

        return ((MapExpression) (mapExpression));
    }

    private MapEntryExpression createMapEntryExpression(String key, Expression value) {
        return new MapEntryExpression(new ConstantExpression(key), value);
    }


}
