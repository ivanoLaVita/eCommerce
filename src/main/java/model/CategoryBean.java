package model;

import java.io.Serializable;

public class CategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    public CategoryBean() {
        this.name = "";
    }
    
    //getter and setter
    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }
}

