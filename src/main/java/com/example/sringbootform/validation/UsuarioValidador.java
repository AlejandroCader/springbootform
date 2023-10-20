package com.example.sringbootform.validation;

import com.example.sringbootform.models.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component//Para trabajar con la inyeccion de dependencias, se utiliza la anotacion Component
public class UsuarioValidador implements Validator {

    @Override //Se crea una clase
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override//Se crean sus metodos
    public void validate(Object target, Errors errors) {
         Usuario usuario = (Usuario)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre",
                "requerido.usuario.nombre");//la forma en la que se llama a lo que se tiene en el file de message.properties

		if(!usuario.getIdentificador()
                .matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {//Expresion regular para validar el identificador
			errors.rejectValue("identificador",
                    "pattern.usuario.identificador");//la forma en la que se llama a lo que se tiene en el file de message.properties
		}

    }

}