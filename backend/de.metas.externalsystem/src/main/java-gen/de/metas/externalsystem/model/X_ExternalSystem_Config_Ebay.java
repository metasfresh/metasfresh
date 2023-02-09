// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_Ebay
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Ebay extends org.compiere.model.PO implements I_ExternalSystem_Config_Ebay, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2015637973L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Ebay (final Properties ctx, final int ExternalSystem_Config_Ebay_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Ebay_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Ebay (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * API_Mode AD_Reference_ID=541360
	 * Reference name: API_Mode_Ebay
	 */
	public static final int API_MODE_AD_Reference_ID=541360;
	/** PRODUCTION = PRODUCTION */
	public static final String API_MODE_PRODUCTION = "PRODUCTION";
	/** SANDBOX = SANDBOX */
	public static final String API_MODE_SANDBOX = "SANDBOX";
	@Override
	public void setAPI_Mode (final java.lang.String API_Mode)
	{
		set_Value (COLUMNNAME_API_Mode, API_Mode);
	}

	@Override
	public java.lang.String getAPI_Mode() 
	{
		return get_ValueAsString(COLUMNNAME_API_Mode);
	}

	@Override
	public void setAppId (final java.lang.String AppId)
	{
		set_Value (COLUMNNAME_AppId, AppId);
	}

	@Override
	public java.lang.String getAppId() 
	{
		return get_ValueAsString(COLUMNNAME_AppId);
	}

	@Override
	public void setCertId (final java.lang.String CertId)
	{
		set_Value (COLUMNNAME_CertId, CertId);
	}

	@Override
	public java.lang.String getCertId() 
	{
		return get_ValueAsString(COLUMNNAME_CertId);
	}

	@Override
	public void setDevId (final java.lang.String DevId)
	{
		set_Value (COLUMNNAME_DevId, DevId);
	}

	@Override
	public java.lang.String getDevId() 
	{
		return get_ValueAsString(COLUMNNAME_DevId);
	}

	@Override
	public void setExternalSystem_Config_Ebay_ID (final int ExternalSystem_Config_Ebay_ID)
	{
		if (ExternalSystem_Config_Ebay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Ebay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Ebay_ID, ExternalSystem_Config_Ebay_ID);
	}

	@Override
	public int getExternalSystem_Config_Ebay_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Ebay_ID);
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
	public void setRefreshToken (final @Nullable java.lang.String RefreshToken)
	{
		set_Value (COLUMNNAME_RefreshToken, RefreshToken);
	}

	@Override
	public java.lang.String getRefreshToken() 
	{
		return get_ValueAsString(COLUMNNAME_RefreshToken);
	}
}