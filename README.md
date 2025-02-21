---
# Caixa Eletrônico - API v2.0.0


API para operações bancárias, incluindo cadastro de contas, depósitos, saques e consultas de saldo.

## 📌 Requisitos
Para executar o aplicativo, você precisa:

- **JDK 20** ([Download](https://jdk.java.net/20/))
- **Maven 3.8.7+** ([Download](https://maven.apache.org/download.cgi))

## ⚙️ Trabalhando Localmente

### Compilando o projeto
mvn clean package


### Executando a aplicação
mvn spring-boot:run


A API estará disponível em **http://localhost:8080**.

## 📖 Acessando a Documentação

- **Swagger UI:**  
  👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI JSON:**  
  👉 [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🚀 Tecnologias Utilizadas
- **Java 20**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database (para testes)**
- **SpringDoc OpenAPI (Swagger)**
- **JUnit 5**
