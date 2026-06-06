package com.agrocalc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agrocalc.dto.ResultadoCalagemEvent;
import com.agrocalc.model.ResultadoCalagem;
import com.agrocalc.model.Solo;
import com.agrocalc.producer.CalagemProducer;
import com.agrocalc.service.CalagemService;
import com.agrocalc.service.PdfService;
import com.agrocalc.service.SoloParserService;

@RestController
@RequestMapping("/calagem")
public class CalagemController {

    private final CalagemService service;
    private final PdfService pdfService;
    private final SoloParserService parser;
    private final CalagemProducer producer;          

    public CalagemController(
            CalagemService service,
            PdfService pdfService,
            SoloParserService parser,
            CalagemProducer producer) {          
        this.service = service;
        this.pdfService = pdfService;
        this.parser = parser;
        this.producer = producer;                  
    }

    @PostMapping
    public ResultadoCalagem calcular(@RequestBody Solo solo) {
        ResultadoCalagem resultado = service.calcular(solo);


        producer.publicar(new ResultadoCalagemEvent(
            solo.cultura, solo.tipoCalcario, solo.prnt, resultado
        ));

        return resultado;
    }

    @PostMapping("/pdf")
    public ResultadoCalagem uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("prnt") Double prnt,
            @RequestParam("cultura") String cultura,
            @RequestParam("tipoCalcario") String tipoCalcario
    ) throws Exception {

        if (prnt == null) {
            throw new RuntimeException("PRNT é obrigatório");
        }

        String texto = pdfService.extrairTexto(file.getInputStream());

        Solo solo = parser.extrairDados(texto);
        solo.prnt = prnt;
        solo.cultura = cultura;
        solo.tipoCalcario = tipoCalcario;

        ResultadoCalagem resultado = service.calcular(solo);


        producer.publicar(new ResultadoCalagemEvent(
            solo.cultura, solo.tipoCalcario, solo.prnt, resultado
        ));

        return resultado;
    }
}