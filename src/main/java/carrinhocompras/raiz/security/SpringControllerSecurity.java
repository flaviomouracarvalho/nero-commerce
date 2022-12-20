package carrinhocompras.raiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringControllerSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuariosDetailsConfig usuariosDetailsConfig;

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http
                .authorizeRequests()//define com as requisições HTTP devem ser tratadas com relação à segurança.
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/produto/listProduto").permitAll()
                .antMatchers("/pessoapf/listPessoa").hasRole("ADMIN")
                .antMatchers("/produto/formProduto").hasRole("ADMIN")
                .antMatchers("/pessoapf/formPessoa").hasRole("ADMIN")
                .antMatchers("venda/comprasRealizadas").hasRole("ADMIN")
                .antMatchers("/pessoapf/listPessoas").hasRole("ADMIN")
                .antMatchers("/pessoapf/listPessoas").hasRole("ADMIN")
                .antMatchers("/register").permitAll()
                .antMatchers("/registerUser").permitAll()
                .anyRequest()//define que a configuração é válida para qualquer requisição.
                .authenticated()//define que o usuário precisa estar autenticado.
                .and()
                .formLogin()//define que a autenticação pode ser feita via formulário de login.
                .loginPage("/login")// passamos como parâmetro a URL para acesso à página de login que criamos
                .permitAll() //define que essa página pode ser acessada por todos, independentemente do usuário estar autenticado ou não.
                .and()
                .logout()
                .permitAll();
    }
    @Autowired
    public void configureUserDetails(AuthenticationManagerBuilder builder) throws Exception {
        try {
            builder
                    .userDetailsService(usuariosDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
                /*.inMemoryAuthentication()
                .withUser("admin").password( new BCryptPasswordEncoder().encode("1234")).roles("ADMIN")
                .and()
                .withUser("user").password( new BCryptPasswordEncoder().encode("1234")).roles("USER");*/
        }catch (UsernameNotFoundException e){
            System.out.println("Ola");
        }
    }
    /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o controle dessa instância como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar disso, ele já terá o que precisa configurado.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public static String passwordEncoder(){

        return new BCryptPasswordEncoder().encode("1234");
    }

    public static void main(String[] args) {
        System.out.println(SpringControllerSecurity.passwordEncoder());
    }*/
}
