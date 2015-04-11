package org.gokb.cred

import javax.persistence.Transient

class DSAppliedCriterion {

  KBComponent appliedTo
  DSCriterion criterion
  RefdataValue value

  static mapping = {
    appliedTo column:'dsac_component_fk'
    criterion column:'dsac_crit_fk'
    value column:'dsac_value_fk'
  }

  static constraints = {
    appliedTo(nullable:false, blank:false)
    criterion(nullable:false, blank:false)
    value(nullable:false, blank:false)
  }

}
