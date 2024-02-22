package com.example.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tunehub.entities.Song;
import com.example.tunehub.services.SongService;

@Controller
public class SongController 
{
	@Autowired
	SongService sserv;
	
	@PostMapping("/addsongs")
	public String addSong(@ModelAttribute Song song) {
		boolean songstatus = sserv.songExists(song.getName());
		if(songstatus == false) {
		     sserv.addSongs(song);
		     return "addsongsuccess";
		}else {
			return "addsongfail";
		}
	}
	@GetMapping("/viewsongs")
	public String viewSongs(Model model){
		List<Song> listsongs= sserv.fetchAllSongs();
		model.addAttribute("songlist", listsongs);
		return "displaysongs";
	}
	@GetMapping("/customerviewsongs")
	public String viewCustomerSongs(Model model) {
		boolean primeCustomerStatus = true;
		if(primeCustomerStatus==true) {
			List<Song> listsongs= sserv.fetchAllSongs();
			model.addAttribute("songlist", listsongs);
			return "displaysongs";
		}else {
			return "makepayment";
		}
			
	}
	
	

}
