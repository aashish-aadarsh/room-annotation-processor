package com.devop.aashish.annotation.processor.provider;

import com.devop.aashish.annotation.processor.util.Constant;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

public class MethodProvider {

    public static MethodSpec getMethodSpecInsert(TypeName inputParameterType, String inputVariable,
                                                 TypeName returnType) {
        return MethodSpec.methodBuilder(Constant.METHOD_NAME_INSERT_ALL)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(inputParameterType, inputVariable)
                .returns(returnType)
                .addAnnotation(AnnotationProvider.getInsertAnnotation())
                .build();
    }

    public static MethodSpec getMethodSpecQuery(TypeName returnType, String tableName) {
        return MethodSpec.methodBuilder(Constant.METHOD_NAME_FETCH_ALL).addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addAnnotation(AnnotationProvider.getQueryAnnotation(tableName)).returns(returnType).build();
    }

    public static MethodSpec getMethodSpecDelete(String tableName) {
        return MethodSpec.methodBuilder(Constant.METHOD_NAME_DELETE_ALL)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addAnnotation(AnnotationProvider.getDeleteAnnotation(tableName)).build();
    }

    public static MethodSpec getMethodSpecQueryField(
                                                     TypeName returnType,
                                                     String tableName,
                                                     String columnName,
                                                     String inputParam,
                                                     Class fieldType) {

        return MethodSpec.methodBuilder
                (Constant.METHOD_NAME_FETCH_BY + inputParam)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(fieldType,"input"+inputParam)
                .addAnnotation(AnnotationProvider.
                        getQueryAnnotationField(tableName, columnName, "input"+inputParam)).
                        returns(returnType).build();
    }

    public static MethodSpec getMethodSpecDeleteField(
            String tableName,
            String columnName,
            String inputParam,
            Class fieldType) {
        return MethodSpec.methodBuilder
                (Constant.METHOD_NAME_DELETE_BY + inputParam)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(fieldType,"input"+inputParam)
                .addAnnotation(AnnotationProvider.
                        getQueryAnnotationDeleteField(tableName, columnName, "input"+inputParam))
                .build();
    }

    public static MethodSpec getMethodSpecQueryFieldIn(
            TypeName returnType,
            String tableName,
            String columnName,
            String inputParam,
            ArrayTypeName fieldType) {
        return MethodSpec.methodBuilder
                (Constant.METHOD_NAME_FETCH_BY + inputParam+"In")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(fieldType,"input"+inputParam+"s")
                .addAnnotation(AnnotationProvider.
                        getQueryAnnotationFieldIn(tableName, columnName, "input"+inputParam+"s")).
                        returns(returnType).build();

    }

}
