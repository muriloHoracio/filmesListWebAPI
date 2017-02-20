/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;

/**
 *
 * @author murilo
 */
public class Filme {
    private int ID;
    private String titulo;
    private String genero;
    private int numero;
    private boolean net;
    private boolean emcasa;

    public Filme(int ID, String titulo, String genero, int numero, boolean net, boolean emcasa) {
        this.ID = ID;
        this.titulo = titulo;
        this.genero = genero;
        this.numero = numero;
        this.net = net;
        this.emcasa = emcasa;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isNet() {
        return net;
    }

    public void setNet(boolean net) {
        this.net = net;
    }

    public boolean isEmcasa() {
        return emcasa;
    }

    public void setEmcasa(boolean emcasa) {
        this.emcasa = emcasa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.ID;
        hash = 79 * hash + Objects.hashCode(this.titulo);
        hash = 79 * hash + Objects.hashCode(this.genero);
        hash = 79 * hash + this.numero;
        hash = 79 * hash + (this.net ? 1 : 0);
        hash = 79 * hash + (this.emcasa ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Filme other = (Filme) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.numero != other.numero) {
            return false;
        }
        if (this.net != other.net) {
            return false;
        }
        if (this.emcasa != other.emcasa) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.genero, other.genero)) {
            return false;
        }
        return true;
    }   
}