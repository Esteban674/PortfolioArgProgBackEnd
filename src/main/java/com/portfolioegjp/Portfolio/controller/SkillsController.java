package com.portfolioegjp.Portfolio.controller;

import com.portfolioegjp.Portfolio.model.Skills;
import com.portfolioegjp.Portfolio.service.ISkillsService;
import com.portfolioegjp.Portfolio.service.IUploadFileService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "http://localhost:4200")
public class SkillsController {
    @Autowired
    private ISkillsService iskillsServ;
    @Autowired
    private IUploadFileService uploadService;
    
    @GetMapping ("/habilidades")
    public List<Skills> getSkills(){
        return iskillsServ.getSkills();
    }
    
      @GetMapping ("/habilidades/{id}")
    public ResponseEntity<?> getSkill(@PathVariable Long id){
        Skills habilidad = null;
        Map<String, Object> response = new HashMap<String, Object>();
        
        try{
            habilidad = iskillsServ.findSkill(id);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(habilidad == null) {
		   response.put("mensaje", "La habilidad ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
		   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Skills>(habilidad, HttpStatus.OK);
    }
     
    @PostMapping ("/habilidades/agregar")
    public ResponseEntity<?> createSkill(@RequestBody Skills habilidad){
        Skills habilidadnew = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            habilidadnew = iskillsServ.saveSkill(habilidad);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La habilidad ha sido creada con éxito!");
        response.put("habilidad", habilidadnew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);     
    }
    
    @DeleteMapping ("/habilidades/eliminar/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id){
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Skills habilidad = iskillsServ.findSkill(id);
            String nombreFotoAnterior = habilidad.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            iskillsServ.deleteSkill(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la habilidad en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La habilidad ha sido eliminada con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);  
    }
    
    @PutMapping ("/habilidades/editar/{id}")
    public ResponseEntity<?> editSkill (@PathVariable Long id,@RequestBody Skills skillEdit){
        Map<String, Object> response = new HashMap<String, Object>();      
        Skills habilidad = iskillsServ.findSkill(id);
        Skills habilidadUpdated = null;
        
        if (habilidad == null) {
            response.put("mensaje", "Error, no se puede editar, la habilidad ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            habilidad.setName(skillEdit.getName());
            habilidad.setGrupo(skillEdit.getGrupo());
            habilidad.setCategory(skillEdit.getCategory());
            habilidad.setStatus(skillEdit.getStatus());
            habilidadUpdated = iskillsServ.saveSkill(habilidad);
            
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la habilidad en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La habilidad ha sido actualizado con éxito!");
        response.put("habilidad", habilidadUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/habilidades/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<String, Object>();

        Skills habilidad = iskillsServ.findSkill(id);

        if (!archivo.isEmpty()) {

            String nombreArchivo = null;
            try {
                nombreArchivo = uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = habilidad.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            habilidad.setImg(nombreArchivo);
            iskillsServ.saveSkill(habilidad);

            response.put("habilidad", habilidad);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
