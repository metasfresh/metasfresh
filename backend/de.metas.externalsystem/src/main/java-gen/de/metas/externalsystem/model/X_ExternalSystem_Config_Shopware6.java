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

	private static final long serialVersionUID = -370255063L;

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
	public void setBaseURL (final String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public String getBaseURL()
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
	}

	@Override
	public void setClient_Id (final String Client_Id)
	{
		set_Value (COLUMNNAME_Client_Id, Client_Id);
	}

	@Override
	public String getClient_Id()
	{
		return get_ValueAsString(COLUMNNAME_Client_Id);
	}

	@Override
	public void setClient_Secret (final String Client_Secret)
	{
		set_Value (COLUMNNAME_Client_Secret, Client_Secret);
	}

	@Override
	public String getClient_Secret()
	{
		return get_ValueAsString(COLUMNNAME_Client_Secret);
	}

	@Override
	public I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class, ExternalSystem_Config);
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
	public void setExternalSystemValue (final String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public String getExternalSystemValue()
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setFreightCost_NormalVAT_Rates (final @Nullable String FreightCost_NormalVAT_Rates)
	{
		set_Value (COLUMNNAME_FreightCost_NormalVAT_Rates, FreightCost_NormalVAT_Rates);
	}

	@Override
	public String getFreightCost_NormalVAT_Rates()
	{
		return get_ValueAsString(COLUMNNAME_FreightCost_NormalVAT_Rates);
	}

	@Override
	public void setFreightCost_Reduced_VAT_Rates (final @Nullable String FreightCost_Reduced_VAT_Rates)
	{
		set_Value (COLUMNNAME_FreightCost_Reduced_VAT_Rates, FreightCost_Reduced_VAT_Rates);
	}

	@Override
	public String getFreightCost_Reduced_VAT_Rates()
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
	public void setJSONPathConstantBPartnerLocationID (final @Nullable String JSONPathConstantBPartnerLocationID)
	{
		set_Value (COLUMNNAME_JSONPathConstantBPartnerLocationID, JSONPathConstantBPartnerLocationID);
	}

	@Override
	public String getJSONPathConstantBPartnerLocationID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathConstantBPartnerLocationID);
	}

	@Override
	public void setJSONPathEmail (final @Nullable String JSONPathEmail)
	{
		set_Value (COLUMNNAME_JSONPathEmail, JSONPathEmail);
	}

	@Override
	public String getJSONPathEmail()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathEmail);
	}

	@Override
	public void setJSONPathMetasfreshID (final @Nullable String JSONPathMetasfreshID)
	{
		set_Value (COLUMNNAME_JSONPathMetasfreshID, JSONPathMetasfreshID);
	}

	@Override
	public String getJSONPathMetasfreshID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathMetasfreshID);
	}

	@Override
	public void setJSONPathSalesRepID (final @Nullable String JSONPathSalesRepID)
	{
		set_Value (COLUMNNAME_JSONPathSalesRepID, JSONPathSalesRepID);
	}

	@Override
	public String getJSONPathSalesRepID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathSalesRepID);
	}

	@Override
	public void setJSONPathShopwareID (final @Nullable String JSONPathShopwareID)
	{
		set_Value (COLUMNNAME_JSONPathShopwareID, JSONPathShopwareID);
	}

	@Override
	public String getJSONPathShopwareID()
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
	public void setProductLookup (final String ProductLookup)
	{
		set_Value (COLUMNNAME_ProductLookup, ProductLookup);
	}

	@Override
	public String getProductLookup()
	{
		return get_ValueAsString(COLUMNNAME_ProductLookup);
	}
}