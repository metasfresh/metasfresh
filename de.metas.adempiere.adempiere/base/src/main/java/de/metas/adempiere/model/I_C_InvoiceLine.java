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

import java.math.BigDecimal;

import de.metas.interfaces.I_C_OrderLine;

public interface I_C_InvoiceLine extends org.compiere.model.I_C_InvoiceLine
{
	// @formatter:off
	String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";
	BigDecimal getTaxAmtInfo();
	void setTaxAmtInfo(BigDecimal taxAmtInfo);

	String COLUMNNAME_ProductDescription = "ProductDescription";
	@Override String getProductDescription();
	@Override void setProductDescription(String ProductDescription);

	String COLUMNNAME_SinglePriceTag = "SinglePriceTag";
	I_C_OrderLine getSinglePriceTag();
	int getSinglePriceTag_ID();
	void setSinglePriceTag_ID(int SinglePriceTag_ID);

	String COLUMNNAME_IsManualPrice = "IsManualPrice";
	boolean isManualPrice();
	void setIsManualPrice(boolean IsManualPrice);

	String COLUMNNAME_IsPriceReadOnly = "IsPriceReadOnly";
	boolean isIsPriceReadOnly();
	void setIsPriceReadOnly(boolean IsPriceReadOnly);

	String COLUMNNAME_IsQtyReadOnly = "IsQtyReadOnly";
	boolean isIsQtyReadOnly();
	void setIsQtyReadOnly(boolean IsQtyReadOnly);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsOrderLineReadOnly = "IsOrderLineReadOnly";
	boolean isIsOrderLineReadOnly();
	@Override void setIsOrderLineReadOnly(boolean IsOrderLineReadOnly);
	// @formatter:on

	// @formatter:off
	// NOTE: virtual column
	String COLUMNNAME_IsFreightCostLine = "IsFreightCostLine";
	boolean isFreightCostLine();
	void setIsFreightCostLine(boolean IsFreightCostLine);
	// @formatter:on

	// 02362: begin
	/** Column name Discount */
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Rabatt %. Abschlag in Prozent
	 */
	void setDiscount(BigDecimal Discount);

	/**
	 * Get Rabatt %. Abschlag in Prozent
	 */
	BigDecimal getDiscount();

	// 02362: end

	// @formatter:off
	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";
	void setPrice_UOM_ID(int Price_UOM_ID);
	int getPrice_UOM_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_QtyInvoicedInPriceUOM = "QtyInvoicedInPriceUOM";
	BigDecimal getQtyInvoicedInPriceUOM();
	void setQtyInvoicedInPriceUOM(BigDecimal QtyInvoicedInPriceUOM);
	// @formatter:on

	I_C_InvoiceLine getRef_InvoiceLine();

	// @formatter:off
	String COLUMNNAME_C_Order_ID = "C_Order_ID";
	void setC_Order_ID(int C_Order_ID);
	int getC_Order_ID();
	void setC_Order(I_C_Order order);
	I_C_Order getC_Order();
	// @formatter:on



	// @formatter:off
	String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";
	boolean IsPackagingMaterial();
	void setIsPackagingMaterial(boolean IsPackagingMaterial);
	// @formatter:on



	// @formatter:off
    String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";
	@Override void setBase_PricingSystem_ID (int Base_PricingSystem_ID);
	@Override int getBase_PricingSystem_ID();
	// @formatter:on

}
