package grails.plugin.i18nEnums.annotations;

import grails.plugin.i18nEnums.transformation.DefaultNameCase;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface I18nEnum {
    public abstract String prefix() default "";

    public abstract String postfix() default "";

    public abstract boolean shortName() default false;

    public abstract DefaultNameCase defaultNameCase() default DefaultNameCase.UNCHANGED;
}
