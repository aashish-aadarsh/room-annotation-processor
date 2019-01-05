package com.devop.aashish.annotation.processor.provider;

import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class InterfaceProvider {

	public static TypeSpec provideInterface(String interfaceName, List<MethodSpec> methodSpecs,
			List<AnnotationSpec> annotationSpecs) {
		return TypeSpec.interfaceBuilder(interfaceName).addModifiers(Modifier.PUBLIC).addMethods(methodSpecs)
				.addAnnotations(annotationSpecs).build();

	}

	public static TypeSpec provideInterfaceExt(String interfaceName,
											   String packageName,
											   List<AnnotationSpec> annotationSpecs) {
		return TypeSpec.interfaceBuilder(interfaceName+"Ext").
				addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassProvider.classNameCustom(packageName,interfaceName))
				.addJavadoc("This class is created to add custom complex queries. Don't alter the generated class !")
				.addAnnotations(annotationSpecs).build();
	}


}
