package com.bookstore.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Country;
import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.setting.CountryRepository;

@Service
public class CustomerService {

	@Autowired private CountryRepository countryRepo;
	@Autowired private CustomerRepository customerRepo;

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}

}
