# Sistema de Controle de Estoque

## Sobre o projeto

Sistema de controle de estoque desenvolvido em Java, integrado a um banco de dados MySQL, com API REST utilizando Spring Boot e interface web.

O projeto foi desenvolvido como parte dos estudos em Ciência da Computação, com o objetivo de aplicar conhecimentos de programação, orientação a objetos, banco de dados, desenvolvimento de APIs e integração entre diferentes tecnologias.

A aplicação permite gerenciar produtos, acompanhar níveis de estoque, registrar vendas e movimentações e consultar informações por meio de uma interface web ou de um sistema executado no terminal.

## Arquitetura do projeto

O sistema é composto por três aplicações integradas:

- **Sistema Java:** aplicação de terminal para gerenciamento de produtos, vendas e controle de estoque.
- **API REST:** desenvolvida com Spring Boot, responsável por disponibilizar endpoints para comunicação com o banco de dados.
- **Frontend:** interface web desenvolvida com HTML, CSS e JavaScript, que permite interagir com os dados por meio da API.

O MySQL funciona como banco de dados central, permitindo que as informações sejam compartilhadas entre os componentes.

## Estrutura do projeto

```text
api-estoque/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── api_estoque/
│       └── resources/
│           └── application.properties
│
├── sistema-java/
│   ├── src/
│   ├── lib/
│   └── README.md
│
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── .mvn/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Tecnologias utilizadas

### Backend e API
- Java 21.
- Spring Boot.
- Spring JDBC.
- API REST.
- Maven.
- JSON.

### Sistema Java
- Java 21.
- JDBC (Java Database Connectivity).
- MySQL Connector/J.
- Programação Orientada a Objetos (POO).
- ArrayList.
- SQL.

### Frontend
- HTML5.
- CSS3.
- JavaScript.

### Banco de dados e ferramentas
- MySQL.
- Git.
- GitHub.
- Visual Studio Code.

## Funcionalidades

### Gerenciamento de produtos
- Cadastro de produtos.
- Consulta e listagem de produtos.
- Pesquisa de produtos pelo nome no sistema Java.
- Atualização de informações.
- Exclusão de produtos.
- Prevenção de cadastro de produtos duplicados no sistema Java.

### Controle de estoque
- Atualização automática da quantidade após vendas.
- Definição de estoque mínimo por produto.
- Identificação de produtos com estoque baixo ou zerado.
- Listagem de produtos que precisam de reposição.
- Cálculo da quantidade necessária para reposição.

### Vendas e movimentações
- Registro de vendas.
- Histórico de vendas.
- Consulta de movimentações de estoque.
- Registro de movimentações pela API.
- Consulta do histórico de movimentações pela API.

### Relatórios
- Dashboard com resumo geral do estoque.
- Cálculo do valor total em estoque.
- Exportação de relatórios em CSV.

## Documentação da API

Endereço local:

`http://localhost:8080`

### Produtos — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/produtos` | Lista os produtos cadastrados |
| POST | `/produtos` | Cadastra um produto |
| PUT | `/produtos` | Atualiza as informações de um produto |
| DELETE | `/produtos/{id}` | Exclui um produto pelo ID |

### Movimentações — `/movimentacoes`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/movimentacoes` | Consulta o histórico de movimentações |
| POST | `/movimentacoes` | Registra uma movimentação |
| DELETE | `/movimentacoes` | Exclui todas as movimentações |

**Atenção:** o endpoint `DELETE /movimentacoes` exclui todo o histórico de movimentações.

## Exemplos de utilização da API

### Cadastrar produto — POST `/produtos`

Requisição JSON:

```json
{
  "nome": "Teclado mecânico",
  "preco": 120.00,
  "quantidade": 10
}
```

### Atualizar produto — PUT `/produtos`

Requisição JSON:

```json
{
  "id": 1,
  "nome": "Teclado mecânico RGB",
  "preco": 150.00,
  "quantidade": 15
}
```

### Consultar produtos — GET `/produtos`

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

### Registrar movimentação — POST `/movimentacoes`

Requisição JSON:

```json
{
  "nomeProduto": "Teclado mecânico",
  "tipo": "SAÍDA",
  "quantidade": 2
}
```

### Consultar histórico — GET `/movimentacoes`

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

A data exibida no histórico é formatada pelo DAO no padrão `dd/MM/yyyy HH:mm:ss`.

## Banco de dados

O projeto utiliza o MySQL com o banco de dados `controle_estoque`.

As aplicações utilizam JDBC e Spring JDBC para realizar operações de consulta, cadastro, atualização e exclusão de dados.

A conexão é configurada por meio das seguintes variáveis de ambiente:

| Variável | Descrição |
|---|---|
| `DB_URL` | Endereço de conexão com o banco |
| `DB_USER` | Usuário de acesso ao MySQL |
| `DB_PASSWORD` | Senha do usuário MySQL |

Exemplo de endereço de conexão:

```text
jdbc:mysql://localhost:3306/controle_estoque
```

As credenciais de acesso não devem ser armazenadas diretamente no código ou publicadas no GitHub.

## Como executar o projeto

### Pré-requisitos

- Java JDK 21 ou superior.
- MySQL Server.
- Git.
- Visual Studio Code (recomendado).

### 1. Clonar o repositório

```bash
git clone https://github.com/contatogabrielf001-a11y/api-estoque.git
```

### 2. Entrar na pasta do projeto

```bash
cd api-estoque
```

### 3. Configurar o banco de dados

Crie o banco de dados `controle_estoque` no MySQL e configure as tabelas necessárias para o funcionamento do sistema.

### 4. Configurar as variáveis de ambiente

No PowerShell, configure as variáveis de conexão:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/controle_estoque"
$env:DB_USER="estoque_app"
$env:DB_PASSWORD="SUA_SENHA"
```

Substitua `SUA_SENHA` pela senha configurada para o usuário MySQL.

Essas variáveis ficam disponíveis na sessão atual do terminal.

### 5. Executar a API

Na pasta raiz do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Após a inicialização, a API estará disponível em:

`http://localhost:8080`

### 6. Executar o frontend

Com a API em execução, abra a pasta `frontend` e execute o arquivo `index.html` em um navegador.

O frontend utiliza JavaScript para realizar requisições à API.

### 7. Executar o sistema Java

Abra outro terminal e entre na pasta `sistema-java`:

```powershell
cd sistema-java
```

Compile os arquivos:

```powershell
javac -cp ".;lib/*" -d out src\*.java
```

Execute o sistema:

```powershell
java -cp "out;lib/*" Main
```

## Testes

Os endpoints da API podem ser testados utilizando ferramentas como Postman ou Insomnia.

A integração entre os componentes pode ser verificada realizando operações no frontend e consultando os mesmos dados no banco MySQL e no sistema Java.

## Objetivo

Este projeto foi desenvolvido para consolidar conhecimentos em desenvolvimento Java, banco de dados, APIs REST, integração entre sistemas e desenvolvimento web.

Também faz parte da construção de um portfólio voltado a oportunidades de estágio e desenvolvimento de software.

## Autor

**Gabriel Farias**

Estudante de Ciência da Computação.
