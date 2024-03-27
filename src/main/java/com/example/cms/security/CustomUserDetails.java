package com.example.cms.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cms.usermodel.User;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CustomUserDetails implements UserDetails{
	private User user;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		//it is not expired
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//it is not locked
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//not expired
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
