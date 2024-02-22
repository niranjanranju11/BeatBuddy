package com.example.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tunehub.entities.PlayList;
import com.example.tunehub.entities.Song;
import com.example.tunehub.services.PlayListService;
import com.example.tunehub.services.SongService;
@Controller
public class PlayListController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlayListService playlistService;
	@GetMapping("/map-playlist")
	public String createPlaylist(Model model) {
		
		List<Song> songlist= songService.fetchAllSongs();
		model.addAttribute("songlist", songlist);
		
		return "createPlaylist";
	}
	
	@PostMapping("/addplaylist")
	public String addPlaylist(@ModelAttribute PlayList playlist) {
		//updating playlist table
		playlistService.addPlaylist(playlist);
		 
		
		
		//updating song table
		List<Song> songList = playlist.getSong();
		
		for(Song s : songList) {
			s.getPlaylist().add(playlist);
			songService.updateSong(s);			
		}
		return "playlistsuccess";
	}
	
	@GetMapping("/viewplaylist")
	public String viewPlaylists(Model model) {
		
		List<PlayList> plist= playlistService.fetchPlaylist();
		model.addAttribute("plist", plist);
		return "viewplaylist";
	}
	
	
}