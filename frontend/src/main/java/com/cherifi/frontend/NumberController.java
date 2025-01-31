package com.cherifi.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class NumberController {

    private final String backendUrl = "http://backend-service";
    private final RestTemplate restTemplate;

    public NumberController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/api/numbers")
    @ResponseBody
    public List<NumberDTO> getNumbers() {
        ResponseEntity<NumberDTO[]> response = restTemplate.getForEntity(backendUrl + "/api/numbers", NumberDTO[].class);
        return List.of(response.getBody());
    }

    @PostMapping("/api/numbers")
    @ResponseBody
    public ResponseEntity<NumberDTO> addNumber(@RequestBody NumberDTO number) {
        ResponseEntity<NumberDTO> response = restTemplate.postForEntity(backendUrl + "/api/numbers", number, NumberDTO.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
