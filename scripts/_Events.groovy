import org.codehaus.gant.GantBinding

includeTargets << grailsScript("_GrailsInit")

/**
 * Hooks to the compile grails event
 */
eventCompileStart = {GantBinding compileBinding ->
    setCompilerSettings()
    resolveDependencies()

    ant.taskdef(name: 'precompileGroovyc', classname: 'org.codehaus.groovy.ant.Groovyc')

    try {
        grailsConsole.updateStatus "Precompiling I18nEnums AST transforms...."
        def _path = basedir + '/src/groovy'
        if (new File(_path).directory) {
            ant.mkdir(dir: classesDirPath)
            ant.precompileGroovyc(destdir: classesDirPath,
                    classpathref: "grails.compile.classpath",
                    encoding: projectCompiler.encoding,
                    verbose: projectCompiler.verbose,
                    listfiles: projectCompiler.verbose) {

                src(path: _path)

            }
        }

        grailsConsole.addStatus "... done precompiling I18nEnums AST transforms...."

    } catch (Exception e) {
        grailsConsole.error("Could not precompile sources: " + e.class.name + ": " + e.message, e)
    }
}
