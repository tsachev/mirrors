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
package io.tsachev.mirrors.reflection.type;

import io.tsachev.mirrors.reflection.element.ReflectionClassTypeElement;
import io.tsachev.mirrors.reflection.element.ReflectionTypeElement;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.lang.model.type.TypeMirror;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionClassDeclaredType extends AbstractReflectionDeclaredType {
  private final Class<?> source;

  public static ReflectionClassDeclaredType valueOf(Class<?> source) {
    return new ReflectionClassDeclaredType(Objects.requireNonNull(source));
  }

  private ReflectionClassDeclaredType(Class<?> source) {
    this.source = source;
  }

  @Override
  public ReflectionTypeElement asElement() {
    return ReflectionClassTypeElement.valueOf(source);
  }

  @Override
  public TypeMirror getEnclosingType() {
    Class<?> enclosing = source.getEnclosingClass();
    if (enclosing != null) {
      return ReflectionClassDeclaredType.valueOf(enclosing);
    }
    return ReflectionNoType.getNoneInstance();
  }

  @Override
  public List<? extends ReflectionTypeMirror> getTypeArguments() {
    return Collections.emptyList();
  }
}
