package com.mediaflow.MediaFlowAPI_GraphQL.repository;

import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
}