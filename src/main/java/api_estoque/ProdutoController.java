package api_estoque;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins= "http://127.0.0.1:5500")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoDAO produtoDAO;

    public ProdutoController(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    // Endpoint responsável por listar todos os produtos cadastrados.
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoDAO.listarTodos();
    }

    // Endpoint responsável por receber os dados de um produto e cadastrá-lo no banco de dados.
    @PostMapping
    public String cadastrarProduto(@RequestBody Produto produto) {

        produtoDAO.cadastrarProduto(produto);

        return "Produto cadastrado com sucesso!";
    }

    @PutMapping 
    public String atualizarProduto(@RequestBody Produto produto){

        produtoDAO.atualizarProduto(produto);

        return "Produto atualizado com sucesso!";

    }

    // Endpoint responsável por excluir um produto pelo ID.
    @DeleteMapping("/{id}")
    public String excluirProduto(@PathVariable int id) {

    produtoDAO.excluirProduto(id);

    return "Produto excluído com sucesso!";
}

 
}