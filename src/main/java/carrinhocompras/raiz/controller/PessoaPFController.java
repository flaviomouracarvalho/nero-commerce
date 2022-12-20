/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrinhocompras.raiz.controller;

import carrinhocompras.raiz.model.entity.PessoaPF;
import carrinhocompras.raiz.model.entity.Usuario;
import carrinhocompras.raiz.model.repository.PessoaPFRepository;
import carrinhocompras.raiz.model.repository.UsuarioRepository;
import carrinhocompras.raiz.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 *
 * @author flavio
 */
@Controller
@Transactional
@RequestMapping("pessoapf")
public class PessoaPFController {
    @Autowired
    PessoaPFRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    VendaRepository vendaRepository;
    
    @GetMapping("/formPessoa")
    public ModelAndView formPessoa(PessoaPF pessoaPF){
        return new ModelAndView("pessoa/formPessoaPF");
    }
    
    @PostMapping("/savePessoa")
    public ModelAndView savePessoa(@Valid PessoaPF pessoaPF, BindingResult result, RedirectAttributes attributes){
        attributes.addFlashAttribute("sucesso", "Cadastrado com sucesso.:)");
        if(result.hasErrors()){
            return formPessoa(pessoaPF);
        }
        usuarioRepository.save(pessoaPF.getUsuario());
        pessoaPF.getUsuario().setPessoa(pessoaPF);
        repository.save(pessoaPF);
        return new ModelAndView("redirect:/pessoapf/formPessoa");
    }
    
    @GetMapping("/listPessoas")
    public ModelAndView listPessoas(ModelMap model, @RequestParam(value = "filtros_pessoas",required = false) String nome){
        if (nome != null){
            model.addAttribute("pessoas", repository.PessoasNome(nome));
            return new ModelAndView("/pessoa/listPessoaPF", model);
        }
        model.addAttribute("pessoas", repository.pessoaPFS());
        return new ModelAndView("/pessoa/listPessoaPF", model);
    }
    
    @GetMapping("/editPessoa/{id}")
    public ModelAndView editCliente(@PathVariable("id") int id, ModelMap model){
        model.addAttribute("pessoaPF", repository.pessoaPF(id));
        return new ModelAndView("/pessoa/formAlterPessoaPF", model);
    }
    
    @PostMapping("/updatePessoa")
    public ModelAndView updatePessoa(PessoaPF pessoaPF, RedirectAttributes attributes){

        repository.update(pessoaPF);
        attributes.addFlashAttribute("sucesso", "Atualizado com sucesso.");
        return new ModelAndView("redirect:/pessoapf/listPessoas");
    }

    @PostMapping("/perfil")
    public ModelAndView perfil(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.usuario(authentication.getName());
        PessoaPF pessoaPF = (PessoaPF) usuario.getPessoa();
        if(pessoaPF == null){
            pessoaPF = new PessoaPF();
            pessoaPF.setUsuario(usuario);
        }

        model.addAttribute("pessoaPF", pessoaPF);
        return new ModelAndView("/pessoa/perfil", model);
    }

    @PostMapping("/editPerfil")
    public ModelAndView editPerfil(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.usuario(authentication.getName());
        PessoaPF pessoaPF = (PessoaPF) usuario.getPessoa();
        if(pessoaPF == null){
            pessoaPF = new PessoaPF();
            pessoaPF.setUsuario(usuario);
        }

        model.addAttribute("pessoaPF", pessoaPF);
        return new ModelAndView("/pessoa/formAlterPessoaPF", model);
    }
}
