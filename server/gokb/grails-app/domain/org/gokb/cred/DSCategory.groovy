package org.gokb.cred

import javax.persistence.Transient

class DSCategory {

  String description

  static mapping = {
    description column:'dscat_desc'
  }

  static constraints = {
    url(nullable:true, blank:true)
  }

    def result = [];
    def ql = null;
    ql = DSCategory.findAllByDescriptionIlike("${params.q}%",params)

    if ( ql ) {
      ql.each { t ->
        result.add([id:"${t.class.name}:${t.id}",text:"${t.name}"])
      }
    }

    result
  }

}
