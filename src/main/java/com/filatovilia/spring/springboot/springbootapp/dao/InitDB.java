package com.filatovilia.spring.springboot.springbootapp.dao;

import com.filatovilia.spring.springboot.springbootapp.model.Role;
import com.filatovilia.spring.springboot.springbootapp.model.User;
import com.filatovilia.spring.springboot.springbootapp.service.RoleService;
import com.filatovilia.spring.springboot.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class InitDB {


    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public InitDB(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostConstruct
    public void initApiUserData() {

        Role user = new Role("USER");
        Role admin = new Role("ADMIN");

        Set<Role> roleUser = new HashSet<>();
        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleAdminUser = new HashSet<>();

        roleUser.add(user);
        roleAdmin.add(admin);
        roleAdminUser.add(user);
        roleAdminUser.add(admin);

        roleService.addRole(user);
        roleService.addRole(admin);


        User user1 = new User("Roma","Roma");
        user1.setRoles(roleUser);

        User user4 = new User("Stas","Stas");
        user4.setRoles(roleAdmin);

        User user2 = new User("Viktor","Viktor");
        user2.setRoles(roleAdminUser);


        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user4);
    }
}
