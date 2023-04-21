// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Label_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Label_Config extends org.compiere.model.PO implements I_M_HU_Label_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1626239778L;

    /** Standard Constructor */
    public X_M_HU_Label_Config (final Properties ctx, final int M_HU_Label_Config_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Label_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Label_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAutoPrintCopies (final int AutoPrintCopies)
	{
		set_Value (COLUMNNAME_AutoPrintCopies, AutoPrintCopies);
	}

	@Override
	public int getAutoPrintCopies() 
	{
		return get_ValueAsInt(COLUMNNAME_AutoPrintCopies);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * HU_SourceDocType AD_Reference_ID=541696
	 * Reference name: HU_SourceDocType (M_HU_Label_Config)
	 */
	public static final int HU_SOURCEDOCTYPE_AD_Reference_ID=541696;
	/** Manufacturing = MO */
	public static final String HU_SOURCEDOCTYPE_Manufacturing = "MO";
	/** MaterialReceipt = MR */
	public static final String HU_SOURCEDOCTYPE_MaterialReceipt = "MR";
	/** Picking = PI */
	public static final String HU_SOURCEDOCTYPE_Picking = "PI";
	@Override
	public void setHU_SourceDocType (final @Nullable java.lang.String HU_SourceDocType)
	{
		set_Value (COLUMNNAME_HU_SourceDocType, HU_SourceDocType);
	}

	@Override
	public java.lang.String getHU_SourceDocType() 
	{
		return get_ValueAsString(COLUMNNAME_HU_SourceDocType);
	}

	@Override
	public void setIsApplyToCUs (final boolean IsApplyToCUs)
	{
		set_Value (COLUMNNAME_IsApplyToCUs, IsApplyToCUs);
	}

	@Override
	public boolean isApplyToCUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToCUs);
	}

	@Override
	public void setIsApplyToLUs (final boolean IsApplyToLUs)
	{
		set_Value (COLUMNNAME_IsApplyToLUs, IsApplyToLUs);
	}

	@Override
	public boolean isApplyToLUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToLUs);
	}

	@Override
	public void setIsApplyToTUs (final boolean IsApplyToTUs)
	{
		set_Value (COLUMNNAME_IsApplyToTUs, IsApplyToTUs);
	}

	@Override
	public boolean isApplyToTUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToTUs);
	}

	@Override
	public void setIsAutoPrint (final boolean IsAutoPrint)
	{
		set_Value (COLUMNNAME_IsAutoPrint, IsAutoPrint);
	}

	@Override
	public boolean isAutoPrint() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoPrint);
	}

	@Override
	public org.compiere.model.I_AD_Process getLabelReport_Process()
	{
		return get_ValueAsPO(COLUMNNAME_LabelReport_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setLabelReport_Process(final org.compiere.model.I_AD_Process LabelReport_Process)
	{
		set_ValueFromPO(COLUMNNAME_LabelReport_Process_ID, org.compiere.model.I_AD_Process.class, LabelReport_Process);
	}

	@Override
	public void setLabelReport_Process_ID (final int LabelReport_Process_ID)
	{
		if (LabelReport_Process_ID < 1) 
			set_Value (COLUMNNAME_LabelReport_Process_ID, null);
		else 
			set_Value (COLUMNNAME_LabelReport_Process_ID, LabelReport_Process_ID);
	}

	@Override
	public int getLabelReport_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LabelReport_Process_ID);
	}

	@Override
	public void setM_HU_Label_Config_ID (final int M_HU_Label_Config_ID)
	{
		if (M_HU_Label_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Label_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Label_Config_ID, M_HU_Label_Config_ID);
	}

	@Override
	public int getM_HU_Label_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Label_Config_ID);
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