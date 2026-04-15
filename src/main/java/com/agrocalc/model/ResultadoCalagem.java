package com.agrocalc.model;

public class ResultadoCalagem {
    
    public double ca;
    public double mg;
    public double k;
    public double hAl;
    public String tipoCalcario;
 
    public double sb;
    public double t;
    public double v1;
    public double nc;

    public ResultadoCalagem(Solo solo, double sb, double t, double v1, double nc) {
        this.ca = solo.ca;
        this.mg = solo.mg;
        this.k = solo.k;
        this.hAl = solo.hAl;
        this.sb = sb;
        this.t = t;
        this.v1 = v1;
        this.nc = nc;
        this.tipoCalcario = solo.tipoCalcario;
    }
}