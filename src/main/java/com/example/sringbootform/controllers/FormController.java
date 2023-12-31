package com.example.sringbootform.controllers;

import com.example.sringbootform.editors.NombreMayusculaEditor;
import com.example.sringbootform.editors.PaisPropertyEditor;
import com.example.sringbootform.editors.RolesEditor;
import com.example.sringbootform.models.domain.Pais;
import com.example.sringbootform.models.domain.Role;
import com.example.sringbootform.models.domain.Usuario;
import com.example.sringbootform.services.PaisService;
import com.example.sringbootform.services.RoleService;
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
import java.util.*;

@Controller
@SessionAttributes("usuario")//se usa para almacenar atributos, se pueden almacenar en caché
public class FormController {

    @Autowired
    private UsuarioValidador validador;
    @Autowired
    private PaisService paisService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PaisPropertyEditor paisEditor;
    @Autowired
    private RolesEditor roleEditor;


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
        binder.registerCustomEditor(Pais.class, "pais", paisEditor);
        binder.registerCustomEditor(Role.class, "roles", roleEditor);
    }

    @ModelAttribute("genero")
    public List<String> genero(){
        return Arrays.asList("Hombre", "Mujer");
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles(){
        return this.roleService.listar();
    }
    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){
        return paisService.listar();
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap(){
        Map<String, String> roles = new HashMap<>();
        roles.put("ROLE_ADMIN","Administrador");
        roles.put("ROLE_USER","Usuario");
        roles.put("ROLE_MODERATOR","Moderador");
        return roles;
    }

    @ModelAttribute("paises")
    public List<String> paises() {
        return Arrays.asList("España", "México", "Chile", "Argentina", "Perú", "Colombia", "Venezuela", "El Salvador");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap() {
        Map<String, String> paises = new HashMap<String, String>();
        paises.put("ES", "España");
        paises.put("MX", "México");
        paises.put("CL", "Chile");
        paises.put("AR", "Argentina");
        paises.put("PE", "Perú");
        paises.put("CO", "Colombia");
        paises.put("VE", "Venezuela");
        paises.put("SV", "El Salvador");
        return paises;
    }

    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario(); //Se instancia el usuario
        usuario.setNombre("Miguel");
        usuario.setApellido("Cruz");
        usuario.setIdentificador("12.456.789-K");
        usuario.setHabilitar(true);
        usuario.setValorSecreto("Algun valor secreto ****");
        usuario.setPais(new Pais(8, "SV", "El Salvador"));
        usuario.setRoles(Arrays.asList(new Role(2,"Usuario", "ROLE_USER")));
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
                           Model model){
        //Se hace uso de los metodos de UsuarioValidador
        //validador.validate(usuario, result); se usa de mejor manera con el Binder
/*
        model.addAttribute("titulo","Resultado Formulario");
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach( fieldError -> {
                errores.put(fieldError.getField(), "El campo "
                        .concat(fieldError.getField()).concat(" ")
                        .concat(fieldError.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "form";
        }*/
/*        if (result.hasErrors()){
            return "form";
        }

        model.addAttribute("usuario",usuario);
        status.setComplete();

        return "resultado";*/
        if (result.hasErrors()){
            model.addAttribute("titulo","Resultado form");
            return "form";
        }
        return "redirect:/ver";
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name = "usuario", required=false) Usuario usuario,
                      Model model, SessionStatus status){

        if(usuario == null) {
            return "redirect:/form";
        }

        model.addAttribute("titulo", "Resultado form");

        status.setComplete();
        return "resultado";
    }
}