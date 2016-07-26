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

import io.tsachev.mirrors.reflection.type.ReflectionClassDeclaredType;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.AnnotationMirror;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionAnnotationMirror implements AnnotationMirror {
  private final Annotation annotation;

  private ReflectionAnnotationMirror(Annotation annotation) {
    this.annotation = annotation;
  }

  public static ReflectionAnnotationMirror valueOf(Annotation annotation) {
    return new ReflectionAnnotationMirror(Objects.requireNonNull(annotation));
  }

  @Override
  public ReflectionClassDeclaredType getAnnotationType() {
    return ReflectionClassDeclaredType.valueOf(annotation.annotationType());
  }

  @Override
  public Map<? extends ReflectionExecutableElement, ? extends ReflectionAnnotationValue> getElementValues() {
    // we cannot distinguish between missing members (with default) and present members with value same as default.
    Method[] methods = annotation.annotationType().getMethods();
    if (methods.length == 0) {
      return Collections.emptyMap();
    }
    Map<ReflectionExecutableElement, ReflectionAnnotationValue> values = new HashMap<>(methods.length);
    for (Method method : methods) {
      Object value;
      // todo: reflection utility for handling reflective invocations.
      try {
        try {
          value = method.invoke(annotation);
        } catch (IllegalAccessException iae) {
          try {
            method.setAccessible(true);
            value = method.invoke(annotation);
          } catch (IllegalAccessException nested) {
            throw new SecurityException(nested);
          }
        }
      } catch (InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }
      values.put(AbstractReflectionExecutableElement.valueOf(method), ReflectionAnnotationValue.valueOf(value));
    }
    return values;
  }
}
