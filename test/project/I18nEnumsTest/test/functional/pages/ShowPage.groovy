package pages

class ShowPage extends ScaffoldPage {

    static at = {
        heading.text() ==~ /Show EnumDomain/
    }

    static content = {
        id {
            def splitUrl = browser.currentUrl.split("/")
            splitUrl[-1]
        }
        editButton(to: EditPage) { $("a", text: "Edit") }
        deleteButton(to: ListPage) { $("input", value: "Delete") }
        row { $("li.fieldcontain span.property-label", text: it).parent() }
        value { row(it).find("span.property-value").text() }
        enabled { Boolean.valueOf(value("Enabled")) }
        capitalizeEnum { value("Capitalize Enum") }
        defaultEnum { value("Default Enum") }
        postfixAllCapsEnum { value("Postfix All Caps Enum") }
        prefixLowercaseEnum { value("Prefix Lowercase Enum") }
        translatedEnum { value("Translated Enum") }
    }
}
