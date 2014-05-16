package grails.plugin.i18n.enums

import spock.lang.Specification

abstract class EnumSpecification extends Specification {

    protected static final CN = 'DefaultAnnotatedEnum'

    static final def pkg = "dk.glasius"
    static final def defaultValues = [ 'ONE', 'two', 'Three' ]

    private EnumCompiler compiler = new EnumCompiler()

    Class<?> compile(EnumModel model) { compiler.loadClass(model.toString()) }

    protected class EnumModel {

        String name = CN

        // annotation attributes
        String prefix, postfix
        Boolean useShortName
        DefaultNameCase nameCase = DefaultNameCase.UNCHANGED

        // values
        List<String> values = defaultValues

        // implements
        List<Class<?>> implementz = []

        // method overrides
        List<String> methods = []

        // fixme: uggly
        String getAttributes() {
            def sb = []
            if(prefix) sb << "prefix='$prefix'"
            if(postfix) sb << "postfix='$postfix'"
            if(useShortName!=null) sb << "shortName=$useShortName"
            if(nameCase&&nameCase!=DefaultNameCase.UNCHANGED) sb << "defaultNameCase=DefaultNameCase.${nameCase.name()}"
            sb.join(', ')
        }

        @Override public String toString() { """
            package $pkg

            import grails.plugin.i18n.enums.*

            @Internationalized($attributes)
            public enum $name ${if(implementz){"implements ${(implementz*.name).join(', ')} "}else{''}}{

                ${values.join(', ')}

                // Where the method goes
                ${methods.join("\n\n")}

            }
        """}

        private Class<?> init
        synchronized Class<?> clazz() { init ? init : (init = compile(this)) }
        Class<?> newClazz() { new EnumCompiler().loadClass(this.toString()) }

    }

    public EnumModel newEnum() { new EnumModel() }

    public def createConfig(Map config) { [ grails: [plugin: [i18n: [enums: config ]]]] }

}
