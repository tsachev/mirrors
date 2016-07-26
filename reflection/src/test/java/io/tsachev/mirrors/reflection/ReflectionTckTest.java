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

package io.tsachev.mirrors.reflection;

import io.tsachev.mirrors.reflection.util.ReflectionElements;
import io.tsachev.mirrors.reflection.util.ReflectionTypes;
import io.tsachev.mirrors.tck.Mirrors;
import io.tsachev.mirrors.tck.MirrorsTck;
import org.junit.Ignore;

/**
 * @author Vladimir Tsanev
 */
@Ignore
public class ReflectionTckTest extends MirrorsTck {
  @Override
  protected Mirrors createMirrors() {
    class TestMirrors implements Mirrors, ReflectionMirrors {
      @Override
      public ReflectionElements getElements() {
        return null;
      }

      @Override
      public ReflectionTypes getTypes() {
        return null;
      }
    }
    return new TestMirrors();
  }
}
