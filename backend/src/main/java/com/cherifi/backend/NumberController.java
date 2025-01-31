package com.cherifi.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NumberController {
    
    private final NumberRepository numberRepository;
    
    public NumberController(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
        // Ajouter quelques nombres par défaut
        if (numberRepository.count() == 0) {
            numberRepository.save(new Number(42));
            numberRepository.save(new Number(7));
            numberRepository.save(new Number(13));
        }
    }
    
    @GetMapping("/numbers")
    public List<Number> getNumbers() {
        return numberRepository.findAll();
    }
    
    @GetMapping("/numbers/{id}")
    public Integer getNumber(@PathVariable Long id) {
        return numberRepository.findById(id)
                .map(Number::getValue)
                .orElse(-1);
    }
    
    @PostMapping("/numbers")
    public Number addNumber(@RequestBody NumberDTO numberDTO) {
        Number number = new Number();
        number.setValue(numberDTO.getValue());
        numberRepository.save(number);
        return number;
    }
    
    @DeleteMapping("/numbers/{id}")
    public String deleteNumber(@PathVariable Long id) {
        if (numberRepository.existsById(id)) {
            numberRepository.deleteById(id);
            return "Nombre supprimé avec succès";
        }
        return "ID invalide";
    }
}
