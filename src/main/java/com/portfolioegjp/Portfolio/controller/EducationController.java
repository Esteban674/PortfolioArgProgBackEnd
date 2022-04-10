package com.portfolioegjp.Portfolio.controller;

import com.portfolioegjp.Portfolio.model.Education;
import com.portfolioegjp.Portfolio.service.IEducationService;
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
@CrossOrigin(origins = "https://portfolioegjp.web.app")
public class EducationController {
    @Autowired
    private IEducationService ieducationServ;
    @Autowired
    private IUploadFileService uploadService;
    
    @GetMapping ("/educacion")
    public List<Education> getEducations(){
        return ieducationServ.getEducations();
    }
    
      @GetMapping ("/educacion/{id}")
    public ResponseEntity<?> getEducation(@PathVariable Long id){
        Education educacion = null;
        Map<String, Object> response = new HashMap<String, Object>();
        
        try{
            educacion = ieducationServ.findEducation(id);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(educacion == null) {
		   response.put("mensaje", "La educacion ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
		   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Education>(educacion, HttpStatus.OK);
    }
     
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/educacion/agregar")
    public ResponseEntity<?> createEducation(@RequestBody Education educacion){
        Education educacionnew = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            educacionnew = ieducationServ.saveEducation(educacion);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La educación ha sido creado con éxito!");
        response.put("educación", educacionnew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/educacion/eliminar/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long id){
       Map<String, Object> response = new HashMap<String, Object>();
        try {
            Education educacion = ieducationServ.findEducation(id);
            String nombreFotoAnterior = educacion.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            ieducationServ.deleteEducation(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la educación en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La educación ha sido eliminada con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/educacion/editar/{id}")
    public ResponseEntity<?> editEducation (@PathVariable Long id,
                                      @RequestBody Education educationEdit
                                     ){
        Map<String, Object> response = new HashMap<String, Object>();
        Education educacion = ieducationServ.findEducation(id);
        Education educacionUpdated = null;
        
        if (educacion == null) {
            response.put("mensaje", "Error, no se puede editar, la educación ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
        educacion.setCareer(educationEdit.getCareer());
        educacion.setComments(educationEdit.getComments());
        educacion.setEndDate(educationEdit.getEndDate());
        educacion.setSchool(educationEdit.getSchool());
        educacion.setStartDate(educationEdit.getStartDate());
        educacion.setTitle(educationEdit.getTitle());
        educacionUpdated = ieducationServ.saveEducation(educacion);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la educación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La educación ha sido actualizada con éxito!");
        response.put("educación", educacionUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/educacion/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<String, Object>();

        Education educacion = ieducationServ.findEducation(id);

        if (!archivo.isEmpty()) {

            String nombreArchivo = null;
            try {
                nombreArchivo = uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = educacion.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            educacion.setImg(nombreArchivo);
            ieducationServ.saveEducation(educacion);

            response.put("educacion", educacion);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}
