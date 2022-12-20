package carrinhocompras.raiz.model.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Scope("session")
//@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Component
public class Venda {
    @GeneratedValue
    @Id
    private int id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @OneToMany(mappedBy="venda")
    private List<ItemVenda> itemVendas = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn
    private PessoaPF pessoaPF;

    public List<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public void setItemVendas(ItemVenda itemVenda) {
        this.itemVendas.add(itemVenda);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Double valor(){
        double valor=0;
        for(ItemVenda v: itemVendas){
            valor = valor + v.valor();
        }
        return valor;
    }

    public PessoaPF getPessoaPF() {
        return pessoaPF;
    }

    public void setPessoaPF(PessoaPF pessoaPF) {
        this.pessoaPF = pessoaPF;
    }
    
}