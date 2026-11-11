
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Locale;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        int opcao = 0;
        String nome = "";
        double preco = 0;
        int quantidade = 0;

        do {

          
int semEstoque = 0;
int estoqueBaixo = 0;
int precisaReposicao = 0;
double valorTotalEstoque = 0;
int totalProdutos = 0;

try {
    ProdutoDAO daoResumo = new ProdutoDAO();
    ArrayList<Produto> produtosResumo = daoResumo.listarProdutos();

    System.out.println("TESTE DE CONEXÃO: produtos encontrados = "
        + produtosResumo.size());

    totalProdutos = produtosResumo.size();

    for (int i = 0; i < produtosResumo.size(); i++) {
        Produto p = produtosResumo.get(i);

        valorTotalEstoque += p.preco * p.quantidade;

        if (p.quantidade == 0) {
            semEstoque++;
        }

        if (p.quantidade > 0 && p.quantidade <= p.estoqueMinimo){
            estoqueBaixo++;
        }

        if (p.quantidade <= p.estoqueMinimo) {
            precisaReposicao++;
        }
    }

} catch (SQLException e) {
    System.out.println("Erro ao atualizar resumo do estoque: "
            + e.getMessage());
}

          System.out.println();
          System.out.println("==========RESUMO DO ESTOQUE=========");
          System.out.println("Total de produtos: " + totalProdutos);
          System.out.println("Produtos sem estoque: " + semEstoque);
          System.out.println("Produtos que precisam de reposicao: " + precisaReposicao);
          System.out.println("Produtos com estoque baixo : " + estoqueBaixo);

          System.out.printf(
          Locale.forLanguageTag("pt-BR"),
          "Valor total do estoque:  R$ %.2f%n",
          valorTotalEstoque
);

            System.out.println("=====================================");


            System.out.println();
            System.out.println("=========================================");
            System.out.println("      SISTEMA DE CONTROLE DE ESTOQUE");
            System.out.println("=========================================");
            System.out.println("|                                       |");
            System.out.println("|   [1] Cadastrar produto               |");
            System.out.println("|   [2] Consultar produtos              |");
            System.out.println("|   [3] Sair do sistema                 |");
            System.out.println("|   [4] Pesquisar produto               |");
            System.out.println("|   [5] Registrar venda                 |");
            System.out.println("|   [6] Excluir produto                 |");
            System.out.println("|   [7] Editar produto                  |");
            System.out.println("|   [8] Listar produtos para reposicão  |");
            System.out.println("|   [9] Historico de vendas             |");
            System.out.println("|                                       |");
            System.out.println("=========================================");
            System.out.println("Escolha uma opção:  ");


            if(scanner.hasNextInt()){
                opcao = scanner.nextInt();
            } else {
                System.out.println("Entrada invalida! Digite um numero.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {

          case 1:
    scanner.nextLine();

    
         System.out.println("Digite o nome do produto:");
           nome = scanner.nextLine();

    while (nome.trim().isEmpty()) {
        System.out.println("O nome nao pode ficar vazio!");
        System.out.println("Digite o nome do produto:");
        nome = scanner.nextLine();
    }

    // Pedir e validar o preco
    System.out.println("Digite o preco do produto:");

    while (true) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Preco invalido! Digite um numero:");
            scanner.nextLine();
        }

        preco = scanner.nextDouble();

        if (preco > 0) {
            break;
        }

        System.out.println("O preco deve ser maior que zero. Digite novamente:");
    }

    if (preco > 1000) {
        System.out.println("ATENCAO! Preco acima do esperado.");
    }

    // Pedir e validar a quantidade
    System.out.println("Digite a quantidade do produto:");

    while (!scanner.hasNextInt()) {
        System.out.println("Quantidade invalida! Digite um numero inteiro:");
        scanner.nextLine();
    }

    quantidade = scanner.nextInt();

    while (quantidade < 0) {
        System.out.println("A quantidade nao pode ser negativa. Digite novamente:");

        while (!scanner.hasNextInt()) {
            System.out.println("Quantidade invalida! Digite um numero inteiro:");
            scanner.nextLine();
        }

        quantidade = scanner.nextInt();
    }

    int estoqueMinimo;
    
    System.out.println("Digite o estoque minimo do produto:");

    while (!scanner.hasNextInt()) {
        System.out.println("Digite um numero inteiro:");
        scanner.nextLine();
    }

    estoqueMinimo = scanner.nextInt();

    while (estoqueMinimo <0){

        System.out.println("O estoque minimo nao pode ser negativo.");

        System.out.println("Digite novamente:");

        while (!scanner.hasNextInt()) {
        System.out.println("Digite um numero inteiro:");
        scanner.nextLine();
        }

        estoqueMinimo = scanner.nextInt();
    }

    boolean cadastroConcluido = false;

       try {
    ProdutoDAO dao = new ProdutoDAO();

    if (dao.existeProduto(nome.trim())) {
        System.out.println("Esse produto já está cadastrado no MySQL!");
    } else {
        Produto novoProduto = new Produto(nome.trim(), preco, quantidade, estoqueMinimo);

        dao.cadastrarProduto(novoProduto);
        cadastroConcluido = true;

    }

} catch (SQLException e) {
    System.out.println("Erro ao cadastrar produto: " + e.getMessage());
}



    if (cadastroConcluido) {

    if (quantidade == 0) {
        System.out.println("Produto sem estoque.");
    } else if (quantidade <= estoqueMinimo) {
        System.out.println("ATENÇÃO! Produto com estoque baixo.");
    } else {
        System.out.println("Produto com estoque normal.");
    }

    System.out.println("Produto: " + nome);
    System.out.println("Preco: R$ " + preco);
    System.out.println("Quantidade em estoque: " + quantidade);
}

    break;

case 2:
    ProdutoDAO dao = new ProdutoDAO();

    try {
        ArrayList<Produto> produtosBD = dao.listarProdutos();

        if (produtosBD.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no MySQL.");
        } else {
            System.out.println("===== PRODUTOS CADASTRADOS =====");

            for (Produto p : produtosBD) {
                System.out.println("------------------------------");
                System.out.println("ID: " + p.id);
                System.out.println("Nome: " + p.nome);
                System.out.println("Preço: R$ " + p.preco);
                System.out.println("Quantidade: " + p.quantidade);
                System.out.println("Estoque minimo: " + p.estoqueMinimo);

            if (p.quantidade == 0) {
                 System.out.println("Status: PRODUTO SEM ESTOQUE!");
            } else if (p.quantidade <= p.estoqueMinimo) {
                 System.out.println("ALERTA: ESTOQUE BAIXO! Necessita reposição.");
            } else {
                 System.out.println("Status: ESTOQUE NORMAL.");
            }
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao consultar o MySQL: " + e.getMessage());
    }

    break;

          case 3:
                    System.out.println("Saindo do sistema...");
                    break;
                
          case 4:
    scanner.nextLine();

    System.out.println("Digite o nome do produto que deseja pesquisar:");
    String busca = scanner.nextLine();

    while (busca.trim().isEmpty()) {
    System.out.println("O nome não pode ficar vazio!");
    System.out.println("Digite novamente:");
    busca = scanner.nextLine();
}

    ProdutoDAO daoPesquisa = new ProdutoDAO();
    try {
        ArrayList<Produto> encontrados = daoPesquisa.pesquisarProduto(busca);

        if (encontrados.isEmpty()) {
            System.out.println("Produto não encontrado.");
        } else {
            System.out.println("===== PRODUTOS ENCONTRADOS =====");

            for (Produto p : encontrados) {
                System.out.println("------------------------------");
                System.out.println("ID: " + p.id);
                System.out.println("Nome: " + p.nome);
                System.out.println("Preço: R$ " + p.preco);
                System.out.println("Quantidade: " + p.quantidade);
                System.out.println("Estoque minimo: " + p.estoqueMinimo);
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao pesquisar produto: " + e.getMessage());
    }

    break;

       

case 5:
    scanner.nextLine();

    System.out.println("Digite o nome do produto:");
    String nomeBusca = scanner.nextLine();

    while (nomeBusca.trim().isEmpty()) {
        System.out.println("O nome não pode ficar vazio!");
        System.out.println("Digite novamente:");
        nomeBusca = scanner.nextLine();
    }

    ProdutoDAO daoVenda = new ProdutoDAO();
    vendasDAO daoHistorico = new vendasDAO();

    try {
        ArrayList<Produto> encontrados =
                daoVenda.pesquisarProduto(nomeBusca);

        if (encontrados.isEmpty()) {
            System.out.println("Produto não encontrado no MySQL.");
            break;
        }

        System.out.println("===== PRODUTOS ENCONTRADOS =====");

        for (Produto p : encontrados) {
            System.out.println("ID: " + p.id);
            System.out.println("Nome: " + p.nome);
            System.out.println("Preço: R$ " + p.preco);
            System.out.println("Estoque: " + p.quantidade);
            System.out.println("Estoque minimo: " + p.estoqueMinimo);
            System.out.println("------------------------------");
        }

        System.out.println("Digite o ID do produto vendido:");

        while (!scanner.hasNextInt()) {
            System.out.println("ID inválido! Digite um número:");
            scanner.nextLine();
        }

        int idVenda = scanner.nextInt();
        scanner.nextLine();

        while (idVenda <= 0) {
            System.out.println("O ID deve ser maior que zero.");
            System.out.println("Digite novamente:");

            while (!scanner.hasNextInt()) {
                System.out.println("ID inválido! Digite um número:");
                scanner.nextLine();
            }

            idVenda = scanner.nextInt();
            scanner.nextLine();
        }

        Produto selecionado = null;

        for (Produto p : encontrados) {
            if (p.id == idVenda) {
                selecionado = p;
                break;
            }
        }

        if (selecionado == null) {
            System.out.println("ID não encontrado na pesquisa.");
            break;
        }

        System.out.println("Quantas unidades foram vendidas?");

        while (!scanner.hasNextInt()) {
            System.out.println("Digite uma quantidade inteira:");
            scanner.nextLine();
        }

        int vendidas = scanner.nextInt();
        scanner.nextLine();

        while (vendidas <= 0) {
            System.out.println(
                "A quantidade vendida deve ser maior que zero."
            );
            System.out.println("Digite novamente:");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite uma quantidade inteira:");
                scanner.nextLine();
            }

            vendidas = scanner.nextInt();
            scanner.nextLine();
        }

        if (vendidas > selecionado.quantidade) {
            System.out.println(
                "Estoque insuficiente para essa venda."
            );
            break;
        }

        try (Connection conexao = ConexaoMySQL.conectar()) {

            conexao.setAutoCommit(false);

            try {
                boolean estoqueAtualizado = daoVenda.registrarVenda(
                    conexao,
                    selecionado.id,
                    vendidas
                );

                if (!estoqueAtualizado) {
                    conexao.rollback();

                    System.out.println(
                        "Venda cancelada. Estoque insuficiente ou produto não encontrado."
                    );

                    break;
                }

                daoHistorico.registrarVenda(
                    conexao,
                    selecionado.nome,
                    vendidas,
                    selecionado.preco
                );

                conexao.commit();

                System.out.println("Venda registrada no MySQL com sucesso!");
                System.out.println("Estoque atualizado no MySQL.");

            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            }
        }

    } catch (SQLException e) {
        System.out.println(
            "Erro ao registrar venda: " + e.getMessage()
        );
        System.out.println(
            "A operação foi cancelada. Verifique o banco de dados."
        );
    }

    break;
case 6:
    scanner.nextLine();

    System.out.println("Digite o nome do produto que deseja excluir:");
    String nomeExcluir = scanner.nextLine();

    while (nomeExcluir.trim().isEmpty()) {
        System.out.println("O nome não pode ficar vazio!");
        System.out.println("Digite novamente:");
        nomeExcluir = scanner.nextLine();
    }

    ProdutoDAO daoExclusao = new ProdutoDAO();

    try {
        ArrayList<Produto> encontrados =
                daoExclusao.pesquisarProduto(nomeExcluir);

        if (encontrados.isEmpty()) {
            System.out.println("Produto não encontrado no MySQL.");
            break;
        }

        System.out.println("===== PRODUTOS ENCONTRADOS =====");

        for (Produto p : encontrados) {
            System.out.println("ID: " + p.id);
            System.out.println("Nome: " + p.nome);
            System.out.println("Preço: R$ " + p.preco);
            System.out.println("Quantidade: " + p.quantidade);
            System.out.println("Estoque minimo: " + p.estoqueMinimo);
            System.out.println("------------------------------");
        }

        System.out.println("Digite o ID do produto que deseja excluir:");

        while (!scanner.hasNextInt()) {
            System.out.println("ID inválido! Digite um número:");
            scanner.nextLine();
        }

        int idExcluir = scanner.nextInt();
        scanner.nextLine();

        while (idExcluir <= 0) {
            System.out.println("O ID deve ser maior que zero.");
            System.out.println("Digite novamente:");

            while (!scanner.hasNextInt()) {
                System.out.println("ID inválido! Digite um número:");
                scanner.nextLine();
            }

            idExcluir = scanner.nextInt();
            scanner.nextLine();
        }

        Produto selecionado = null;

        for (Produto p : encontrados) {
            if (p.id == idExcluir) {
                selecionado = p;
                break;
            }
        }

        if (selecionado == null) {
            System.out.println("ID não encontrado na pesquisa.");
            break;
        }

        System.out.println("Tem certeza que deseja excluir "
                + selecionado.nome + "?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int confirmacao;

        while (!scanner.hasNextInt()) {
            System.out.println("Digite 1 para Sim ou 2 para Não:");
            scanner.nextLine();
        }

        confirmacao = scanner.nextInt();
        scanner.nextLine();

        while (confirmacao != 1 && confirmacao != 2) {
            System.out.println("Opção inválida! Digite 1 ou 2:");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite 1 para Sim ou 2 para Não:");
                scanner.nextLine();
            }

            confirmacao = scanner.nextInt();
            scanner.nextLine();
        }

        if (confirmacao == 1) {
            boolean excluido = daoExclusao.excluirProduto(idExcluir);

            if (excluido) {
                System.out.println("Produto excluído do MySQL com sucesso!");
            } else {
                System.out.println("O produto não foi encontrado no banco.");
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao excluir produto: " + e.getMessage());
    }

    break;
       case 7:
    scanner.nextLine();

    System.out.println("Digite o nome do produto que deseja editar:");
    String nomeEditar = scanner.nextLine();

    while (nomeEditar.trim().isEmpty()) {
        System.out.println("O nome não pode ficar vazio!");
        System.out.println("Digite novamente:");
        nomeEditar = scanner.nextLine();
    }

    ProdutoDAO daoEdicao = new ProdutoDAO();

    try {
        ArrayList<Produto> encontrados =
                daoEdicao.pesquisarProduto(nomeEditar);

        if (encontrados.isEmpty()) {
            System.out.println("Produto não encontrado no MySQL.");
            break;
        }

        System.out.println("===== PRODUTOS ENCONTRADOS =====");

        for (Produto p : encontrados) {
            System.out.println("ID: " + p.id);
            System.out.println("Nome: " + p.nome);
            System.out.println("Preço: R$ " + p.preco);
            System.out.println("Quantidade: " + p.quantidade);
            System.out.println("Estoque mínimo: " + p.estoqueMinimo);
            System.out.println("------------------------------");
        }

        System.out.println("Digite o ID do produto que deseja editar:");

        while (!scanner.hasNextInt()) {
            System.out.println("ID inválido! Digite um número:");
            scanner.nextLine();
        }

        int idEditar = scanner.nextInt();
        scanner.nextLine();

        while (idEditar <= 0) {
            System.out.println("O ID deve ser maior que zero.");
            System.out.println("Digite novamente:");

            while (!scanner.hasNextInt()) {
                System.out.println("ID inválido! Digite um número:");
                scanner.nextLine();
            }

            idEditar = scanner.nextInt();
            scanner.nextLine();
        }

        Produto selecionado = null;

        for (Produto p : encontrados) {
            if (p.id == idEditar) {
                selecionado = p;
                break;
            }
        }

        if (selecionado == null) {
            System.out.println("ID não encontrado na pesquisa.");
            break;
        }

        System.out.println("Digite o novo nome do produto:");
        String novoNome = scanner.nextLine();

        while (novoNome.trim().isEmpty()) {
            System.out.println("O nome não pode ficar vazio!");
            System.out.println("Digite novamente:");
            novoNome = scanner.nextLine();
        }

        double novoPreco;

        while (true) {
            System.out.println("Digite o novo preço:");

            while (!scanner.hasNextDouble()) {
                System.out.println("Preço inválido! Digite um número:");
                scanner.nextLine();
            }

            novoPreco = scanner.nextDouble();
            scanner.nextLine();

            if (novoPreco > 0) {
                break;
            }

            System.out.println("O preço deve ser maior que zero.");
        }

        if (novoPreco > 1000) {
            System.out.println("ATENÇÃO! Preço acima do esperado.");
        }

        int novaQuantidade;

        System.out.println("Digite a nova quantidade:");

        while (!scanner.hasNextInt()) {
            System.out.println("Quantidade inválida! Digite um número inteiro:");
            scanner.nextLine();
        }

        novaQuantidade = scanner.nextInt();
        scanner.nextLine();

        while (novaQuantidade < 0) {
            System.out.println("A quantidade não pode ser negativa.");
            System.out.println("Digite novamente:");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número inteiro:");
                scanner.nextLine();
            }

            novaQuantidade = scanner.nextInt();
            scanner.nextLine();
        }

        int novoEstoqueMinimo;

        System.out.println("Digite o novo estoque mínimo:");

        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Valor inválido! Digite um número inteiro:");
                scanner.nextLine();
            }

            novoEstoqueMinimo = scanner.nextInt();
            scanner.nextLine();

            if (novoEstoqueMinimo >= 0) {
                break;
            }

            System.out.println("O estoque mínimo não pode ser negativo.");
            System.out.println("Digite novamente:");
        }

        if (daoEdicao.existeOutroProduto(novoNome, selecionado.id)) {
          System.out.println("Já existe outro produto com esse nome!");
         break;
}

        selecionado.nome = novoNome.trim();
        selecionado.preco = novoPreco;
        selecionado.quantidade = novaQuantidade;
        selecionado.estoqueMinimo = novoEstoqueMinimo;

        boolean atualizado = daoEdicao.editarProduto(selecionado);

        if (atualizado) {
            System.out.println("Produto atualizado no MySQL com sucesso!");
            System.out.println("Novo nome: " + selecionado.nome);
            System.out.println("Novo preço: R$ " + selecionado.preco);
            System.out.println("Nova quantidade: " + selecionado.quantidade);
            System.out.println("Novo estoque mínimo: " + selecionado.estoqueMinimo);
        } else {
            System.out.println("Nenhum produto foi atualizado.");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao editar produto: " + e.getMessage());
    }

    break;
case 8:
    boolean encontrouReposicao = false;

    System.out.println("====== PRODUTOS PARA REPOSIÇÃO ======");

    try {
        ProdutoDAO daoReposicao = new ProdutoDAO();
        ArrayList<Produto> produtosBD = daoReposicao.listarProdutos();

        for (Produto p : produtosBD) {

            if (p.quantidade <= p.estoqueMinimo) {

                System.out.println("-------------------------------------------");
                System.out.println("ID: " + p.id);
                System.out.println("Produto: " + p.nome);
                System.out.println("Estoque atual: " + p.quantidade);
                System.out.println("Estoque mínimo: " + p.estoqueMinimo);

                int quantidadeReposicao =
                        p.estoqueMinimo - p.quantidade;

                System.out.println(
                    "Necessário repor: "
                    + quantidadeReposicao + " unidades"
                );

                if (p.quantidade == 0) {
                    System.out.println("STATUS: PRODUTO SEM ESTOQUE!");
                } else {
                    System.out.println("STATUS: ESTOQUE BAIXO!");
                }

                encontrouReposicao = true;
            }
        }

        if (!encontrouReposicao) {
            System.out.println("Nenhum produto precisa de reposição.");
        }

    } catch (SQLException e) {
        System.out.println(
            "Erro ao consultar produtos: " + e.getMessage()
        );
    }

    break;

case 9:
    System.out.println("====== HISTÓRICO DE VENDAS ======");

    vendasDAO daoConsultaHistorico = new vendasDAO();

    try {
        daoConsultaHistorico.listarVendas();

    } catch (SQLException e) {
        System.out.println(
            "Erro ao consultar histórico de vendas: "
            + e.getMessage()
        );
    }

    break;
            }

        } while (opcao != 3);

      scanner.close();
          
    }



}

