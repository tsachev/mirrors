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
package io.tsachev.mirrors.tck;

import com.google.testing.compile.CompilationRule;
import org.junit.Rule;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class JavacTckTest extends MirrorsTck {
  @Rule
  public CompilationRule compilationRule = new CompilationRule();

  class CompilationRuleMirrors implements Mirrors {

    @Override
    public Elements getElements() {
      return compilationRule.getElements();
    }

    @Override
    public Types getTypes() {
      return compilationRule.getTypes();
    }
  }

  @Override
  protected Mirrors createMirrors() {
    return new CompilationRuleMirrors();
  }
}
