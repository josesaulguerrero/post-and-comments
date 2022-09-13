package co.com.post_comments.alpha.application.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWT extends AbstractAuthenticationToken {
    public String value;

    public JWT(String value, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.value = value;
    }

    @Override
    public Object getCredentials() {
        return this.value;
    }

    @Override
    public Object getPrincipal() {
        return this.value;
    }
}
