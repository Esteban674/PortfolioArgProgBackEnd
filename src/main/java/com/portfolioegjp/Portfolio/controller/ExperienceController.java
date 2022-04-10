package com.portfolioegjp.Portfolio.controller;

import com.portfolioegjp.Portfolio.model.Experience;
import com.portfolioegjp.Portfolio.model.Mode;
import com.portfolioegjp.Portfolio.service.IExperienceService;
import com.portfolioegjp.Portfolio.service.IUploadFileService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "https://portfolioegjp.web.app") ///@CrossOrigin(origins = "*")
public class ExperienceController {
    
    @Autowired
    private IExperienceService iexperienceServ;
    @Autowired
    private IUploadFileService uploadService;
    
    @GetMapping ("/experiencia")
    public List<Experience> getExperiences(){
        return iexperienceServ.getExperiences();
    }
    
    
    @GetMapping ("/experiencia/{id}")
    public ResponseEntity<?> getExperience(@PathVariable Long id){
        Experience experiencia = null;
        Map<String, Object> response = new HashMap<String, Object>();
        
        try{
            experiencia = iexperienceServ.findExperience(id);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(experiencia == null) {
		   response.put("mensaje", "La experiencia ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
		   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Experience>(experiencia, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/experiencia/agregar")
    public ResponseEntity<?> createExperience(@RequestBody Experience experiencia){
        Experience experiencianew = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            experiencianew = iexperienceServ.saveExperience(experiencia);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La experiencia ha sido creado con éxito!");
        response.put("experiencia", experiencianew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/experiencia/eliminar/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id){
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Experience experiencia = iexperienceServ.findExperience(id);
            String nombreFotoAnterior = experiencia.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            iexperienceServ.deleteExperience(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la experiencia en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La experiencia ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/experiencia/editar/{id}")
    public ResponseEntity<?> editExperience (@PathVariable Long id,
                                      @RequestBody Experience experienciaEdit
                                     ){
        Map<String, Object> response = new HashMap<String, Object>();
        Experience experiencia = iexperienceServ.findExperience(id);
        Experience experienciaUpdated = null;
        
        if (experiencia == null) {
            response.put("mensaje", "Error, no se puede editar, la experiencia ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
       try{ 
       experiencia.setPosition(experienciaEdit.getPosition());
       experiencia.setMode(experienciaEdit.getMode());
       experiencia.setCompany(experienciaEdit.getCompany());
       experiencia.setLocation(experienciaEdit.getLocation());
       experiencia.setStartDate(experienciaEdit.getStartDate());
       experiencia.setEndDate(experienciaEdit.getEndDate());
       experiencia.setComments(experienciaEdit.getComments());
       experienciaUpdated = iexperienceServ.saveExperience(experiencia);
       }catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la experiencia en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La experiencia ha sido actualizada con éxito!");
        response.put("experiencia", experienciaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/experiencia/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<String, Object>();

        Experience experiencia = iexperienceServ.findExperience(id);

        if (!archivo.isEmpty()) {

            String nombreArchivo = null;
            try {
                nombreArchivo = uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = experiencia.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            experiencia.setImg(nombreArchivo);
            iexperienceServ.saveExperience(experiencia);

            response.put("experiencia", experiencia);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}

