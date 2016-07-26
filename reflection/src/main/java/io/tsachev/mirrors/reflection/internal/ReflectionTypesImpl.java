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

import io.tsachev.mirrors.reflection.element.ReflectionElement;
import io.tsachev.mirrors.reflection.element.ReflectionTypeElement;
import io.tsachev.mirrors.reflection.type.ReflectionNoType;
import io.tsachev.mirrors.reflection.type.ReflectionNullType;
import io.tsachev.mirrors.reflection.type.ReflectionPrimitiveType;
import io.tsachev.mirrors.reflection.util.ReflectionTypes;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;

/**
 * @author Vladimir Tsanev
 */
public class ReflectionTypesImpl implements ReflectionTypes {
  @Override
  public ReflectionElement asElement(TypeMirror type) {
    if (type instanceof TypeVariable) {
      return (ReflectionElement) ((TypeVariable) type).asElement();
    } else if (type instanceof DeclaredType) {
      return (ReflectionElement) ((DeclaredType) type).asElement();
    }
    return null;
  }

  @Override
  public boolean isSameType(TypeMirror t1, TypeMirror t2) {
    return false; // todo
  }

  @Override
  public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
    return false; // todo
  }

  @Override
  public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
    return false; // todo
  }

  @Override
  public boolean contains(TypeMirror t1, TypeMirror t2) {
    return false; // todo
  }

  @Override
  public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
    return false; // todo
  }

  @Override
  public List<? extends TypeMirror> directSupertypes(TypeMirror type) {
    if (type instanceof ExecutableType || type.getKind() == TypeKind.PACKAGE) {
      throw new IllegalArgumentException("Type must not be executable or package, but is: " + type);
    }
    // TODO
    return null;
  }

  @Override
  public TypeMirror erasure(TypeMirror type) {
    return null; // todo
  }

  @Override
  public ReflectionTypeElement boxedClass(PrimitiveType primitiveType) {
    switch (primitiveType.getKind()) {
      //      case BOOLEAN:
      //        return ReflectionPrimitiveType.getBooleanInstance();
      //      case BYTE:
      //        return ReflectionPrimitiveType.getByteInstance();
      //      case SHORT:
      //        return ReflectionPrimitiveType.getShortInstance();
      //      case INT:
      //        return ReflectionPrimitiveType.getIntInstance();
      //      case LONG:
      //        return ReflectionPrimitiveType.getLongInstance();
      //      case CHAR:
      //        return ReflectionPrimitiveType.getCharInstance();
      //      case FLOAT:
      //        return ReflectionPrimitiveType.getFloatInstance();
      //      case DOUBLE:
      //        return ReflectionPrimitiveType.getDoubleInstance();
      default:
        throw new IllegalStateException("Unexpected primitive type: " + primitiveType);
    }
  }

  @Override
  public PrimitiveType unboxedType(TypeMirror type) {
    return null; // todo
  }

  @Override
  public TypeMirror capture(TypeMirror type) {
    return null;// todo
  }

  @Override
  public ReflectionPrimitiveType getPrimitiveType(TypeKind kind) {
    switch (kind) {
      case BOOLEAN:
        return ReflectionPrimitiveType.getBooleanInstance();
      case BYTE:
        return ReflectionPrimitiveType.getByteInstance();
      case SHORT:
        return ReflectionPrimitiveType.getShortInstance();
      case INT:
        return ReflectionPrimitiveType.getIntInstance();
      case LONG:
        return ReflectionPrimitiveType.getLongInstance();
      case CHAR:
        return ReflectionPrimitiveType.getCharInstance();
      case FLOAT:
        return ReflectionPrimitiveType.getFloatInstance();
      case DOUBLE:
        return ReflectionPrimitiveType.getDoubleInstance();
      default:
        throw new IllegalArgumentException("Kind must be primitive kind, but is: " + kind);
    }
  }

  @Override
  public ReflectionNullType getNullType() {
    return ReflectionNullType.getInstance();
  }

  @Override
  public ReflectionNoType getNoType(TypeKind kind) {
    switch (kind) {
      case NONE:
        return ReflectionNoType.getNoneInstance();
      case VOID:
        return ReflectionNoType.getVoidInstance();
      default:
        throw new IllegalArgumentException("Kind must be 'NONE' or 'VOID', but is '" + kind + "'");

    }
  }

  @Override
  public ArrayType getArrayType(TypeMirror componentType) {
    return null; // todo
  }

  @Override
  public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
    return null;// todo
  }

  @Override
  public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
    return null;// todo
  }

  @Override
  public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem, TypeMirror... typeArgs) {
    return null;// todo
  }

  @Override
  public TypeMirror asMemberOf(DeclaredType containing, Element element) {
    return null;// todo
  }
}
