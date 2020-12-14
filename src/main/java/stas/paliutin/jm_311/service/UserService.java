package stas.paliutin.jm_311.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stas.paliutin.jm_311.dao.UserRepository;
import stas.paliutin.jm_311.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public User findById(long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    public User update(User user) {
        if (( user.getPassword() == null ) ||  ( "".equals( user.getPassword() ) )) {
            user.setPassword( findById( user.getId() ).getPassword() );
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);
        return user;
    }

    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElse(null);
    }
}