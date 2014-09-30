package test
import grails.plugin.i18nEnums.annotations.I18nEnum
import grails.plugin.i18nEnums.transformation.DefaultNameCase

@I18nEnum(postfix = 'postfix', defaultNameCase = DefaultNameCase.ALL_CAPS)
public enum PostfixAllCapsEnum {
    ONE,
    TWO,
    THREE_OR_MORE
}
