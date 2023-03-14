// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_SAP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -177110671L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final int ExternalSystem_Config_SAP_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApiVersion (final @Nullable String ApiVersion)
	{
		set_Value (COLUMNNAME_ApiVersion, ApiVersion);
	}

	@Override
	public String getApiVersion()
	{
		return get_ValueAsString(COLUMNNAME_ApiVersion);
	}

	@Override
	public void setBaseURL (final @Nullable String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public String getBaseURL()
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
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
	public void setExternalSystem_Config_SAP_ID (final int ExternalSystem_Config_SAP_ID)
	{
		if (ExternalSystem_Config_SAP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, ExternalSystem_Config_SAP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_ID);
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
	public void setIsCheckDescriptionForMaterialType (final boolean IsCheckDescriptionForMaterialType)
	{
		set_Value (COLUMNNAME_IsCheckDescriptionForMaterialType, IsCheckDescriptionForMaterialType);
	}

	@Override
	public boolean isCheckDescriptionForMaterialType() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCheckDescriptionForMaterialType);
	}

	@Override
	public void setPost_Acct_Documents_Path (final @Nullable String Post_Acct_Documents_Path)
	{
		set_Value (COLUMNNAME_Post_Acct_Documents_Path, Post_Acct_Documents_Path);
	}

	@Override
	public String getPost_Acct_Documents_Path()
	{
		return get_ValueAsString(COLUMNNAME_Post_Acct_Documents_Path);
	}

	@Override
	public void setSignatureSAS (final @Nullable String SignatureSAS)
	{
		set_Value (COLUMNNAME_SignatureSAS, SignatureSAS);
	}

	@Override
	public String getSignatureSAS()
	{
		return get_ValueAsString(COLUMNNAME_SignatureSAS);
	}

	@Override
	public void setSignedPermissions (final @Nullable String SignedPermissions)
	{
		set_Value (COLUMNNAME_SignedPermissions, SignedPermissions);
	}

	@Override
	public String getSignedPermissions()
	{
		return get_ValueAsString(COLUMNNAME_SignedPermissions);
	}

	@Override
	public void setSignedVersion (final @Nullable String SignedVersion)
	{
		set_Value (COLUMNNAME_SignedVersion, SignedVersion);
	}

	@Override
	public String getSignedVersion()
	{
		return get_ValueAsString(COLUMNNAME_SignedVersion);
	}
}