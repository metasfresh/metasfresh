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

import org.adempiere.model.ModelColumn;

import java.sql.Timestamp;

public interface I_AD_User extends org.compiere.model.I_AD_User
{

	public static final String NOTIFICATIONTYPE_NotifyUserInCharge = "O";

	// LoginFailureCount
	public static final String COLUMNNAME_LoginFailureCount = "LoginFailureCount";

	@Override
	public int getLoginFailureCount();

	@Override
	public void setLoginFailureCount(int loginFailureCount);

	// IsAccountLocked
	public static final String COLUMNNAME_IsAccountLocked = "IsAccountLocked";

	@Override
	public boolean isAccountLocked();

	@Override
	public void setIsAccountLocked(boolean isAccountLocked);

	// LockedFromIP
	public static final String COLUMNNAME_LockedFromIP = "LockedFromIP";

	@Override
	public String getLockedFromIP();

	@Override
	public void setLockedFromIP(String lockedFromIP);

	// LoginFailureDate
	public static final String COLUMNNAME_LoginFailureDate = "LoginFailureDate";

	@Override
	public Timestamp getLoginFailureDate();

	@Override
	public void setLoginFailureDate(Timestamp loginFailureDate);

	public static final String COLUMNNAME_PasswordResetCode = "PasswordResetCode";

	public static final String COLUMNNAME_IsSystemUser = "IsSystemUser";

	@Override
	public boolean isSystemUser();

	@Override
	public void setIsSystemUser(boolean IsSystemUser);

	// 04212: Add field Login
	public static final String COLUMNNAME_Login = "Login";

	@Override
	public String getLogin();

	@Override
	public void setLogin(String Login);

	@Override
	void setC_Title_ID (int C_Title_ID);

	@Override
	int getC_Title_ID();

	String COLUMNNAME_C_Title_ID = "C_Title_ID";

	@Override
	void setIsAuthorizedSignatory (boolean IsAuthorizedSignatory);

	@Override
	boolean isAuthorizedSignatory();

	String COLUMNNAME_IsAuthorizedSignatory = "IsAuthorizedSignatory";

}
