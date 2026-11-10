# API de Controle de Estoque

API REST desenvolvida em Java e Spring Boot para gerenciamento de produtos e movimentações de estoque, integrada a um banco de dados MySQL.

## Sobre o projeto

Este projeto faz parte de um sistema de controle de estoque desenvolvido com Java. A API permite consultar, cadastrar, atualizar e excluir produtos, além de registrar e consultar movimentações de estoque.

A aplicação utiliza Spring Boot para disponibilizar endpoints REST e JDBC para comunicação com o banco de dados MySQL.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring JDBC
- MySQL
- Maven
- JDBC
- JSON
- Git e GitHub

## Funcionalidades

### Produtos

- Listar produtos cadastrados.
- Cadastrar novos produtos.
- Atualizar informações dos produtos.
- Excluir produtos.

### Movimentações

- Consultar histórico de movimentações.
- Registrar movimentações.
- Excluir o histórico de movimentações.

## Endpoints

### Produtos — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/produtos` | Lista os produtos |
| POST | `/produtos` | Cadastra um produto |
| PUT | `/produtos` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Exclui um produto pelo ID |

### Movimentações — `/movimentacoes`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/movimentacoes` | Lista as movimentações |
| POST | `/movimentacoes` | Registra uma movimentação |
| DELETE | `/movimentacoes` | Exclui todas as movimentações |

**Atenção:** a rota DELETE `/movimentacoes` exclui todo o histórico.

## Configuração e execução

### Pré-requisitos

- Java 21 ou superior
- MySQL
- Git

### 1. Clonar o repositório

```bash
git clone https://github.com/contatogabrielf001-a11y/api-estoque.git
```

### 2. Entrar na pasta do projeto

```bash
cd api-estoque
```

### 3. Configurar o banco de dados

Crie o banco de dados `controle_estoque` no MySQL e configure as credenciais de acesso.

### 4. Configurar a variável de ambiente

No PowerShell:

```powershell
$env:DB_PASSWORD="SUA_NOVA_SENHA"
```

Configure também o usuário e a URL do banco no arquivo `application.properties`, caso necessário.

### 5. Executar a aplicação

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API será disponibilizada, por padrão, em:

`http://localhost:8080`

## Testes

Os endpoints podem ser testados utilizando ferramentas como Postman ou Insomnia.

## Autor

Gabriel Farias

Projeto desenvolvido como parte dos estudos em Ciência da Computação, com foco em Java, desenvolvimento de APIs REST e integração com banco de dados.