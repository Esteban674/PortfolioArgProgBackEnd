package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.AcercaDe;
import java.util.List;


public interface IAcercaDeService {
    
     public List<AcercaDe> getAcercaDe ();
    
    public AcercaDe saveAcercaDe (AcercaDe acercaDe);
    
    public void deleteAcercaDe (Long id);
    
    public AcercaDe findAcercaDe (Long id);
}
