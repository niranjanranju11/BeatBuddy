package com.example.tunehub.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.tunehub.entities.Users;
import com.example.tunehub.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	
	@Autowired
	UsersService userv;
	
	
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {
		Order order = null;
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_C21h0tZO08Wzat", "ucI8PE2lLKqqsbz5n9UFctbH");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount",50000);
			orderRequest.put("currency","INR");
			orderRequest.put("receipt", "receipt#1");
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1","Tea, Earl Grey, Hot");
			orderRequest.put("notes",notes);

			order =razorpay.orders.create(orderRequest);
			

			
		}
		catch(Exception e) {
			System.out.println("Exception while making payment");
		}
		return order.toString()	;
	}
	

	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam  String orderId, @RequestParam String paymentId, @RequestParam String signature) {
	    try {
	        // Initialize Razorpay client with your API key and secret
	        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_C21h0tZO08Wzat", "ucI8PE2lLKqqsbz5n9UFctbH");
	        // Create a signature verification data string
	        String verificationData = orderId + "|" + paymentId;

	        // Use Razorpay's utility function to verify the signature
	        boolean isValidSignature = Utils.verifySignature(verificationData, signature, "ucI8PE2lLKqqsbz5n9UFctbH");

	        return isValidSignature;
	    } catch (RazorpayException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//payment success updating user 
	@GetMapping("payment-success")
	public String paymentSuccess(HttpSession session) {
		String email = (String) session.getAttribute("email");
	    Users user = userv.getUser(email);
	    user.setPremium(true);
	    userv.updateUser(user);
	    return "login";
	}
	
	//payment failure redirect to login
	@GetMapping("payment-failure")
	public String paymentFailure() {
	   
	    return "login";
	}

}
