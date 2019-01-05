package com.devop.aashish.annotation.processor.provider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class ParameterProvider {

	public static TypeName provideParameterCustomList(String packageName, String className){
		ClassName customClass = ClassProvider.classNameCustom(packageName, className);
		ClassName list = ClassName.get("java.util", "List");
		TypeName typeName = ParameterizedTypeName.get(list, customClass);
		return typeName;
	}


}
