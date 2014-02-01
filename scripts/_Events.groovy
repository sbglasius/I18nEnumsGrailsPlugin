import org.codehaus.gant.GantBinding

/*
includeTargets << grailsScript("_GrailsInit")

eventCompileStart = {GantBinding compileBinding ->
    setCompilerSettings()
    resolveDependencies()

    ant.taskdef(name: 'precompileGroovyc', classname: 'org.codehaus.groovy.ant.Groovyc')

    try {
        grailsConsole.updateStatus "Precompiling I18nEnums AST transforms...."
        def pluginDir = i18nEnumsPluginDir?.toString() ?: basedir
        def pluginSourcePath = pluginDir + '/src/groovy'
        def pluginDestPath = projectCompiler.pluginSettings.buildSettings.pluginBuildClassesDir.absolutePath
        println "Compiling files in ${pluginSourcePath}"
        println "Compiling files to ${pluginDestPath}"

        if (new File(pluginSourcePath).directory) {
            ant.mkdir(dir: pluginDestPath)
            ant.precompileGroovyc(destdir: pluginDestPath,
                    classpathref: "grails.compile.classpath",
                    encoding: projectCompiler.encoding,
                    verbose: projectCompiler.verbose,
                    listfiles: projectCompiler.verbose) {

                src(path: pluginSourcePath)

            }
        }

        grailsConsole.addStatus "... done precompiling I18nEnums AST transforms...."

    } catch (Exception e) {
        grailsConsole.error("Could not precompile sources: " + e.class.name + ": " + e.message, e)
    }
}
*/
