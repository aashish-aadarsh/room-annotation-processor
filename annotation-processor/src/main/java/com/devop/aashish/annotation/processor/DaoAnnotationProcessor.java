package com.devop.aashish.annotation.processor;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.devop.aashish.annotation.GenerateDao;
import com.devop.aashish.annotation.GenerateMethod;
import com.devop.aashish.annotation.processor.provider.AnnotationProvider;
import com.devop.aashish.annotation.processor.provider.ClassProvider;
import com.devop.aashish.annotation.processor.provider.InterfaceProvider;
import com.devop.aashish.annotation.processor.provider.MethodProvider;
import com.devop.aashish.annotation.processor.provider.ParameterProvider;
import com.devop.aashish.annotation.processor.provider.ReturnTypeProvider;
import com.devop.aashish.annotation.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import static java.nio.charset.StandardCharsets.UTF_8;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DaoAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotations = new HashSet<>();
        supportedAnnotations.add(GenerateDao.class.getCanonicalName());
        supportedAnnotations.add(Entity.class.getCanonicalName());
        supportedAnnotations.add(ColumnInfo.class.getCanonicalName());
        supportedAnnotations.add(GenerateMethod.class.getCanonicalName());
        return supportedAnnotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(GenerateDao.class);
        generateDefaultTemplate(elements);
        return true;
    }

    private void generateDefaultTemplate(Set<? extends Element> elementsDao) {
        try {
            for (Element element : elementsDao) {

                List<MethodSpec> methodList = new ArrayList<>();
                if (Utils.validateInput(element)) {
                    GenerateDao generateDaoElement = element.getAnnotation(GenerateDao.class);
                    Entity entityElement = element.getAnnotation(Entity.class);
                    if (null != generateDaoElement && null != entityElement) {
                        String packageName = Utils.getPackageName(element);
                        String sourceClassName = element.getSimpleName().toString();
                        String tableName = entityElement.tableName();
                        if (tableName.isEmpty()) {
                            tableName = sourceClassName;
                        }
                        getDefaultMethods(packageName, sourceClassName, tableName, methodList);

                        generateOtherMethods(element, methodList, generateDaoElement,
                                packageName, sourceClassName, tableName);

                        generateJavaFile(packageName, sourceClassName, methodList);
                    }
                } else {
                    messager.printMessage(Diagnostic.Kind.ERROR, "This can be applied only to class !");
                }
            }

        } catch (Exception ex) {
            String errorMsg = ex.getMessage();
            messager.printMessage(Kind.ERROR, " Inside Exception Block block!!!  --> " + errorMsg + "");
        }
    }


    private void getDefaultMethods(String packageName, String sourceClassName, String
            tableName,
                                   List<MethodSpec> methodList) {
        String entitiesName = Character.toLowerCase(sourceClassName.charAt(0))
                + sourceClassName.substring(1).concat("List");
        MethodSpec methodSpecInsert = MethodProvider.getMethodSpecInsert(
                ParameterProvider.provideParameterCustomList(packageName, sourceClassName), entitiesName,
                ReturnTypeProvider.provideList(Long.class));

        MethodSpec methodSpecQuery = MethodProvider.getMethodSpecQuery(ReturnTypeProvider
                        .provideCustomListLiveData(ReturnTypeProvider.provideCustomList(packageName, sourceClassName)),
                tableName);

        MethodSpec methodSpecDelete = MethodProvider.getMethodSpecDelete(tableName);

        methodList.add(methodSpecInsert);
        methodList.add(methodSpecQuery);
        methodList.add(methodSpecDelete);
    }

    private void generateOtherMethods(Element element, List<MethodSpec> methodList,
                                      GenerateDao generateDaoElement,
                                      String packageName, String sourceClassName,
                                      String tableName) {
        Set<? extends Element> fieldElementOfClass =
                new HashSet<>(element.getEnclosedElements());
        for (Element elementField : fieldElementOfClass) {

            if (elementField.getKind().isField()) {
                ColumnInfo columnInfo = elementField.getAnnotation(ColumnInfo.class);
                String columnName = "";
                if (null != columnInfo) {
                    columnName = columnInfo.name();
                    if (ColumnInfo.INHERIT_FIELD_NAME.equals(columnName)) {
                        columnName = elementField.getSimpleName().toString();
                    }
                } else {
                    columnName = elementField.getSimpleName().toString();
                }
                String inputParamName = Character.toUpperCase(elementField.getSimpleName().toString().charAt(0)) +
                        elementField.getSimpleName().toString().substring(1);


                TypeMirror fieldType = elementField.asType();

                if (generateDaoElement.generateAllField()) {
                    generateGetMethod(packageName, columnName, inputParamName,
                            sourceClassName, tableName, Utils.provideClassType(fieldType)
                            , methodList);
                } else if (null != elementField.getAnnotation(GenerateMethod.class)) {

                    GenerateMethod generateMethod = elementField.getAnnotation(GenerateMethod.class);
                    if (generateMethod.generateGet()) {
                        generateGetMethod(packageName, columnName, inputParamName,
                                sourceClassName, tableName, Utils.provideClassType(fieldType)
                                , methodList);
                    }
                }
                if (generateDaoElement.generateDeleteAllField()) {
                    generateDeleteMethod(columnName, inputParamName, tableName, Utils.provideClassType(fieldType)
                            , methodList);
                } else if (null != elementField.getAnnotation(GenerateMethod.class)) {

                    GenerateMethod generateMethod = elementField.getAnnotation(GenerateMethod.class);
                    if (generateMethod.generateDelete()) {
                        generateDeleteMethod(columnName, inputParamName, tableName, Utils.provideClassType(fieldType)
                                , methodList);
                    }
                }

                if (generateDaoElement.generateAllFieldIn()) {
                    generateGetMethodIn(packageName, columnName, inputParamName,
                            sourceClassName, tableName, Utils.provideClassType(fieldType)
                            , methodList);
                } else if (null != elementField.getAnnotation(GenerateMethod.class)) {

                    GenerateMethod generateMethod = elementField.getAnnotation(GenerateMethod.class);
                    if (generateMethod.generateGetIn()) {
                        generateGetMethodIn(packageName, columnName, inputParamName,
                                sourceClassName, tableName, Utils.provideClassType(fieldType)
                                , methodList);
                    }
                }

            }
        }
    }

    private void generateGetMethod(String packageName,
                                   String columnName,
                                   String inputParamName,
                                   String sourceClassName, String tableName,
                                   Class fieldType,
                                   List<MethodSpec> methodList) {
        MethodSpec methodSpecGetQuery = MethodProvider.getMethodSpecQueryField
                (ReturnTypeProvider
                                .provideCustomListLiveData
                                        (ReturnTypeProvider.provideCustomList(packageName, sourceClassName)),
                        tableName, columnName, inputParamName,
                        fieldType);
        methodList.add(methodSpecGetQuery);
    }

    private void generateDeleteMethod(String columnName,
                                      String inputParamName,
                                      String tableName,
                                      Class fieldType,
                                      List<MethodSpec> methodList) {
        MethodSpec methodSpecDeleteQuery = MethodProvider.getMethodSpecDeleteField
                (tableName, columnName, inputParamName, fieldType);
        methodList.add(methodSpecDeleteQuery);
    }

    private void generateGetMethodIn(String packageName,
                                     String columnName,
                                     String inputParamName,
                                     String sourceClassName, String tableName,
                                     Class fieldType,
                                     List<MethodSpec> methodList) {

        MethodSpec methodSpecGetQuery = MethodProvider.getMethodSpecQueryFieldIn
                (ReturnTypeProvider
                                .provideCustomListLiveData
                                        (ReturnTypeProvider.provideCustomList(packageName, sourceClassName)),
                        tableName, columnName, inputParamName,
                        ArrayTypeName.of(fieldType));
        methodList.add(methodSpecGetQuery);
    }

    private void generateJavaFile(String inputPackageName, String sourceClassName, List<MethodSpec> methods) throws IOException {
        String finalPackageName = inputPackageName.concat(".dao");
        String interfaceName = Character.toUpperCase(sourceClassName.charAt(0))
                + sourceClassName.substring(1).concat("Dao");

        List<AnnotationSpec> annotationSpecs = Collections.singletonList(AnnotationProvider.getDaoAnnotation());

        TypeSpec typeSpec = InterfaceProvider.provideInterface(interfaceName, methods, annotationSpecs);
        JavaFile javaFile = JavaFile.builder(finalPackageName, typeSpec).build();
        javaFile.writeTo(filer);

        generateDaoExt(finalPackageName, interfaceName, annotationSpecs);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void generateDaoExt(String finalPackageName, String interfaceName,
                                List<AnnotationSpec> annotationSpecs) throws IOException {
        String workingDir = System.getProperty("user.dir").concat("\\app\\src\\main\\java\\");
        File workingDirectory = new File(workingDir);
        Path outputDirectory = workingDirectory.toPath();
        if (!finalPackageName.isEmpty()) {
            for (String packageComponent : finalPackageName.split("\\.")) {
                outputDirectory = outputDirectory.resolve(packageComponent);
            }
        }
        TypeSpec typeSpecExtFile = ClassProvider.provideAbstractClass(interfaceName
                , finalPackageName, annotationSpecs);
        JavaFile javaFileExt = JavaFile.builder(finalPackageName, typeSpecExtFile).build();
        if (!outputDirectory.toFile().exists()) {
            outputDirectory.toFile().mkdirs();
        }
        String finalFile = outputDirectory.toString().concat("\\" + typeSpecExtFile.name + ".java");
        File finalOutputFile = new File(finalFile);

        if (!finalOutputFile.exists()) {
            Path outputPath = outputDirectory.resolve(typeSpecExtFile.name + ".java");
            try (Writer writer = new OutputStreamWriter(Files.newOutputStream(outputPath), UTF_8)) {
                javaFileExt.writeTo(writer);
            }
        }
    }
}
