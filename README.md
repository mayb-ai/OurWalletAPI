# 💰 OurWallet API

API REST para controle financeiro familiar, desenvolvida com **Java** e **Spring Boot**.
O sistema permite o gerenciamento de despesas e receitas de forma colaborativa, onde transações são vinculadas a usuários e famílias, garantindo a integridade dos dados e regras de negócio.

## 🚀 Tecnologias Utilizadas

- **Java 17+** (Compatível com JDK 24)
- **Spring Boot 4** (Web, Data JPA, Validation, Security)
- **PostgreSQL** (Banco de Dados Relacional)
- **SpringDoc OpenAPI / Swagger** (v2.8.3 - Documentação Automática)
- **Auth0 java-jwt** (Geração e Validação de Tokens)
- **Lombok** (Produtividade e redução de boilerplate)
- **Maven** (Gerenciamento de dependências)

## 🏗️ Arquitetura do Projeto

O projeto foi estruturado seguindo a **Arquitetura em Camadas (Layered Architecture)** para garantir a separação de responsabilidades, escalabilidade e manutenibilidade:

* **Controller:** Camada responsável pela exposição dos endpoints REST e comunicação HTTP. ("Recepcionista")
* **Service:** Camada responsável pelas regras de negócio e validações. ("Gerente")
* **Repository:** Camada responsável pela persistência e comunicação direta com o banco de dados. ("Estoquista")
* **Entity:** Mapeamento objeto-relacional (ORM) das tabelas do banco.

## 🔌 Documentação (Swagger UI)

A API possui documentação interativa gerada automaticamente via **Swagger**.
Com a aplicação rodando, acesse o link abaixo para visualizar e testar os endpoints:

📍 **Acesse:** `http://localhost:8080/swagger-ui/index.html`

### Endpoints Principais:
* **Families** (`/families`): Criação e listagem de grupos familiares.
* **Users** (`/users`): Cadastro de usuários vinculados a uma família.
* **Transactions** (`/transactions`): Registro de despesas (`EXPENSE`) e receitas (`INCOME`) com vínculo de usuário/família.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java 17 JDK instalado.
* PostgreSQL instalado e rodando.
* Maven instalado.

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/mayb-ai/OurWallet.git](https://github.com/mayb-ai/OurWallet.git)
    ```

2.  **Configure o Banco de Dados:**
    Abra o arquivo `src/main/resources/application.properties` e configure suas credenciais do PostgreSQL:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/ourwallet
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

3.  **Execute a aplicação:**
    Pelo terminal na raiz do projeto:
    ```bash
    mvn spring-boot:run
    ```

## 📅 Histórico de Atualizações

### [27/01/2026] - Implementação de Segurança Completa (JWT) e Dashboard
**Foco:** Blindagem da aplicação com Spring Security e Inteligência de Dados.

- **🔐 Segurança e Autenticação (Core Security):**
  - **Spring Security:** Configuração completa da `SecurityFilterChain`, definindo a política de "Zero Trust" (todas as rotas trancadas, exceto Login e Cadastro).
  - **Criptografia de Senhas:** Implementação do algoritmo **BCrypt** (`PasswordEncoder`). Nenhuma senha é salva em texto puro no banco de dados.
  - **Tokens JWT (JSON Web Token):**
    - Integração com a biblioteca **Auth0** para geração e validação de tokens.
    - Criação do `TokenService` para assinar tokens (HMAC256) com expiração automática.
  - **Filtros de Requisição:** Implementação do `SecurityFilter` (via `OncePerRequestFilter`) para interceptar requisições HTTP, capturar o token no Header `Authorization` e autenticar o usuário antes de chegar ao Controller.
  - **Endpoint de Login:** Criação da rota `POST /auth/login` que recebe credenciais e devolve o Token de acesso.

- **📊 Inteligência de Negócio (Dashboard):**
  - **Endpoint de Resumo:** Criação do `GET /transactions/dashboard`.
  - **Cálculo em Tempo Real:** Lógica implementada no Service para agregar receitas e despesas e calcular o saldo final dinamicamente.
  - **DTOs Específicos:** Uso de Records (`DashboardResponse`, `LoginRequest`, `LoginResponse`) para transferência de dados limpa e eficiente.

- **🛠️ Infraestrutura e Correções:**
  - Ajuste de credenciais de banco de dados no `application.properties` para conexão estável com PostgreSQL.
  - Configuração de CORS e CSRF para compatibilidade com clientes REST (Postman/Front-end).

### [14/01/2026] - Implementação do Core Financeiro e Integração de Membros
**Foco:** Desenvolvimento do fluxo de Transações e refinamento da associação de Usuários em Famílias.

- **Gestão de Transações (Fluxo Financeiro):**
  - Implementação completa do `TransactionService` com regras de negócio.
  - **Validação de Valores:** Uso de `BigDecimal.compareTo` para garantir que apenas valores positivos sejam lançados.
  - **Vínculo Inteligente:** Lógica para identificar automaticamente a Família do usuário no momento da compra e vincular a despesa ao grupo, garantindo consistência nos relatórios.
  - **Prevenção de Efeitos Colaterais:** Tratamento cuidadoso de objetos gerenciados pelo Hibernate (`@Transactional`) para evitar alterações indesejadas na entidade User durante o lançamento de despesas.

- **Gestão de Membros (Join Family):**
  - Novo endpoint `POST /users/{id}/join-family`: Permite que usuários já cadastrados ("solitários") entrem em uma família existente utilizando o `inviteCode`.
  - Tratamento de exceções com `orElseThrow` para códigos de convite inválidos.

- **Melhorias Técnicas:**
  - Refatoração para uso de Injeção de Dependência segura entre `TransactionService` e `UserRepository`.

### [13/01/2026] - Implementação de Regras de Negócio e Vínculos Inteligentes
**Foco:** Refinamento das entidades `User` e `Family` com validações robustas e lógica de convites.

- **Autenticação & Segurança de Dados:**
  - Implementação de validações de entrada (Regex para E-mail, validação de formato de CPF).
  - Sanitização de dados automática (remoção de espaços, padronização para minúsculas, limpeza de formatação de CPF).
  - Regras de unicidade no banco de dados para CPF, E-mail e Username.
- **Lógica de Família:**
  - Criação do sistema de **Invite Code** (Geração automática de códigos de convite `FAM-XXXX` via `@PrePersist`).
  - Implementação de fluxo para vincular usuários a famílias através do código de convite.
  - Ajuste para suportar usuários sem família (relacionamento opcional).
- **Arquitetura:**
  - Uso de `@Transient` para manipulação de dados temporários no DTO de entrada.
  - Refatoração do tratamento de exceções no Service Layer.

### [11/01/2026] - Conclusão da Meta 1: Estrutura Base (MVP)
**Foco:** Configuração inicial do ambiente e estruturação do Backend.

- **Setup do Projeto:**
  - Inicialização com Java 17 e Spring Boot 3.
  - Configuração do Banco de Dados PostgreSQL e integração com Spring Data JPA.
  - Configuração do **Swagger/OpenAPI** para documentação e testes dos endpoints.
- **Entidades e Camadas:**
  - Modelagem das entidades principais: `User`, `Family` e `Transaction`.
  - Implementação do padrão arquitetural em camadas (Controller, Service, Repository).
  - Criação dos primeiros endpoints CRUD para testes de integridade.
---
Desenvolvido por **Maria Gabriela** 👩‍💻
