package org.nbu.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.nbu.shared.ApplicationRole;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakConfigurationSecurity extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/companies/")
            .hasRole(ApplicationRole.SYSTEM_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/api/companies/{companyId}")
            .hasRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}")
            .hasAnyRole(ApplicationRole.SYSTEM_ADMIN, ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_CLIENT,
                        ApplicationRole.COMPANY_EMPLOYEE)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/api/companies/{companyId}")
            .hasRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/companies/{companyId}/administrators")
            .hasRole(ApplicationRole.SYSTEM_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/api/companies/{companyId}/clients/{clientId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_CLIENT)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/clients/{clientId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_CLIENT)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/api/companies/{companyId}/clients/{clientId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_CLIENT)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/clients")
            .hasRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/api/companies/{companyId}/employees/{employeeId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/api/companies/{companyId}/employees/{employeeId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/employees/{employeeId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/locations/{locationId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE, ApplicationRole.COMPANY_CLIENT)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/api/companies/{companyId}/locations/{locationId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/api/companies/{companyId}/locations/{locationId}")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/companies/{companyId}/locations/")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/locations/")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE, ApplicationRole.COMPANY_CLIENT)
            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/companies/{companyId}/employees")
            .hasAnyRole(ApplicationRole.COMPANY_ADMIN, ApplicationRole.COMPANY_EMPLOYEE)
            .anyRequest()
            .authenticated()
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
