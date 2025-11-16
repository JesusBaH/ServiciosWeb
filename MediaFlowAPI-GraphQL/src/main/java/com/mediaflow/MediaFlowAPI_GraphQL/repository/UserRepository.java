package com.mediaflow.MediaFlowAPI_GraphQL.repository;

import com.mediaflow.MediaFlowAPI_GraphQL.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   
}