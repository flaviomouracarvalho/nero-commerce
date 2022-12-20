package carrinhocompras.raiz.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
public class ItemVenda implements Serializable {
    
    @GeneratedValue
    @Id
    private Long id;
    @Min(1)
    private double qtd;

    @OneToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    public double valor(){
        return this.produto.getValor() * this.qtd;
    }
}
