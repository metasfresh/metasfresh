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

public interface I_M_PriceList extends org.compiere.model.I_M_PriceList
{

	public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	int getM_PricingSystem_ID();

	I_M_PricingSystem getM_PricingSystem();

	void setM_PricingSystem_ID(int M_PricingSystem_ID);


	public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";
	
	int getC_Country_ID();

	I_M_PricingSystem getC_Country();

	void setC_Country_ID(int C_Country_ID);

}
