/*
 * Copyright (c) 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tsachev.mirrors.reflection.element;

import io.tsachev.mirrors.reflection.type.ReflectionClassDeclaredType;
import io.tsachev.mirrors.reflection.type.ReflectionNoType;
import io.tsachev.mirrors.reflection.type.ReflectionTypeMirror;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.type.TypeMirror;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionClassTypeElement extends AbstractReflectionElement<Class> implements ReflectionTypeElement {

  public static ReflectionClassTypeElement valueOf(Class<?> source) {
    if (source.isPrimitive() || source.isArray()) {
      throw new IllegalArgumentException("TypeElement source must not be primitive or array class, but is: " + source);
    }
    return new ReflectionClassTypeElement(source);
  }

  private ReflectionClassTypeElement(Class<?> source) {
    super(source);
  }

  @Override
  public <R, P> R accept(ElementVisitor<R, P> visitor, P param) {
    return visitor.visitType(this, param);
  }

  @Override
  public TypeMirror asType() {
    return null; //todo
  }

  @Override
  public List<? extends ReflectionTypeParameterElement> getTypeParameters() {
    return null; // todo
  }

  @Override
  public List<? extends ReflectionTypeMirror> getInterfaces() {
    return null; // todo
  }

  @Override
  public Set<Modifier> getModifiers() {
    return modifiers(getSource().getModifiers() & (getSource().isInterface() ? java.lang.reflect.Modifier.interfaceModifiers() : java.lang.reflect.Modifier.constructorModifiers()));
  }

  @Override
  public NestingKind getNestingKind() {
    if (getSource().isAnonymousClass()) {
      return NestingKind.ANONYMOUS;
    } else if (getSource().isLocalClass()) {
      return NestingKind.LOCAL;
    } else if (getSource().isMemberClass()) {
      return NestingKind.MEMBER;
    }
    return NestingKind.TOP_LEVEL;
  }

  @Override
  public Name getQualifiedName() {
    String canonicalName = getSource().getCanonicalName();
    if (canonicalName == null) {
      return StringName.valueOf("");
    }
    return StringName.valueOf(canonicalName);
  }

  @Override
  public Name getSimpleName() {
    String simpleName = getSource().getSimpleName();
    return StringName.valueOf(simpleName);
  }

  @Override
  public ReflectionTypeMirror getSuperclass() {
    if (getSource().equals(Object.class) || getSource().isInterface()) {
      return ReflectionNoType.getNoneInstance();
    }
    return ReflectionClassDeclaredType.valueOf(getSource().getSuperclass());
  }

  @Override
  public ReflectionElement getEnclosingElement() {
    Class enclosing = getSource().getEnclosingClass();
    if (enclosing == null) {
      // package
    } else {
      Executable executable = getSource().getEnclosingConstructor();
      if (executable == null) {
        executable = getSource().getEnclosingMethod();
      }
      if (executable == null) {
        return ReflectionClassTypeElement.valueOf(enclosing);
      } else {
        return AbstractReflectionExecutableElement.valueOf(executable);
      }
    }
    return null;
  }

  @Override
  public ElementKind getKind() {
    if (getSource().isInterface()) {
      if (getSource().isAnnotation()) {
        return ElementKind.ANNOTATION_TYPE;
      } else {
        return ElementKind.INTERFACE;
      }
    } else if (getSource().isEnum()) {
      return ElementKind.ENUM;
    } else {
      return ElementKind.CLASS;
    }
  }

}
