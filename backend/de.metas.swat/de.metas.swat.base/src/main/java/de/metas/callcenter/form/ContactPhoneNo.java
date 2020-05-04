/**
 * 
 */
package de.metas.callcenter.form;

/*
 * #%L
 * de.metas.swat.base
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


/**
 * @author teo_sarca
 *
 */
public class ContactPhoneNo
{
	private final int AD_User_ID;
	private final String phoneNo;
	private final String contactName;
	
	public ContactPhoneNo(String phoneNo, String contactName, int AD_User_ID)
	{
		super();
		this.phoneNo = phoneNo;
		this.contactName = contactName;
		this.AD_User_ID = AD_User_ID;
	}

	public String getPhoneNo()
	{
		return phoneNo;
	}

	public String getContactName()
	{
		return contactName;
	}

	public int getAD_User_ID()
	{
		return AD_User_ID;
	}
	
	@Override
	public String toString()
	{
		return phoneNo + " ("+contactName+")";
	}
	
	
}
