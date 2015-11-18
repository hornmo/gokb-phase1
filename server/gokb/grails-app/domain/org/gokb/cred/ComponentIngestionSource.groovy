package org.gokb.cred

import javax.persistence.Transient

class ComponentIngestionSource {

  KBComponent component
  IngestionProfile profile
  Long elapsed
  Long numtipps
  Long createcount
  Long removecount
  Long updatecount

  static hasMany = [
  ]

  static mappedBy = [
  ]

  static mapping = {
    component column:'cs_comp_fk'
    profile column:'cs_profile_fk'
    elapsed column:'cs_elapsed'
    numtipps column:'cs_numtipps'
    createcount column:'cs_createcount'
    removecount column:'cs_removecount'
    updatecount column:'cs_updatecount'
  }

  static constraints = {
    component(nullable:false, blank:false)
    profile(nullable:false, blank:false)
    elapsed(nullable:true, blank:true)
    numtipps(nullable:true, blank:true)
    createcount(nullable:true, blank:true)
    removecount(nullable:true, blank:true)
    updatecount(nullable:true, blank:true)
  }

}
