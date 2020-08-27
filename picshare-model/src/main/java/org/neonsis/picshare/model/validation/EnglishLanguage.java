package org.neonsis.picshare.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EnglishLanguageValidator.class)
@Documented
public @interface EnglishLanguage {

    String message() default "{org.neonsis.picshare.model.validation.EnglishLanguage.message}";

    // 0123456789
    boolean withNumbers() default true;

    //.,?!-:()'"[]{}; \t\n
    boolean withPunctuations() default true;

    //~#$%^&*-+=_\\|/@`!'\";:><,.?{}
    boolean withSpecialSymbols() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
