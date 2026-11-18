
package api_estoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class Produto {

    private Integer id;

    @NotBlank(message = "O nome do produto não pode ficar vazio.")
    private String nome;

    @Positive(message = "O preço deve ser maior que zero.")
    private double preco;

    @NotNull(message = "A quantidade deve ser informada.")
    @PositiveOrZero(message = "A quantidade não pode ser negativa.")
    private Integer quantidade;

    public Produto() {
    }

    public Produto(Integer id, String nome, double preco, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

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
