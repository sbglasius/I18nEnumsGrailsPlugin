import grails.plugin.i18nEnums.util.MessageSourceHolder
import org.springframework.context.MessageSource
import org.springframework.context.support.ReloadableResourceBundleMessageSource

class I18nEnumsGrailsPlugin {
    def version = "1.0.2"
    def grailsVersion = "2.0 > *"
    def title = "I18nEnums Grails Plugin"
    def author = "Søren Berg Glasius"
    def authorEmail = "soeren@glasius.dk"
    def description = 'Adds an enumeration usable on Enums to easy add and implement the MessageSourceResolvable interface'
    def documentation = "http://grails.org/plugin/i18n-enums"

    def license = "APACHE"
    def organization = [name: "Groovy Freelancer", url: "http://www.groovy-freelancer.dk/"]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/sbglasius/I18nEnumsGrailsPlugin/issues']
    def scm = [url: 'https://github.com/sbglasius/I18nEnumsGrailsPlugin']
    def loadBefore = ['services', 'dataSource', 'hibernate', 'hibernate4', 'validation', 'controllers']
    def packaging = "binary"

    def observe = [ 'i18n' ]

    def doWithApplicationContext = { ctx ->
        MessageSourceHolder.messageSource = ctx.getBean(MessageSource) as MessageSource
    }

    def onChange = { event ->
        def ctx = event.ctx
        if (!ctx) {
            log.debug("Application context not found. Can't reload")
            return
        }
        def ms = ctx.messageSource as MessageSource
        if (!(ms instanceof ReloadableResourceBundleMessageSource)) {
            MessageSourceHolder.messageSource = ms
        }
    }

}
