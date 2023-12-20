package org.example.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QueryController {

  @RequestMapping("/")
  public String index() {
    return "query";
  }
}
