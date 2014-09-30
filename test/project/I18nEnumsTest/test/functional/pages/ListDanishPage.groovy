package pages

class ListDanishPage extends ScaffoldPage {
    static url = "enumDomain/index?lang=da"
	
	static at = {
		title ==~ /EnumDomain Liste/
	}
	
	static content = {
		enumDomainTable { $("div.content table", 0) }
		enumDomainRow { module EnumDomainTableRow, enumDomainRows[it] }
		enumDomainRows(required: false) { enumDomainTable.find("tbody").find("tr") }
	}
}

