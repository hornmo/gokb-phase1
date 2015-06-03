package org.gokb

import grails.test.mixin.TestFor
import org.gokb.cred.EBookDataFile
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TSVIngestionService)
class TSVIngestionServiceSpec extends Specification {
	
    def setup() {
    }

    def cleanup() {
    }

	/*
	void "test kbart2 writeToDB"() {
		given: "a valid kbart2 bean"
		EBookDataFile test_file=new EBookDataFile(packageType:'kbart2');
		def kbart_csv = getClass().getClassLoader().getResourceAsStream("kbart2.tsv")
		test_file.fileData = kbart_csv.getBytes()
		when: "attempt to write to DB"
		def results = service.getKbartBeansFromKBartFile(test_file)
		service.writeToDB(results[0])
		then: "see what happens"
	}
/*	
    void "test ingram conversion"() {
		given: "given valid ingram csv file"
		EBookDataFile test_file=new EBookDataFile(packageType:'ingram');
		def ingram_csv = getClass().getClassLoader().getResourceAsStream("ingram.tsv")
		test_file.fileData = ingram_csv.getBytes()
		when: "attempted to convert to kbart records"
		def results = service.convertToKbart(test_file)
		then: "then should have 24 results"
		assert results !=null
		assert results.size()==24
    }
	
	void "test kbart conversion"() {
		given: "given valid kbart csv file"
		EBookDataFile test_file=new EBookDataFile(packageType:'kbart2');
		def kbart_csv = getClass().getClassLoader().getResourceAsStream("kbart2.tsv")
		test_file.fileData = kbart_csv.getBytes()
		when: "attempted to convert to kbart records"
		def results = service.getKbartBeansFromKBartFile(test_file)
		then: "then should have 9 results"
		assert results !=null
		assert results.size()==9
	}
/*	
	void "test vbp conversion"() {
		given: "given valid vbp csv file"
		EBookDataFile test_file=new EBookDataFile(packageType:'ingram');
		def ingram_csv = getClass().getClassLoader().getResourceAsStream("ingram.tsv")
		test_file.fileData = ingram_csv.getBytes()
		when: "attempted to convert to kbart records"
		def results = service.convertToKbart(test_file)
		then: "then should have 24 results"
		assert results !=null
		assert results.size()==24
	}
*/
}
