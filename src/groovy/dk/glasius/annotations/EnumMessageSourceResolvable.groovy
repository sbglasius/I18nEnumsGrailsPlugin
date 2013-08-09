package dk.glasius.annotations

import dk.glasius.transformation.DefaultNameCase
import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass(["dk.glasius.transformation.EnumMessageSourceResolvableTransformation"])
public @interface EnumMessageSourceResolvable {
	String prefix() default '';


	String postfix() default '';


	boolean shortName() default false;


	DefaultNameCase defaultNameCase() default DefaultNameCase.UNCHANGED;
}
