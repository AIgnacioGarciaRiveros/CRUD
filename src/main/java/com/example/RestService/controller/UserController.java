package com.example.RestService.controller;

import com.example.RestService.entity.User;
import com.example.RestService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService servicio;

    @GetMapping("/todos")
    public ModelAndView showUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("user-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("errorUser", flashMap.get("error-name"));
            mav.addObject("exitoModificacion", flashMap.get("exito-modificado"));
        }
        mav.addObject("users", servicio.showUser());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView createUser() {
        ModelAndView mav = new ModelAndView("userformulario");
        mav.addObject("user", new User());
        mav.addObject("action", "guardar");
        return mav;

    }

    @PostMapping("/guardar")
    public RedirectView saveUser(@RequestParam String name, RedirectAttributes attributes) {
        try{
            servicio.createUser(name);
            attributes.addFlashAttribute("exito-name","El usuario ha sido creado exitosamente");
        }catch(Exception e){
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/user/todos");
    }

    @PostMapping("/modificar")
    public RedirectView modifyUser(@RequestParam Integer id, @RequestParam String name,RedirectAttributes attributes) {
        servicio.modifyUser(id, name);
        attributes.addFlashAttribute("exito-modificado", "El usuario ha sido modificado exitosamente");
        return new RedirectView("/user/todos");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editUser(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("userformulario");
        mav.addObject("user", servicio.searchForId(id));
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView deleteUser(@PathVariable Integer id) {
        servicio.deleteUser(id);
        return new RedirectView("/user/todos");
    }
}
