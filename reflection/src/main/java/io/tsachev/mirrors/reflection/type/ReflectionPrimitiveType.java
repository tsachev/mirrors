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

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionPrimitiveType extends AbstractReflectionTypeMirror implements PrimitiveType {

  private static final ReflectionPrimitiveType BOOLEAN_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.BOOLEAN, boolean.class);

  private static final ReflectionPrimitiveType BYTE_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.BYTE, byte.class);

  private static final ReflectionPrimitiveType CHAR_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.CHAR, char.class);

  private static final ReflectionPrimitiveType SHORT_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.SHORT, short.class);

  private static final ReflectionPrimitiveType INT_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.INT, int.class);

  private static final ReflectionPrimitiveType LONG_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.LONG, long.class);

  private static final ReflectionPrimitiveType FLOAT_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.FLOAT, float.class);

  private static final ReflectionPrimitiveType DOUBLE_INSTANCE =
      new ReflectionPrimitiveType(TypeKind.DOUBLE, double.class);

  private final Class<?> source;

  private ReflectionPrimitiveType(TypeKind kind, Class<?> source) {
    super(kind);
    this.source = source;
  }

  public static ReflectionPrimitiveType getBooleanInstance() {
    return BOOLEAN_INSTANCE;
  }

  public static ReflectionPrimitiveType getByteInstance() {
    return BYTE_INSTANCE;
  }

  public static ReflectionPrimitiveType getCharInstance() {
    return CHAR_INSTANCE;
  }

  public static ReflectionPrimitiveType getShortInstance() {
    return SHORT_INSTANCE;
  }

  public static ReflectionPrimitiveType getIntInstance() {
    return INT_INSTANCE;
  }

  public static ReflectionPrimitiveType getLongInstance() {
    return LONG_INSTANCE;
  }

  public static ReflectionPrimitiveType getFloatInstance() {
    return FLOAT_INSTANCE;
  }

  public static ReflectionPrimitiveType getDoubleInstance() {
    return DOUBLE_INSTANCE;
  }

  @Override
  public <R, P> R accept(TypeVisitor<R, P> visitor, P param) {
    return visitor.visitPrimitive(this, param);
  }

  @Override
  public String toString() {
    return source.getName();
  }
}
