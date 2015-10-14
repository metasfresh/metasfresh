package de.metas.interfaces;

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


public interface I_C_BPartner_Product extends org.compiere.model.I_C_BPartner_Product
{
	// @formatter:off
	String COLUMNNAME_ProductNo = "ProductNo";
	String getProductNo();
	void setProductNo(String ProductNo);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_ProductName = "ProductName";
	String getProductName();
	void setProductName(String ProductName);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_ProductDescription = "ProductDescription";
	String getProductDescription();
	void setProductDescription(String ProductDescription);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_UPC = "UPC";
	String getUPC();
	void setUPC(String UPC);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_ProductCategory = "ProductCategory";
	String getProductCategory();
	void setProductCategory(String ProductCategory);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_UsedForCustomer = "UsedForCustomer";
	boolean isUsedForCustomer();
	void setUsedForCustomer(boolean UsedForCustomer);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_UsedForVendor = "UsedForVendor";
	boolean isUsedForVendor();
	void setUsedForVendor(boolean UsedForVendor);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsDropShip = "IsDropShip";
	boolean isDropShip();
	void setIsDropShip(boolean IsDropShip);
	// @formatter:on
}
