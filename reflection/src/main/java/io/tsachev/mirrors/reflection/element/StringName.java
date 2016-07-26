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

import java.util.Objects;
import javax.lang.model.element.Name;

/**
 * @author Vladimir Tsanev
 */
public final class StringName implements Name {
  private final String name;

  public static StringName valueOf(String name) {
    return new StringName(Objects.requireNonNull(name));
  }

  private StringName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object instanceof StringName) {
      StringName other = (StringName) object;
      return name.equals(other.name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean contentEquals(CharSequence cs) {
    return name.contentEquals(cs);
  }

  @Override
  public int length() {
    return name.length();
  }

  @Override
  public char charAt(int index) {
    return name.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return name.subSequence(start, end);
  }
}
