// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for EDI_EPCIS_Transmitted_SSCC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_EPCIS_Transmitted_SSCC extends org.compiere.model.PO implements I_EDI_EPCIS_Transmitted_SSCC, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2014499035L;

    /** Standard Constructor */
    public X_EDI_EPCIS_Transmitted_SSCC (final Properties ctx, final int EDI_EPCIS_Transmitted_SSCC_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_EPCIS_Transmitted_SSCC_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_EPCIS_Transmitted_SSCC (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setEDI_EPCIS_Transmitted_SSCC_ID (final int EDI_EPCIS_Transmitted_SSCC_ID)
	{
		if (EDI_EPCIS_Transmitted_SSCC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_EPCIS_Transmitted_SSCC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_EPCIS_Transmitted_SSCC_ID, EDI_EPCIS_Transmitted_SSCC_ID);
	}

	@Override
	public int getEDI_EPCIS_Transmitted_SSCC_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_EPCIS_Transmitted_SSCC_ID);
	}

	@Override
	public void setExternalSystem_Config_ScriptedExportConversion_ID (final int ExternalSystem_Config_ScriptedExportConversion_ID)
	{
		if (ExternalSystem_Config_ScriptedExportConversion_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public int getExternalSystem_Config_ScriptedExportConversion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setSSCC18 (final java.lang.String SSCC18)
	{
		set_Value (COLUMNNAME_SSCC18, SSCC18);
	}

	@Override
	public java.lang.String getSSCC18() 
	{
		return get_ValueAsString(COLUMNNAME_SSCC18);
	}

	@Override
	public void setTransmitted (final java.sql.Timestamp Transmitted)
	{
		set_Value (COLUMNNAME_Transmitted, Transmitted);
	}

	@Override
	public java.sql.Timestamp getTransmitted() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Transmitted);
	}
}