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

import org.compiere.model.I_C_UOM;

import de.metas.interfaces.I_C_OrderLine;

public interface I_C_InvoiceLine extends org.compiere.model.I_C_InvoiceLine
{
	// @formatter:off
	public static String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";
	public BigDecimal getTaxAmtInfo();
	public void setTaxAmtInfo(BigDecimal taxAmtInfo);

	public static String COLUMNNAME_ProductDescription = "ProductDescription";
	@Override
	public String getProductDescription();
	@Override
	public void setProductDescription(String ProductDescription);

	public static final String COLUMNNAME_SinglePriceTag = "SinglePriceTag";
	public I_C_OrderLine getSinglePriceTag();
	public int getSinglePriceTag_ID();
	public void setSinglePriceTag_ID(int SinglePriceTag_ID);

	public static String COLUMNNAME_IsManualPrice = "IsManualPrice";
	public boolean isManualPrice();
	public void setIsManualPrice(boolean IsManualPrice);

	public static String COLUMNNAME_IsPriceReadOnly = "IsPriceReadOnly";
	public boolean isIsPriceReadOnly();
	public void setIsPriceReadOnly(boolean IsPriceReadOnly);
	
	public static String COLUMNNAME_IsQtyReadOnly = "IsQtyReadOnly";
	public boolean isIsQtyReadOnly();
	public void setIsQtyReadOnly(boolean IsQtyReadOnly);
	// @formatter:on

	// @formatter:off
	public static String COLUMNNAME_IsOrderLineReadOnly = "IsOrderLineReadOnly";
	public boolean isIsOrderLineReadOnly();
	public void setIsOrderLineReadOnly(boolean IsOrderLineReadOnly);
	// @formatter:on

	// @formatter:off
	// NOTE: virtual column
	public static final String COLUMNNAME_IsFreightCostLine = "IsFreightCostLine";
	public boolean isFreightCostLine();
	public void setIsFreightCostLine(boolean IsFreightCostLine);
	// @formatter:on

	// 02362: begin
	/** Column name Discount */
	public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Rabatt %. Abschlag in Prozent
	 */
	public void setDiscount(BigDecimal Discount);

	/**
	 * Get Rabatt %. Abschlag in Prozent
	 */
	public BigDecimal getDiscount();

	// 02362: end

	// @formatter:off
	public static final String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";
	public void setPrice_UOM_ID(int Price_UOM_ID);
	public int getPrice_UOM_ID();
	public void setPrice_UOM(I_C_UOM uom);
	public I_C_UOM getPrice_UOM();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_QtyInvoicedInPriceUOM = "QtyInvoicedInPriceUOM";
	public BigDecimal getQtyInvoicedInPriceUOM();
	public void setQtyInvoicedInPriceUOM(BigDecimal QtyInvoicedInPriceUOM);
	// @formatter:on

	public I_C_InvoiceLine getRef_InvoiceLine();

	// @formatter:off
	public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
	public void setC_Order_ID(int C_Order_ID);
	public int getC_Order_ID();
	public void setC_Order(I_C_Order order);
	public I_C_Order getC_Order();
	// @formatter:on
	
	
	
	// @formatter:off
	public static String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";
	public boolean IsPackagingMaterial();
	public void setIsPackagingMaterial(boolean IsPackagingMaterial);
	// @formatter:on
	
	
	
	// @formatter:off
    public static final String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID);
	public int getBase_PricingSystem_ID();
	// @formatter:on

}
