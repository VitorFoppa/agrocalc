package com.agrocalc.producer;

import com.agrocalc.dto.ResultadoCalagemEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CalagemProducer {

    @Autowired
    private KafkaTemplate<String, ResultadoCalagemEvent> kafkaTemplate;

    public void publicar(ResultadoCalagemEvent event) {
        kafkaTemplate.send("calagem-resultado", event);
    }
}