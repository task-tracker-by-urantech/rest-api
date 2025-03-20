package com.urantech.restapi.service.user;

import com.urantech.restapi.security.adapter.UserDetailsAdapter;
import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepo.findByEmailWithAuthorities(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User \"%s\" not found", email)));
        return new UserDetailsAdapter(user);
    }
}
