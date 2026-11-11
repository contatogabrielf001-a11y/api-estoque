# Sistema de Controle de Estoque em Java

## Sobre o projeto

Sistema de controle de estoque desenvolvido em Java, com integração ao banco de dados MySQL, como projeto de portfólio para praticar programação, orientação a objetos e persistência de dados.

O sistema permite gerenciar produtos, registrar vendas, acompanhar o estoque e consultar informações por meio de um menu interativo no terminal.

## Funcionalidades

- Cadastro de produtos.
- Consulta e listagem de produtos.
- Pesquisa de produtos pelo nome.
- Edição e exclusão de produtos.
- Prevenção de cadastro de produtos duplicados.
- Registro e histórico de vendas.
- Atualização automática do estoque após as vendas.
- Definição de estoque mínimo por produto.
- Identificação de produtos com estoque baixo ou zerado.
- Listagem de produtos que precisam de reposição.
- Cálculo da quantidade necessária para reposição.
- Dashboard com resumo geral do estoque.
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

## Banco de dados

O projeto utiliza o MySQL para armazenar os produtos e o histórico de vendas.

A conexão com o banco é realizada por meio do JDBC, utilizando variáveis de ambiente para proteger as credenciais de acesso.

## Como executar

**Pré-requisitos:**

- Java JDK 21 ou superior.
- MySQL Server.
- MySQL Connector/J.

**Passos:**

1. Configure o banco de dados MySQL e crie o banco `controle_estoque`, com as tabelas necessárias.
2. Configure as variáveis de ambiente `DB_URL`, `DB_USER` e `DB_PASSWORD`.
3. Abra o terminal na pasta `src`.
4. Compile os arquivos Java:

```powershell
javac -cp ".;../lib/*" *.java
```

5. Execute o sistema:

```powershell
java -cp ".;../lib/*" Main
```

## Objetivo

Projeto desenvolvido para consolidar conhecimentos em Java, banco de dados, integração com MySQL e desenvolvimento de sistemas, além de construir um portfólio para oportunidades de estágio e desenvolvimento de software.