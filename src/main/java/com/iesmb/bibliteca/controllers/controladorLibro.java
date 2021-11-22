package com.iesmb.bibliteca.controllers;

import com.iesmb.bibliteca.entities.Libro;
import com.iesmb.bibliteca.services.ServicioAutor;
import com.iesmb.bibliteca.services.ServicioGenero;
import com.iesmb.bibliteca.services.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
public class controladorLibro {

    @Autowired
    private ServicioLibro svcLibro;
   @Autowired
    private ServicioGenero svcGenero;
    @Autowired
    private ServicioAutor svcAutor;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            List<Libro> libros = this.svcLibro.findAllByActivo();
            model.addAttribute("libros", libros);

            return "views/inicio";
        } catch (Exception e) {
            //model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalleLibro(Model model, @PathVariable("id") long id) {
        try {
            Libro libro = this.svcLibro.findByIdAndActivo(id);
            model.addAttribute("libro",libro);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/busqueda")
    public String busquedaLibro(Model model, @RequestParam(value ="query",required = false)String q){
        try {
            List<Libro> libros = this.svcLibro.findByTitle(q);
            model.addAttribute("libros", libros);
            model.addAttribute("resultado",q);
            return "views/busqueda";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crud")
    public String crudLibro(Model model){
        try{
            List<Libro> libros = this.svcLibro.findAll();
            model.addAttribute("libros", libros);

            return "views/crud";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/libro/{id}")
    public String formularioLibro(Model model, @PathVariable("id")long id){
        try{
            model.addAttribute("generos", this.svcGenero.findAll());
            model.addAttribute("autores", this.svcAutor.findAll());
            if (id==0){
                model.addAttribute("libro", new Libro());
            }else{
                model.addAttribute("libro", this.svcLibro.findById(id));
            }

            return "views/formulario/libro";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }


    }

   @PostMapping("/formulario/libro/{id}")
    public String guardarLibro(@RequestParam("archivo")MultipartFile archivo,
                                    @Valid @ModelAttribute("libro") Libro libro,
                                    BindingResult result,
                                    Model model,
                                    @PathVariable("id")long id)
    {
        try{
            model.addAttribute("generos",this.svcGenero.findAll());
            model.addAttribute("autores",this.svcAutor.findAll());
            if(result.hasErrors()){
                return "views/formulario/libro";
            }
           String ruta = "C://imagenes";
            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "";
            extension = "."+archivo.getOriginalFilename().substring(index+1);
            String nombreFoto = Calendar.getInstance().getTimeInMillis()+extension;
            Path rutaAbsoluta = id != 0 ? Paths.get(ruta + "//"+libro.getImagen()) : Paths.get(ruta+"//"+nombreFoto);
            System.out.println("ANTES DEL IF");
            if (id==0){
               if(archivo.isEmpty()){
                    model.addAttribute("errorImagenMsg","La imagen es requerida");
                    return "views/formulario/libro";
                }
                if (!this.validarExtension(archivo)){
                    model.addAttribute("imageErrorMsg","La extensión no es válida");
                    return "views/formulario/libro";
                }
                if(archivo.getSize() >= 15000000){
                    model.addAttribute("errorImagenMsg","El peso excede 15MB");
                    return "views/formulario/libro";
                }
                Files.write(rutaAbsoluta,archivo.getBytes());
                libro.setImagen(nombreFoto);
                this.svcLibro.saveOne(libro);
                System.out.println("SAVE");
            }else{
                if (!archivo.isEmpty()){
                    if (!this.validarExtension(archivo)){
                        System.out.println("hola");
                        model.addAttribute("imageErrorMsg","La extensión no es válida");
                        return "views/formulario/libro";
                    }
                    if(archivo.getSize() >= 15000000){
                        model.addAttribute("errorImagenMsg","El peso excede 15MB");
                        return "views/formulario/libro";
                    }
                    Files.write(rutaAbsoluta,archivo.getBytes());
                }
                this.svcLibro.updateOne(libro,id);
            }
            System.out.println("REDIRECT");
            return "redirect:/crud";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/eliminar/libro/{id}")
    public String eliminarLibro(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("libro",this.svcLibro.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/libro/{id}")
    public String desactivarLibro(Model model,@PathVariable("id")long id){
        try {
            this.svcLibro.deleteById(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());

            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo){
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
