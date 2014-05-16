package grails.plugin.i18n.enums

import groovy.text.GStringTemplateEngine
import groovy.text.TemplateEngine

/**
 * Created by Admin on 08.05.2014.
 */
class EnumCompiler {

    private TemplateEngine templateEngine = new GStringTemplateEngine()
    private GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader())

    String createSourceCodeForTemplate(final String template, final Map binding)  {
        templateEngine.createTemplate(template).make(binding).toString()
    }

    def newInstance(final String sourceCode) { newInstance(sourceCode, new Object[0]) }

    def newInstance(final String sourceCode, def constructor_args) {
        def clazz = loadClass(sourceCode)
        clazz.newInstance(constructor_args as Object[])
    }

    def loadClass(final String sourceCode) { loader.parseClass(sourceCode) }

}
