package com.bookstore.admin.setting;

import java.util.List;

import com.bookstore.admin.entity.settting.Setting;
import com.bookstore.admin.entity.settting.SettingBag;

public class PaymentSettingBag extends SettingBag {

	public PaymentSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}

	public String getURL() {
		return super.getValue("PAYPAL_API_BASE_URL");
	}

	public String getClientID() {
		return super.getValue("PAYPAL_API_CLIENT_ID");
	}

	public String getClientSecret() {
		return super.getValue("PAYPAL_API_CLIENT_SECRET");
	}
}