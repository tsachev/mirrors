/*
 * Copyright (c) 2016 the original author or authors.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

abstract class AbstractReflectionElement<S extends AnnotatedElement> implements ReflectionElement {
  private final S source;

  protected AbstractReflectionElement(S source) {
    this.source = Objects.requireNonNull(source);
  }

  @Override
  public S getSource() {
    return source;
  }

  @Override
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return getSource().getAnnotation(annotationType);
  }

  @Override
  public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
    return getSource().getAnnotationsByType(annotationType);
  }

  @Override
  public Annotation[] getAnnotations() {
    return getSource().getAnnotations();
  }

  @Override
  public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationType) {
    return getSource().getDeclaredAnnotation(annotationType);
  }

  @Override
  public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationType) {
    return getSource().getDeclaredAnnotationsByType(annotationType);
  }

  @Override
  public Annotation[] getDeclaredAnnotations() {
    return getSource().getDeclaredAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return source.isAnnotationPresent(annotationType);
  }


  protected static Set<Modifier> modifiers(int mod) {
    return modifiers(mod, false);
  }

  protected static Set<Modifier> modifiers(int mod, boolean isDefault) {
    Set<Modifier> modifiers = EnumSet.noneOf(Modifier.class);
    if (java.lang.reflect.Modifier.isAbstract(mod)) {
      modifiers.add(Modifier.ABSTRACT);
    }
    if (java.lang.reflect.Modifier.isFinal(mod)) {
      modifiers.add(Modifier.FINAL);
    }
    if (java.lang.reflect.Modifier.isNative(mod)) {
      modifiers.add(Modifier.NATIVE);
    }
    if (java.lang.reflect.Modifier.isPrivate(mod)) {
      modifiers.add(Modifier.PRIVATE);
    }
    if (java.lang.reflect.Modifier.isProtected(mod)) {
      modifiers.add(Modifier.PROTECTED);
    }
    if (java.lang.reflect.Modifier.isPublic(mod)) {
      modifiers.add(Modifier.PUBLIC);
    }
    if (java.lang.reflect.Modifier.isStatic(mod)) {
      modifiers.add(Modifier.STATIC);
    }
    if (java.lang.reflect.Modifier.isStrict(mod)) {
      modifiers.add(Modifier.STRICTFP);
    }
    if (java.lang.reflect.Modifier.isSynchronized(mod)) {
      modifiers.add(Modifier.SYNCHRONIZED);
    }
    if (java.lang.reflect.Modifier.isTransient(mod)) {
      modifiers.add(Modifier.TRANSIENT);
    }
    if (java.lang.reflect.Modifier.isVolatile(mod)) {
      modifiers.add(Modifier.VOLATILE);
    }
    if (isDefault) {
      modifiers.add(Modifier.DEFAULT);
    }
    return modifiers;
  }

  @Override
  public List<? extends ReflectionElement> getEnclosedElements() {
    return null;//TODO
  }

  @Override
  public List<? extends AnnotationMirror> getAnnotationMirrors() {
    return null; // TODO
  }
}
