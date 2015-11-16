package org.gokb.cred

import javax.persistence.Transient

class ComponentIngestionSource {

  KBComponent component
  IngestionProfile profile
  Long elapsed

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    component column:'cs_comp_fk'
    profile column:'cs_profile_fk'
    elapsed column:'cs_elapsed'
  }

  static constraints = {
    component(nullable:false, blank:false)
    profile(nullable:false, blank:false)
    elapsed(nullable:true, blank:true)
  }

}
