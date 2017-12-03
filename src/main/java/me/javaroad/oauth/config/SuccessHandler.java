package me.javaroad.oauth.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

/**
 * @author heyx
 */
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        if (hasRole(authentication.getAuthorities(), "ROLE_ADMIN")) {
            setDefaultTargetUrl("/admin/dashboard");
        } else if (hasRole(authentication.getAuthorities(), "ROLE_USER")) {
            setDefaultTargetUrl("/console/dashboard");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        GrantedAuthority authority = authorities.stream()
            .filter(auth -> auth.getAuthority().equals(role)).findAny()
            .orElse(null);
        return Objects.nonNull(authority);
    }

}
