package com.filatovilia.spring.springboot.springbootapp.dao;

import com.filatovilia.spring.springboot.springbootapp.model.Role;
import java.util.List;


public interface RoleDao {

    List<Role> getAllRole();
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(long id);
    Role getRoleByName(String name);
}
