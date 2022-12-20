package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.Venda;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class VendaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Venda venda){
        em.persist(venda);
    }

    public void update(Venda venda){
        em.merge(venda);
    }

    public void remove(int id){

        Venda venda = em.find(Venda.class, id);
        em.remove(venda);
    }

    public List<Venda> vendas(){
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public List<Venda> vendasData(String data){
        Query query = em.createQuery("from Venda as venda where venda.date LIKE '" + data + "%'");
        return query.getResultList();
    }

    public Venda venda(int id){
        return em.find(Venda.class, id);
    }

    public List<Venda> vendas(int id){
        Query query = em.createQuery("from Venda as venda where venda.pessoaPF = " + id);
        return query.getResultList();
    }
}
