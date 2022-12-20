package carrinhocompras.raiz.model.repository;

import carrinhocompras.raiz.model.entity.Produto;
import carrinhocompras.raiz.model.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager em;

    public Usuario usuario(String email){
        return em.find(Usuario.class, email);
    }

    public void save(Usuario usuario){
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        em.persist(usuario);

    }
    public void remove(String email){
        Usuario usuario = em.find(Usuario.class, email);
        em.remove(usuario);
    }
    public List usuarios(){
        Query query = em.createQuery("from Usuario");
        return query.getResultList();
    }
}
