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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionNoType extends AbstractReflectionTypeMirror implements NoType {

  private static final ReflectionNoType noneInstance = new ReflectionNoType("none");
  private static final ReflectionNoType packageInstance = new ReflectionNoType("package");
  private static final ReflectionNoType voidInstance = new ReflectionNoType("void");

  private final String name;

  private ReflectionNoType(String name) {
    super(TypeKind.NONE);
    this.name = name;
  }

  @Override
  public <R, P> R accept(TypeVisitor<R, P> visitor, P param) {
    return visitor.visitNoType(this, param);
  }


  @Override
  public List<? extends AnnotationMirror> getAnnotationMirrors() {
    return Collections.emptyList();
  }

  @Override
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
    return (A[]) Array.newInstance(annotationType, 0);
  }

  @Override
  public String toString() {
    return name;
  }

  public static ReflectionNoType getNoneInstance() {
    return noneInstance;
  }

  public static ReflectionNoType getPackageInstance() {
    return packageInstance;
  }

  public static ReflectionNoType getVoidInstance() {
    return voidInstance;
  }
}
