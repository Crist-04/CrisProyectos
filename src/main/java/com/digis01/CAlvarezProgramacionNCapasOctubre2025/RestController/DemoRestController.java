package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {

    @GetMapping("saludo")
    public String Saludo() {
        return "Hola Mundo, Soy Cristobal";
    }

    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
        Result result = new Result();
        try {
            if (numeroDos == 0) {
                result.correct = false;
                result.errorMessage = "Syntax ERROR";
                result.status = 400;

            } else {
                int division = numeroUno / numeroDos;
                result.data = division;
                result.correct = true;
                result.status = 200;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("multiplicacion")
    public ResponseEntity Multiplicacion(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
        Result result = new Result();
        try {
            int multiplicacion = numeroUno * numeroDos;
            result.data = multiplicacion;
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

//    @GetMapping("suma")
//    public ResponseEntity Suma(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
//        Result result = new Result();
//        try {
//            int suma = numeroUno + numeroDos;
//            result.correct = true;
//            result.status = 200;
//
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//            result.status = 500;
//        }
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    @PostMapping("suma")
    public ResponseEntity Suma(@RequestBody List<Integer> numeros) {
        Result result = new Result();
        try {
            int suma = 0;
            for (int numero : numeros) {
                suma += numero;
            }
            result.data = suma;
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

}
