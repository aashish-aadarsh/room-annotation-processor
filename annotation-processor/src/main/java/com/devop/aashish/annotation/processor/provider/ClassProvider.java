package com.devop.aashish.annotation.processor.provider;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class ClassProvider {

	public static final ClassName daoInterface (){
		return ClassName.get("android.arch.persistence.room", "Dao");
	}
	
	public static final ClassName queryInterface (){
		return ClassName.get("android.arch.persistence.room", "Query");
	}
	
	public static final ClassName insertInterface (){
		return ClassName.get("android.arch.persistence.room", "Insert");
	}
	
	public static final ClassName classNameCustom (String packageName, String className){
		return ClassName.get(packageName, className);
	}

	public static TypeSpec provideAbstractClass(String interfaceName,
												String finalPackageName, List<AnnotationSpec> annotationSpecs) {
		return TypeSpec.classBuilder(interfaceName+"Ext").
				addModifiers(Modifier.PUBLIC,Modifier.ABSTRACT)
				.addSuperinterface(ClassProvider.classNameCustom(finalPackageName,interfaceName))
				.addJavadoc("This abstract class is created to add custom complex queries. " +
						"\nDon't alter the generated class which is implemented ! \nWhile adding dao reference to " +
						"database class, please mark both abstract class and its interface.\n\n")
				.addAnnotations(annotationSpecs).build();
	}
}
