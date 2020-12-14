package stas.paliutin.jm_311.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stas.paliutin.jm_311.dao.RoleRepository;
import stas.paliutin.jm_311.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleDao;

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Transactional
    public Role findOne(long id) {
        return roleDao.findById(id).orElse(null);
    }

    @Transactional
    public Role findOne(String role) {
        return roleDao.findByName(role).orElse(null);
    }

    @Transactional
    public void delete(long id) {
        roleDao.deleteById(id);
    }

    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    public Set<Role> findByNames(String[] roleNames) {
        Set<Role> dbRoles = new HashSet<>();
        if (roleNames != null) {
            for (String roleName : roleNames) {
                dbRoles.add( findOne( roleName ) );
            }
        }
        return dbRoles;
    }

    public Set<Role> findByIds(Set<Long> roleIds) {
        Set<Role> dbRoles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                dbRoles.add( findOne( roleId ) );
            }
        }
        return dbRoles;
    }

}
