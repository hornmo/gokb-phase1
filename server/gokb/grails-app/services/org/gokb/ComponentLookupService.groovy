package org.gokb

import org.gokb.cred.KBComponent
import org.gokb.cred.KBComponentVariantName
import org.gokb.cred.Org
import org.gokb.validation.types.LookedUpValue
import grails.util.GrailsNameUtils

class ComponentLookupService {

  def grailsApplication

  //This one tries to find based on the class and the names it might have (including variants.
  //first it looks at the actual name, then the normalised names, then the alternate names and finally alternate normalised names.
  //it stops when it finds a hit.
  def findComponents(String the_class_name, String the_name) {
	  def results
	  def normalised_name = GOKbTextUtils.normaliseString(the_name);
	  def clazz=Class.forName(the_class_name);
	  log.debug("looking for ${the_class_name}:${the_name}:${normalised_name}")
	  results = clazz.findAllByNameIlike(the_name);
	  log.debug("direct name results: ${results}")
	  if (results.size()==0) {
		  results = clazz.findAllByNormnameIlike(normalised_name)
		  log.debug("norm name results: ${results}")
	  }
	  if (results.size()==0) {
		results = KBComponentVariantName.withCriteria() {
			projections {
				distinct('owner')
			}
			eq 'variantName', the_name
		}
		log.debug("variant name results: ${results}")
	  }
	  if (results.size()==0) {
	    results = KBComponentVariantName.withCriteria() {
			projections {
				distinct('owner')
			}
			eq 'normVariantName', normalised_name
		}
		log.debug("variant norm name results: ${results}")
	  }
	  log.debug("final results: ${results}")
	  results
  }
  
  public <T extends KBComponent> Map<String, T> lookupComponents(String... comp_name_strings) {
    Map<String, T> results = [:]
    for (String comp_name_string : comp_name_strings) {
      T comp = lookupComponent (comp_name_string)
      if (comp) {
        // Add the result.
        results["${comp_name_string}"] = comp
      }
    }
    
    results
  }
  
  public <T extends KBComponent> Map<String, T> lookupComponents(Collection<String> comp_name_strings) {
    Map<String, T> results = [:]
    for (String comp_name_string : comp_name_strings) {
      T comp = lookupComponent (comp_name_string)
      if (comp) {
        // Add the result.
        results["${comp_name_string}"] = comp
      }
    }
    
    results
  }
  
//  private Map<String, ?> vals = [:].withDefault { String key ->
//    lookupComponentDB (key)
//  }  
//  
//  public <T extends KBComponent> T lookupComponent(String comp_name_string) {
//    
//    // Merge this object into the current session if needed.
//    T object = (T)vals.get(comp_name_string)
//    if (object != null && !object.isAttached()) {
//      object = object.merge()
//      vals.put(comp_name_string, object)
//    }
//    return object
//  }
  
  private <T extends KBComponent> T lookupComponent (String comp_name_string) {
    return lookupComponent(comp_name_string,false)
  }

  private <T extends KBComponent> T lookupComponent (String comp_name_string, boolean lock) {

    // The Component
    T comp = null
	log.debug("lookup component: ${comp_name_string}")
    if (comp_name_string) {
      def component_match = comp_name_string =~ "${LookedUpValue.REGEX_TEMPLATE[0]}([^\\:]+)${LookedUpValue.REGEX_TEMPLATE[1]}\$"
	  log.debug("component match ${component_match}")
      if (component_match) {

        log.debug ("Matched the component syntax \"Display Text::{ComponentType:ID}\".")

        try {

          log.debug("Try and lookup ${component_match[0][1]} with ID ${component_match[0][2]}")

          // We have a match.
          Class<? extends KBComponent> c = grailsApplication.getClassLoader().loadClass(
              "org.gokb.cred.${GrailsNameUtils.getClassNameRepresentation(component_match[0][1])}"
          )
          

          // Parse the long.
          long the_id = Long.parseLong( component_match[0][2] )
          
          if (the_id > 0) {
  
            // Try and get the component.
            if ( lock ) {
              comp = c.lock(the_id)
            }
            else {
              comp = c.get(the_id)
            }
  
            if (!c) log.debug ("No component with that ID. Return null.")
          } else {
            log.debug ("Attempting to create a new component.")
            comp = c.newInstance()
          }

        } catch (Throwable t) {
          // Suppress errors here. Just return null.
          log.debug("Unable to parse component string.", t)
        }
      } else {
	  	log.debug("component match returned false")
	  }
    }

    comp
  }
}
