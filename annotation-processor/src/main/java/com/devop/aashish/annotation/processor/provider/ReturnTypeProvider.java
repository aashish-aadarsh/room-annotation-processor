package com.devop.aashish.annotation.processor.provider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class ReturnTypeProvider {

	public static TypeName provideCustomList(String packageName, String className) {
		ClassName customClass = ClassProvider.classNameCustom(packageName, className);
		ClassName list = ClassName.get("java.util", "List");
        return ParameterizedTypeName.get(list, customClass);
	}

	public static TypeName provideList(Class<?> classType) {
		ClassName customClass = ClassProvider.classNameCustom("java.lang", classType.getSimpleName());
		ClassName list = ClassName.get("java.util", "List");
        return ParameterizedTypeName.get(list, customClass);
	}

	public static TypeName provideCustomListLiveData(TypeName customClass) {
		ClassName list = ClassName.get("android.arch.lifecycle", "LiveData");
        return ParameterizedTypeName.get(list, customClass);
	}
}
