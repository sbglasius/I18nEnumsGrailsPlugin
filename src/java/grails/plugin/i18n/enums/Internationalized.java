package grails.plugin.i18n.enums;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass({"grails.plugin.i18n.enums.Transformation"})
public @interface Internationalized {
	String prefix() default "";
	String postfix() default "";
	boolean shortName() default false;
	DefaultNameCase defaultNameCase() default DefaultNameCase.UNCHANGED;
}
