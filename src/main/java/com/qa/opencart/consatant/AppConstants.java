package com.qa.opencart.consatant;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final String LOGIN_PAGE_TITLE="Account Login";
	
	public static final String LOGIN_PAGE_URL_FRACTION="route=account/login";
	
	public static final String ACCOUNT_PAGE_TITLE="My Account";
	
    public static final String ACCOUNT_PAGE_URL_FRACTION="route=account/account";

    public static final int ACCOUNTS_PAGE_HEADER_COUNTS=4;
    
    public static final List<String>  EXPECTED_ACCOUNT_PAGE_HEADER_LIST=Arrays.asList("My Account","My Orders",
    		                    "My Affiliate Account","Newsletter");
    
    public static final String REGISTRATION_PAGE_TITLE="Register Account";
    
    public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created";

    
	//************************Default Time Out Values******************//
	public static final int SHORT_TIME_OUT=5;
	
	public static final int MEDIUM_TIME_OUT=10;
 
	public static final int LONG_TIME_OUT=15;
	
	
	
	
	
	//*********************************Sheets Name****************
	
	public static final String REGISTER_SHEET_NAME="register";
	public static final String PRODUCTINFO_SHEET_NAME="productinfo";

	}
