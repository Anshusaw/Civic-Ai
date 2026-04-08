package com.incapp.security;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.incapp.entity.Citizen;
import com.incapp.entity.Role;
import com.incapp.entity.User;
import com.incapp.repo.CitizenRepository;
import com.incapp.repo.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CitizenRepository citizenRepo;
	
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception   {
		http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/citizen_register","/register","/forgotPassword","/reset_password","/updatePassword", "/civic_login", "/css/**", "/images/**").permitAll()
                
				.requestMatchers("/citizen/**").hasRole("USER")
                .requestMatchers("/officer/**").hasRole("OFFICER")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/civic_login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler())
                .failureUrl("/civic_login?error=true")
            )

            .oauth2Login(oauth -> oauth
                .loginPage("/civic_login")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(oauth2UserService())
                )
                .successHandler(successHandler())
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/civic_login?logout=true")
            )
			.exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"));

        return http.build();
    }

    // BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Redirect based on role
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            for (var auth : authentication.getAuthorities()) {

                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin/home");
                    return;
                }

                if (auth.getAuthority().equals("ROLE_OFFICER")) {
                    response.sendRedirect("/officer/home");
                    return;
                }

                if (auth.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect("/citizen/home");
                    return;
                }
            }

            response.sendRedirect("/civic_login?error=true");
        };
    }
    
    @Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		UserDetailsService customUserDetailsService = new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				User user = userRepo.findByEmail(email);
				if (user == null) {
					throw new UsernameNotFoundException("User not found");
				}
				// Convert roles → authorities
		        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // ROLE_USER, ROLE_OFFICER, ROLE_ADMIN
                .toList();
				return new org.springframework.security.core.userdetails.User(
						user.getEmail(), 
						user.getPassword(), 
						true, // accountIsEnable 
						true, // accountNonExpired
						true, // credentialsNonExpired
						true, // accountNonLocked
						authorities);
			}

		};
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

    // Google login (Citizen)
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return request -> {

            OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(request);
            String email = oauthUser.getAttribute("email");

            User user = userRepo.findByEmail(email);

            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setProvider("GOOGLE");
                user.setRoles(Set.of(Role.ROLE_USER));
                userRepo.save(user);
                
                Citizen citizen=new Citizen();
                citizen.setEmail(email);
                String name = oauthUser.getAttribute("name");
                citizen.setName(name);
                String phone = oauthUser.getAttribute("phone");
                citizen.setPhone(phone);
//                String pictureUrl = oauthUser.getAttribute("picture");
//                System.out.println(pictureUrl);
//	             // Convert to bytes
//	   	   	     byte[] photoBytes = null;
//	   	   	     try (InputStream in = new URL(pictureUrl).openStream()) {
//	   	   	         photoBytes = in.readAllBytes();
//	   	   	     } catch (IOException e) {
//	   	   			e.printStackTrace();
//	   	   		}
                citizenRepo.save(citizen);
            }

            return new DefaultOAuth2User(
                    List.of(new SimpleGrantedAuthority("ROLE_USER")),
                    oauthUser.getAttributes(),
                    "email"
            );
        };
    }
}
