// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_ProCareManagement_LocalFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ProCareManagement_LocalFile extends org.compiere.model.PO implements I_ExternalSystem_Config_ProCareManagement_LocalFile, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1610272271L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_ProCareManagement_LocalFile (final Properties ctx, final int ExternalSystem_Config_ProCareManagement_LocalFile_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ProCareManagement_LocalFile_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_ProCareManagement_LocalFile (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerFileNamePattern (final @Nullable String BPartnerFileNamePattern)
	{
		set_Value (COLUMNNAME_BPartnerFileNamePattern, BPartnerFileNamePattern);
	}

	@Override
	public String getBPartnerFileNamePattern() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerFileNamePattern);
	}

	@Override
	public void setErroredDirectory (final String ErroredDirectory)
	{
		set_Value (COLUMNNAME_ErroredDirectory, ErroredDirectory);
	}

	@Override
	public String getErroredDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_ErroredDirectory);
	}

	@Override
	public I_ExternalSystem_Config_ProCareManagement getExternalSystem_Config_ProCareManagement()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, I_ExternalSystem_Config_ProCareManagement.class);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement(final I_ExternalSystem_Config_ProCareManagement ExternalSystem_Config_ProCareManagement)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, I_ExternalSystem_Config_ProCareManagement.class, ExternalSystem_Config_ProCareManagement);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement_ID (final int ExternalSystem_Config_ProCareManagement_ID)
	{
		if (ExternalSystem_Config_ProCareManagement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public int getExternalSystem_Config_ProCareManagement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement_LocalFile_ID (final int ExternalSystem_Config_ProCareManagement_LocalFile_ID)
	{
		if (ExternalSystem_Config_ProCareManagement_LocalFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_LocalFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_LocalFile_ID, ExternalSystem_Config_ProCareManagement_LocalFile_ID);
	}

	@Override
	public int getExternalSystem_Config_ProCareManagement_LocalFile_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ProCareManagement_LocalFile_ID);
	}

	@Override
	public void setFrequency (final int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Frequency);
	}

	@Override
	public int getFrequency() 
	{
		return get_ValueAsInt(COLUMNNAME_Frequency);
	}

	@Override
	public void setLocalRootLocation (final String LocalRootLocation)
	{
		set_Value (COLUMNNAME_LocalRootLocation, LocalRootLocation);
	}

	@Override
	public String getLocalRootLocation() 
	{
		return get_ValueAsString(COLUMNNAME_LocalRootLocation);
	}

	@Override
	public void setProcessedDirectory (final String ProcessedDirectory)
	{
		set_Value (COLUMNNAME_ProcessedDirectory, ProcessedDirectory);
	}

	@Override
	public String getProcessedDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessedDirectory);
	}

	@Override
	public void setProductFileNamePattern (final @Nullable String ProductFileNamePattern)
	{
		set_Value (COLUMNNAME_ProductFileNamePattern, ProductFileNamePattern);
	}

	@Override
	public String getProductFileNamePattern() 
	{
		return get_ValueAsString(COLUMNNAME_ProductFileNamePattern);
	}

	@Override
	public void setPurchaseOrderFileNamePattern (final @Nullable String PurchaseOrderFileNamePattern)
	{
		set_Value (COLUMNNAME_PurchaseOrderFileNamePattern, PurchaseOrderFileNamePattern);
	}

	@Override
	public String getPurchaseOrderFileNamePattern() 
	{
		return get_ValueAsString(COLUMNNAME_PurchaseOrderFileNamePattern);
	}

	@Override
	public void setWarehouseFileNamePattern (final @Nullable String WarehouseFileNamePattern)
	{
		set_Value (COLUMNNAME_WarehouseFileNamePattern, WarehouseFileNamePattern);
	}

	@Override
	public String getWarehouseFileNamePattern() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseFileNamePattern);
	}
}