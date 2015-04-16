package org.gokb.cred

import javax.persistence.Transient

class ComponentPerson {

  KBComponent component
  Person person

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    component column:'cp_comp_fk'
    person column:'cp_person_fk'
  }

  static constraints = {
    component(nullable:false, blank:false)
    person(nullable:false, blank:false)
  }

}
