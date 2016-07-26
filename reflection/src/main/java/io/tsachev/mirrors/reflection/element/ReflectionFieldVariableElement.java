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

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionFieldVariableElement extends AbstractReflectionVariableElement<Field>
    implements ReflectionVariableElement {
  public static ReflectionFieldVariableElement valueOf(Field field) {
    return new ReflectionFieldVariableElement(Objects.requireNonNull(field));
  }

  private ReflectionFieldVariableElement(Field field) {
    super(field);
  }

  @Override
  public ElementKind getKind() {
    if (getSource().isEnumConstant()) {
      return ElementKind.ENUM_CONSTANT;
    }
    return ElementKind.FIELD;
  }

  @Override
  public Set<Modifier> getModifiers() {
    return modifiers(getSource().getModifiers() & java.lang.reflect.Modifier.fieldModifiers());
  }

  @Override
  public Name getSimpleName() {
    return StringName.valueOf(getSource().getName());
  }

  @Override
  public Object getConstantValue() {
    Field field = getSource();
    Class<?> type = field.getType();

    if (!(type.equals(String.class) || type.isPrimitive())
        || !java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
      return null;
    }
    // only static field can be supported.
    if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
      return null;
    }

    try {
      return field.get(null);
    } catch (IllegalAccessException iae) {
      try {
        field.setAccessible(true);
        return field.get(null);
      } catch (IllegalAccessException nested) {
        throw new SecurityException(nested);
      }
    }
  }

  @Override
  public ReflectionElement getEnclosingElement() {
    Class<?> type = getSource().getDeclaringClass();
    return ReflectionClassTypeElement.valueOf(type);
  }
}


