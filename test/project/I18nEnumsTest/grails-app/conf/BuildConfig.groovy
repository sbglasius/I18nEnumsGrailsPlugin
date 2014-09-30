grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits 'global'
    log "error"
    checksums true
    legacyResolve false

    repositories {
        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    def webDriverVersion = "2.43.1"
    def gebVersion = "0.9.3"
    dependencies {
        test "org.gebish:geb-spock:$gebVersion"

        test "org.seleniumhq.selenium:selenium-support:$webDriverVersion"
        test "org.seleniumhq.selenium:selenium-firefox-driver:$webDriverVersion"
    }

    plugins {
        build ":tomcat:7.0.54"

        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'

        compile 'org.grails.plugins:i18n-enums:1.0.7'

        runtime ":hibernate:3.6.10.17"

        test ":geb:$gebVersion"

    }
}

