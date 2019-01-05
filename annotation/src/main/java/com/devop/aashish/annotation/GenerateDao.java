package com.devop.aashish.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface GenerateDao {

    boolean generateAllField() default false;

    boolean generateDeleteAllField() default false;

    boolean generateAllFieldIn() default false;


}
