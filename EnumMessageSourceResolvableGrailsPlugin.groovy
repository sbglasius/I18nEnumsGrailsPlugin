class EnumMessageSourceResolvableGrailsPlugin {
	// the plugin version
	def version = "0.9"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "2.2 > *"
	// the other plugins this plugin depends on
	def dependsOn = [:]
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
			"grails-app/views/error.gsp"
	]

	// TODO Fill in these fields
	def title = "EnumMessageSourceResolvable Grails Plugin" // Headline display name of the plugin
	def author = "Søren Berg Glasius"
	def authorEmail = "soeren@glasius.dk"
	def description = '''\
This plugin adds an enumeration usable on Enums to easy add and implement the MessageSourceResolvable interface
'''

	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/enum-message-source-resolvable-plugin"

	// Extra (optional) plugin metadata

	// License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

	// Details of company behind the plugin (if there is one)
    def organization = [ name: "Groovy Freelancer", url: "http://www.groovy-freelancer.dk/" ]

	// Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

	// Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

	// Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]
	def doWithWebDescriptor = { xml ->
	}

	def doWithSpring = {
	}

	def doWithDynamicMethods = { ctx ->
	}

	def doWithApplicationContext = { applicationContext ->
	}

	def onChange = { event ->
	}

	def onConfigChange = { event ->
	}

	def onShutdown = { event ->
	}
}
