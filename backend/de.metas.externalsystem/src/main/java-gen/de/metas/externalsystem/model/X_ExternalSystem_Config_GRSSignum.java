// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_GRSSignum
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_GRSSignum extends org.compiere.model.PO implements I_ExternalSystem_Config_GRSSignum, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1588297937L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_GRSSignum (final Properties ctx, final int ExternalSystem_Config_GRSSignum_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_GRSSignum_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_GRSSignum (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAuthToken (final @Nullable java.lang.String AuthToken)
	{
		set_Value (COLUMNNAME_AuthToken, AuthToken);
	}

	@Override
	public java.lang.String getAuthToken() 
	{
		return get_ValueAsString(COLUMNNAME_AuthToken);
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
	public void setCamelHttpResourceAuthKey (final @Nullable java.lang.String CamelHttpResourceAuthKey)
	{
		set_Value (COLUMNNAME_CamelHttpResourceAuthKey, CamelHttpResourceAuthKey);
	}

	@Override
	public java.lang.String getCamelHttpResourceAuthKey() 
	{
		return get_ValueAsString(COLUMNNAME_CamelHttpResourceAuthKey);
	}

	@Override
	public void setExternalSystem_Config_GRSSignum_ID (final int ExternalSystem_Config_GRSSignum_ID)
	{
		if (ExternalSystem_Config_GRSSignum_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_GRSSignum_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_GRSSignum_ID, ExternalSystem_Config_GRSSignum_ID);
	}

	@Override
	public int getExternalSystem_Config_GRSSignum_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_GRSSignum_ID);
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
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
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
	public void setIsAutoSendCustomers (final boolean IsAutoSendCustomers)
	{
		set_Value (COLUMNNAME_IsAutoSendCustomers, IsAutoSendCustomers);
	}

	@Override
	public boolean isAutoSendCustomers() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoSendCustomers);
	}

	@Override
	public void setIsAutoSendVendors (final boolean IsAutoSendVendors)
	{
		set_Value (COLUMNNAME_IsAutoSendVendors, IsAutoSendVendors);
	}

	@Override
	public boolean isAutoSendVendors() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoSendVendors);
	}

	@Override
	public void setIsSyncBPartnersToRestEndpoint (final boolean IsSyncBPartnersToRestEndpoint)
	{
		set_Value (COLUMNNAME_IsSyncBPartnersToRestEndpoint, IsSyncBPartnersToRestEndpoint);
	}

	@Override
	public boolean isSyncBPartnersToRestEndpoint() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSyncBPartnersToRestEndpoint);
	}

	@Override
	public void setIsSyncHUsOnMaterialReceipt (final boolean IsSyncHUsOnMaterialReceipt)
	{
		set_Value (COLUMNNAME_IsSyncHUsOnMaterialReceipt, IsSyncHUsOnMaterialReceipt);
	}

	@Override
	public boolean isSyncHUsOnMaterialReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSyncHUsOnMaterialReceipt);
	}

	@Override
	public void setIsSyncHUsOnProductionReceipt (final boolean IsSyncHUsOnProductionReceipt)
	{
		set_Value (COLUMNNAME_IsSyncHUsOnProductionReceipt, IsSyncHUsOnProductionReceipt);
	}

	@Override
	public boolean isSyncHUsOnProductionReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSyncHUsOnProductionReceipt);
	}

	/** 
	 * TenantId AD_Reference_ID=276
	 * Reference name: AD_Org (all)
	 */
	public static final int TENANTID_AD_Reference_ID=276;
	@Override
	public void setTenantId (final java.lang.String TenantId)
	{
		set_Value (COLUMNNAME_TenantId, TenantId);
	}

	@Override
	public java.lang.String getTenantId() 
	{
		return get_ValueAsString(COLUMNNAME_TenantId);
	}
}