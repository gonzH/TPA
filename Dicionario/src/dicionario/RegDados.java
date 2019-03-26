/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

/**
 *
 * @author helle
 */
public class RegDados {
    private String palPt;
    private String palEng;
    
    public RegDados(String palPt, String palEng) {
        this.palPt = palPt;
        this.palEng = palEng;
    }
    
    public String getPalPt() {
        return palPt;
    }
    
    public String getPalEng() {
        return palEng;
    }
    
    public void setPalPt(String palPt) {
        this.palPt = palPt;
    }
    
    public void setPalEng(String palEng) {
        this.palEng = palEng;
    }
    
    
}
