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

![openapi](https://github.com/user-attachments/assets/680029c9-fb3a-4b6d-af54-3b3ba134625c)



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

  # caso a porta 8080 esteja em uso, vai ser utilizada outra, verificar a saída do console
   App running at:
    - Local:   http://localhost:8080/
    - Network: http://192.168.15.122:8080/

```

![transferencias](https://github.com/user-attachments/assets/3269b1c9-e43c-424d-8193-b40a0caa73bf)



<br /><br />
Emerson Dias de Oliveira<br />
https://github.com/diasRibeirao
