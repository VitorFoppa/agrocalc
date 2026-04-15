AgroCalc - Recomendação de Calagem 

O AgroCalc é uma ferramenta desenvolvida em Java e Spring Boot para auxiliar engenheiros agrônomos e produtores no cálculo da Necessidade de Calagem (NC). O sistema permite tanto a entrada manual de dados quanto a extração automatizada de informações a partir de laudos laboratoriais em PDF.


Funcionalidades:

    - Cálculo de Calagem Automatizado: Calcula Soma de Bases (SB), CTC (T), Saturação por Bases Atual ($V_1$) e a Recomendação Final ($NC$).
    - Extração Inteligente de PDF: Motor de busca via Regex que ignora ruídos comuns em laudos (símbolos de LaTeX, cifrões, aspas e quebras de linha).
    - Suporte a Múltiplas Culturas: Configurações pré-definidas de Saturação Desejada ($V_2$) para Soja, Milho, Café, Trigo e Pastagens.
    - Relatório de Impressão: Gera um laudo técnico formatado pronto para ser entregue ao cliente.
    - Seleção de Corretivos: Interface para escolha de diferentes tipos de calcário (Calcítico, Magnesiano, Dolomítico) e seus respectivos PRNTs.


Tecnologias Utilizadas:

    - Backend: Java 21, Spring Boot 3.
    - Frontend: HTML5, CSS3, JavaScript (Vanilla).
    - Processamento de PDF: Apache PDFBox.
    - Gerenciamento de Dependências: Maven.


Estrutura do Projeto:

src/main/java/com/agrocalc/
├── controller/       # Endpoints da API (Manual e PDF)
├── model/            # Classes de dados (Solo e Resultado)
├── service/          # Lógica de cálculo e extração (Parser)
└── AgroCalcApplication.java
src/main/resources/
└── static/           # Frontend (index.html)
