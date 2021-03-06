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

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionParameterVariableElement extends AbstractReflectionVariableElement<Parameter>
    implements ReflectionVariableElement {
  public static ReflectionParameterVariableElement valueOf(Parameter source) {
    return new ReflectionParameterVariableElement(Objects.requireNonNull(source));
  }

  private ReflectionParameterVariableElement(Parameter source) {
    super(source);
  }

  @Override
  public ElementKind getKind() {
    return ElementKind.PARAMETER;
  }

  @Override
  public Set<Modifier> getModifiers() {
    return modifiers(getSource().getModifiers() & java.lang.reflect.Modifier.parameterModifiers());
  }

  @Override
  public ReflectionElement getEnclosingElement() {
    Executable enclosing = getSource().getDeclaringExecutable();
    return AbstractReflectionExecutableElement.valueOf(enclosing);
  }

  @Override
  public Name getSimpleName() {
    return StringName.valueOf(getSource().getName());
  }

  @Override
  public Object getConstantValue() {
    return null;
  }
}
