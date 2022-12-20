package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.PessoaPF;
import carrinhocompras.raiz.model.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepository {
    @PersistenceContext
    private EntityManager em;
    public void save(Role role){
        em.persist(role);
    }

    public void update(PessoaPF pessoaPF){
        em.merge(pessoaPF);
    }

    public Role role(String nome){
        return em.find(Role.class, nome);
    }
}
