package org.gokb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.gokb.cred.Combo;
import org.gokb.cred.KBComponent;
import org.gokb.cred.RefdataValue;

public class ComboPersistedList extends org.apache.commons.collections.list.AbstractListDecorator {
  
  private final KBComponent component;
  private final boolean incoming;
  private final RefdataValue status;
  private final RefdataValue type;
  
  public ComboPersistedList (KBComponent component, RefdataValue status, RefdataValue type, Collection<Combo> vals) {
    this (component, status, type, vals, false );
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ComboPersistedList (KBComponent component, RefdataValue status, RefdataValue type, Collection vals, boolean incoming) {
    super(new ArrayList(vals));
    this.component = component;
    this.status = status;
    this.type = type;
    this.incoming = incoming;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean add(Object element) {
    
    KBComponent comp = (KBComponent)element;
    
    // Try and add the object.
    boolean added = super.add(element);
    
    if (added) {
      
      // Create the Combo.
      Combo combo = new Combo ();
      combo.setStatus(status);
      combo.setType(type);
      
      // Add to the 2 comboLists.
      if (incoming) {
        
        // Incoming combos of component.
        component.getIncomingCombos().add(combo);
        comp.getOutgoingCombos().add(combo);
        
      } else {
        
        // Outgoing combos of component.
        component.getOutgoingCombos().add (combo);
        comp.getIncomingCombos().add (combo);
      }
    }
    
    return added;
  }

  @SuppressWarnings({ "rawtypes"})
  @Override
  public boolean addAll( Collection coll) {
    
    // Ensure we use our extended add.
    boolean added = true;
    for (Object element : coll) {
      added = added && this.add(element);
    }
    
    return added;
  }

  @Override
  public boolean remove(Object element) {
    
    KBComponent comp = (KBComponent)element;
    
    // Removed item successfully.
    boolean removed = super.remove(comp);
    if (removed) {
      
      // Create the Combo.
      Combo combo = new Combo ();
      combo.setStatus(status);
      combo.setType(type);
      
      // Add the from/to components before removing from the 2 lists.
      if (incoming) {
        
        combo.setFromComponent(comp);
        combo.setToComponent(component);
        
        // Incoming combos of component.
        component.getIncomingCombos().remove (combo);
        comp.getOutgoingCombos().remove (combo);
        
      } else {
      
        combo.setFromComponent(component);
        combo.setToComponent(comp);
        
        // Outgoing combos of component.
        comp.getIncomingCombos().remove (combo);
        component.getOutgoingCombos().remove (combo);
      }
    }
    
    return removed;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean removeAll(Collection coll) {
    // Ensure we use our extended remove.
    boolean removed = true;
    for (Object element : coll) {
      removed = removed && this.remove(element);
    }
    
    return removed;
  }

  @SuppressWarnings({ "rawtypes" })
  @Override
  public boolean retainAll(Collection coll) {
    
    boolean removed = true;

    // Remove all items not in the supplied collection.
    Iterator items = this.iterator();
    Object element;
    while (items.hasNext()) {
      element = items.next();
      if (!coll.contains(element)) {
        // Remove.
        removed = removed && this.remove(element);
      }
    }
    
    return removed;
  }
}