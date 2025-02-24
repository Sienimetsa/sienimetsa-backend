package sienimetsa.sienimetsa_backend.web;

import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;

@Service
public class AppuserService implements UserDetailsService {
    private final AppuserRepository appuserRepository;

    public AppuserService(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    // Fetch user by email
    public Optional<Appuser> getUserByEmail(String email) {
        return appuserRepository.findByEmail(email);
    }

    //  Load user details for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Appuser currUser = appuserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                currUser.getEmail(),
                currUser.getPasswordHash(),
                AuthorityUtils.NO_AUTHORITIES
        );
    }
}
