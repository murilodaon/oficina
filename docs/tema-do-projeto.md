# Tema do Projeto - Oficina

## Visao geral

O projeto Oficina consiste em uma API para gerenciamento de servicos oferecidos por uma oficina.

A aplicacao e desenvolvida de forma incremental durante as aulas da disciplina, utilizando Java e Spring Boot.

O sistema permite representar categorias de servico, servicos oferecidos pela oficina e fornecedores relacionados aos servicos.

## Entidades principais

### CategoriaServico

Representa uma categoria utilizada para organizar os servicos oferecidos pela oficina.

Exemplos de categorias:

- Manutencao;
- Eletrica;
- Mecanica;
- Diagnostico.

A categoria possui um identificador e um nome.

### Servico

Representa um servico que pode ser oferecido pela oficina.

Um servico possui informacoes como:

- identificador;
- codigo;
- nome;
- descricao;
- horas estimadas;
- valor base;
- estoque minimo;
- status;
- categoria;
- fornecedor.

Cada servico pertence a uma categoria.

O fornecedor associado ao servico pode ser opcional, de acordo com as regras implementadas no sistema.

### Fornecedor

Representa um fornecedor que pode ser relacionado aos servicos da oficina.

Um fornecedor possui:

- identificador;
- razao social;
- CNPJ;
- status.

O CNPJ deve identificar o fornecedor de forma unica no sistema.

## Status

O projeto utiliza o enum `Status` para representar a situacao dos registros que possuem controle de estado.

O uso de um enum evita valores arbitrarios e permite que os estados aceitos sejam definidos pelo proprio dominio da aplicacao.

## Regras de dominio

O modelo protege regras importantes da aplicacao, evitando a criacao ou alteracao de objetos em estados invalidos.

Entre as regras tratadas pelo projeto estao:

- validacao dos dados obrigatorios;
- identificacao unica dos recursos quando aplicavel;
- associacao de servicos com categorias;
- associacao opcional de servicos com fornecedores;
- controle de status;
- estoque minimo nao negativo;
- tratamento de recursos inexistentes;
- tratamento de recursos duplicados.

## Persistencia

A persistencia dos dados e realizada utilizando Spring Data JPA e PostgreSQL.

As entidades de dominio sao mapeadas para tabelas do banco de dados.

A estrutura do banco e versionada por meio do Liquibase, permitindo que alteracoes no modelo sejam aplicadas por migracoes controladas.

## Camada de servicos

A camada de servicos concentra as operacoes da aplicacao e as regras relacionadas aos casos de uso.

Ela utiliza os repositories para acesso aos dados e controla as operacoes transacionais.

Tambem sao utilizadas excecoes especificas para representar situacoes como:

- recurso nao encontrado;
- recurso duplicado.

## API REST

A aplicacao disponibiliza uma API REST para acesso aos recursos do sistema.

Os principais recursos disponibilizados sao:

- categorias de servico;
- fornecedores;
- servicos.

Os controllers recebem requisicoes HTTP e utilizam a camada de servicos para executar as operacoes da aplicacao.

## DTOs e mapeadores

A API utiliza DTOs para representar os dados de entrada e saida.

Dessa forma, as entidades JPA nao sao expostas diretamente pela API.

Os mapeadores sao responsaveis pela conversao entre:

- DTOs de requisicao;
- entidades de dominio;
- DTOs de resposta.

Essa separacao reduz o acoplamento entre a API e a camada de persistencia.

## Validacao e tratamento de erros

Os dados recebidos pela API sao validados antes da execucao das operacoes.

A aplicacao possui tratamento padronizado para erros, incluindo:

- requisicoes com dados invalidos;
- JSON malformado;
- recursos nao encontrados;
- recursos duplicados.

Os erros sao convertidos em respostas HTTP adequadas para o cliente da API.

## Testes

O projeto possui testes automatizados para verificar o comportamento das diferentes camadas da aplicacao.

Os testes abrangem:

- dominio;
- servicos;
- persistencia e integracao com o banco;
- controllers e API REST.

Os endpoints da API tambem podem ser verificados manualmente utilizando o Postman.

## Tecnologias

As principais tecnologias utilizadas no projeto sao:

- Java 21;
- Spring Boot;
- Spring Web;
- Spring Data JPA;
- Bean Validation;
- PostgreSQL;
- Liquibase;
- Maven;
- Docker;
- Docker Compose;
- JUnit;
- MockMvc;
- Postman;
- Git;
- GitHub.

## Objetivo academico

O objetivo do projeto e aplicar de forma incremental os conceitos apresentados durante as aulas, mantendo um historico de evolucao por meio de commits e tags Git.

Cada etapa adiciona novos conceitos ao mesmo projeto, permitindo acompanhar a evolucao desde a configuracao inicial do repositorio ate a implementacao da API REST.
