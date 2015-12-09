package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;

public interface I_AD_User extends org.compiere.model.I_AD_User
{

	public static final String NOTIFICATIONTYPE_NotifyUserInCharge = "O";

	public static final String COLUMNNAME_Firstname = "Firstname";
	@Deprecated
	public static final String FIRST_NAME = COLUMNNAME_Firstname;

	String getFirstName();

	void setFirstName(String firstName);

	public static final String COLUMNNAME_Lastname = "Lastname";
	@Deprecated
	public static final String LAST_NAME = COLUMNNAME_Lastname;

	String getLastName();

	void setLastName(String lastName);

	public static final String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	public boolean isDefaultContact();

	public void setIsDefaultContact(boolean IsDefaultContact);

	// LoginFailureCount
	public static final String COLUMNNAME_LoginFailureCount = "LoginFailureCount";

	public int getLoginFailureCount();

	public void setLoginFailureCount(int loginFailureCount);

	// IsAccountLocked
	public static final String COLUMNNAME_IsAccountLocked = "IsAccountLocked";

	public boolean isAccountLocked();

	public void setIsAccountLocked(boolean isAccountLocked);

	// LockedFromIP
	public static final String COLUMNNAME_LockedFromIP = "LockedFromIP";

	public String getLockedFromIP();

	public void setLockedFromIP(String lockedFromIP);

	// LoginFailureDate
	public static final String COLUMNNAME_LoginFailureDate = "LoginFailureDate";

	public Timestamp getLoginFailureDate();

	public void setLoginFailureDate(Timestamp loginFailureDate);

	public static final String COLUMNNAME_PasswordResetCode = "PasswordResetCode";

	public String getPasswordResetCode();

	public void setPasswordResetCode(String PasswordResetCode);

	public static final String COLUMNNAME_IsSystemUser = "IsSystemUser";

	public boolean isSystemUser();

	public void setIsSystemUser(boolean IsSystemUser);

	// 04212: Add field Login
	public static final String COLUMNNAME_Login = "Login";

	public String getLogin();

	public void setLogin(String Login);

	// 08578: Add sales and purchase contact fields
	public static final String COLUMNNAME_IsSalesContact = "IsSalesContact";

	public boolean isSalesContact();

	public void setIsSalesContact(boolean IsSalesContact);

	public static final String COLUMNNAME_IsPurchaseContact = "IsPurchaseContact";

	public boolean isPurchaseContact();

	public void setIsPurchaseContact(boolean IsPurchaseContact);

	//@formatter:off
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);
	public int getAD_User_InCharge_ID();
	public org.compiere.model.I_AD_User getAD_User_InCharge();
	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge);
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";
	//@formatter:on
}
