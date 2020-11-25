package stas.paliutin.jm_311.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stas.paliutin.jm_311.dao.Dao;
import stas.paliutin.jm_311.model.Role;

import java.util.*;

@Service
public class RoleService {

    @Autowired
    @Qualifier("roleDaoImp")
    private Dao<Role> roleDao;

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Transactional
    public Role getById(long id) {
        return roleDao.findOne(id);
    }

    @Transactional
    public Role findOne(String role) {
        return roleDao.findOne(role);
    }

    @Transactional
    public void delete(long id) {
        roleDao.deleteById(id);
    }

    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }

    @Transactional
    public void create(Role role) {
        roleDao.create(role);
    }

    public Set<Role> findByRoles(String[] roleNames) {
        Set<Role> dbRoles = new HashSet<>();
        if (roleNames != null) {
            for (String roleName : roleNames) {
                dbRoles.add( findOne( roleName ) );
            }
        }
        return dbRoles;
    }
}
