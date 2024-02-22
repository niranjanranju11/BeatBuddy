package com.example.tunehub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tunehub.entities.Song;
import com.example.tunehub.repository.SongRepository;

@Service
public class SongServiceImplementation implements SongService{
	
	@Autowired
	SongRepository srepo;

	@Override
	public String addSongs(Song song) {
		srepo.save(song);
		return "song is added";
	}

	@Override
	public boolean songExists(String name) {
		if(srepo.findByName(name)==null) {
			return false;
		}else {
			return true;
		}
		 
	}

	@Override
	public List<Song> fetchAllSongs() {
	    List<Song> listsongs = srepo.findAll();
		return listsongs;
	}

	@Override
	public void updateSong(Song song) {
		srepo.save(song);
		
	}
	

}
