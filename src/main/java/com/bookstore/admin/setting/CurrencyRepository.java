package com.bookstore.admin.setting;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}