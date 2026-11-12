# Sistema de Controle de Estoque em Java

## Sobre o projeto

Aplicação de controle de estoque desenvolvida em Java, integrada ao banco de dados MySQL por meio de JDBC.

O projeto foi desenvolvido como parte dos estudos em Ciência da Computação, com o objetivo de praticar programação orientada a objetos, persistência de dados, manipulação de informações e integração com banco de dados.

O sistema funciona por meio de um menu interativo no terminal e permite gerenciar produtos, acompanhar níveis de estoque, registrar vendas e consultar movimentações.

## Funcionalidades

### Gerenciamento de produtos
- Cadastro de produtos.
- Consulta e listagem de produtos.
- Pesquisa de produtos pelo nome.
- Edição e exclusão de produtos.
- Prevenção de cadastro de produtos duplicados.

### Controle de estoque
- Atualização automática da quantidade após vendas.
- Definição de estoque mínimo por produto.
- Identificação de produtos com estoque baixo ou zerado.
- Listagem de produtos que precisam de reposição.
- Cálculo da quantidade necessária para reposição.

### Vendas e movimentações
- Registro de vendas.
- Histórico de vendas.
- Atualização do estoque após as operações.
- Consulta de movimentações registradas.

### Relatórios
- Dashboard com resumo geral do estoque.
- Cálculo do valor total dos produtos em estoque.
- Exportação de relatórios em CSV.

## Tecnologias utilizadas

- Java 21.
- MySQL.
- JDBC (Java Database Connectivity).
- MySQL Connector/J.
- Programação Orientada a Objetos (POO).
- ArrayList.
- SQL.
- Tratamento de exceções.
- Estruturas condicionais e de repetição.

## Estrutura do projeto

```text
sistema-java/
│
├── src/
│   ├── Main.java
│   ├── Produto.java
│   ├── ProdutoDAO.java
│   ├── ConexaoMySQL.java
│   └── outras classes do sistema
│
├── lib/
│   └── mysql-connector-j-26.10.0.jar
│
├── .gitignore
└── README.md
```

## Banco de dados

O sistema utiliza o MySQL para armazenar os produtos e as informações relacionadas às operações de estoque.

A conexão com o banco é realizada por meio do JDBC, utilizando variáveis de ambiente para proteger as credenciais de acesso.

O banco de dados utilizado é:

`controle_estoque`

As variáveis necessárias para conexão são:

| Variável | Descrição |
|---|---|
| `DB_URL` | Endereço de conexão com o banco |
| `DB_USER` | Usuário de acesso ao MySQL |
| `DB_PASSWORD` | Senha do usuário MySQL |

Exemplo de endereço de conexão:

```text
jdbc:mysql://localhost:3306/controle_estoque
```

As credenciais não devem ser armazenadas diretamente no código ou publicadas no GitHub.

## Como executar

### Pré-requisitos

- Java JDK 21 ou superior.
- MySQL Server.
- MySQL Connector/J.
- Terminal PowerShell ou equivalente.

### 1. Configurar o banco de dados

Configure o servidor MySQL e disponibilize o banco de dados `controle_estoque`, com as tabelas necessárias para o funcionamento do sistema.

### 2. Configurar as variáveis de ambiente

No PowerShell, configure as variáveis de conexão:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/controle_estoque"
$env:DB_USER="estoque_app"
$env:DB_PASSWORD="SUA_SENHA"
```

Substitua `SUA_SENHA` pela senha configurada para o usuário MySQL.

Essas variáveis ficam disponíveis na sessão atual do terminal.

### 3. Verificar o MySQL Connector/J

Certifique-se de que o arquivo do MySQL Connector/J esteja disponível na pasta `lib`.

### 4. Compilar o sistema

Abra o terminal na pasta `sistema-java` e execute:

```powershell
javac -cp ".;lib/*" -d out src\*.java
```

### 5. Executar o sistema

Após a compilação, execute:

```powershell
java -cp "out;lib/*" Main
```

O sistema será iniciado no terminal, apresentando o menu interativo de controle de estoque.

## Objetivo

Este projeto foi desenvolvido para consolidar conhecimentos em Java, programação orientada a objetos, banco de dados, JDBC e desenvolvimento de sistemas.

Também faz parte da construção de um portfólio voltado a oportunidades de estágio e desenvolvimento de software.