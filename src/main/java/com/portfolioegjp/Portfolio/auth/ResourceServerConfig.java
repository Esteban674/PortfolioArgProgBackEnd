package com.portfolioegjp.Portfolio.auth;

import java.util.Arrays;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET,
                        "/experiencia", "/experiencia/{id}",
                        "/educacion", "/educacion/{id}",
                        "/cursos", "/cursos/{id}",
                        "/habilidades", "/habilidades/{id}",
                        "/images/**", "/uploads/img/**").permitAll()
//		.antMatchers(HttpMethod.POST, "experiencia/agregar").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "experiencia/editar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "experiencia/eliminar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "experiencia/upload").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "educacion/agregar").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "educacion/editar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "educacion/eliminar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "educacion/upload").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "cursos/agregar").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "cursos/editar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "cursos/eliminar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "cursos/upload").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "habilidades/agregar").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "habilidades/editar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "habilidades/eliminar/{id}").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "habilidades/upload").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
	}
        
        @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE", "OPTIONS"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
        
        @Bean
        public FilterRegistrationBean<CorsFilter> corsFilter(){
            FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
            bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return bean;
        }
}