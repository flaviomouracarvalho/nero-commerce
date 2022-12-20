package carrinhocompras.raiz.controller;

import carrinhocompras.raiz.model.entity.ItemVenda;
import carrinhocompras.raiz.model.entity.Produto;
import carrinhocompras.raiz.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository repository;

    @GetMapping("/formProduto")
    public ModelAndView formProduto(Produto produto){
        return new ModelAndView("/produto/formProduto");
    }

    @PostMapping("/saveProduto")
    public ModelAndView saveProduto(@Valid Produto produto, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return formProduto(produto);
        }
        attributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso.:)");
        repository.save(produto);
        return new ModelAndView("redirect:/produto/formProduto");
    }

    @GetMapping("/listProduto")
    public ModelAndView listProduto(ModelMap model, ItemVenda itemVenda){
        model.addAttribute("produtos", repository.produtos());
        return new ModelAndView("/produto/listProduto" );
    }

}