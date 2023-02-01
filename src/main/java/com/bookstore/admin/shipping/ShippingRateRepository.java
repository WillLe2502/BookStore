package com.bookstore.admin.shipping;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.Country;
import com.bookstore.admin.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

	public ShippingRate findByCountryAndState(Country country, String state);
}