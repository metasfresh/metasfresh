// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_LeichMehl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_LeichMehl extends org.compiere.model.PO implements I_ExternalSystem_Config_LeichMehl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 72495201L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_LeichMehl (final Properties ctx, final int ExternalSystem_Config_LeichMehl_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_LeichMehl_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_LeichMehl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * CU_TU_PLU AD_Reference_ID=541906
	 * Reference name: CU_TU_PLU
	 */
	public static final int CU_TU_PLU_AD_Reference_ID=541906;
	/** CU = CU */
	public static final String CU_TU_PLU_CU = "CU";
	/** TU = TU */
	public static final String CU_TU_PLU_TU = "TU";
	@Override
	public void setCU_TU_PLU (final String CU_TU_PLU)
	{
		set_Value (COLUMNNAME_CU_TU_PLU, CU_TU_PLU);
	}

	@Override
	public String getCU_TU_PLU() 
	{
		return get_ValueAsString(COLUMNNAME_CU_TU_PLU);
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
	public void setExternalSystem_Config_LeichMehl_ID (final int ExternalSystem_Config_LeichMehl_ID)
	{
		if (ExternalSystem_Config_LeichMehl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, ExternalSystem_Config_LeichMehl_ID);
	}

	@Override
	public int getExternalSystem_Config_LeichMehl_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_LeichMehl_ID);
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
	public void setIsPluFileExportAuditEnabled (final boolean IsPluFileExportAuditEnabled)
	{
		set_Value (COLUMNNAME_IsPluFileExportAuditEnabled, IsPluFileExportAuditEnabled);
	}

	@Override
	public boolean isPluFileExportAuditEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPluFileExportAuditEnabled);
	}

	@Override
	public void setProduct_BaseFolderName (final String Product_BaseFolderName)
	{
		set_Value (COLUMNNAME_Product_BaseFolderName, Product_BaseFolderName);
	}

	@Override
	public String getProduct_BaseFolderName() 
	{
		return get_ValueAsString(COLUMNNAME_Product_BaseFolderName);
	}

	@Override
	public void setTCP_Host (final String TCP_Host)
	{
		set_Value (COLUMNNAME_TCP_Host, TCP_Host);
	}

	@Override
	public String getTCP_Host() 
	{
		return get_ValueAsString(COLUMNNAME_TCP_Host);
	}

	@Override
	public void setTCP_PortNumber (final int TCP_PortNumber)
	{
		set_Value (COLUMNNAME_TCP_PortNumber, TCP_PortNumber);
	}

	@Override
	public int getTCP_PortNumber() 
	{
		return get_ValueAsInt(COLUMNNAME_TCP_PortNumber);
	}
}