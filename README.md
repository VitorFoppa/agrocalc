# AgroCalc

Aplicação web para recomendação técnica de calagem do solo, desenvolvida com Java 21 e Spring Boot 3. O sistema calcula a Necessidade de Calagem (NC) a partir de dados de análise de solo inseridos manualmente ou extraídos automaticamente de laudos laboratoriais em PDF.

Acesse: [agrocalc-sx77.onrender.com](https://agrocalc-sx77.onrender.com)

---

## Funcionalidades

- **Cálculo automatizado de calagem:** Determina Soma de Bases (SB), CTC (T), Saturação por Bases Atual (V1) e a Necessidade de Calagem (NC).
- **Extração inteligente de PDF:** Motor baseado em Regex que filtra ruídos comuns em laudos laboratoriais (símbolos LaTeX, cifrões, aspas, quebras de linha).
- **Suporte a múltiplas culturas:** Configurações pré-definidas de Saturação Desejada (V2) para Soja, Milho, Café, Trigo e Pastagens.
- **Seleção de corretivos:** Suporte a diferentes tipos de calcário (Calcítico, Magnesiano, Dolomítico) com seus respectivos PRNTs.
- **Relatório de impressão:** Laudo técnico formatado pronto para entrega ao cliente.
- **API REST** documentada com Swagger/OpenAPI.
- **Publicação de eventos** via Apache Kafka.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Java 21, Spring Boot 3 |
| Frontend | HTML5, CSS3, JavaScript (Vanilla) |
| Processamento de PDF | Apache PDFBox |
| Mensageria | Apache Kafka (KRaft, sem Zookeeper) |
| Conteinerização | Docker, Docker Compose |
| Gerenciamento de dependências | Maven |
| Documentação da API | Swagger / OpenAPI |
| Testes | JUnit 5 |

---

## Executando com Docker

**Pré-requisitos:** Docker e Docker Compose instalados.

Suba a aplicação e o Kafka com um único comando:

```bash
docker compose up --build
```

A API estará disponível em `http://localhost:8080`.

Para consumir as mensagens do Kafka em tempo real:

```bash
docker exec -it agrocalc-kafka-1 \
  kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic calagem-resultado \
  --from-beginning
```

---

## Exemplo de Uso

**Requisição:**

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

**Evento publicado no tópico `calagem-resultado`:**

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

---

## Testes

Os testes unitários cobrem as principais regras de negócio do domínio de calagem:

- Cálculo correto da Necessidade de Calagem (NC)
- Cenários onde não há necessidade de aplicação de calcário
- Tratamento de valores limite (T = 0)
- Validação das fórmulas agronômicas implementadas

Para executar:

```bash
mvn test
```

---

## Estrutura do Projeto

```
src/main/java/com/agrocalc/
├── controller/       # Endpoints da API (Manual e PDF)
├── producer/         # Publicação de eventos no Kafka
├── dto/              # Objetos de transferência de dados (eventos Kafka)
├── config/           # Configuração do Kafka
├── model/            # Classes de dados (Solo e Resultado)
├── service/          # Lógica de cálculo e extração (Parser)
└── AgroCalcApplication.java

src/main/resources/
└── static/           # Frontend (index.html)
```

---

## Deploy

A aplicação é hospedada na plataforma Render via Docker, com deploy contínuo integrado ao GitHub. O ambiente de produção utiliza apenas a API REST — o Kafka está disponível exclusivamente no ambiente local para simplificar a infraestrutura e reduzir custos operacionais.
