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


import org.compiere.model.I_M_PricingSystem;

public interface I_C_BP_Group extends org.compiere.model.I_C_BP_Group {

	public static final String M_PRICING_SYSTEM_ID = "M_PricingSystem_ID";

	public int getM_PricingSystem_ID();
	
	public I_M_PricingSystem getM_PricingSystem();
	
	public void setM_PricingSystem_ID(int M_PricingSystem_ID);
	
	
	public static final String PO_PRICING_SYSTEM_ID = "PO_PricingSystem_ID";
	
	int getPO_PricingSystem_ID();
	
	
	
	// @formatter:off
	public static final String COLUMNNAME_IsCustomer = "IsCustomer";
	public void setIsCustomer(boolean IsCustomer);
	boolean isCustomer();
	// @formatter:on
}
