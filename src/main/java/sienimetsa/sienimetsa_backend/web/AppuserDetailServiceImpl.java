package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService; 

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;

@Service
public class AppuserDetailServiceImpl implements UserDetailsService {
    @Autowired 
    AppuserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Appuser curruser = repository.findByUsername(username);
        
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        UserDetails appuser = new org.springframework.security.core.userdetails.User(
                username, 
                curruser.getPasswordHash(),
                AuthorityUtils.NO_AUTHORITIES
        );
        
        return appuser;
    }
}
