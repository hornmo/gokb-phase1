package org.gokb.cred

import javax.persistence.Transient

class Subject extends KBComponent {

  RefdataValue clsmrk
  RefdataValue scheme

  static manyByCombo = [
	  children: Subject
	]
  
  static hasByCombo = [
	//scheme: Classification,
  	parent: Subject 
  ]

  static mappedByCombo = [
	 children: 'parent'  //my children to my parent in quotes
  ]

  static mapping = {
    clsmrk column:'subj_class'
	scheme column:'subj_scheme'
  }

  static constraints = {
    clsmrk(nullable:true, blank:false)
	scheme(nullable:true, blank:false)
  }
  
  /**
   *  refdataFind generic pattern needed by inplace edit taglib to provide reference data to typedowns and other UI components.
   *  objects implementing this method can be easily located and listed / selected
   */
  static def refdataFind(params) {
	def result = [];
	def ql = null;
	ql = Subject.findAllByNameIlike("${params.q}%",params)

	if ( ql ) {
	  ql.each { t ->
	  result.add([id:"${t.class.name}:${t.id}",text:"${t.name}"])
	  }
	}

	result
  }


}
