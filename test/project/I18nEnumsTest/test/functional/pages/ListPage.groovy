package pages

import geb.Module

class ListPage extends ScaffoldPage {
	static url = "enumDomain/index?lang=en"
	
	static at = {
		title ==~ /EnumDomain List/
	}
	
	static content = {
		newEnumDomainButton(to: CreatePage) { $("a", text: "New EnumDomain") }
		enumDomainTable { $("div.content table", 0) }
		enumDomainRow { module EnumDomainTableRow, enumDomainRows[it] }
		enumDomainRows(required: false) { enumDomainTable.find("tbody").find("tr") }
	}
}

class EnumDomainTableRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		enabled { Boolean.valueOf(cellHrefText(0)) }
		capitalizeEnum { cellText(0) }
		defaultEnum { cellText(1) }
		postfixAllCapsEnum { cellText(2) }
		prefixLowercaseEnum { cellText(3) }
        translatedEnum { cellText(4) }
		showLink(to: ShowPage) { cell(0).find("a") }
	}
}