package sienimetsa.sienimetsa_backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sienimetsa.sienimetsa_backend.domain.User;
import sienimetsa.sienimetsa_backend.domain.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
        User curruser = repository.findByAUsername(aUsername);
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found with username: " + aUsername);
        }

  
        return new org.springframework.security.core.userdetails.User(
                curruser.getUsername(),
                curruser.getPasswordHash(),
                curruser.getUsername().equals("admin")
                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN")
                : AuthorityUtils.NO_AUTHORITIES
        );
    }
}