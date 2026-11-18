
package api_estoque;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoDAO movimentacaoDAO;
    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(
            MovimentacaoDAO movimentacaoDAO,
            MovimentacaoService movimentacaoService) {

        this.movimentacaoDAO = movimentacaoDAO;
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping
    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoDAO.listarTodas();
    }

    @PostMapping
    public String registrarMovimentacao(
            @RequestBody MovimentacaoRequest requisicao) {

        movimentacaoService.registrarMovimentacao(
            requisicao.getIdProduto(),
            requisicao.getTipo(),
            requisicao.getQuantidade()
        );

        return "Movimentação registrada com sucesso!";
    }

    @DeleteMapping
    public String excluirMovimentacoes() {
        movimentacaoDAO.excluirTodas();
        return "Histórico de movimentações excluído com sucesso!";
    }
}
