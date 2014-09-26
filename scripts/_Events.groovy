import org.codehaus.gant.GantBinding

includeTargets << grailsScript("_GrailsInit")

/**
 * Hooks to the compile grails event
 */
eventClasspathStart = { GantBinding compileBinding ->
    resolveDependencies()

    ant.taskdef(name: 'precompileGroovyc', classname: 'org.codehaus.groovy.ant.Groovyc')
    try {
        def sourceDir = (compileBinding.variables.i18nEnumsPluginDir?.absolutePath ?: basedir) + '/src/groovy'
        def destDir = grailsSettings.classesDir
        ant.mkdir(dir: destDir)
        grailsConsole.addStatus "Precompiling I18nEnums AST transforms."
        ant.precompileGroovyc(destdir: destDir,
                classpathref: "grails.compile.classpath",
                encoding: projectCompiler.encoding,
                verbose: projectCompiler.verbose,
                listfiles: projectCompiler.verbose) {
            src(path: sourceDir)
        }
    } catch (Exception e) {
        grailsConsole.error("Could not precompile sources: " + e.class.name + ": " + e.message, e)
    }
}
