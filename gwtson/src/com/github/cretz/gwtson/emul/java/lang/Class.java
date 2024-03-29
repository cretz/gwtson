/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

//Modified from GWT 2.4:
//  http://google-web-toolkit.googlecode.com/svn/releases/2.4/user/super/com/google/gwt/emul/java/lang/Class.java

package java.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Generally unsupported. This class is provided so that the GWT compiler can
 * choke down class literal references.
 * 
 * @param <T> the type of the object
 */
public final class Class<T> implements Type, GenericDeclaration {

  private static final int PRIMITIVE = 0x00000001;
  private static final int INTERFACE = 0x00000002;
  private static final int ARRAY = 0x00000004;
  private static final int ENUM = 0x00000008;

  /**
   * Create a Class object for an array.
   * 
   * @skip
   */
  static <T> Class<T> createForArray(String packageName, String className,
      String seedName, Class<?> componentType) {
    // Initialize here to avoid method inliner
    Class<T> clazz = new Class<T>();
    setName(clazz, packageName, className, seedName);
    clazz.modifiers = ARRAY;
    clazz.superclass = Object.class;
    clazz.componentType = componentType;
    return clazz;
  }

  /**
   * Create a Class object for a class.
   * 
   * @skip
   */
  static <T> Class<T> createForClass(String packageName, String className,
      String seedName, Class<? super T> superclass) {
    // Initialize here to avoid method inliner
    Class<T> clazz = new Class<T>();
    setName(clazz, packageName, className, seedName);
    clazz.superclass = superclass;
    return clazz;
  }

  /**
   * Create a Class object for an enum.
   * 
   * @skip
   */
  static <T> Class<T> createForEnum(String packageName, String className,
      String seedName, Class<? super T> superclass,
      JavaScriptObject enumConstantsFunc, JavaScriptObject enumValueOfFunc) {
    // Initialize here to avoid method inliner
    Class<T> clazz = new Class<T>();
    setName(clazz, packageName, className, seedName);
    clazz.modifiers = (enumConstantsFunc != null) ? ENUM : 0;
    clazz.superclass = clazz.enumSuperclass = superclass;
    clazz.enumConstantsFunc = enumConstantsFunc;
    clazz.enumValueOfFunc = enumValueOfFunc;
    return clazz;
  }

  /**
   * Create a Class object for an interface.
   * 
   * @skip
   */
  static <T> Class<T> createForInterface(String packageName, String className) {
    // Initialize here to avoid method inliner
    Class<T> clazz = new Class<T>();
    setName(clazz, packageName, className, null);
    clazz.modifiers = INTERFACE;
    return clazz;
  }

  /**
   * Create a Class object for a primitive.
   * 
   * @skip
   */
  static Class<?> createForPrimitive(String packageName, String className,
      String seedName) {
    // Initialize here to avoid method inliner
    Class<?> clazz = new Class<Object>();
    setName(clazz, packageName, className, seedName);
    clazz.modifiers = PRIMITIVE;
    return clazz;
  }

  static boolean isClassMetadataEnabled() {
    // This body may be replaced by the compiler
    return true;
  }

  static void setName(Class<?> clazz, String packageName, String className,
      String seedName) {
    if (clazz.isClassMetadataEnabled()) {
      clazz.typeName = packageName + className;
    } else {
      /*
       * The initial "" + in the below code is to prevent clazz.hashCode() from
       * being autoboxed. The class literal creation code is run very early
       * during application start up, before class Integer has been initialized.
       */
      clazz.typeName = "Class$"
          + (seedName != null ? seedName : "" + clazz.hashCode());
    }
  }

  JavaScriptObject enumValueOfFunc;

  int modifiers;

  private Class<?> componentType;

  @SuppressWarnings("unused")
  private JavaScriptObject enumConstantsFunc;

  private Class<? super T> enumSuperclass;

  private Class<? super T> superclass;

  private String typeName;

  /**
   * Not publicly instantiable.
   * 
   * @skip
   */
  private Class() {
  }

  public boolean desiredAssertionStatus() {
    // This body is ignored by the JJS compiler and a new one is
    // synthesized at compile-time based on the actual compilation arguments.
    return false;
  }

  public Class<?> getComponentType() {
    return componentType;
  }

  public native T[] getEnumConstants() /*-{
    return this.@java.lang.Class::enumConstantsFunc
        && (this.@java.lang.Class::enumConstantsFunc)();
  }-*/;

  public String getName() {
    return typeName;
  }

  public Class<? super T> getSuperclass() {
    if (isClassMetadataEnabled()) {
      return superclass;
    } else {
      return null;
    }
  }

  public boolean isArray() {
    return (modifiers & ARRAY) != 0;
  }

  public boolean isEnum() {
    return (modifiers & ENUM) != 0;
  }

  public boolean isInterface() {
    return (modifiers & INTERFACE) != 0;
  }

  public boolean isPrimitive() {
    return (modifiers & PRIMITIVE) != 0;
  }

  public String toString() {
    return (isInterface() ? "interface " : (isPrimitive() ? "" : "class "))
        + getName();
  }

  /**
   * Used by Enum to allow getSuperclass() to be pruned.
   */
  Class<? super T> getEnumSuperclass() {
    return enumSuperclass;
  }
  
  //------------------ gwtson stuff ---------------------
  
  public boolean isAssignableFrom(Class<?> clazz) {
    throw new UnsupportedOperationException();
  }

  public boolean isAnonymousClass() {
    throw new UnsupportedOperationException();
  }

  public boolean isLocalClass() {
    throw new UnsupportedOperationException();
  }

  public Constructor<T> getDeclaredConstructor() {
    throw new UnsupportedOperationException();
  }

  public String getSimpleName() {
    throw new UnsupportedOperationException();
  }

  public T cast(Object object) {
    throw new UnsupportedOperationException();
  }

  public boolean isMemberClass() {
    throw new UnsupportedOperationException();
  }

  public int getModifiers() {
    throw new UnsupportedOperationException();
  }

  public Field[] getDeclaredFields() {
    throw new UnsupportedOperationException();
  }

  public boolean isSynthetic() {
    throw new UnsupportedOperationException();
  }

  public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
    throw new UnsupportedOperationException();
  }
}
