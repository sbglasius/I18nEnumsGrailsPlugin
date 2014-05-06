I18nEnums Grails Plugin
=========================================

This plugin adds an annontation usable on Enums to easy add and implement the MessageSourceResolvable interface,
in an standard way throughout a project.

Quick Start
=========================================

Let's create some Enum to start.

/src/groovy/your/namespace/SomeEnum.groovy

```groovy
package your.namespace

@grails.plugin.i18nEnums.I18nEnum
enum SomeEnum {

    VAL0, VAL1, VAL2;

}
```

We will add @grails.plugin.i18nEnums.I18nEnum annotation to the enum class. It will initiate all the magic.

So.. Next thing we will do is preparing our translations. We will add following values into Grails i18n message[_en|_de|_blah|_blah].properties

/grails-app/i18n/messages.properties

```
your.namespace.SomeEnum.VAL0=Value 0
your.namespace.SomeEnum.VAL1=Value 1
your.namespace.SomeEnum.VAL2=Value 2
```

Ok. That's all. Now we can use it in number of ways within our Grails app.

Variant 1
---------
It can be done as from View and from Controller/Service

```groovy
messageSource.getMessage(your.namespace.SomeEnum.VAL1, Locale.default)
```

Variant 2
---------
This can be done as from Views

```groovy
<g:message error="${your.namespace.SomeEnum.VAL1}"/>
```
Here some hack used. Message tag can render MessageSourceResolvable only if we pass it via 'error' attribute.

Variant 3
---------
This can be done as from anywhere

```groovy
your.namespace.SomeEnum.VAL1.message
```

Thanks )