package carrinhocompras.raiz.controller;

import carrinhocompras.raiz.model.entity.Role;
import carrinhocompras.raiz.model.entity.Usuario;
import carrinhocompras.raiz.model.repository.RoleRepository;
import carrinhocompras.raiz.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class LoginController {
    @Autowired
    UsuarioRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("/login/login");
    }

    @GetMapping("/register")
    public ModelAndView register(Usuario usuario){
        return new ModelAndView("/login/register");
    }

    @PostMapping("/registerUser")
    public ModelAndView registerUser(@Valid Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            return register(usuario);
        }
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.role("ROLE_EDITOR"));
        usuario.setRoles(roles);
        repository.save(usuario);



        return new ModelAndView("/login/login");
    }
}
