/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_Shopware6
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Shopware6 extends org.compiere.model.PO implements I_ExternalSystem_Config_Shopware6, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1090812976L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Shopware6 (final Properties ctx, final int ExternalSystem_Config_Shopware6_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Shopware6_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Shopware6 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBaseURL (final java.lang.String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public java.lang.String getBaseURL() 
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
	}

	@Override
	public void setClient_Id (final java.lang.String Client_Id)
	{
		set_Value (COLUMNNAME_Client_Id, Client_Id);
	}

	@Override
	public java.lang.String getClient_Id() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Id);
	}

	@Override
	public void setClient_Secret (final java.lang.String Client_Secret)
	{
		set_Value (COLUMNNAME_Client_Secret, Client_Secret);
	}

	@Override
	public java.lang.String getClient_Secret() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Secret);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Config_Shopware6_ID (final int ExternalSystem_Config_Shopware6_ID)
	{
		if (ExternalSystem_Config_Shopware6_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public int getExternalSystem_Config_Shopware6_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public void setExternalSystemValue (final java.lang.String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public java.lang.String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setFreightCost_NormalVAT_Rates (final @Nullable java.lang.String FreightCost_NormalVAT_Rates)
	{
		set_Value (COLUMNNAME_FreightCost_NormalVAT_Rates, FreightCost_NormalVAT_Rates);
	}

	@Override
	public java.lang.String getFreightCost_NormalVAT_Rates() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCost_NormalVAT_Rates);
	}

	@Override
	public void setFreightCost_Reduced_VAT_Rates (final @Nullable java.lang.String FreightCost_Reduced_VAT_Rates)
	{
		set_Value (COLUMNNAME_FreightCost_Reduced_VAT_Rates, FreightCost_Reduced_VAT_Rates);
	}

	@Override
	public java.lang.String getFreightCost_Reduced_VAT_Rates() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCost_Reduced_VAT_Rates);
	}

	@Override
	public void setIsSyncAvailableForSalesToShopware6 (final boolean IsSyncAvailableForSalesToShopware6)
	{
		set_Value (COLUMNNAME_IsSyncAvailableForSalesToShopware6, IsSyncAvailableForSalesToShopware6);
	}

	@Override
	public boolean isSyncAvailableForSalesToShopware6() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSyncAvailableForSalesToShopware6);
	}

	@Override
	public void setJSONPathConstantBPartnerLocationID (final @Nullable java.lang.String JSONPathConstantBPartnerLocationID)
	{
		set_Value (COLUMNNAME_JSONPathConstantBPartnerLocationID, JSONPathConstantBPartnerLocationID);
	}

	@Override
	public java.lang.String getJSONPathConstantBPartnerLocationID() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPathConstantBPartnerLocationID);
	}

	@Override
	public void setJSONPathEmail (final @Nullable java.lang.String JSONPathEmail)
	{
		set_Value (COLUMNNAME_JSONPathEmail, JSONPathEmail);
	}

	@Override
	public java.lang.String getJSONPathEmail() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPathEmail);
	}

	@Override
	public void setJSONPathMetasfreshID (final @Nullable java.lang.String JSONPathMetasfreshID)
	{
		set_Value (COLUMNNAME_JSONPathMetasfreshID, JSONPathMetasfreshID);
	}

	@Override
	public java.lang.String getJSONPathMetasfreshID() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPathMetasfreshID);
	}

	@Override
	public void setJSONPathSalesRepID (final @Nullable java.lang.String JSONPathSalesRepID)
	{
		set_Value (COLUMNNAME_JSONPathSalesRepID, JSONPathSalesRepID);
	}

	@Override
	public java.lang.String getJSONPathSalesRepID() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPathSalesRepID);
	}

	@Override
	public void setJSONPathShopwareID (final @Nullable java.lang.String JSONPathShopwareID)
	{
		set_Value (COLUMNNAME_JSONPathShopwareID, JSONPathShopwareID);
	}

	@Override
	public java.lang.String getJSONPathShopwareID() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPathShopwareID);
	}

	@Override
	public void setM_FreightCost_NormalVAT_Product_ID (final int M_FreightCost_NormalVAT_Product_ID)
	{
		if (M_FreightCost_NormalVAT_Product_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_NormalVAT_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_NormalVAT_Product_ID, M_FreightCost_NormalVAT_Product_ID);
	}

	@Override
	public int getM_FreightCost_NormalVAT_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCost_NormalVAT_Product_ID);
	}

	@Override
	public void setM_FreightCost_ReducedVAT_Product_ID (final int M_FreightCost_ReducedVAT_Product_ID)
	{
		if (M_FreightCost_ReducedVAT_Product_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_ReducedVAT_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_ReducedVAT_Product_ID, M_FreightCost_ReducedVAT_Product_ID);
	}

	@Override
	public int getM_FreightCost_ReducedVAT_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCost_ReducedVAT_Product_ID);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	/** 
	 * OrderProcessing AD_Reference_ID=541831
	 * Reference name: Order Processing
	 */
	public static final int ORDERPROCESSING_AD_Reference_ID=541831;
	/** None = N */
	public static final String ORDERPROCESSING_None = "N";
	/** Sales Order = O */
	public static final String ORDERPROCESSING_SalesOrder = "O";
	/** Sales Order and Shipment = S */
	public static final String ORDERPROCESSING_SalesOrderAndShipment = "S";
	/** Sales Order, Shipment and Invoice = I */
	public static final String ORDERPROCESSING_SalesOrderShipmentAndInvoice = "I";
	@Override
	public void setOrderProcessing (final java.lang.String OrderProcessing)
	{
		set_Value (COLUMNNAME_OrderProcessing, OrderProcessing);
	}

	@Override
	public java.lang.String getOrderProcessing() 
	{
		return get_ValueAsString(COLUMNNAME_OrderProcessing);
	}

	@Override
	public void setPercentageOfAvailableForSalesToSync (final BigDecimal PercentageOfAvailableForSalesToSync)
	{
		set_Value (COLUMNNAME_PercentageOfAvailableForSalesToSync, PercentageOfAvailableForSalesToSync);
	}

	@Override
	public BigDecimal getPercentageOfAvailableForSalesToSync() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentageOfAvailableForSalesToSync);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ProductLookup AD_Reference_ID=541499
	 * Reference name: _ProductLookup
	 */
	public static final int PRODUCTLOOKUP_AD_Reference_ID=541499;
	/** Product Id = ProductId */
	public static final String PRODUCTLOOKUP_ProductId = "ProductId";
	/** Product Number = ProductNumber */
	public static final String PRODUCTLOOKUP_ProductNumber = "ProductNumber";
	@Override
	public void setProductLookup (final java.lang.String ProductLookup)
	{
		set_Value (COLUMNNAME_ProductLookup, ProductLookup);
	}

	@Override
	public java.lang.String getProductLookup() 
	{
		return get_ValueAsString(COLUMNNAME_ProductLookup);
	}
}