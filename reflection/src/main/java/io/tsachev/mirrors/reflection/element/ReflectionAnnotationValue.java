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

import io.tsachev.mirrors.reflection.internal.Constant;
import io.tsachev.mirrors.reflection.type.ReflectionClassDeclaredType;
import io.tsachev.mirrors.reflection.type.ReflectionTypeMirror;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;

/**
 * @author Vladimir Tsanev
 */
public abstract class ReflectionAnnotationValue<T> implements AnnotationValue {

  private final T value;

  public static ReflectionAnnotationValue<?> valueOf(Object value) {
    Objects.requireNonNull(value);
    if (Constant.isConstantValue(value)) {
      return new ReflectionConstantAnnotationValue(value);
    } else if (value instanceof Class<?>) {
      Class<?> source = (Class<?>) value;
      return new ReflectionTypeAnnotationValue(ReflectionClassDeclaredType.valueOf(source));
    } else if (value.getClass().isEnum()) {
      Field field;
      try {
        field = value.getClass().getField(((Enum<?>) value).name());
      } catch (NoSuchFieldException noSuchField) {
        throw new AssertionError("bad enum field", noSuchField);
      }
      return
          new ReflectionEnumConstantAnnotationValue(ReflectionFieldVariableElement.valueOf(field));
    } else if (value.getClass().isAnnotation()) {
      Annotation annotation = (Annotation) value;
      return new ReflectionAnnotationAnnotationValue(ReflectionAnnotationMirror.valueOf(annotation));
    } else if (value.getClass().isArray()) {
      List<ReflectionAnnotationValue<?>> values =
          Arrays.stream((Object[]) value)
              .map(ReflectionAnnotationValue::valueOf)
              .collect(Collectors.toList());
      return new ReflectionArrayAnnotationValue(values);
    }
    throw new IllegalArgumentException("Annotation value must be"
        + " Primitive, String, Class, Enum, Annotation or Array of the preceding, but is: " + value);
  }

  private ReflectionAnnotationValue(T value) {
    this.value = value;
  }

  @Override
  public T getValue() {
    return value;
  }

  private static final class ReflectionArrayAnnotationValue
      extends ReflectionAnnotationValue<List<? extends ReflectionAnnotationValue<?>>> {
    private ReflectionArrayAnnotationValue(List<? extends ReflectionAnnotationValue<?>> value) {
      super(value);
    }

    @Override
    public <R, P> R accept(AnnotationValueVisitor<R, P> visitor, P param) {
      return visitor.visitArray(getValue(), param);
    }
  }

  private static final class ReflectionAnnotationAnnotationValue
      extends ReflectionAnnotationValue<ReflectionAnnotationMirror> {
    private ReflectionAnnotationAnnotationValue(ReflectionAnnotationMirror value) {
      super(value);
    }

    @Override
    public <R, P> R accept(AnnotationValueVisitor<R, P> visitor, P param) {
      return visitor.visitAnnotation(getValue(), param);
    }
  }

  private static final class ReflectionEnumConstantAnnotationValue
      extends ReflectionAnnotationValue<ReflectionFieldVariableElement> {
    private ReflectionEnumConstantAnnotationValue(ReflectionFieldVariableElement value) {
      super(value);
    }

    @Override
    public String toString() {
      return getValue().getEnclosingElement() + "." + getValue();
    }

    @Override
    public <R, P> R accept(AnnotationValueVisitor<R, P> visitor, P param) {
      return visitor.visitEnumConstant(getValue(), param);
    }
  }

  private static final class ReflectionTypeAnnotationValue
      extends ReflectionAnnotationValue<ReflectionTypeMirror> {
    private ReflectionTypeAnnotationValue(ReflectionTypeMirror value) {
      super(value);
    }

    @Override
    public String toString() {
      return getValue() + ".class";
    }

    @Override
    public <R, P> R accept(AnnotationValueVisitor<R, P> visitor, P param) {
      return visitor.visitType(getValue(), param);
    }
  }

  private static final class ReflectionConstantAnnotationValue
      extends ReflectionAnnotationValue<Object> {
    private ReflectionConstantAnnotationValue(Object value) {
      super(value);
    }

    @Override
    public String toString() {
      return Constant.formatConstantValue(getValue());
    }

    @Override
    public <R, P> R accept(AnnotationValueVisitor<R, P> visitor, P param) {
      Object target = getValue();
      if (target instanceof String) {
        return visitor.visitString((String) target, param);
      } else if (target instanceof Boolean) {
        return visitor.visitBoolean((Boolean) target, param);
      } else if (target instanceof Character) {
        return visitor.visitChar((Character) target, param);
      } else if (target instanceof Byte) {
        return visitor.visitByte((Byte) target, param);
      } else if (target instanceof Short) {
        return visitor.visitShort((Short) target, param);
      } else if (target instanceof Integer) {
        return visitor.visitInt((Integer) target, param);
      } else if (target instanceof Long) {
        return visitor.visitLong((Long) target, param);
      } else if (target instanceof Float) {
        return visitor.visitFloat((Float) target, param);
      } else if (target instanceof Double) {
        return visitor.visitDouble((Double) target, param);
      }
      throw new AssertionError("value is not a constant, but is: " + target);
    }
  }
}

