# Coupon API

## Pré-requisitos
- [Docker](https://get.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/#install-compose)
- [JDK-21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/download.cgi)

## Visão geral
A **Coupon API** é um serviço para gerenciamento de cupons de desconto. Permite criar, consultar e deletar cupons aplicando regras de negócio como:

- Validação de código do cupom (alfanumérico e tamanho fixo)
- Validação de descrição, valor de desconto mínimo e data de expiração
- Soft delete (status DELETED)
- Controle de publicação e resgate do cupom

A aplicação segue uma arquitetura **hexagonal** com camadas bem definidas: `Controller`, `Service`, `Domain` e `Repository`.

## Running the application

Você pode executar o aplicativo usando:

Utilizar o maven.

```bash
# Run Maven
./mvnw spring-boot:run
```
ou

Utilizar o docker.

```bash
# Compile
./mvnw clean package
```

```bash
# Up service
docker-compose up --build
```
A API estará disponível em: http://localhost:8080

Para consultar a documentação: http://localhost:8080/swagger-ui/index.html

### Tecnologias Utilizadas
- Docker e Docker Compose — Containerização e execução isolada da aplicação
- Java — Linguagem principal da aplicação
- Spring Boot — Framework para construção da API e configuração simplificada
- Maven — Gerenciador de dependências e build da aplicação
- JUnit — Framework para testes unitários
- Jacoco — Ferramenta para análise de cobertura de testes
- Git — Controle de versão
- Lombok — Redução de boilerplate (getters/setters/constructors)
- Jackson — Serialização e desserialização de JSON
- Swagger/OpenAPI — Documentação da API
- H2 — Banco de dados em memória para testes