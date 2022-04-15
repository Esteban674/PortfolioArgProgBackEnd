package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.AcercaDe;
import com.portfolioegjp.Portfolio.repository.AcercaDeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcercaDeService implements IAcercaDeService{

    @Autowired
    private AcercaDeRepository acercaDeRepo;
    
    @Override
    public List<AcercaDe> getAcercaDe() {
        List<AcercaDe> listaAcercaDe = acercaDeRepo.findAll();
      return listaAcercaDe;
    }

    @Override
    public AcercaDe saveAcercaDe(AcercaDe acercaDe) {
        return acercaDeRepo.save(acercaDe);
    }

    @Override
    public void deleteAcercaDe(Long id) {
        acercaDeRepo.deleteById(id);
    }

    @Override
    public AcercaDe findAcercaDe(Long id) {
       AcercaDe acercaDe = acercaDeRepo.findById(id).orElse(null);
        return acercaDe;
    }
    
}
