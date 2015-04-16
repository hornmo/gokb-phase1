package org.gokb.cred

import javax.persistence.Transient

class Subject {

  RefdataValue cls
  String heading

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    cls column:'subj_class'
    heading column:'subj_heading'
  }

  static constraints = {
    cls(nullable:true, blank:false)
    heading(nullable:false, blank:false)
  }

}
