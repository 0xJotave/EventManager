# EventManager

Sistema distribuído para gestão de eventos, venda de ingressos e processamento de compras, desenvolvido sob uma arquitetura de microsserviços.

## Arquitetura e Tecnologias

O projeto utiliza uma estrutura orientada a serviços para garantir escalabilidade e desacoplamento entre as regras de negócio de eventos e o processamento de ingressos.

### Stack Técnica
- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.4.3
- **Gateway:** Spring Cloud Gateway
- **Segurança:** Keycloak (OAuth2 / OpenID Connect)
- **Bancos de Dados:** MongoDB (Persistência) e Redis (Cache/Rate Limiting)
- **Mensageria:** Apache Kafka
- **Infraestrutura:** Docker & Docker Compose

## Estrutura do Ecossistema

O projeto é dividido em serviços especializados que se comunicam de forma síncrona (REST) e assíncrona (Kafka).

### 1. Gateway Service (Porta 8080)
Atua como o ponto de entrada único da aplicação.
- Centraliza a autenticação via JWT com Keycloak.
- Gerencia o roteamento dinâmico para os serviços internos.
- Implementa resiliência e controle de fluxo.

### 2. Core Service (Porta 8081)
Responsável pelo domínio principal da aplicação.
- Gestão do ciclo de vida de eventos.
- Controle de estoque e categorias de ingressos.
- Processamento de ordens de compra e integração de dados.

### 3. Infraestrutura (infra/)
Contém as configurações necessárias para subir o ambiente de desenvolvimento via Docker, incluindo:
- **MongoDB:** Persistência de documentos.
- **Kafka & Kafka-UI:** Gestão de tópicos e eventos.
- **Redis:** Suporte para o Gateway.
- **Keycloak (Porta 8082):** Servidor de identidade pré-configurado.

## Execução do Projeto

### Pré-requisitos
- Docker e Docker Compose instalados.
- JDK 21 e Maven 3.9+ (caso deseje realizar o build manual).

### Inicialização Rápida
Para subir todo o ecossistema (infraestrutura e serviços):

1. Acesse o diretório de infraestrutura:
   ```bash
   cd infra
   ```

2. Inicie os serviços via Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Verifique se os serviços estão operacionais:
   - Gateway: `http://localhost:8080`
   - Core Service: `http://localhost:8081`
   - Keycloak: `http://localhost:8082`
   - Kafka UI: `http://localhost:8083`

### Variáveis de Ambiente
As configurações de conexão e segurança são gerenciadas através de arquivos `.env` localizados no diretório `infra/`. Certifique-se de validar as seguintes variáveis antes da execução:
- `MONGO_USER` / `MONGO_PASS`: Credenciais do banco de dados.
- `KEYCLOAK_ADMIN_USER` / `KEYCLOAK_ADMIN_PASS`: Acesso administrativo ao console de identidade.
- `GATEWAY_CLIENT_SECRET`: Segredo para integração OAuth2.

## Padrões de Desenvolvimento

O projeto utiliza princípios de **Clean Architecture** e **Hexagonal Architecture** para manter o núcleo de negócio isolado de dependências externas:

- **Domain:** Modelos, contratos e lógica pura de negócio.
- **Application:** Orquestração de fluxos e casos de uso.
- **Adapter:** Implementações técnicas (Controllers, Repositórios MongoDB, Kafka Producers/Consumers).

## Segurança

O acesso aos recursos é protegido por autenticação baseada em tokens. O Gateway valida a assinatura do JWT contra o Keycloak antes de encaminhar a requisição para os serviços internos. Roles e permissões são propagadas no contexto de segurança do Spring Security.

---

# EventManager (English Version)

Distributed system for event management, ticket sales, and purchase processing, developed under a microservices architecture.

## Architecture and Technologies

The project utilizes a service-oriented structure to ensure scalability and decoupling between event business rules and ticket processing.

### Technical Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.4.3
- **Gateway:** Spring Cloud Gateway
- **Security:** Keycloak (OAuth2 / OpenID Connect)
- **Databases:** MongoDB (Persistence) and Redis (Cache/Rate Limiting)
- **Messaging:** Apache Kafka
- **Infrastructure:** Docker & Docker Compose

## Ecosystem Structure

The project is divided into specialized services that communicate synchronously (REST) and asynchronously (Kafka).

### 1. Gateway Service (Port 8080)
Acts as the single entry point for the application.
- Centralizes authentication via JWT with Keycloak.
- Manages dynamic routing to internal services.
- Implements resilience and flow control.

### 2. Core Service (Port 8081)
Responsible for the application's core domain.
- Event lifecycle management.
- Inventory and ticket category control.
- Purchase order processing and data integration.

### 3. Infrastructure (infra/)
Contains the necessary configurations to spin up the development environment via Docker, including:
- **MongoDB:** Document persistence.
- **Kafka & Kafka-UI:** Topic and event management.
- **Redis:** Support for the Gateway.
- **Keycloak (Port 8082):** Pre-configured identity server.

## Running the Project

### Prerequisites
- Docker and Docker Compose installed.
- JDK 21 and Maven 3.9+ (for manual builds).

### Quick Start
To spin up the entire ecosystem (infrastructure and services):

1. Access the infrastructure directory:
   ```bash
   cd infra
   ```

2. Start services via Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Verify service status:
   - Gateway: `http://localhost:8080`
   - Core Service: `http://localhost:8081`
   - Keycloak: `http://localhost:8082`
   - Kafka UI: `http://localhost:8083`

### Environment Variables
Connection and security settings are managed via `.env` files located in the `infra/` directory. Validate the following variables before execution:
- `MONGO_USER` / `MONGO_PASS`: Database credentials.
- `KEYCLOAK_ADMIN_USER` / `KEYCLOAK_ADMIN_PASS`: Administrative access to the identity console.
- `GATEWAY_CLIENT_SECRET`: Secret for OAuth2 integration.

## Development Patterns

The project follows **Clean Architecture** and **Hexagonal Architecture** principles to keep the business core isolated from external dependencies:

- **Domain:** Models, contracts, and pure business logic.
- **Application:** Flow orchestration and use cases.
- **Adapter:** Technical implementations (Controllers, MongoDB Repositories, Kafka Producers/Consumers).

## Security

Resource access is protected by token-based authentication. The Gateway validates the JWT signature against Keycloak before forwarding the request to internal services. Roles and permissions are propagated within the Spring Security context.

