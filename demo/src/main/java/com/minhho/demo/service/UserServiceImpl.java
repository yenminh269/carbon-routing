package com.minhho.demo.service;

import com.minhho.demo.entity.Role;
import com.minhho.demo.entity.User;
import com.minhho.demo.repository.RoleRepository;
import com.minhho.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, String password, Set<String> roleNames){
        Set<Role> roles = new HashSet<>();
        for(String roleName : roleNames){
            Role role = roleRepository.findByName(roleName).
                    orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public void seedRolesAndAdmin(){
        //seed default roles
        if(roleRepository.count() == 0){
            Role admin = new Role("ADMIN");
            Role manager = new Role("MANAGER");
            Role employee = new Role("EMPLOYEE");
            roleRepository.save(admin);
            roleRepository.save(manager);
            roleRepository.save(employee);
        }

        //seed default admin user
        if(!userRepository.existsByUsername("admin")){
            Set<String> roles = Set.of("ADMIN");
            createUser("admin", "admin123", roles);
        }
    }
}
