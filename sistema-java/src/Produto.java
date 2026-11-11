public class Produto {
  int id;
  String nome;
  double preco;
  int quantidade;
  int estoqueMinimo;

public Produto(int id, String nome, double preco, int quantidade, int estoqueMinimo) {
    this.id = id;
    this.nome = nome;
    this.preco = preco;
    this.quantidade = quantidade;
    this.estoqueMinimo = estoqueMinimo;
}

public Produto(String nome, double preco, int quantidade, int estoqueMinimo) {
    this.nome = nome;
    this.preco = preco;
    this.quantidade = quantidade;
    this.estoqueMinimo = estoqueMinimo;
}

}
