
package api_estoque;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarErroValidacao(
            IllegalArgumentException erro) {

        Map<String, String> resposta = Map.of(
            "erro", erro.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosDosCampos(
            MethodArgumentNotValidException erro) {

        String mensagem = erro.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(campo -> campo.getDefaultMessage())
                .findFirst()
                .orElse("Dados do produto inválidos.");

        Map<String, String> resposta = Map.of(
            "erro", mensagem
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> tratarNaoEncontrado(
            NoSuchElementException erro) {

        Map<String, String> resposta = Map.of(
            "erro", erro.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resposta);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarErroInterno(
            RuntimeException erro) {

        Map<String, String> resposta = Map.of(
            "erro", "Ocorreu um erro interno no servidor."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resposta);
    }
}
