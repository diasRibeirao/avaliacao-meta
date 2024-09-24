<h1 align="center">
Avaliação Prática de Java 
</h1>

<p align="center">
  Desenvolver tanto a API quanto o front-end (Spring boot e VueJs no front, caso não tenha 
conhecimentos de VueJs, pode ser feito o front com angular). Utilizar a versão 11 do java. 
</p>

## Requisitos
### Backend
- Git instalado - [**Download**](https://git-scm.com/downloads).
- JDK 17 instalado - [**Download**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### Frontend
- Node.js v18.14.1 instalado - [**Download**](https://nodejs.org/en/download/prebuilt-installer)

## Baixando o Projeto
``` bash
  # Clonar o projeto:
  $ git clone https://github.com/diasRibeirao/avaliacao-meta.git
```


## Iniciando Backend
``` bash
  # Entrar no diretório do projeto:
  $ cd avaliacao-meta
  $ cd avaliacao-meta-back
```

## Executando o Projeto
```bash
  # Instalar as dependências:
  $ mvn clean install 

  # Rodar a aplicação:
  $ mvn spring-boot:run

  # Rodar a aplicação com o CMD:
  $ Executar o seguinte comando: java -jar target/avaliacao-meta-back-0.0.1-SNAPSHOT.jar

```

## Banco de Dados

#### [**http://localhost:8081/api/h2-console/login.jsp**](http://localhost:8081/api/h2-console/login.jsp)
```bash
 spring.datasource.driverClassName=org.h2.Driver
 spring.datasource.url=jdbc:h2:mem:metadb
 spring.datasource.username=sa
 spring.datasource.password=
```

## Documentação Online

#### [**http://localhost:8081/api/swagger-ui/index.html**](http://localhost:8081/api/swagger-ui/index.html)

![openapi](https://github.com/user-attachments/assets/b73b1c84-2906-4e41-b9fd-f7a4aa7b8b2f)


<br /><br />

## Iniciando Frontend
``` bash  
  # Abrir outro prompt
  # Entrar no diretório do projeto:
  $ cd avaliacao-meta
  $ cd avaliacao-meta-front
```

## Executando o Projeto
```bash
  # Instalar as dependências:
  $ npm install 

  # Rodar a aplicação:
  $ npm run serve
  
  # Acessar o sistema
  $ http://localhost:8080/

```

![transferencias](https://github.com/user-attachments/assets/666faad8-fec3-4910-9bdb-84d38d437da5)


<br /><br />
Emerson Dias de Oliveira<br />
https://github.com/diasRibeirao
