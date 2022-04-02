package com.portfolioegjp.Portfolio.controller;

import com.portfolioegjp.Portfolio.model.Course;
import com.portfolioegjp.Portfolio.service.ICourseService;
import com.portfolioegjp.Portfolio.service.IUploadFileService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    @Autowired
    private ICourseService icourseServ;
    @Autowired
    private IUploadFileService uploadService;
    
    @GetMapping ("/cursos")
    public List<Course> getCourses(){
        return icourseServ.getCourses();
    }
    
      @GetMapping ("/cursos/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        Course curso = null;
        Map<String, Object> response = new HashMap<String, Object>();
        
        try{
            curso = icourseServ.findCourse(id);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(curso == null) {
		   response.put("mensaje", "El curso ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
		   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Course>(curso, HttpStatus.OK);
    }
     
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/cursos/agregar")
    public ResponseEntity<?> createCourse(@RequestBody Course curso){
        Course cursonew = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            cursonew = icourseServ.saveCourse(curso);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El curso ha sido creado con éxito!");
        response.put("curso", cursonew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);     
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/cursos/eliminar/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Course curso = icourseServ.findCourse(id);
            String nombreFotoAnterior = curso.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            icourseServ.deleteCourse(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el curso en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El curso ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);  
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/cursos/editar/{id}")
    public ResponseEntity<?> editCourse (@PathVariable Long id,@RequestBody Course courseEdit){
        Map<String, Object> response = new HashMap<String, Object>();      
        Course curso = icourseServ.findCourse(id);
        Course cursoUpdated = null;
        
        if (curso == null) {
            response.put("mensaje", "Error, no se puede editar, el curso ID: ".concat(id.toString().concat(" ").concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
        curso.setComments(courseEdit.getComments());
        curso.setExpeditionDate(courseEdit.getExpeditionDate());
        curso.setCredentialId(courseEdit.getCredentialId());
        curso.setIssuingAuthority(courseEdit.getIssuingAuthority());
        curso.setName(courseEdit.getName());
        curso.setCredentialUrl(courseEdit.getCredentialUrl());
        cursoUpdated = icourseServ.saveCourse(curso);
        }catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el curso en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El curso ha sido actualizado con éxito!");
        response.put("curso", cursoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cursos/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<String, Object>();

        Course curso = icourseServ.findCourse(id);

        if (!archivo.isEmpty()) {

            String nombreArchivo = null;
            try {
                nombreArchivo = uploadService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = curso.getImg();

            uploadService.eliminar(nombreFotoAnterior);

            curso.setImg(nombreArchivo);
            icourseServ.saveCourse(curso);

            response.put("curso", curso);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

//    @GetMapping("/uploads/img/{nombreFoto:.+}")
//    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
//
//        Resource recurso = null;
//        try {
//            recurso = uploadService.cargar(nombreFoto);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        HttpHeaders cabecera = new HttpHeaders();
//        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
//
//        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
//    }

}
