package com.lrm.service;

import com.lrm.po.User;

import java.util.List;

public interface UserService {

    User checkUser(String username, String password);

    User getUser();

}
