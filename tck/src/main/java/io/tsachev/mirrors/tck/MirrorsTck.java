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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import io.tsachev.mirrors.tck.playground.MyAnnotation;

import org.junit.Test;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.PackageElement;

/**
 * @author Vladimir Tsanev
 */
public abstract class MirrorsTck {

  protected abstract Mirrors createMirrors();

  @Test
  public void packageElementGetAnnotation() {
    Mirrors mirrors = createMirrors();

    PackageElement packageElement =
        mirrors.getElements().getPackageElement("io.tsachev.mirrors.tck.playground");
    MyAnnotation annotation = packageElement.getAnnotation(MyAnnotation.class);

    assertThat(annotation, notNullValue());
    assertThat(annotation.value(), is("package"));
  }

  @Test
  public void packageElementGetAnnotationsByType() {
    Mirrors mirrors = createMirrors();

    PackageElement packageElement =
        mirrors.getElements().getPackageElement("io.tsachev.mirrors.tck.playground");
    MyAnnotation[] annotations = packageElement.getAnnotationsByType(MyAnnotation.class);

    assertThat(annotations, notNullValue());
    assertThat(annotations.length, is(1));
    assertThat(annotations[0].value(), is("package"));
  }


  @Test
  public void packageElementGetAnnotationMirrors() {
    Mirrors mirrors = createMirrors();

    PackageElement packageElement =
        mirrors.getElements().getPackageElement("io.tsachev.mirrors.tck.playground");
    List<? extends AnnotationMirror> annotations = packageElement.getAnnotationMirrors();

    assertThat(annotations, notNullValue());
    assertThat(annotations.size(), is(1));
    AnnotationValue value = annotations.get(0).getElementValues().values().iterator().next();
    assertThat(value.getValue(), is("package"));
  }
}
