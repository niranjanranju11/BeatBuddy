package com.example.tunehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tunehub.entities.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList,Integer> {

}
