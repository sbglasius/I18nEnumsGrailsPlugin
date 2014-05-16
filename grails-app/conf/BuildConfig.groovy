grails.project.work.dir = 'target'
grails.release.scm.enabled = false
grails.project.plugin.includeSource = false

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		mavenLocal()
		mavenCentral()
        grailsCentral()
	}

	dependencies {
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build(':release:3.0.1', ':rest-client-builder:1.0.3') {
			export = true
		}
		test(":spock:0.7") {
			exclude "spock-grails-support"
			export = false
		}
	}
}
