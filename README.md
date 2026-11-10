# API de Controle de Estoque

API REST desenvolvida em Java e Spring Boot para gerenciamento de produtos e movimentações de estoque, integrada a um banco de dados MySQL.

## Sobre o projeto

Este projeto faz parte de um sistema de controle de estoque desenvolvido com Java. A API permite consultar, cadastrar, atualizar e excluir produtos, além de registrar e consultar movimentações de estoque.

A aplicação utiliza Spring Boot para disponibilizar endpoints REST e Spring JDBC para comunicação com o banco de dados MySQL.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring JDBC
- MySQL
- JDBC
- Maven
- JSON
- Git e GitHub

## Funcionalidades

### Produtos

- Listagem de produtos cadastrados.
- Cadastro de novos produtos.
- Atualização de informações dos produtos.
- Exclusão de produtos pelo ID.

### Movimentações

- Consulta do histórico de movimentações.
- Registro de movimentações de estoque.
- Exclusão do histórico de movimentações.

## Endpoints

### Produtos — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/produtos` | Lista todos os produtos |
| POST | `/produtos` | Cadastra um produto |
| PUT | `/produtos` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Exclui um produto pelo ID |

### Movimentações — `/movimentacoes`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/movimentacoes` | Lista as movimentações |
| POST | `/movimentacoes` | Registra uma movimentação |
| DELETE | `/movimentacoes` | Exclui todas as movimentações |

**Atenção:** o endpoint `DELETE /movimentacoes` exclui todo o histórico de movimentações.

## Exemplos de utilização

### Produtos

**Cadastrar produto — POST `/produtos`**

Requisição JSON:

```json
{
  "nome": "Teclado mecânico",
  "preco": 120.00,
  "quantidade": 10
}
```

Resposta:

```text
Produto cadastrado com sucesso!
```

**Atualizar produto — PUT `/produtos`**

Requisição JSON:

```json
{
  "id": 1,
  "nome": "Teclado mecânico RGB",
  "preco": 150.00,
  "quantidade": 15
}
```

**Consultar produtos — GET `/produtos`**

Exemplo de resposta:

```json
[
  {
    "id": 1,
    "nome": "Teclado mecânico RGB",
    "preco": 150.0,
    "quantidade": 15
  }
]
```

### Movimentações

**Registrar movimentação — POST `/movimentacoes`**

Requisição JSON:

```json
{
  "nomeProduto": "Teclado mecânico",
  "tipo": "SAÍDA",
  "quantidade": 2
}
```

Resposta:

```text
Movimentação registrada com sucesso!
```

**Consultar histórico — GET `/movimentacoes`**

Exemplo de resposta:

```json
[
  {
    "id": 1,
    "nomeProduto": "Teclado mecânico",
    "tipo": "SAÍDA",
    "quantidade": 2,
    "data": "10/11/2026 15:30:00"
  }
]
```

**Observação:** a data e a hora são registradas automaticamente pelo MySQL por meio da configuração `CURRENT_TIMESTAMP`. Na consulta, o DAO formata a data no padrão `dd/MM/yyyy HH:mm:ss`.

Os exemplos de respostas apresentados são ilustrativos.

## Configuração e execução

### Pré-requisitos

- Java 21 ou superior.
- MySQL.
- Git.

### 1. Clonar o repositório

```bash
git clone https://github.com/contatogabrielf001-a11y/api-estoque.git
```

### 2. Entrar na pasta do projeto

```bash
cd api-estoque
```

### 3. Configurar o banco de dados

Crie o banco de dados `controle_estoque` no MySQL e prepare as tabelas necessárias para a aplicação.

A aplicação utiliza a tabela `produtos` e a tabela `movimentacoes`.

### 4. Configurar as credenciais

A aplicação utiliza a variável de ambiente `DB_PASSWORD` para acessar o banco de dados.

No PowerShell, configure:

```powershell
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
```

O arquivo `application.properties` já contém a URL e o usuário da conexão. Caso utilize outras configurações de MySQL, ajuste esses dados conforme necessário.

### 5. Executar a aplicação

No Windows, utilize o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

`http://localhost:8080`

## Testes

Os endpoints podem ser testados utilizando ferramentas como Postman ou Insomnia.

### Endpoints para teste

- `http://localhost:8080/produtos`
- `http://localhost:8080/movimentacoes`

## Autor

**Gabriel Farias**

Projeto desenvolvido como parte dos estudos em Ciência da Computação, com foco em Java, desenvolvimento de APIs REST e integração com banco de dados MySQL.