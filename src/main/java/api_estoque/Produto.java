package api_estoque;

public class Produto {
     private Integer id;
     private String nome;
     private double preco;
     private Integer quantidade;


public Produto(){

}

     public Produto(Integer id, String nome, double preco, Integer quantidade){
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
     }

public Integer getId(){
        return id;
     }


public String getNome(){
    return nome;
   }

public double getPreco(){
    return preco;
   }

public Integer getQuantidade(){
    return quantidade;
   }


// Métodos responsáveis por definir os valores dos atributos.
public void setId(Integer id) {
    this.id = id;
}

public void setNome(String nome) {
    this.nome = nome;
}

public void setPreco(double preco) {
    this.preco = preco;
}

public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
}

}