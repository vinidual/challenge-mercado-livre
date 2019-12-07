package br.com.mercadolivre.controller;

import br.com.mercadolivre.contract.SimianContract;
import br.com.mercadolivre.service.SimianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("simian")
@Validated
public class SimianController {

    @Autowired
    private SimianService simianService;

    @PostMapping
    public ResponseEntity<Object> simian(@Valid @RequestBody SimianContract simianContract){

        return simianService.isSimian(simianContract.getDna().toArray(new String[0])) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
