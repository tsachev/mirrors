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

package io.tsachev.mirrors.tck.playground;

@MyAnnotation("annotation")
public @interface TestAnnotation {
  boolean DEBUG = false;

  double doubleValue() default 0.0000d;

  double doubleNaN() default Double.NaN;

  double doubleNegativeInfinity() default Double.NEGATIVE_INFINITY;

  double doublePositiveInfinity() default Double.POSITIVE_INFINITY;

  float floatValue() default 0.000000f;

  float floatNaN() default Float.NaN;

  float floatNegativeInfinity() default Float.NEGATIVE_INFINITY;

  float floatPositiveInfinity() default Float.POSITIVE_INFINITY;

  String vlado() default DEBUG ? "false" : "true";

  int test() default 1 + 2 ^ (DEBUG ? 2 : 12);

}
