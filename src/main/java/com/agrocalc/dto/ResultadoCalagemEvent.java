package com.agrocalc.dto;

import com.agrocalc.model.ResultadoCalagem;

public class ResultadoCalagemEvent {

    private String cultura;
    private String tipoCalcario;
    private Double prnt;
    private ResultadoCalagem resultado;

    public ResultadoCalagemEvent() {}

    public ResultadoCalagemEvent(String cultura, String tipoCalcario, Double prnt, ResultadoCalagem resultado) {
        this.cultura = cultura;
        this.tipoCalcario = tipoCalcario;
        this.prnt = prnt;
        this.resultado = resultado;
    }

    public String getCultura() { return cultura; }
    public void setCultura(String cultura) { this.cultura = cultura; }

    public String getTipoCalcario() { return tipoCalcario; }
    public void setTipoCalcario(String tipoCalcario) { this.tipoCalcario = tipoCalcario; }

    public Double getPrnt() { return prnt; }
    public void setPrnt(Double prnt) { this.prnt = prnt; }

    public ResultadoCalagem getResultado() { return resultado; }
    public void setResultado(ResultadoCalagem resultado) { this.resultado = resultado; }
}