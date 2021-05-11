// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_Shopware6
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Shopware6 extends org.compiere.model.PO implements I_ExternalSystem_Config_Shopware6, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1866486218L;

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

	/**
	 * BPartner_IfExists AD_Reference_ID=541309
	 * Reference name: SyncAdvice_IfExists
	 */
	public static final int BPARTNER_IFEXISTS_AD_Reference_ID=541309;
	/** Update = UPDATE_MERGE */
	public static final String BPARTNER_IFEXISTS_Update = "UPDATE_MERGE";
	/** Nothing = DONT_UPDATE */
	public static final String BPARTNER_IFEXISTS_Nothing = "DONT_UPDATE";
	@Override
	public void setBPartner_IfExists (final java.lang.String BPartner_IfExists)
	{
		set_Value (COLUMNNAME_BPartner_IfExists, BPartner_IfExists);
	}

	@Override
	public java.lang.String getBPartner_IfExists()
	{
		return get_ValueAsString(COLUMNNAME_BPartner_IfExists);
	}

	/**
	 * BPartner_IfNotExists AD_Reference_ID=541310
	 * Reference name: SyncAdvice_IfNotExists
	 */
	public static final int BPARTNER_IFNOTEXISTS_AD_Reference_ID=541310;
	/** Create = CREATE */
	public static final String BPARTNER_IFNOTEXISTS_Create = "CREATE";
	/** Fail = FAIL */
	public static final String BPARTNER_IFNOTEXISTS_Fail = "FAIL";
	@Override
	public void setBPartner_IfNotExists (final java.lang.String BPartner_IfNotExists)
	{
		set_Value (COLUMNNAME_BPartner_IfNotExists, BPartner_IfNotExists);
	}

	@Override
	public java.lang.String getBPartner_IfNotExists()
	{
		return get_ValueAsString(COLUMNNAME_BPartner_IfNotExists);
	}

	/**
	 * BPartnerLocation_IfExists AD_Reference_ID=541309
	 * Reference name: SyncAdvice_IfExists
	 */
	public static final int BPARTNERLOCATION_IFEXISTS_AD_Reference_ID=541309;
	/** Update = UPDATE_MERGE */
	public static final String BPARTNERLOCATION_IFEXISTS_Update = "UPDATE_MERGE";
	/** Nothing = DONT_UPDATE */
	public static final String BPARTNERLOCATION_IFEXISTS_Nothing = "DONT_UPDATE";
	@Override
	public void setBPartnerLocation_IfExists (final java.lang.String BPartnerLocation_IfExists)
	{
		set_Value (COLUMNNAME_BPartnerLocation_IfExists, BPartnerLocation_IfExists);
	}

	@Override
	public java.lang.String getBPartnerLocation_IfExists()
	{
		return get_ValueAsString(COLUMNNAME_BPartnerLocation_IfExists);
	}

	/**
	 * BPartnerLocation_IfNotExists AD_Reference_ID=541310
	 * Reference name: SyncAdvice_IfNotExists
	 */
	public static final int BPARTNERLOCATION_IFNOTEXISTS_AD_Reference_ID=541310;
	/** Create = CREATE */
	public static final String BPARTNERLOCATION_IFNOTEXISTS_Create = "CREATE";
	/** Fail = FAIL */
	public static final String BPARTNERLOCATION_IFNOTEXISTS_Fail = "FAIL";
	@Override
	public void setBPartnerLocation_IfNotExists (final java.lang.String BPartnerLocation_IfNotExists)
	{
		set_Value (COLUMNNAME_BPartnerLocation_IfNotExists, BPartnerLocation_IfNotExists);
	}

	@Override
	public java.lang.String getBPartnerLocation_IfNotExists()
	{
		return get_ValueAsString(COLUMNNAME_BPartnerLocation_IfNotExists);
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
	public void setExternalSystemValue (final @Nullable java.lang.String ExternalSystemValue)
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
	public void setJSONPathConstantBPartnerID (final @Nullable java.lang.String JSONPathConstantBPartnerID)
	{
		set_Value (COLUMNNAME_JSONPathConstantBPartnerID, JSONPathConstantBPartnerID);
	}

	@Override
	public java.lang.String getJSONPathConstantBPartnerID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathConstantBPartnerID);
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
}