/*
 * MutableValue.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;

public class MutableValue<T> implements Value {

  private final Context context;
  private final String[] identifiers;

  public MutableValue(Context context, String identifier) {
    this.context = context;
    this.identifiers = identifier.split("[.]");
  }

  public T get() {
    try {
      Object target = identifyTarget();
      String name = identifiers[identifiers.length - 1];
      if (target instanceof Map) {
        return (T) ((Map) target).get(name);
      } else {
        PropertyDescriptor prop = findProperty(target, name);
        return (T) prop.getReadMethod().invoke(target);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }

  public void set(Object value) {
    try {
      String name = identifiers[identifiers.length - 1];
      Object target = identifyTarget();
      if (target instanceof Map) {
        ((Map) target).put(name, value);
      } else {
        PropertyDescriptor prop = findProperty(target, name);
        prop.getWriteMethod().invoke(target, value);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }

  private Object identifyTarget() throws Exception {
    Object target = context;
    for (int i = 0; i < identifiers.length - 1; i++) {
      String identifier = identifiers[i];
      if (target instanceof Map) {
        target = ((Map) target).get(identifier);
      } else {
        PropertyDescriptor prop = findProperty(target, identifier);
        target = prop.getReadMethod().invoke(target);
      }
    }
    return target;
  }

  private PropertyDescriptor findProperty(Object target, String name)
      throws Exception {
    BeanInfo bean = Introspector.getBeanInfo(target.getClass());
    for (PropertyDescriptor prop : bean.getPropertyDescriptors()) {
      if (prop.getName().equals(name)) {
        return prop;
      }
    }
    throw new NoSuchAttributeException(name);
  }
}
