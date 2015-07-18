package org.gokb.cred

import javax.persistence.Transient

class DSNote {

  DSCriterion criterion
  KBComponent component
  User user
  Date commentTimestamp
  String note

  static mapping = {
    id column:'dsn_id'
    criterion column:'dsn_crit_fk'
    component column:'dsn_component_fk'
    user column:'dsn_user_fk'
    commentTimestamp column:'dsn_comment_timestamp'
    note column:'dsn_note_txt', type:'text'
  }

  static constraints = {
    criterion(nullable:true, blank:false)
    component(nullable:true, blank:true)
    user(nullable:true, blank:true)
    commentTimestamp(nullable:true, blank:true)
    note(nullable:true, blank:true)
  }

}
