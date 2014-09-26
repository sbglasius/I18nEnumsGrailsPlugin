class I18nEnumsGrailsPlugin {
    def version = "1.0.7"
    def grailsVersion = "2.0 > *"
    def title = "I18nEnums Grails Plugin"
    def author = "Søren Berg Glasius"
    def authorEmail = "soeren@glasius.dk"
    def description = 'Adds an enumeration usable on Enums to easy add and implement the MessageSourceResolvable interface'
    def documentation = "http://sbglasius.github.io/I18nEnumsGrailsPlugin/"

    def license = "APACHE"
    def organization = [name: "Groovy Freelancer", url: "http://www.groovy-freelancer.dk/"]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/sbglasius/I18nEnumsGrailsPlugin/issues']
    def scm = [url: 'https://github.com/sbglasius/I18nEnumsGrailsPlugin']
    def loadBefore = ['services', 'dataSource', 'hibernate', 'hibernate4', 'validation','controllers']
}
