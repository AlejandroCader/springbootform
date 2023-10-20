package com.example.sringbootform.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = IdentificadorRegexValidador.class)
@Retention(RUNTIME)//Se le dice como se va a ejecutar
@Target({FIELD, METHOD})//Se definen los campos y los metodos a utilizar
public @interface IdentificadorRegex {
    String message() default "Identificador invalido";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
