package br.com.mercadolivre.service;

import br.com.mercadolivre.repository.SimianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimianService {

    @Autowired
    private SimianRepository simianRepository;

    public Boolean isSimian(String[] string){
        return true;
    }

}
