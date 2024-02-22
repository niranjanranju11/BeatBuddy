package com.example.tunehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tunehub.entities.Song;

public interface SongRepository extends JpaRepository<Song, Integer>{
	
	public Song findByName(String name);

}
