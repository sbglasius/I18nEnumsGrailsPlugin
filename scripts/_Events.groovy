import org.codehaus.gant.GantBinding

includeTargets << grailsScript("_GrailsInit")

/**
 * Hooks to the compile grails event
 */
eventClasspathStart = {GantBinding compileBinding ->
    setCompilerSettings()
    resolveDependencies()

    ant.taskdef(name: 'precompileGroovyc', classname: 'org.codehaus.groovy.ant.Groovyc')

    try {
        grailsConsole.addStatus "Precompiling I18nEnums AST transforms...."
        def sourceDir =  (compileBinding.variables.i18nEnumsPluginDir?.absolutePath ?: basedir) + '/src/groovy-ast'
        def destDir = new File(grailsSettings.projectWorkDir, 'ast-classes')
        println grailsSettings.compileDependencies
        println sourceDir
        println destDir.absolutePath
        if (new File(sourceDir).directory) {
            ant.mkdir(dir: destDir)
            ant.precompileGroovyc(destdir: destDir,
                    classpathref: "grails.compile.classpath",
                    encoding: projectCompiler.encoding,
                    verbose: projectCompiler.verbose,
                    listfiles: projectCompiler.verbose) {

                src(path: sourceDir)

            }
        }
        grailsSettings.compileDependencies << destDir
        println grailsSettings.compileDependencies

        grailsConsole.addStatus "... done precompiling I18nEnums AST transforms...."

    } catch (Exception e) {
        grailsConsole.error("Could not precompile sources: " + e.class.name + ": " + e.message, e)
    }
}
