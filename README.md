# AgroCalc - Recomendacao de Calagem

O AgroCalc e uma ferramenta desenvolvida em Java e Spring Boot para auxiliar engenheiros agronomos e produtores no calculo da Necessidade de Calagem (NC). O sistema permite tanto a entrada manual de dados quanto a extracao automatizada de informacoes a partir de laudos laboratoriais em PDF.


## Funcionalidades

- Calculo de Calagem Automatizado: Calcula Soma de Bases (SB), CTC (T), Saturacao por Bases Atual (V1) e a Recomendacao Final (NC).
- Extracao Inteligente de PDF: Motor de busca via Regex que ignora ruidos comuns em laudos (simbolos de LaTeX, cifroes, aspas e quebras de linha).
- Suporte a Multiplas Culturas: Configuracoes pre-definidas de Saturacao Desejada (V2) para Soja, Milho, Cafe, Trigo e Pastagens.
- Relatorio de Impressao: Gera um laudo tecnico formatado pronto para ser entregue ao cliente.
- Selecao de Corretivos: Interface para escolha de diferentes tipos de calcario (Calcítico, Magnesiano, Dolomitico) e seus respectivos PRNTs.
- API REST documentada com Swagger/OpenAPI.
- Publicacao de eventos de calagem via Apache Kafka.


## Tecnologias Utilizadas

- Backend: Java 21, Spring Boot 3.
- Frontend: HTML5, CSS3, JavaScript (Vanilla).
- Processamento de PDF: Apache PDFBox.
- Mensageria: Apache Kafka (KRaft, sem Zookeeper).
- Conteinizacao: Docker e Docker Compose.
- Gerenciamento de Dependencias: Maven.
- Documentacao da API: Swagger / OpenAPI.
- Testes: JUnit 5.


## Kafka

Apos cada calculo de calagem, o sistema publica automaticamente um evento no topico `calagem-resultado` com os dados do solo e o resultado calculado.

Exemplo de mensagem publicada:

```json
{
  "cultura": "soja",
  "tipoCalcario": "calcario",
  "prnt": 80.0,
  "resultado": {
    "sb": 3.8,
    "t": 8.3,
    "v1": 45.78,
    "nc": 1.48
  }
}
```


## Executando com Docker

Pre-requisitos: Docker e Docker Compose instalados.

Suba a aplicacao e o Kafka com um unico comando:

```bash
docker compose up --build
```

A API estara disponivel em `http://localhost:8080`.

Para consumir as mensagens do Kafka em tempo real:

```bash
docker exec -it agrocalc-kafka-1 \
  kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic calagem-resultado \
  --from-beginning
```


## Exemplo de Uso

```bash
curl -X POST http://localhost:8080/calagem \
  -H "Content-Type: application/json" \
  -d '{
    "ca": 2.5,
    "mg": 1.0,
    "k": 0.3,
    "hAl": 4.5,
    "prnt": 80,
    "tipoCalcario": "calcario",
    "cultura": "soja"
  }'
```


## Testes

O projeto possui testes unitarios desenvolvidos com JUnit 5 para validacao das regras de negocio da calagem.

Cobertura dos principais cenarios:

- Calculo correto da Necessidade de Calagem (NC).
- Cenarios onde nao ha necessidade de aplicacao de calcario.
- Tratamento de valores limite (T = 0).
- Validacao das formulas agronomicas implementadas.

Para executar os testes:

```bash
mvn test
```


## Estrutura do Projeto

```
src/main/java/com/agrocalc/
├── controller/       # Endpoints da API (Manual e PDF)
├── producer/         # Publicacao de eventos no Kafka
├── dto/              # Objetos de transferencia de dados (eventos Kafka)
├── config/           # Configuracao do Kafka
├── model/            # Classes de dados (Solo e Resultado)
├── service/          # Logica de calculo e extracao (Parser)
└── AgroCalcApplication.java
src/main/resources/
└── static/           # Frontend (index.html)
```