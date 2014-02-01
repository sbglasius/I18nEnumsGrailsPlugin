package grails.plugin.i18nEnums
import groovy.text.GStringTemplateEngine
import groovy.text.TemplateEngine
/**
 * @author ast
 *
 * This class is inspired / borrowed from the GContracts project
 */
class AnnotationTestHelper {

	private TemplateEngine templateEngine = new GStringTemplateEngine()

	String createSourceCodeForTemplate(final String template, final Map binding)  {
		templateEngine.createTemplate(template).make(binding).toString()
	}
}
