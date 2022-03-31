package com.springBasics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBasics.entities.Vendor;
import com.springBasics.models.VendorOperation;

@RestController
public class VendorController {
	
@Autowired
VendorOperation vo;	

@RequestMapping("/vendor")
public List<Vendor> getAllVendors(){
	
	return vo.getAllVendors();
}

@RequestMapping("/vendor/{id}")
public Vendor getVendorById(@PathVariable("id") String id){
	
	return vo.getVendorById(id);
}

@PostMapping("/vendor")
public Vendor createVendor(@RequestBody Vendor Payload) {

	return vo.createVendor(Payload);
	
}


}
