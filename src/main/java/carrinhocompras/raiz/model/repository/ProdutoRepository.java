package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.Produto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProdutoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Produto produto){
        em.persist(produto);
    }

    public void update(Produto produto){
        em.merge(produto);
    }
    public void remove(int id){
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }

    public List<Produto> produtos(){
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public Produto produto(Long id){
        return em.find(Produto.class, id);
    }
}
