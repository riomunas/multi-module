package org.example.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandRestController {

  @GetMapping("/")
  public String index() {
    return "command";
  }
}
