package com.devop.aashish.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface GenerateMethod {

    boolean generateGet() default true;

    boolean generateDelete() default false;

    boolean generateGetIn() default false;


}
