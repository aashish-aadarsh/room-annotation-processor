package com.devop.aashish.annotation.processor.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

public class Utils {

    public static boolean checkIdValidity(String name, Element e) {
        return SourceVersion.isIdentifier(name) && !SourceVersion.isKeyword(name);
    }

    public static String getPackageName(Element element) {
        List<PackageElement> packageElements = ElementFilter.packagesIn(Collections.singletonList
                (element.getEnclosingElement()));
        Optional<PackageElement> packageElement = packageElements.stream().findAny();
        return packageElement.map(packageElement1 -> packageElement1.getQualifiedName().toString()).orElse(null);
    }

    public static boolean validateInput(Element element) {
        boolean status = true;
        if (element.getKind() != ElementKind.CLASS) {
            status = false;
        }
        return status;
    }

    public static Class<?> provideClassType(TypeMirror fieldType) {
        String fullTypeClassName = fieldType.toString();
        if (fullTypeClassName.contains(String.class.getSimpleName()))
            return String.class;
        else if (fullTypeClassName.contains(Integer.class.getSimpleName()) || fullTypeClassName.equals("int"))
            return Integer.class;
        else if (fullTypeClassName.contains(Long.class.getSimpleName())|| fullTypeClassName.equals("long"))
            return Long.class;
        else if (fullTypeClassName.contains(Double.class.getSimpleName()) || fullTypeClassName.equals("double"))
            return Double.class;
        else if (fullTypeClassName.contains(Float.class.getSimpleName()) || fullTypeClassName.equals("float"))
            return Float.class;
        else if (fullTypeClassName.contains(Boolean.class.getSimpleName()) || fullTypeClassName.equals("boolean"))
            return Boolean.class;
        else if (fullTypeClassName.contains(Date.class.getSimpleName()))
            return Long.class;
        else
            return String.class;
    }
}
