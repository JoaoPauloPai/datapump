/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.datapump.java;

import java.util.List;

/**
 *
 * @author Joao
 */
public class TabelaVO {
    
    private String nome;
    private List<CampoVO> campos;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the campos
     */
    public List<CampoVO> getCampos() {
        return campos;
    }

    /**
     * @param campos the campos to set
     */
    public void setCampos(List<CampoVO> campos) {
        this.campos = campos;
    }
    
    
}
