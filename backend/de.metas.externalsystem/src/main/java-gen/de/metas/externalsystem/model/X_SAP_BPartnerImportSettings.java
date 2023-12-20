// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SAP_BPartnerImportSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SAP_BPartnerImportSettings extends org.compiere.model.PO implements I_SAP_BPartnerImportSettings, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1751448261L;

    /** Standard Constructor */
    public X_SAP_BPartnerImportSettings (final Properties ctx, final int SAP_BPartnerImportSettings_ID, @Nullable final String trxName)
    {
      super (ctx, SAP_BPartnerImportSettings_ID, trxName);
    }

    /** Load Constructor */
    public X_SAP_BPartnerImportSettings (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_BP_Group getC_BP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(final org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
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
	public void setIsSingleBPartner (final boolean IsSingleBPartner)
	{
		set_Value (COLUMNNAME_IsSingleBPartner, IsSingleBPartner);
	}

	@Override
	public boolean isSingleBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSingleBPartner);
	}

	@Override
	public void setPartnerCodePattern (final java.lang.String PartnerCodePattern)
	{
		set_Value (COLUMNNAME_PartnerCodePattern, PartnerCodePattern);
	}

	@Override
	public java.lang.String getPartnerCodePattern() 
	{
		return get_ValueAsString(COLUMNNAME_PartnerCodePattern);
	}

	@Override
	public void setSAP_BPartnerImportSettings_ID (final int SAP_BPartnerImportSettings_ID)
	{
		if (SAP_BPartnerImportSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_BPartnerImportSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_BPartnerImportSettings_ID, SAP_BPartnerImportSettings_ID);
	}

	@Override
	public int getSAP_BPartnerImportSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_BPartnerImportSettings_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}