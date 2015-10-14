package de.metas.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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



public interface I_C_BPartner_Location extends
		org.compiere.model.I_C_BPartner_Location
{
	public static String COLUMNNAME_Address = "Address";
	public String getAddress();
	public void setAddress(String Address);

	public static String COLUMNNAME_IsBillToDefault = "IsBillToDefault";
	public boolean isBillToDefault();
	public void setIsBillToDefault(boolean IsBillToDefault);
	
	public static String COLUMNNAME_IsShipToDefault = "IsShipToDefault";
	public boolean isShipToDefault();
	public void setIsShipToDefault(boolean IsShipToDefault);
	
	public static String COLUMNNAME_IsHandOverLocation = "IsHandOverLocation";
	public boolean isHandOverLocation();
	public void setIsHandOverLocation(boolean IsHandOverLocation);
//
//  Commenting out this de.metas.terminable related code, because it assumes that the following columns exist
//
//	public static final String COLUMNNAME_Next_ID = "Next_ID";
//	public int getNext_ID();
//	public void setNext_ID(int Next_ID);
//	
//	// 02618
//	public String COLUMNNAME_ValidTo = "ValidTo";
//	public Timestamp getValidTo();

}
