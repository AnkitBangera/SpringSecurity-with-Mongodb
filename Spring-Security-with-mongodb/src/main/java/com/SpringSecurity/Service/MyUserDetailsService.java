package com.SpringSecurity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringSecurity.Dao.Dao;
import com.SpringSecurity.Pojo.UserDetail;
import com.SpringSecurity.Pojo.UserDetailsC;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	Dao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetail userdetail=dao.getUser(username);
		UserDetailsC userDetailsC=new UserDetailsC(userdetail);
		System.out.println("Password>>>"+userDetailsC.getPassword());
		System.out.println("UserName>>>>"+userDetailsC.getUsername());
		System.out.println("Authorities"+userDetailsC.getAuthorities());
		System.out.println("AccountNonExpired"+userDetailsC.isAccountNonExpired());
		System.out.println("AccountNonLocked"+userDetailsC.isAccountNonLocked());
		System.out.println("CredentialsNonExpired"+userDetailsC.isCredentialsNonExpired());
		System.out.println("Enabled"+userDetailsC.isEnabled());
		
		return userDetailsC;
	}

}
