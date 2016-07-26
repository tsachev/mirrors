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

import io.tsachev.mirrors.tck.playground.MyAnnotation;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author Vladimir Tsanev
 */
@SupportedAnnotationTypes("io.tsachev.mirrors.tck.playground.MyAnnotation")
public class Processor extends AbstractProcessor {
  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return super.getSupportedAnnotationTypes();
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  static class JavacMirrors implements Mirrors {
    private final ProcessingEnvironment processing;

    public JavacMirrors(ProcessingEnvironment processing) {
      this.processing = processing;
    }

    @Override
    public Elements getElements() {
      return processing.getElementUtils();
    }

    @Override
    public Types getTypes() {
      return processing.getTypeUtils();
    }

  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Mirrors mirrors = new Processor.JavacMirrors(processingEnv);

    run(mirrors, roundEnv);
    return false;
  }

  private void run(Mirrors mirrors, RoundEnvironment roundEnv) {
    MyAnnotation annotation =
        mirrors.getElements().getPackageElement("io.tsachev.mirrors.tck.playground").getAnnotation(MyAnnotation.class);
    processingEnv.getMessager()
        .printMessage(Diagnostic.Kind.WARNING,
            "@MyAnnotation(" + annotation.value() + ") found.");

    PrimitiveType primitiveType = mirrors.getTypes().getPrimitiveType(TypeKind.INT);
    TypeElement typeElement = mirrors.getTypes().boxedClass(primitiveType);
    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
        "PrimitiveType " + primitiveType + " is boxed to " + typeElement + " of kind " + typeElement.getKind());

    roundEnv.getElementsAnnotatedWith(MyAnnotation.class).forEach(element -> {
      if (element instanceof TypeElement) {
        TypeMirror superclass = ((TypeElement) element).getSuperclass();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
            "TypeElement " + element + " has superclass " + superclass);
      }
    });

    TypeElement nestingType = mirrors.getElements().getTypeElement("io.tsachev.mirrors.tck.playground.NestingClass");
    nestingType.getEnclosedElements().forEach(element -> {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "found: " + element.getSimpleName());
      element.getEnclosedElements().forEach(enclosed -> {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "nested found: " + enclosed.getSimpleName());
      });
    });

    TypeElement annotationType = mirrors.getElements().getTypeElement("io.tsachev.mirrors.tck.playground.TestAnnotation");
    annotationType.getEnclosedElements().forEach(element -> {
      if (element instanceof ExecutableElement) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "found: " + ((ExecutableElement) element).getDefaultValue());
      }
    });
  }
}
