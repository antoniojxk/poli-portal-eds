package co.com.estacionsannicolas.config.security;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Esta clase realiza la configuración de seguridad de la aplicación
 *
 * @author Antonio Paternina <acpaternina@poli.edu.co>
 */
@EnableWebSecurity
public class ConfiguracionSpringSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionSpringSecurity.class);

    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.debug("Inicia configuración global de SpringSecurity");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfiguracionSpring.class);
        dataSource = ctx.getBean(DataSource.class);

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username as principal, password as credentials, activo from Usuario where username = ?")
                .authoritiesByUsernameQuery(
                        "select username as principal, nombre as role from Usuario u inner join RolUsuario r on u.id = r.usuario_id and u.username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/javax.faces.resource/**").permitAll()
                .antMatchers("/index.xhtml").permitAll()
                .antMatchers("/registro/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .permitAll();

        http.
                logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.xhtml?logout")
                .invalidateHttpSession(true);

    }
}