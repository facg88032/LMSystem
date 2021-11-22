package com.james.wj.service;


import com.james.wj.dao.UserDao;
import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.AdminUserRole;
import com.james.wj.pojo.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    public List<User> list() {
        List<User> users = userDao.findAll();

        // Find all roles in DB to enable JPA persistence context.
//        List<AdminRole> allRoles = adminRoleService.findAll();

//        List<User> userDTOS = users
//                .stream().map(user -> (UserDTO) new UserDTO().convertFrom(user)).collect(Collectors.toList());

        users.forEach(u -> {
            List<AdminRole> roles = adminRoleService.listRolesByUser(u.getUsername());
            u.setRoles(roles);
        });

        return users;
    }

    public int register(User user){
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if (username.equals("") || password.equals("")) {
            return 0;
        }
        boolean exist = isExist(username);
        if(exist){
            return 2;
        }

        //生成 salt
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        User newUser = userDao.save(user);
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setUid(newUser.getId());
        adminUserRole.setRid(3);
        adminUserRoleService.addRelate(adminUserRole);

        return 1;
    }

    public void editUser(User user){
        User userInDB = userDao.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setEmail(user.getEmail());
        userInDB.setPhone(user.getPhone());
        add(userInDB);
        adminUserRoleService.saveRoleChanges(userInDB.getId(),user.getRoles());
    }


    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }



    public void resetPassword(User user) {
        User userInDB = userDao.findByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();

        userInDB.setSalt(salt);
        userInDB.setPassword(encodedPassword);
        add(userInDB);
    }

    public boolean isExist(String username){
        User user= userDao.findByUsername(username);
        return null!=user;
    }

    public void add(User user){
        userDao.save(user);
    }

    @Transactional
    public void deleteById(int id){
        userDao.deleteById(id);
        adminUserRoleService.deleteByUid(id);
    }
}
