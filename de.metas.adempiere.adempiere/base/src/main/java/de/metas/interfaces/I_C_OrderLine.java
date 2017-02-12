package de.metas.interfaces;

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

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Warehouse;

import de.metas.document.model.IDocumentLocation;

/**
 * {@link org.compiere.model.I_C_OrderLine} extension with Swat columns.
 */
public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine, IDocumentLocation
{
	// @formatter:off
	public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";
	public void setM_PriceList_Version_ID(int plvId);
	public void setM_PriceList_Version(I_M_PriceList_Version plv);
	public int getM_PriceList_Version_ID();
	public I_M_PriceList_Version getM_PriceList_Version();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";
	public BigDecimal getTaxAmtInfo();
	public void setTaxAmtInfo(BigDecimal taxAmtInfo);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsIndividualDescription = "IsIndividualDescription";
	@Override
	public boolean isIndividualDescription();
	public void setIndividualDescription(boolean individualDescription);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ProductDescription = "ProductDescription";
	@Override
	public String getProductDescription();
	@Override
	public void setProductDescription(String productDescription);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";
	public void setAD_User_ID(int AD_User_ID);
	@Override
	public int getAD_User_ID();
	@Override
	public I_AD_User getAD_User();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsManualPrice = "IsManualPrice";
	public boolean isManualPrice();
	public void setIsManualPrice(boolean isManualPrice);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsDiverse = "IsDiverse";
	public boolean isDiverse();
	public void setIsDiverse(boolean isDiverse);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_PriceStd = "PriceStd";
	public BigDecimal getPriceStd();
	public void setPriceStd(BigDecimal PriceStd);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsManualDiscount="IsManualDiscount";
	public boolean isManualDiscount();
	public void setIsManualDiscount(boolean isManualDiscount);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_OrderDiscount = "OrderDiscount";
	@Override
	public BigDecimal getOrderDiscount();
	@Override
	public void setOrderDiscount(BigDecimal OrderDiscount);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_SinglePriceTag = "SinglePriceTag";
	public I_C_OrderLine getSinglePriceTag();
	public int getSinglePriceTag_ID();
	public void setSinglePriceTag_ID(int SinglePriceTag_ID);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_GroupSumTag = "GroupSumTag";
	public String getGroupSumTag();
	public void setGroupSumTag(String GroupSumTag);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsIncludeInTotal = "IsIncludeInTotal";
	public String isIncludeInTotal();
	public void setIsIncludeInTotal(boolean IsIncludeInTotal);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";
	@Override
	public String getBPartnerAddress();
	@Override
	public void setBPartnerAddress(String BPartnerAddress);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";
	public boolean isUseBPartnerAddress();
	public void setIsUseBPartnerAddress(boolean IsUseBPartnerAddress);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";
	public void setM_Warehouse_Dest_ID(int M_Warehouse_Dest_ID);
	public int getM_Warehouse_Dest_ID();
	public I_M_Warehouse getM_Warehouse_Dest();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Handover_BPartner_ID = "Handover_BPartner_ID";
	public void setHandover_BPartner_ID(int handoverBPartnerId);
	public int getHandover_BPartner_ID();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_HandOver_Location_ID = " HandOver_Location_ID";
	public void setHandOver_Location_ID(int handoverLocationId);
	public int getHandOver_Location_ID();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";
	public void setPrice_UOM_ID(int Price_UOM_ID);
	public int getPrice_UOM_ID();
	public I_C_UOM getPrice_UOM();
	public void setPrice_UOM(I_C_UOM oum);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_QtyEnteredInPriceUOM = "QtyEnteredInPriceUOM";
	public BigDecimal getQtyEnteredInPriceUOM();
	public void setQtyEnteredInPriceUOM(BigDecimal QtyEnteredInPriceUOM);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";
	public boolean isPackagingMaterial();
	public void setIsPackagingMaterial(boolean isPackagingMaterial);
	// @formatter:on
}
