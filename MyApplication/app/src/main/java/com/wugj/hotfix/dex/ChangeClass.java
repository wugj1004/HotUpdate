package com.wugj.hotfix.dex;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/8/15
 * </br>
 * version:
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeClass {
    String value() default "";
}
