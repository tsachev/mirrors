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

import io.tsachev.mirrors.reflection.type.ReflectionTypeMirror;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

/**
 * @author Vladimir Tsanev
 */
public abstract class AbstractReflectionExecutableElement<E extends Executable>
    extends AbstractReflectionElement<E> implements ReflectionExecutableElement {

  private final ElementKind kind;

  public static ReflectionExecutableElement valueOf(Executable source) {
    Objects.requireNonNull(source);
    if (source instanceof Constructor<?>) {
      return new ReflectionConstructorExecutableElement<>((Constructor<?>) source);
    }
    return new ReflectionMethodExecutableElement((Method) source);

  }

  private static class ReflectionMethodExecutableElement extends AbstractReflectionExecutableElement<Method> {
    private ReflectionMethodExecutableElement(Method source) {
      super(source, ElementKind.METHOD);
    }

    @Override
    public AnnotationValue getDefaultValue() {
      Object value = getSource().getDefaultValue();
      if (value == null) {
        return null;
      }

      return ReflectionAnnotationValue.valueOf(value);
    }

    @Override
    public Set<Modifier> getModifiers() {
      return modifiers(getSource().getModifiers() & java.lang.reflect.Modifier.methodModifiers(), isDefault());
    }

    @Override
    public boolean isDefault() {
      return getSource().isDefault();
    }
  }

  private static class ReflectionConstructorExecutableElement<T> extends AbstractReflectionExecutableElement<Constructor<T>> {
    private ReflectionConstructorExecutableElement(Constructor<T> source) {
      super(source, ElementKind.CONSTRUCTOR);
    }


    @Override
    public AnnotationValue getDefaultValue() {
      return null; // constructor is never an annotation type element
    }

    @Override
    public Set<Modifier> getModifiers() {
      return modifiers(getSource().getModifiers() & java.lang.reflect.Modifier.constructorModifiers());
    }

    @Override
    public boolean isDefault() {
      return false;
    }
  }

  private AbstractReflectionExecutableElement(E source, ElementKind kind) {
    super(source);
    this.kind = kind;
  }

  @Override
  public ElementKind getKind() {
    return kind;
  }

  @Override
  public ReflectionTypeMirror asType() {
    return null; // todo
  }

  @Override
  public Name getSimpleName() {
    return null; // todo
  }

  @Override
  public List<? extends ReflectionTypeMirror> getThrownTypes() {
    return null; // todo
  }

  @Override
  public ReflectionTypeMirror getReturnType() {
    return null; // todo
  }

  @Override
  public ReflectionTypeMirror getReceiverType() {
    return null; // todo
  }

  @Override
  public boolean isVarArgs() {
    return getSource().isVarArgs();
  }

  @Override
  public <R, P> R accept(ElementVisitor<R, P> visitor, P param) {
    return visitor.visitExecutable(this, param);
  }

  @Override
  public ReflectionTypeElement getEnclosingElement() {
    return ReflectionClassTypeElement.valueOf(getSource().getDeclaringClass());
  }

  @Override
  public List<? extends ReflectionVariableElement> getParameters() {
    return null; //todo
  }

  @Override
  public List<? extends ReflectionTypeParameterElement> getTypeParameters() {
    return null; //todo
  }
}
