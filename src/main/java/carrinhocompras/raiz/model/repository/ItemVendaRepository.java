package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.ItemVenda;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemVendaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(ItemVenda itemVenda){
        em.persist(itemVenda);
    }

    public void update(ItemVenda itemVenda){
        em.merge(itemVenda);
    }

    public void remove(int id){
        ItemVenda itemVenda = em.find(ItemVenda.class, id);
        em.remove(itemVenda);
    }

    public List<ItemVenda> itemVendas(){
        Query query = em.createQuery("from ItemVenda");
        return query.getResultList();
    }

    public ItemVenda itemVenda(int id){
        return em.find(ItemVenda.class, id);
    }
}
