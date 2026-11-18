
package api_estoque;

public class MovimentacaoRequest {

    private Integer idProduto;
    private String tipo;
    private Integer quantidade;

    public MovimentacaoRequest() {
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
