package org.gokb.cred

class EBookDataFile extends DataFile {
	String packageName;
	RefdataValue packageType;
	String platformUrl
	
	static constraints = {
		packageName (nullable:false, blank:false)
		packageType (nullable:false, blank:false)
		platformUrl (nullable:true, blank:false)	
	}
	
	static mapping = {
		packageName column:'ebdf_packageName'
		packageType column:'ebdf_packageType'
		platformUrl column:'endf_default_platform_url'
	}

	public String getNiceName() {
		return "eBookDataFile"
	}
	
	/**
	 *  Override so that we only return EbookDataFiles that are editable on the
	 * typedown searches
	 */
	@Override
	static def refdataFind(params) {
	  def result = [];
	  def ql = null;
	  def editable = RefdataCategory.lookupOrCreate('YN', 'Yes').save()
	  ql = DataFile.findAllByNameIlikeAndCanEdit("${params.q}%",editable,params)
	  if ( ql ) {
		ql.each { t ->
		  result.add([id:"${t.class.name}:${t.id}",text:"${t.name}"])
		}
	  }
  
	  result
	}
}
