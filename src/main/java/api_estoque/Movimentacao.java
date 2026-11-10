package api_estoque;

public class Movimentacao {

    private Integer id;
    private String nomeProduto;
    private String tipo;
    private Integer quantidade;
    private String data;

    public Movimentacao() {
    }

    public Movimentacao(Integer id, String nomeProduto, String tipo,
                        Integer quantidade, String data) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getData() {
        return data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setData(String data) {
        this.data = data;
    }
}