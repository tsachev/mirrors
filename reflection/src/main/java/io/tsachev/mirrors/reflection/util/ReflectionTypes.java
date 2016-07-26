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
import io.tsachev.mirrors.reflection.element.ReflectionTypeElement;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * A specialization of {@link Types} that is backed by reflection.
 *
 * @author Vladimir Tsanev
 */
public interface ReflectionTypes extends Types {

  @Override
  ReflectionElement asElement(TypeMirror type);

  @Override
  ReflectionTypeElement boxedClass(PrimitiveType primitiveType);

}

