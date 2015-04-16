package org.gokb.cred

import javax.persistence.Transient

class Person {

  String label

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    label column:'pers_label'
  }

  static constraints = {
    label(nullable:false, blank:false)
  }

}
