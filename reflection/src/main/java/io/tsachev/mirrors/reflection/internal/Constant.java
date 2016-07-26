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

package io.tsachev.mirrors.reflection.internal;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a  constant value.
 * <p>
 * Constant values are values that are expressed in the source code as
 * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.28">constant expressions</a>.
 * </p>
 *
 * @author Vladimir Tsnev
 */
public final class Constant {
  private static final Set<Class<?>> TYPES = Collections.unmodifiableSet(
      Stream.of(String.class, Boolean.class, Character.class, Byte.class, Short.class,
          Integer.class, Long.class, Float.class, Double.class).collect(Collectors.toSet()));


  private final Object value;

  private Constant(Object value) {
    this.value = value;
  }

  public static Constant valueOf(Object value) {
    if (!isConstantValue(value)) {
      throw new IllegalArgumentException();
    }
    return new Constant(value);
  }

  public Object getValue() {
    return value;
  }

  public String toJavaSource() {
    return formatConstantValue(value);
  }

  public static boolean isConstantValue(Object value) {
    Objects.requireNonNull(value);
    return TYPES.contains(value.getClass());
  }

  /**
   * Returns a standard string representation of a constant value, quoted and formatted as it would
   * appear in source code.
   *
   * @param value constant value in standard wrapper representation.
   * @return standard string representation of the given constant.
   * @throws IllegalArgumentException if the given value is not a constant value.
   */
  public static String formatConstantValue(Object value) {
    if (value instanceof Byte) {
      return formatByte((Byte) value);
    }
    if (value instanceof Short) {
      return formatShort((Short) value);
    }
    if (value instanceof Long) {
      return formatLong((Long) value);
    }
    if (value instanceof Float) {
      return formatFloat((Float) value);
    }
    if (value instanceof Double) {
      return formatDouble((Double) value);
    }
    if (value instanceof Character) {
      return formatChar((Character) value);
    }
    if (value instanceof String) {
      return formatString((String) value);
    }
    if (value instanceof Integer || value instanceof Boolean) {
      return value.toString();
    } else {
      throw new IllegalArgumentException("Argument is not a primitive type or a string; it "
          + ((value == null) ? "is a null value." : "has class " + value.getClass().getName())
          + ".");
    }
  }

  private static String formatByte(byte value) {
    return String.format("(byte)0x%02x", value);
  }

  private static String formatShort(short value) {
    return String.format("(short)%d", value);
  }

  private static String formatLong(long value) {
    return value + "L";
  }

  private static String formatFloat(float value) {
    if (Float.isNaN(value)) {
      return "0.0f/0.0f";
    } else if (Float.isInfinite(value)) {
      return value < 0 ? "-1.0f/0.0f" : "1.0f/0.0f";
    } else {
      return value + "f";
    }
  }

  private static String formatDouble(double value) {
    if (Double.isNaN(value)) {
      return "0.0/0.0";
    } else if (Double.isInfinite(value)) {
      return value < 0 ? "-1.0/0.0" : "1.0/0.0";
    } else {
      return value + "";
    }
  }

  private static String formatChar(char value) {
    return '\'' + quote(value) + '\'';
  }

  private static String formatString(String value) {
    return '"' + quote(value) + '"';
  }

  /**
   * Escapes each character in a string that has an escape sequence or
   * is non-printable ASCII.  Leaves ASCII characters alone.
   */
  private static String quote(String value) {
    StringBuilder buf = new StringBuilder(value.length());
    for (int i = 0; i < value.length(); i++) {
      buf.append(quote(value.charAt(i)));
    }
    return buf.toString();
  }

  /**
   * Escapes a character if it has an escape sequence or is
   * non-printable ASCII.  Leaves ASCII characters alone.
   */
  private static String quote(char value) {
    switch (value) {
      case '\b':
        return "\\b";
      case '\f':
        return "\\f";
      case '\n':
        return "\\n";
      case '\r':
        return "\\r";
      case '\t':
        return "\\t";
      case '\'':
        return "\\'";
      case '\"':
        return "\\\"";
      case '\\':
        return "\\\\";
      default:
        return isPrintableAscii(value) ? String.valueOf(value) : String.format("\\u%04x", (int) value);
    }
  }

  /**
   * Is a character printable ASCII?
   */
  private static boolean isPrintableAscii(char value) {
    return value >= ' ' && value <= '~';
  }
}
