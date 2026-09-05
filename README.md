# Oficina

API REST desenvolvida em Java com Spring Boot para gerenciamento de serviços de uma oficina.

O projeto é desenvolvido de forma incremental durante as aulas da disciplina, aplicando conceitos de Git, Java, Spring Boot, modelagem de domínio, persistência com JPA, banco de dados PostgreSQL, migrações com Liquibase, camada de serviços, DTOs, mapeadores e API REST.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- Spring Data JPA
- PostgreSQL
- Liquibase
- Docker
- Docker Compose
- JUnit
- MockMvc
- Postman
- Git
- GitHub

## Estrutura do projeto

O projeto está organizado em camadas:

```text
src/main/java/com/example/oficina
├── api
│   ├── controller
│   ├── dto
│   ├── exception
│   └── mapper
├── controller
├── domain
├── exception
├── repository
├── service
└── OficinaApplication.java
```

### Domínio

As principais entidades utilizadas atualmente são:

- `CategoriaServico`
- `Servico`
- `Fornecedor`

O projeto também possui o enum `Status`.

### Persistência

A aplicação utiliza PostgreSQL como banco de dados e Spring Data JPA para persistência.

A evolução do esquema do banco é controlada pelo Liquibase por meio dos arquivos localizados em:

```text
src/main/resources/db/changelog
```

O Hibernate é configurado para validar o esquema existente no banco.

## Banco de dados com Docker

O PostgreSQL pode ser executado utilizando Docker Compose.

Para iniciar o banco:

```bash
docker compose up -d
```

Para verificar os containers em execução:

```bash
docker ps
```

O banco de desenvolvimento utiliza a porta padrão:

```text
5432
```

## Executando a aplicação

Antes de iniciar a aplicação, certifique-se de que o PostgreSQL esteja em execução.

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Em Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Por padrão, a aplicação fica disponível em:

```text
http://localhost:8080
```

## Health Check

Para verificar se a aplicação está funcionando:

```text
GET /api/health
```

Exemplo:

```text
http://localhost:8080/api/health
```

## API REST

A aplicação possui endpoints REST para gerenciamento de:

- categorias de serviço;
- fornecedores;
- serviços.

Os controllers estão localizados em:

```text
src/main/java/com/example/oficina/api/controller
```

A API utiliza DTOs para entrada e saída de dados, evitando expor diretamente as entidades JPA.

## Validação e tratamento de erros

Os dados recebidos pela API são validados antes do processamento.

A aplicação também possui tratamento padronizado para situações como:

- dados inválidos;
- recursos não encontrados;
- recursos duplicados;
- JSON inválido.

## Testes

Os testes automatizados abrangem domínio, serviços, persistência e controllers da API.

No Windows:

```bash
.\mvnw.cmd test
```

Em Linux ou macOS:

```bash
./mvnw test
```

## Configuração local

As configurações principais da aplicação estão em:

```text
src/main/resources/application.properties
```

Arquivos com variáveis de ambiente locais, como `.env`, não devem ser enviados ao Git.

O projeto mantém um arquivo:

```text
.env.example
```

como referência das variáveis necessárias, sem armazenar credenciais privadas de ambiente.

## Histórico das aulas

O desenvolvimento do projeto é registrado por commits e tags Git, permitindo consultar os estados correspondentes às etapas concluídas do curso.

Entre os marcos do projeto estão:

- Aula 02 - criação do projeto Spring Boot e endpoint de health;
- Aula 03 - modelagem do domínio;
- Aula 04 - configuração de persistência e banco de dados;
- Aula 05 - repositories e serviços transacionais;
- Aula 06 - evolução do modelo e migrações com Liquibase;
- Aula 07 - API REST com DTOs e mapeadores.

## Repositório

Este projeto é mantido no GitHub no repositório `murilodaon/oficina`.

## Autor

Murilo Daon
