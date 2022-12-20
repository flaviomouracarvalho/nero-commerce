/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.PessoaPF;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author flavio
 */
@Repository
public class PessoaPFRepository {
    @PersistenceContext
    private EntityManager em;
    
    public void save(PessoaPF pessoaPF){
        em.persist(pessoaPF);
    }

    public void update(PessoaPF pessoaPF){
        em.merge(pessoaPF);
    }

    public void remove(int id){
        PessoaPF pessoaPF = em.find(PessoaPF.class, id);
        em.remove(pessoaPF);
    }

    public List<PessoaPF> pessoaPFS(){
        Query query = em.createQuery("from PessoaPF");
        return query.getResultList();
    }

    public List<PessoaPF> PessoasNome(String nome){
        Query query = em.createQuery("from PessoaPF as pessoa where pessoa.nome LIKE '" + nome + "%'");
        return query.getResultList();
    }

    public PessoaPF pessoaPF(int id){
        return em.find(PessoaPF.class, id);
    }
    
}
