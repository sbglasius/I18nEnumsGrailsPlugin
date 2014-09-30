package test

import geb.spock.GebReportingSpec
import pages.*
import spock.lang.Stepwise

@Stepwise
class EnumDomainCRUDSpec extends GebReportingSpec {
	
	def "there are no enumDomain objects"() {
		when:
		to ListPage
		then:
		enumDomainRows.size() == 0
	}
	
	def "add a enumDomain"() {
		when:
		newEnumDomainButton.click()
		then:
		at CreatePage
	}
	
	def "enter the details"() {
		when:
		$("#enabled").click()
        capitalizeEnum = "ONE"
        defaultEnum = "TWO"
        prefixLowercaseEnum = "THREE_OR_MORE"
        postfixAllCapsEnum = "THREE_OR_MORE"
        translatedEnum = "THREE_OR_MORE"

		createButton.click()
		then:
		at ShowPage
	}
	
	def "check the entered details"() {
		expect:
		capitalizeEnum == "One"
		defaultEnum == "TWO"
		postfixAllCapsEnum == "Three Or More"
        prefixLowercaseEnum == "three_or_more"
        translatedEnum == "More than three"
	}

	def "edit the details"() {
		when:
		editButton.click()
		then:
		at EditPage
		when:
        $("#enabled").click()
        capitalizeEnum = "TWO"
        defaultEnum = "ONE"
        postfixAllCapsEnum = "TWO"
        prefixLowercaseEnum = "ONE"
        translatedEnum = "ONE"
		updateButton.click()
		then:
		at ShowPage
	}


    def "check in danish listing"() {
        when:
        to ListDanishPage
        then:
        enumDomainRows.size() == 1
        def row = enumDomainRow(0)
        row.capitalizeEnum == "Two"
        row.defaultEnum == "ONE"
        row.postfixAllCapsEnum == "Two"
        row.prefixLowercaseEnum == "one"
        row.translatedEnum == "Kun en"
    }

	def "check in listing"() {
		when:
		to ListPage
		then:
		enumDomainRows.size() == 1
		def row = enumDomainRow(0)
        row.capitalizeEnum == "Two"
        row.defaultEnum == "ONE"
        row.postfixAllCapsEnum == "Two"
        row.prefixLowercaseEnum == "one"
        row.translatedEnum == "Only one"
	}

	def "show enumDomain"() {
		when:
		enumDomainRow(0).showLink.click()
		then:
		at ShowPage
	}
	
	def "delete user"() {
		given:
		def deletedId = id
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListPage
		message == "EnumDomain $deletedId deleted"
		enumDomainRows.size() == 0
	}
}