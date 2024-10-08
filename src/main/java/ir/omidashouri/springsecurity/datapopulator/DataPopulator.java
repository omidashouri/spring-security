package ir.omidashouri.springsecurity.datapopulator;

import ir.omidashouri.springsecurity.entities.UserEntity;
import ir.omidashouri.springsecurity.enums.Privilege;
import ir.omidashouri.springsecurity.enums.Role;
import ir.omidashouri.springsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataPopulator implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = UserEntity.builder().
                username("user").password(passwordEncoder.encode("user")).build();
        user.setRole(Role.USER);
        userRepository.save(user);

        UserEntity admin = UserEntity.builder().
                username("admin").password(passwordEncoder.encode("admin")).build();
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }
}
