# Aula 01 - Ambiente de Desenvolvimento Java

## Objetivo

Esta etapa teve como objetivo preparar e validar o ambiente utilizado para o desenvolvimento do projeto Oficina.

Foram configuradas e verificadas as ferramentas necessárias para trabalhar com Java, Spring Boot, Maven, Git, Docker e PostgreSQL durante as aulas seguintes.

## Java

O projeto utiliza Java 21.

Versao utilizada durante o desenvolvimento:

```text
OpenJDK 21.0.12.1
```

Distribuicao utilizada:

```text
Eclipse Temurin
```

O compilador Java tambem foi verificado:

```text
javac 21.0.12.1
```

A versao 21 do Java e utilizada como referencia para compilacao e execucao do projeto.

## IntelliJ IDEA

O ambiente de desenvolvimento utilizado e o IntelliJ IDEA.

O IntelliJ e utilizado para:

- editar o codigo-fonte;
- organizar os pacotes e classes;
- executar a aplicacao;
- executar testes;
- utilizar o terminal integrado;
- acompanhar erros e avisos durante o desenvolvimento.

## Maven

O projeto utiliza Maven para gerenciamento de dependencias e execucao do processo de build.

Para evitar dependencia de uma instalacao global especifica do Maven, o projeto utiliza Maven Wrapper.

No Windows, os comandos podem ser executados utilizando:

```powershell
.\mvnw.cmd
```

Por exemplo, para executar os testes:

```powershell
.\mvnw.cmd test
```

Em Linux ou macOS, pode ser utilizado:

```bash
./mvnw test
```

## Spring Boot

A aplicacao foi criada utilizando Spring Boot.

O projeto utiliza o servidor web incorporado fornecido pelo ecossistema Spring Boot, permitindo executar a aplicacao sem instalar manualmente um servidor externo.

Quando executada, a aplicacao disponibiliza a API localmente na porta:

```text
8080
```

## Git

O Git e utilizado para controle de versao do projeto.

Durante o desenvolvimento foram utilizados recursos como:

- commits;
- branch principal `main`;
- tags;
- repositorio remoto;
- push para o GitHub.

O historico Git permite identificar os principais marcos correspondentes as aulas do projeto.

## GitHub

O repositorio remoto do projeto esta hospedado no GitHub.

Repositorio:

```text
murilodaon/oficina
```

O remoto utilizado pelo Git possui o nome:

```text
origin
```

A branch principal utilizada e:

```text
main
```

## Docker

O Docker e utilizado para executar o PostgreSQL utilizado pela aplicacao.

Versao verificada durante a configuracao do ambiente:

```text
Docker 29.7.2
```

O projeto tambem utiliza Docker Compose.

Versao verificada:

```text
Docker Compose v5.4.0
```

## PostgreSQL

O banco de dados utilizado pelo projeto e PostgreSQL.

Durante o desenvolvimento, o PostgreSQL e executado em um container Docker utilizando a imagem:

```text
postgres:17
```

A porta utilizada e:

```text
5432
```

Dessa forma, nao e necessario instalar o servidor PostgreSQL diretamente no Windows para executar o projeto.

## Maven Wrapper

Os arquivos do Maven Wrapper fazem parte do repositorio e permitem executar o Maven de forma padronizada.

No Windows:

```powershell
.\mvnw.cmd test
```

Em Linux ou macOS:

```bash
./mvnw test
```

O uso do Wrapper facilita a reproducao do projeto em outros ambientes.

## Verificacao do ambiente

As principais ferramentas necessarias para o desenvolvimento foram verificadas antes da continuidade do projeto.

Entre as verificacoes realizadas estao:

```powershell
java -version
javac -version
git --version
docker --version
docker compose version
```

Tambem foi validada a execucao dos testes utilizando o Maven Wrapper.

## Cuidados com o repositorio

Arquivos especificos da maquina local nao devem ser versionados.

Entre eles estao:

- configuracoes da IDE;
- arquivos gerados pelo build;
- logs;
- arquivos temporarios;
- variaveis de ambiente locais;
- credenciais e outros segredos.

Esses arquivos sao tratados pelas regras definidas no `.gitignore`.

## Resultado

Ao final da configuracao, o ambiente ficou preparado para o desenvolvimento das etapas seguintes do projeto Oficina utilizando Java 21, Spring Boot, Maven, Git, Docker e PostgreSQL.
