package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.model.User;

public interface UserService {

    User findById(Long id);
    

}