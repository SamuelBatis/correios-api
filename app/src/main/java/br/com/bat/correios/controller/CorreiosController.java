package br.com.bat.correios.controller;

import br.com.bat.correios.exceptions.NoContentException;
import br.com.bat.correios.exceptions.NotReadyException;
import br.com.bat.correios.model.Address;
import br.com.bat.correios.service.CorreiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CorreiosController {

  @Autowired
  private CorreiosService service;

  @GetMapping("/status")
  public String getStatus() {
    return "Service status: " + this.service.getStatus();
  }

  @GetMapping("/zipcode/{zipcode}")
  public Address getAddressByZipcode(@PathVariable String zipcode) throws NoContentException, NotReadyException {
    return this.service.getAddressByZipcode(zipcode);
  }
}
