// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_SAP_SFTP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP_SFTP extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP_SFTP, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1247519103L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP_SFTP (final Properties ctx, final int ExternalSystem_Config_SAP_SFTP_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_SFTP_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP_SFTP (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalSystem_Config_SAP_SFTP_ID (final int ExternalSystem_Config_SAP_SFTP_ID)
	{
		if (ExternalSystem_Config_SAP_SFTP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_SFTP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_SFTP_ID, ExternalSystem_Config_SAP_SFTP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_SFTP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_SFTP_ID);
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

	@Override
	public void setSFTP_BPartner_FileName_Pattern (final @Nullable java.lang.String SFTP_BPartner_FileName_Pattern)
	{
		set_Value (COLUMNNAME_SFTP_BPartner_FileName_Pattern, SFTP_BPartner_FileName_Pattern);
	}

	@Override
	public java.lang.String getSFTP_BPartner_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_BPartner_FileName_Pattern);
	}

	@Override
	public void setSFTP_BPartner_TargetDirectory (final @Nullable java.lang.String SFTP_BPartner_TargetDirectory)
	{
		set_Value (COLUMNNAME_SFTP_BPartner_TargetDirectory, SFTP_BPartner_TargetDirectory);
	}

	@Override
	public java.lang.String getSFTP_BPartner_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_BPartner_TargetDirectory);
	}

	@Override
	public void setSFTP_ConversionRate_FileName_Pattern (final @Nullable java.lang.String SFTP_ConversionRate_FileName_Pattern)
	{
		set_Value (COLUMNNAME_SFTP_ConversionRate_FileName_Pattern, SFTP_ConversionRate_FileName_Pattern);
	}

	@Override
	public java.lang.String getSFTP_ConversionRate_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_ConversionRate_FileName_Pattern);
	}

	@Override
	public void setSFTP_ConversionRate_TargetDirectory (final @Nullable java.lang.String SFTP_ConversionRate_TargetDirectory)
	{
		set_Value (COLUMNNAME_SFTP_ConversionRate_TargetDirectory, SFTP_ConversionRate_TargetDirectory);
	}

	@Override
	public java.lang.String getSFTP_ConversionRate_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_ConversionRate_TargetDirectory);
	}

	@Override
	public void setSFTP_CreditLimit_FileName_Pattern (final @Nullable java.lang.String SFTP_CreditLimit_FileName_Pattern)
	{
		set_Value (COLUMNNAME_SFTP_CreditLimit_FileName_Pattern, SFTP_CreditLimit_FileName_Pattern);
	}

	@Override
	public java.lang.String getSFTP_CreditLimit_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_CreditLimit_FileName_Pattern);
	}

	@Override
	public void setSFTP_CreditLimit_TargetDirectory (final @Nullable java.lang.String SFTP_CreditLimit_TargetDirectory)
	{
		set_Value (COLUMNNAME_SFTP_CreditLimit_TargetDirectory, SFTP_CreditLimit_TargetDirectory);
	}

	@Override
	public java.lang.String getSFTP_CreditLimit_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_CreditLimit_TargetDirectory);
	}

	@Override
	public void setSFTP_HostName (final java.lang.String SFTP_HostName)
	{
		set_Value (COLUMNNAME_SFTP_HostName, SFTP_HostName);
	}

	@Override
	public java.lang.String getSFTP_HostName() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_HostName);
	}

	@Override
	public void setSFTP_Password (final java.lang.String SFTP_Password)
	{
		set_Value (COLUMNNAME_SFTP_Password, SFTP_Password);
	}

	@Override
	public java.lang.String getSFTP_Password() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Password);
	}

	@Override
	public void setSFTP_Port (final java.lang.String SFTP_Port)
	{
		set_Value (COLUMNNAME_SFTP_Port, SFTP_Port);
	}

	@Override
	public java.lang.String getSFTP_Port() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Port);
	}

	@Override
	public void setSFTP_Product_FileName_Pattern (final @Nullable java.lang.String SFTP_Product_FileName_Pattern)
	{
		set_Value (COLUMNNAME_SFTP_Product_FileName_Pattern, SFTP_Product_FileName_Pattern);
	}

	@Override
	public java.lang.String getSFTP_Product_FileName_Pattern() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Product_FileName_Pattern);
	}

	@Override
	public void setSFTP_Product_TargetDirectory (final @Nullable java.lang.String SFTP_Product_TargetDirectory)
	{
		set_Value (COLUMNNAME_SFTP_Product_TargetDirectory, SFTP_Product_TargetDirectory);
	}

	@Override
	public java.lang.String getSFTP_Product_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Product_TargetDirectory);
	}

	@Override
	public void setSFTP_Username (final java.lang.String SFTP_Username)
	{
		set_Value (COLUMNNAME_SFTP_Username, SFTP_Username);
	}

	@Override
	public java.lang.String getSFTP_Username() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Username);
	}
}