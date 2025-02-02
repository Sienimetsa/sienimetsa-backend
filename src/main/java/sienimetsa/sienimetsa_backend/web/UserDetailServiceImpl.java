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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = repository.findByUsername(username);
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

  
        return new org.springframework.security.core.userdetails.User(
                curruser.getUsername(),
                curruser.getPasswordHash(),
                AuthorityUtils.NO_AUTHORITIES // No roles assigned
        );
    }
}