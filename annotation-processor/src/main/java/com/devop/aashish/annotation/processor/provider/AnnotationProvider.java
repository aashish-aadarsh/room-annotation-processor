package com.devop.aashish.annotation.processor.provider;

import com.devop.aashish.annotation.processor.util.Constant;
import com.squareup.javapoet.AnnotationSpec;

public class AnnotationProvider {

	public static AnnotationSpec getDaoAnnotation() {
		return AnnotationSpec.builder(ClassProvider.daoInterface()).build();
	}

	public static AnnotationSpec getQueryAnnotation(String tableName) {
		return AnnotationSpec.builder(ClassProvider.queryInterface())
				.addMember("value","\""+Constant.SELECT_ALL_QUERY+tableName+"\"")
				.build();
	}

	public static AnnotationSpec getInsertAnnotation() {
		return AnnotationSpec.builder(ClassProvider.insertInterface())
				.addMember("onConflict",
						ClassProvider.classNameCustom("android.arch.persistence.room",
                                "OnConflictStrategy.REPLACE").toString())
				.build();
	}
	
	public static AnnotationSpec getDeleteAnnotation(String tableName) {
		return AnnotationSpec.builder(ClassProvider.queryInterface())
				.addMember("value","\""+Constant.DELETE_ALL_QUERY+tableName+"\"")
				.build();
	}

    public static AnnotationSpec getQueryAnnotationField(String tableName,String fieldName,String inputParamName) {
	    StringBuilder query   = new StringBuilder();
	    query.append("\"" + Constant.SELECT_ALL_QUERY).append(tableName);
	    query.append(" WHERE ").append(tableName).append(".").append(fieldName).append(" =:").append(inputParamName);
	    query.append("\"");
        return AnnotationSpec.builder(ClassProvider.queryInterface())
                .addMember("value",query.toString())
                .build();
    }

    public static AnnotationSpec getQueryAnnotationDeleteField(String tableName,String fieldName,String inputParamName) {
        StringBuilder query   = new StringBuilder();
        query.append("\"" + Constant.DELETE_ALL_QUERY).append(tableName);
        query.append(" WHERE ").append(tableName).append(".").append(fieldName).append(" =:").append(inputParamName);
        query.append("\"");
        return AnnotationSpec.builder(ClassProvider.queryInterface())
                .addMember("value",query.toString())
                .build();
    }

    public static AnnotationSpec getQueryAnnotationFieldIn(String tableName,String fieldName,String inputParamName) {
        StringBuilder query   = new StringBuilder();
        query.append("\"" + Constant.SELECT_ALL_QUERY).append(tableName);
        query.append(" WHERE ").append(tableName).append(".").append(fieldName).append(" IN (:").append(inputParamName);
        query.append(")\"");
        return AnnotationSpec.builder(ClassProvider.queryInterface())
                .addMember("value",query.toString())
                .build();
    }

}
