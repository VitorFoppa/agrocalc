package com.agrocalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.agrocalc.model.ResultadoCalagem;
import com.agrocalc.model.Solo;
import com.agrocalc.service.CalagemService;

public class CalagemServiceTeste {

    private final CalagemService service = new CalagemService();

    @Test
    void deveCalcularValoresCorretamente() {

        Solo solo = new Solo();

        solo.cultura = "soja";
        solo.ca = 2.0;
        solo.mg = 1.0;
        solo.k = 0.2;
        solo.hAl = 6.8;
        solo.prnt = 80.0;

        ResultadoCalagem resultado = service.calcular(solo);

        assertEquals(3.20, resultado.sb);
        assertEquals(10.00, resultado.t);
        assertEquals(32.00, resultado.v1);
        assertEquals(3.50, resultado.nc);
    }

    @Test
    void naoDeveCalcularNcQuandoV1ForMaiorQueV2() {

        Solo solo = new Solo();

        solo.cultura = "soja";
        solo.ca = 8.0;
        solo.mg = 3.0;
        solo.k = 1.0;
        solo.hAl = 1.0;
        solo.prnt = 80.0;

        ResultadoCalagem resultado = service.calcular(solo);

        assertEquals(0.0, resultado.nc);
    }

    @Test
    void deveDefinirV1ComoZeroQuandoTForZero() {

        Solo solo = new Solo();

        solo.cultura = "soja";
        solo.ca = 0;
        solo.mg = 0;
        solo.k = 0;
        solo.hAl = 0;
        solo.prnt = 80;

        ResultadoCalagem resultado = service.calcular(solo);

        assertEquals(0.0, resultado.v1);
    }
}