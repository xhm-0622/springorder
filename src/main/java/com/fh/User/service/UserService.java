package com.fh.User.service;

import com.fh.User.model.User;
import com.fh.common.ServerResponse;

public interface UserService {
    ServerResponse queryByName(String name);

    ServerResponse queryByPhone(String phone);

    ServerResponse register(User user);

    ServerResponse login(User user);
}
