package org.gokb.cred

import javax.persistence.Transient

class Subject {

  RefdataValue clsmrk
  String heading

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    clsmrk column:'subj_class'
    heading column:'subj_heading'
  }

  static constraints = {
    clsmrk(nullable:true, blank:false)
    heading(nullable:false, blank:false)
  }

}
