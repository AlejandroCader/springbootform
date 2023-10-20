package com.example.sringbootform.models.domain;

import com.example.sringbootform.validation.IdentificadorRegex;
import com.example.sringbootform.validation.Requerido;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Usuario {

    @IdentificadorRegex
    private String identificador;
    private String nombre;
    @Requerido
    private String apellido;
    @NotBlank
    @Size(min = 3, max=8) //Crea una validacion, esta validacion se muestra en el idioma del navegador.
    private String username;
    @NotEmpty
    private String password;
    @Requerido
    @Email(message = "correo con formato incorrecto")
    private String email;

    @NotNull
    @Min(5)
    @Max(5000)
    private Integer cuenta;

    @NotNull
    @Future //Sirve para designar que una fecha tiene que ser futura
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
