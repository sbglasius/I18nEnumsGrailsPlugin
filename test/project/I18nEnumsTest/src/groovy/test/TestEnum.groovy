package test

import grails.plugin.i18nEnums.annotations.I18nEnum
import grails.plugin.i18nEnums.transformation.DefaultNameCase

@I18nEnum(defaultNameCase = DefaultNameCase.CAPITALIZE)
public enum TestEnum {
    ONE,
    TWO
}
