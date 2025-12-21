package com.hfuninternal.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.hfuninternal.model.User;
import com.hfuninternal.model.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
    
    private User user;
    private List<GrantedAuthority> authorities;
    
    // Constructor
    public UserDetailsImpl(User user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }
    
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> {
                // Remove the ERole type declaration - let Java infer it
                Object roleNameObj = role.getName();
                
                if (roleNameObj == null) {
                    return new SimpleGrantedAuthority("ROLE_USER");
                }
                
                // Use toString() which works for any object including enums
                String roleName = roleNameObj.toString();
                
                // Optional: Add ROLE_ prefix if not present (though your enum already has it)
                if (!roleName.startsWith("ROLE_")) {
                    roleName = "ROLE_" + roleName;
                }
                
                return new SimpleGrantedAuthority(roleName);
            })
            .collect(Collectors.toList());
        
        return new UserDetailsImpl(user, authorities);
    }
    
    // Implement UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
    
    // Getters for additional fields
    public Long getId() {
        return user.getId();
    }
    
    public String getEmail() {
        return user.getEmail();
    }
    
    public User getUser() {
        return user;
    }
}