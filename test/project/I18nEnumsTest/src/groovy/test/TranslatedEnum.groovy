package test
import grails.plugin.i18nEnums.annotations.I18nEnum

@I18nEnum(prefix = 'prefix', shortName = true, postfix = 'label')
public enum TranslatedEnum {
    ONE,
    TWO,
    THREE_OR_MORE
}
