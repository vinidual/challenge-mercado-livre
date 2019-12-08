package br.com.mercadolivre.controller;

import br.com.mercadolivre.contract.SimianContract;
import br.com.mercadolivre.service.SimianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController("simian")
@Api
@Validated
public class SimianController {

    @Autowired
    private SimianService simianService;

    @ApiOperation(value = "POST simian payload to verification")
    @PostMapping(value = "/simian")
    public ResponseEntity<Object> simian(@Valid @RequestBody SimianContract simianContract)
            throws ExecutionException, InterruptedException {

        return simianService.isSimian(simianContract.getDna().toArray(new String[0])) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ApiOperation(value = "GET stats population simians ratio")
    @GetMapping(value = "/stats")
    public ResponseEntity<Object> stats(){
        return ResponseEntity.ok(simianService.getStats());
    }


}
