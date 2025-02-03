package sienimetsa.sienimetsa_backend.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService; 

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;

@Service
public class AppuserDetailServiceImpl implements UserDetailsService {
    
    private final AppuserRepository repository;

    public AppuserDetailServiceImpl(AppuserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Appuser curruser = repository.findByEmail(email);
        
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found with email: " + email); 
        }
        
        return new org.springframework.security.core.userdetails.User(
                curruser.getEmail(), 
                curruser.getPasswordHash(),
                AuthorityUtils.NO_AUTHORITIES
        );
    }
}
