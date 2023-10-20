package com.example.sringbootform.controllers;

import com.example.sringbootform.editors.NombreMayusculaEditor;
import com.example.sringbootform.models.domain.Usuario;
import com.example.sringbootform.validation.UsuarioValidador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("usuario")//se usa para almacenar atributos, se pueden almacenar en caché
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        // se puede utilizar así sin necesidad de
        // llamar el metodo desde allá abajo
        binder.addValidators(validador);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "fechaNacimiento",
                new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, "nombre",
                new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido",
                new NombreMayusculaEditor());
    }

    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario(); //Se instancia el usuario
        usuario.setNombre("Miguel");
        usuario.setApellido("Cruz");
        usuario.setIdentificador("123.456.789-k");
        model.addAttribute("titulo", "Formulario de usuarios");
        model.addAttribute("usuario", usuario); //Se agrega al model
        return "form";
    }

//Primer metodo creado, antes de la implementacion de las validaciones
/*    @PostMapping("/form")
    public String procesar(Model model, @RequestParam(name = "username") String username,
                           @RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword(password);

        model.addAttribute("titulo","Resultado Formulario");
        model.addAttribute("usuario",usuario);
        return "resultado";
    }*/

//Segundo metodo, ya con la implementacion de las validaciones
    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult result,
                           Model model, SessionStatus status){
//se    //hace uso de los metodos de UsuarioValidador
        //validador.validate(usuario, result); se usa de mejor manera con el Binder

        model.addAttribute("titulo","Resultado Formulario");
/*        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach( fieldError -> {
                errores.put(fieldError.getField(), "El campo "
                        .concat(fieldError.getField()).concat(" ")
                        .concat(fieldError.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "form";
        }*/
        if (result.hasErrors()){
            return "form";
        }

        model.addAttribute("usuario",usuario);
        status.setComplete();

        return "resultado";
    }
}