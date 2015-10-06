/**
 * 
 */
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


import org.compiere.model.I_M_PricingSystem;

/**
 * @author tsa
 * 
 */
public interface I_AD_OrgInfo extends org.compiere.model.I_AD_OrgInfo
{
	public static final String COLUMNNAME_ZK_DomainName = "ZK_DomainName";

	public void setZK_DomainName(String ZK_DomainName);

	public String getZK_DomainName();

	public static final String COLUMNNAME_ZK_Logo_Large_ID = "ZK_Logo_Large_ID";

	public void setZK_Logo_Large_ID(int ZK_Logo_Large_ID);

	public int getZK_Logo_Large_ID();

	public org.compiere.model.I_AD_Image getZK_Logo_Large();

	public static final String COLUMNNAME_ZK_Logo_Small_ID = "ZK_Logo_Small_ID";

	public void setZK_Logo_Small_ID(int ZK_Logo_Small_ID);

	public int getZK_Logo_Small_ID();

	public org.compiere.model.I_AD_Image getZK_Logo_Small();

	public static final String StoreCreditCardData_STORE = "STORE";
	public static final String StoreCreditCardData_DONT_STORE = "DONT";
	public static final String StoreCreditCardData_STORE_LAST_4 = "LAST4";

	public static final String COLUMNAME_StoreCreditCardData = "StoreCreditCardData";

	public void setStoreCreditCardData(String StoreCreditCardData);

	public String getStoreCreditCardData();

	public static final String M_PRICING_SYSTEM_ID = "M_PricingSystem_ID";

	public int getM_PricingSystem_ID();

	public I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem_ID(int M_PricingSystem_ID);

	public static final String COLUMNNAME_OrgBP_Location_ID = "OrgBP_Location_ID";

	public int getOrgBP_Location_ID();

	public I_C_BPartner_Location getOrgBP_Location();

	public void setOrgBP_Location(I_C_BPartner_Location OrgBP_Location);

	public void setOrgBP_Location_ID(int OrgBP_Location_ID);
}
