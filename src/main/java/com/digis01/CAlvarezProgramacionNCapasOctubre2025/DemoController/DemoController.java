package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DemoController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("multiplicacion/{n}/{m}")
    public String Multiplicacion(@PathVariable("n") int n, @PathVariable("m") int m, Model model) {
        int resultado = n * m;
        model.addAttribute("n", n);
        model.addAttribute("m", m);
        model.addAttribute("resultado", resultado);
        return "HolaMundo";
    }
    
    
//    @GetMapping("multiplicacion/{n}")
//    public String Saludo(@PathVariable("m") String nombre, Model model) {
//        model.addAttribute("m", nombre);
//        return "HolaMundo";
//    }
    
    
//    @GetMapping("saludo")
//    public String Saludo(@RequestParam("nombre") String nombre, Model model){
//        model.addAttribute("nombre", nombre); 
//        return "HolaMundo";
//    } 
    
    
    
//    @GetMapping("multiplicacion")
//    public String Multiplicacion(@RequestParam("multiplicacion") String multiplicacion, Model model){
//        model.addAttribute("multiplicacion", multiplicacion); 
//        return "HolaMundo";
//    } 
    
}
