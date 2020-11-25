package stas.paliutin.jm_311.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stas.paliutin.jm_311.dao.Dao;
import stas.paliutin.jm_311.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    @Qualifier("userDaoImp")
    private Dao<User> userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public User getById(long id) {
        return userDao.findOne(id);
    }

    @Transactional
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    public void update(User user) {
        if (user.getPassword().equals("")) {
            user.setPassword(getById(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }

    @Transactional
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Transactional
    public User findOne(String username) {
        return userDao.findOne(username);
    }
}