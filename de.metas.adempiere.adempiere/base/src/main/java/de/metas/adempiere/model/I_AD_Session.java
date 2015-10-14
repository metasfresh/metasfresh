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


public interface I_AD_Session extends org.compiere.model.I_AD_Session
{
	public static final String COLUMNNAME_IsLoginIncorect = "IsLoginIncorrect";
	@Override
	public boolean isLoginIncorrect();
	@Override
	public void setIsLoginIncorrect(boolean isLoginIncorrect);
	
	public static final String COLUMNNAME_LoginUsername = "LoginUsername";
	public String getLoginUsername();
	public void setLoginUsername(String LoginUsername);
	
	public static final String COLUMNNAME_HostKey = "HostKey";
	public String getHostKey();
	public void setHostKey(String HostKey);
}
