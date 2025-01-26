package com.cherifi.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/")
public class NumberController {
    
    private final RestTemplate restTemplate;
    
    @Value("${backend.url}")
    private String backendUrl;
    
    public NumberController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/numbers")
    public List<Integer> getAllNumbers() {
        return restTemplate.getForObject(backendUrl + "/api/numbers", List.class);
    }
    
    @GetMapping("/numbers/{index}")
    public Integer getNumber(@PathVariable int index) {
        return restTemplate.getForObject(backendUrl + "/api/numbers/" + index, Integer.class);
    }
    
    @PostMapping("/numbers")
    public String addNumber(@RequestBody Integer number) {
        return restTemplate.postForObject(backendUrl + "/api/numbers", number, String.class);
    }
    
    @DeleteMapping("/numbers/{index}")
    public String deleteNumber(@PathVariable int index) {
        restTemplate.delete(backendUrl + "/api/numbers/" + index);
        return "Nombre supprimé avec succès";
    }
}
