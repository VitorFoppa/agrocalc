package com.agrocalc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.agrocalc.model.ResultadoCalagem;
import com.agrocalc.model.Solo;

@Service
public class CalagemService {

    public ResultadoCalagem calcular(Solo solo) {
        double v2 = definirV2(solo.cultura);

        
        double sb = arredondar(solo.ca + solo.mg + solo.k);
        double t = arredondar(sb + solo.hAl);
        double v1 = (t > 0) ? arredondar((sb / t) * 100) : 0;
        
        double nc = 0;
        if (v2 > v1) {
            nc = arredondar(((v2 - v1) * t) / solo.prnt);
        }

        return new ResultadoCalagem(solo, sb, t, v1, nc);
    }

    private double arredondar(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double definirV2(String c) {
        return switch (c.toLowerCase()) {
            case "soja" -> 60.0;
            case "milho", "trigo", "cafe" -> 70.0;
            case "pastagem" -> 50.0;
            default -> 60.0;
        };
    }
}