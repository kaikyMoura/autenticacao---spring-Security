package com.lab.ecommercebackend.dataAcess;

import com.lab.ecommercebackend.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter //Pode ser um record
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private final User user;

    public UserDetailsImpl(User user) {
    this.user = user;
    }

    @Override //Garante a "autoridade" do usuário através do seu role
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //Só fiz essa diferenciação para fins didatics
    public String getEmail() {
        return user.getEmail();
    }
    
    @Override
	public String getUsername() {
		return user.getName();
	}

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
