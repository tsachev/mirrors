/*
 * Copyright (c) 2016-2017 the original author or authors.
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

package io.tsachev.mirrors.reflection.internal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConstantTest {

  @Test
  void testBigDecimalConstantThrowsIllegalArgumentException(TestInfo testInfo) {
    assertThrows(IllegalArgumentException.class, () -> Constant.valueOf(BigDecimal.ZERO));
  }

  @Test
  void testBoolean(TestInfo testInfo) {
    assertEquals("false", Constant.valueOf(false).toJavaSource());
    assertEquals("true", Constant.valueOf(true).toJavaSource());
  }

  @Test
  void testInt(TestInfo testInfo) {
    Constant answer = Constant.valueOf(42);
    assertEquals("42", answer.toJavaSource());
  }

  @Test
  void testLong(TestInfo testInfo) {
    Constant answer = Constant.valueOf(42L);
    assertEquals("42L", answer.toJavaSource());
  }
}
