package com.example.calorius.objetos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class alimentos {

    @SerializedName("codigo")
    @Expose
    private Integer codigo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("calorias")
    @Expose
    private Integer calorias;

    public Integer getCodigoAlimento() {
        return codigo;
    }

    /*public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }*/

    public String getNombreAlimento() {
        return nombre;
    }

    /*public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }*/

    public Integer getCaloriasAlimento() {
        return calorias;
    }

    /*public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }*/
}
