# 💰 OurWallet API

API REST para controle financeiro familiar, desenvolvida com **Java** e **Spring Boot**.
O sistema permite o gerenciamento de despesas e receitas de forma colaborativa, onde transações são vinculadas a usuários e famílias, garantindo a integridade dos dados e regras de negócio.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3** (Web, Data JPA, Validation)
- **PostgreSQL** (Banco de Dados Relacional)
- **SpringDoc OpenAPI / Swagger** (Documentação Automática)
- **Lombok** (Redução de boilerplate code)
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

---
Desenvolvido por **Maria Gabriela** 👩‍💻
