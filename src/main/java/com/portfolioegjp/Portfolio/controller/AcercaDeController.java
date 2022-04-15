package com.portfolioegjp.Portfolio.controller;

import com.portfolioegjp.Portfolio.model.AcercaDe;
import com.portfolioegjp.Portfolio.service.IAcercaDeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://portfolioegjp.web.app")
public class AcercaDeController {
    
    @Autowired
    private IAcercaDeService iacercaDeServ;
    
    @GetMapping ("/acercade")
    public List<AcercaDe> getAcercaDes(){
        return iacercaDeServ.getAcercaDe();
    }
    
      @GetMapping ("/acercade/{id}")
    public ResponseEntity<?> getAcercaDe(@PathVariable Long id){
        AcercaDe acercaDe = null;
        Map<String, Object> response = new HashMap<String, Object>();
        
        try{
            acercaDe = iacercaDeServ.findAcercaDe(id);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(acercaDe == null) {
		   response.put("mensaje", "El acercaDe ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
		   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AcercaDe>(acercaDe, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/acercade/editar/{id}")
    public ResponseEntity<?> editAcercaDe (@PathVariable Long id,@RequestBody AcercaDe acercaDeEdit){
        Map<String, Object> response = new HashMap<String, Object>();      
        AcercaDe acercaDe = iacercaDeServ.findAcercaDe(id);
        AcercaDe acercaDeUpdated = null;
        
        if (acercaDe == null) {
            response.put("mensaje", "Error, no se puede editar, acercaDe ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
        acercaDe.setContenido(acercaDeEdit.getContenido());
        acercaDeUpdated = iacercaDeServ.saveAcercaDe(acercaDe);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar acercaDe en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El contenido ha sido actualizado con éxito!");
        response.put("acercade", acercaDeUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
}
