// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_Alberta
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Alberta extends org.compiere.model.PO implements I_ExternalSystem_Config_Alberta, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -40681645L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Alberta (final Properties ctx, final int ExternalSystem_Config_Alberta_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Alberta_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Alberta (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApiKey (final String ApiKey)
	{
		set_Value (COLUMNNAME_ApiKey, ApiKey);
	}

	@Override
	public String getApiKey()
	{
		return get_ValueAsString(COLUMNNAME_ApiKey);
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
	public void setC_Root_BPartner_ID (final int C_Root_BPartner_ID)
	{
		if (C_Root_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_Root_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_Root_BPartner_ID, C_Root_BPartner_ID);
	}

	@Override
	public int getC_Root_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Root_BPartner_ID);
	}

	@Override
	public void setExternalSystem_Config_Alberta_ID (final int ExternalSystem_Config_Alberta_ID)
	{
		if (ExternalSystem_Config_Alberta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Alberta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Alberta_ID, ExternalSystem_Config_Alberta_ID);
	}

	@Override
	public int getExternalSystem_Config_Alberta_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Alberta_ID);
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
	public void setPharmacy_PriceList_ID (final int Pharmacy_PriceList_ID)
	{
		if (Pharmacy_PriceList_ID < 1) 
			set_Value (COLUMNNAME_Pharmacy_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_Pharmacy_PriceList_ID, Pharmacy_PriceList_ID);
	}

	@Override
	public int getPharmacy_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Pharmacy_PriceList_ID);
	}

	@Override
	public void setTenant (final String Tenant)
	{
		set_Value (COLUMNNAME_Tenant, Tenant);
	}

	@Override
	public String getTenant()
	{
		return get_ValueAsString(COLUMNNAME_Tenant);
	}
}