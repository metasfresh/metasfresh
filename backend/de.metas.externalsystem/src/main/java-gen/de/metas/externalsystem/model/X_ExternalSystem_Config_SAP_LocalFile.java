// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_SAP_LocalFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP_LocalFile extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP_LocalFile, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1985976800L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP_LocalFile (final Properties ctx, final int ExternalSystem_Config_SAP_LocalFile_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_LocalFile_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP_LocalFile (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApprovedBy_ID (final int ApprovedBy_ID)
	{
		if (ApprovedBy_ID < 1) 
			set_Value (COLUMNNAME_ApprovedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ApprovedBy_ID, ApprovedBy_ID);
	}

	@Override
	public int getApprovedBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ApprovedBy_ID);
	}

	@Override
	public void setErroredDirectory (final java.lang.String ErroredDirectory)
	{
		set_Value (COLUMNNAME_ErroredDirectory, ErroredDirectory);
	}

	@Override
	public java.lang.String getErroredDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_ErroredDirectory);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config_SAP getExternalSystem_Config_SAP()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_SAP_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class);
	}

	@Override
	public void setExternalSystem_Config_SAP(final de.metas.externalsystem.model.I_ExternalSystem_Config_SAP ExternalSystem_Config_SAP)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_SAP_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class, ExternalSystem_Config_SAP);
	}

	@Override
	public void setExternalSystem_Config_SAP_ID (final int ExternalSystem_Config_SAP_ID)
	{
		if (ExternalSystem_Config_SAP_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_SAP_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_SAP_ID, ExternalSystem_Config_SAP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_ID);
	}

	@Override
	public void setExternalSystem_Config_SAP_LocalFile_ID (final int ExternalSystem_Config_SAP_LocalFile_ID)
	{
		if (ExternalSystem_Config_SAP_LocalFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_LocalFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_LocalFile_ID, ExternalSystem_Config_SAP_LocalFile_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_LocalFile_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_LocalFile_ID);
	}

	@Override
	public void setLocal_Root_Location (final java.lang.String Local_Root_Location)
	{
		set_Value (COLUMNNAME_Local_Root_Location, Local_Root_Location);
	}

	@Override
	public java.lang.String getLocal_Root_Location() 
	{
		return get_ValueAsString(COLUMNNAME_Local_Root_Location);
	}

	@Override
	public void setLocalFile_BPartner_FileName_Pattern (final @Nullable java.lang.String LocalFile_BPartner_FileName_Pattern)
	{
		set_Value (COLUMNNAME_LocalFile_BPartner_FileName_Pattern, LocalFile_BPartner_FileName_Pattern);
	}

	@Override
	public java.lang.String getLocalFile_BPartner_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_BPartner_FileName_Pattern);
	}

	@Override
	public void setLocalFile_BPartner_TargetDirectory (final @Nullable java.lang.String LocalFile_BPartner_TargetDirectory)
	{
		set_Value (COLUMNNAME_LocalFile_BPartner_TargetDirectory, LocalFile_BPartner_TargetDirectory);
	}

	@Override
	public java.lang.String getLocalFile_BPartner_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_BPartner_TargetDirectory);
	}

	@Override
	public void setLocalFile_ConversionRate_FileName_Pattern (final @Nullable java.lang.String LocalFile_ConversionRate_FileName_Pattern)
	{
		set_Value (COLUMNNAME_LocalFile_ConversionRate_FileName_Pattern, LocalFile_ConversionRate_FileName_Pattern);
	}

	@Override
	public java.lang.String getLocalFile_ConversionRate_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_ConversionRate_FileName_Pattern);
	}

	@Override
	public void setLocalFile_ConversionRate_TargetDirectory (final @Nullable java.lang.String LocalFile_ConversionRate_TargetDirectory)
	{
		set_Value (COLUMNNAME_LocalFile_ConversionRate_TargetDirectory, LocalFile_ConversionRate_TargetDirectory);
	}

	@Override
	public java.lang.String getLocalFile_ConversionRate_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_ConversionRate_TargetDirectory);
	}

	@Override
	public void setLocalFile_CreditLimit_FileName_Pattern (final @Nullable java.lang.String LocalFile_CreditLimit_FileName_Pattern)
	{
		set_Value (COLUMNNAME_LocalFile_CreditLimit_FileName_Pattern, LocalFile_CreditLimit_FileName_Pattern);
	}

	@Override
	public java.lang.String getLocalFile_CreditLimit_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_CreditLimit_FileName_Pattern);
	}

	@Override
	public void setLocalFile_CreditLimit_TargetDirectory (final @Nullable java.lang.String LocalFile_CreditLimit_TargetDirectory)
	{
		set_Value (COLUMNNAME_LocalFile_CreditLimit_TargetDirectory, LocalFile_CreditLimit_TargetDirectory);
	}

	@Override
	public java.lang.String getLocalFile_CreditLimit_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_CreditLimit_TargetDirectory);
	}

	@Override
	public void setLocalFile_Product_FileName_Pattern (final @Nullable java.lang.String LocalFile_Product_FileName_Pattern)
	{
		set_Value (COLUMNNAME_LocalFile_Product_FileName_Pattern, LocalFile_Product_FileName_Pattern);
	}

	@Override
	public java.lang.String getLocalFile_Product_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_Product_FileName_Pattern);
	}

	@Override
	public void setLocalFile_Product_TargetDirectory (final @Nullable java.lang.String LocalFile_Product_TargetDirectory)
	{
		set_Value (COLUMNNAME_LocalFile_Product_TargetDirectory, LocalFile_Product_TargetDirectory);
	}

	@Override
	public java.lang.String getLocalFile_Product_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_LocalFile_Product_TargetDirectory);
	}

	@Override
	public void setPollingFrequencyInMs (final int PollingFrequencyInMs)
	{
		set_Value (COLUMNNAME_PollingFrequencyInMs, PollingFrequencyInMs);
	}

	@Override
	public int getPollingFrequencyInMs() 
	{
		return get_ValueAsInt(COLUMNNAME_PollingFrequencyInMs);
	}

	@Override
	public void setProcessedDirectory (final java.lang.String ProcessedDirectory)
	{
		set_Value (COLUMNNAME_ProcessedDirectory, ProcessedDirectory);
	}

	@Override
	public java.lang.String getProcessedDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessedDirectory);
	}
}