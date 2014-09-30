package test
import grails.plugin.i18nEnums.annotations.I18nEnum
import grails.plugin.i18nEnums.transformation.DefaultNameCase

@I18nEnum(prefix = 'prefix', defaultNameCase = DefaultNameCase.LOWER_CASE)
public enum PrefixLowercaseEnum {
    ONE,
    TWO,
    THREE_OR_MORE
}
