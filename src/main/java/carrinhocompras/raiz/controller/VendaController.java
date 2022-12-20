package carrinhocompras.raiz.controller;

import carrinhocompras.raiz.model.entity.*;
import carrinhocompras.raiz.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

//redirectViewController
//@Valid
//@BidingREsult
//$ -> dados
//@ -> controller
//thymeleaf - formatar doados #dados.format(data, formato)
//session vendas.ittens
//será usado no Scopo da session
@Scope("request")
@Transactional
@Controller
@RequestMapping("venda")
public class VendaController {

    @Autowired
    VendaRepository repository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    VendaRepository VendaRepository;
    
    @Autowired
    PessoaPFRepository pessoaPFRepository;

    @Autowired
    ProdutoController produtoController;

    @Autowired
    UsuarioRepository usuarioRepository;
            
    //pede para o controller cuidar da instancia do objeto
    ///será ultilizado na session
    @Autowired
    Venda venda;
    // usado no banco

    @PostMapping("/adicionarProdutoCarrinho")
    public ModelAndView adicionarProdutoCarrinho(@Valid ItemVenda itemVenda, BindingResult result, ModelMap model, RedirectAttributes attributes) {
        if(result.hasErrors()){
            return produtoController.listProduto(model, itemVenda);
        }
        if (venda.getItemVendas().size() != 0) {
            int cont = 0;
            for (ItemVenda it : venda.getItemVendas()) {
                if (it.getProduto().getId() == itemVenda.getProduto().getId()) {
                    it.setQtd(it.getQtd() + itemVenda.getQtd());
                    cont++;
                }
            }
            if (cont == 0) {
                ItemVenda it = new ItemVenda();
                it.setProduto(produtoRepository.produto(itemVenda.getProduto().getId()));
                it.setQtd(itemVenda.getQtd());
                it.setVenda(venda);
                venda.setItemVendas(it);
            }
        }
        if (venda.getItemVendas().size() == 0) {
            ItemVenda it = new ItemVenda();
            it.setProduto(produtoRepository.produto(itemVenda.getProduto().getId()));
            it.setQtd(itemVenda.getQtd());
            it.setVenda(venda);
            venda.setItemVendas(it);
        }
        attributes.addFlashAttribute("sucesso", "Item adicionado com sucesso");
        return new ModelAndView("redirect:/produto/listProduto");
    }

    @GetMapping("carrinhoProdutos")
    public ModelAndView carrinhoProdutos(ModelMap model) {
        model.addAttribute("pessoas", pessoaPFRepository.pessoaPFS());
        return new ModelAndView("/venda/carrinhoProdutos", model);
    }

    @PostMapping("/comprarProdutos")
    public ModelAndView comprarProdutos(HttpSession sessao, RedirectAttributes attributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.usuario(authentication.getName());
        PessoaPF pessoaPF = (PessoaPF) usuario.getPessoa();
        if(pessoaPF == null) {
            attributes.addFlashAttribute("erro", "Finalize o seu cadastro para realizar a compra.");
            return new ModelAndView("redirect:/venda/carrinhoProdutos");
        }
            venda.setDate(LocalDate.now());
        venda.setPessoaPF(pessoaPFRepository.pessoaPF(pessoaPF.getId()));
        VendaRepository.save(venda);
        for (ItemVenda itemVenda : venda.getItemVendas()) {
            itemVendaRepository.save(itemVenda);
        }
        sessao.invalidate();
        attributes.addFlashAttribute("sucesso", "Compra realizada com sucesso.");
        return new ModelAndView("redirect:/venda/carrinhoProdutos");
    }

    @GetMapping("/comprasRealizadas")
    public ModelAndView comprasRealizadas(ModelMap model, @RequestParam(value = "filtros_datas", required = false) String data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.usuario(authentication.getName());
        if(data != null){
            model.addAttribute("compras", repository.vendasData(data));
        }
        else
            if(authentication.getAuthorities().size() > 0)
                for(var collection: authentication.getAuthorities()) {
                    if (collection.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
                        model.addAttribute("compras", repository.vendas());
                    }else {
                        if(usuario.getPessoa() != null) {
                            model.addAttribute("compras", repository.vendas(usuario.getPessoa().getId()));
                        }else{
                            return new ModelAndView("redirect:/produto/listProduto");
                        }
                    }
                }
        return new ModelAndView("/venda/comprasRealizadas", model);
    }
    
    @GetMapping("/detalhesCompra/{id}")
    public ModelAndView detalhesCompra(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("venda", VendaRepository.venda(id));
        return  new ModelAndView("/venda/detalhesCompra", model);
    }
    
    @GetMapping("/removerProdutoCarrinho/{id}")
    public ModelAndView removerProdutoCarrinho(@PathVariable("id") int id, RedirectAttributes attributes){
        for (int i=0; i<venda.getItemVendas().size();i++){
            if(venda.getItemVendas().get(i).getProduto().getId() == id){
                venda.getItemVendas().remove(i);
            }
        }
        attributes.addFlashAttribute("sucesso", "Item excluido com sucesso");
        return new ModelAndView("redirect:/venda/carrinhoProdutos");
    }
}