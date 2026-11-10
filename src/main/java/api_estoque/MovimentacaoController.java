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

    public MovimentacaoController(MovimentacaoDAO movimentacaoDAO) {
        this.movimentacaoDAO = movimentacaoDAO;
    }

    // Consulta todas as movimentações registradas.
    @GetMapping
    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoDAO.listarTodas();
    }

    // Registra uma nova movimentação.
    @PostMapping
    public String registrarMovimentacao(@RequestBody Movimentacao movimentacao) {
        movimentacaoDAO.registrar(movimentacao);
        return "Movimentação registrada com sucesso!";
    }

        // Exclui todo o histórico de movimentações.
    @DeleteMapping
    public String excluirMovimentacoes() {
        movimentacaoDAO.excluirTodas();
        return "Histórico de movimentações excluído com sucesso!";
    }
}