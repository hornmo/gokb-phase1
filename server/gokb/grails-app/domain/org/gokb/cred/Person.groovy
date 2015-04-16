package org.gokb.cred

import javax.persistence.Transient

class Person {

  String label;

  static mapping = {
    label column:'subj_heading'
  }

  static constraints = {
    label(nullable:false, blank:false)
  }

}
