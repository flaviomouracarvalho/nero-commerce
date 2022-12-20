package carrinhocompras.raiz.security;

import carrinhocompras.raiz.model.entity.Usuario;
import carrinhocompras.raiz.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;


@Repository
@Transactional
public class UsuariosDetailsConfig implements UserDetailsService {
    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.usuario(email);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não Encontrado");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
    }
}
