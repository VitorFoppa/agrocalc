package com.agrocalc.service;

import com.agrocalc.model.Solo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.*;
import java.text.Normalizer;

@Service
public class SoloParserService {
    private static final Logger logger = LoggerFactory.getLogger(SoloParserService.class);

    
    private static final String[] CA_KEYS = {"calcio", "ca", "cálicio"};
    private static final String[] MG_KEYS = {"magnesio", "mg", "magnésio"};
    private static final String[] K_KEYS = {"potassio", "k", "potássio"};
    private static final String[] HAL_KEYS = {"h+al", "h + al", "acidez potencial", "h.al", "al+h"};

    public Solo extrairDados(String textoOriginal) {
     
        String texto = prepararTexto(textoOriginal);
        Solo solo = new Solo();

        solo.ca = buscarValor(texto, CA_KEYS);
        solo.mg = buscarValor(texto, MG_KEYS);
        solo.k = buscarValor(texto, K_KEYS);
        solo.hAl = buscarValor(texto, HAL_KEYS);

        logger.info("Valores Extraídos: Ca={}, Mg={}, K={}, H+Al={}", solo.ca, solo.mg, solo.k, solo.hAl);
        return solo;
    }

    private String prepararTexto(String t) {
        if (t == null) return "";
        
        String n = Normalizer.normalize(t, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return n.toLowerCase()
                .replaceAll("[\"']", "") 
                .replaceAll("\\s+", " "); 
    }

    private double buscarValor(String texto, String[] chaves) {
        for (String chave : chaves) {
         
            String regex = "(?i)\\b\\Q" + chave + "\\E\\b[^0-9,.]*?(\\d+([\\.,]\\d+)?)";
            
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(texto);

            if (matcher.find()) {
                try {
                    String valorRaw = matcher.group(1).replace(",", ".");
                    double valor = Double.parseDouble(valorRaw);
                    
                   
                    return valor;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return 0.0;
    }
}