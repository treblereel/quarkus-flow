package io.quarkiverse.flow.casehub.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface WorkerSettings {

    String name();

    int attempts() default 3;

    long timeout() default 1000;
}
