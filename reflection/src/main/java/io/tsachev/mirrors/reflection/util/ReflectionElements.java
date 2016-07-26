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

package io.tsachev.mirrors.reflection.util;

import io.tsachev.mirrors.reflection.element.ReflectionElement;
import io.tsachev.mirrors.reflection.element.ReflectionExecutableElement;
import io.tsachev.mirrors.reflection.element.ReflectionPackageElement;
import io.tsachev.mirrors.reflection.element.ReflectionTypeElement;

import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * A specialization of {@link Elements} that is backed by reflection.
 *
 * @author Vladimir Tsanev
 */
public interface ReflectionElements extends Elements {

  @Override
  ReflectionPackageElement getPackageElement(CharSequence name);

  @Override
  ReflectionTypeElement getTypeElement(CharSequence name);

  @Override
  Map<? extends ReflectionExecutableElement, ? extends AnnotationValue>
      getElementValuesWithDefaults(AnnotationMirror annotation);

  @Override
  ReflectionPackageElement getPackageOf(Element type);

  @Override
  List<? extends ReflectionElement> getAllMembers(TypeElement type);
}
