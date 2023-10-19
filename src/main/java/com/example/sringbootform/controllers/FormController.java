package com.example.sringbootform.controllers;

import com.example.sringbootform.models.domain.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("usuario")//se usa para almacenar atributos, se pueden almacenar en caché
public class FormController {
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