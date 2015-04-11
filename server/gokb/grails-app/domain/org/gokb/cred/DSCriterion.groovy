package org.gokb.cred

import javax.persistence.Transient

class DSCriterion {

  CuratoryGroup curator
  DSCategory owner
  String title
  String description
  String explanation

  static mapping = {
    curator column:'dscrit_curator_fk'
    owner column:'dscrit_owner_fk'
    title column:'dscrit_title'
    description column:'dscrit_desc'
    explanation column:'dscrit_expl'
  }

  static hasMany = [
    appliedCriterion : DSAppliedCriterion
  ]

  static mappedBy = [
    appliedCriterion : 'criterion'
  ]

  static constraints = {
    curator(nullable:true, blank:false)
    owner(nullable:false, blank:false)
    title(nullable:false, blank:false)
    description(nullable:true, blank:false)
    explanation(nullable:true, blank:false)
  }

 static def refdataFind(params) {
    def result = [];
    def ql = null;
    ql = DSCriterion.findAllByDescriptionIlike("${params.q}%",params)

    if ( ql ) {
      ql.each { t ->
        result.add([id:"${t.class.name}:${t.id}",text:"${t.name}"])
      }
    }

    result
  }

}
