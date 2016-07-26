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
import java.util.List;
import javax.lang.model.element.Element;

/**
 * A specialization of {@link Element} that is backed by reflection.
 *
 * @author Vladimir Tsanev
 */
public interface ReflectionElement extends Element, AnnotatedElement {

  @Override
  <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);

  @Override
  ReflectionElement getEnclosingElement();

  @Override
  List<? extends ReflectionElement> getEnclosedElements();

  /**
   * Returns the underlying reflection source object, if applicable.
   *
   * @return the underlying reflection source object, or {@code null} if not applicable.
   */
  AnnotatedElement getSource();
}
