/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

/**
 *
 * @author hellesandrocarvalho
 */
public class RegHost {
    private long ip;
    private String nome;
    private long ram;
    private long disco;
    
    public RegHost(long ip, String nome, long ram, long disco) {
        super();
        this.ip = ip;
        this.nome = nome;
        this.ram = ram;
        this.disco = disco;
    }

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getDisco() {
        return disco;
    }

    public void setDisco(long disco) {
        this.disco = disco;
    }
    
    
}