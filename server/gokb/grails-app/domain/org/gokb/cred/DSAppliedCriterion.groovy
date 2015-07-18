package org.gokb.cred

import javax.persistence.Transient

class DSAppliedCriterion {

  User voter
  KBComponent appliedTo
  DSCriterion criterion
  RefdataValue value

  static mappedBy = [
    notes: 'criterion'
  ]

  static mapping = {
    voter column:'dsac_user'
    appliedTo column:'dsac_component_fk'
    criterion column:'dsac_crit_fk'
    value column:'dsac_value_fk'
  }

  static constraints = {
    voter(nullable:false, blank:false)
    appliedTo(nullable:false, blank:false)
    criterion(nullable:false, blank:false)
    value(nullable:false, blank:false)
  }

}
