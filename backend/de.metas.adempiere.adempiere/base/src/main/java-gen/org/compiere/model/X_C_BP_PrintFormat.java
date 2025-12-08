// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_PrintFormat
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_PrintFormat extends org.compiere.model.PO implements I_C_BP_PrintFormat, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -670894029L;

    /** Standard Constructor */
    public X_C_BP_PrintFormat (final Properties ctx, final int C_BP_PrintFormat_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_PrintFormat_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_PrintFormat (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	@Nullable
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public org.compiere.model.I_AD_Zebra_Config getAD_Zebra_Config()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Zebra_Config_ID, org.compiere.model.I_AD_Zebra_Config.class);
	}

	@Override
	public void setAD_Zebra_Config(final org.compiere.model.I_AD_Zebra_Config AD_Zebra_Config)
	{
		set_ValueFromPO(COLUMNNAME_AD_Zebra_Config_ID, org.compiere.model.I_AD_Zebra_Config.class, AD_Zebra_Config);
	}

	@Override
	public void setAD_Zebra_Config_ID (final int AD_Zebra_Config_ID)
	{
		if (AD_Zebra_Config_ID < 1) 
			set_Value (COLUMNNAME_AD_Zebra_Config_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Zebra_Config_ID, AD_Zebra_Config_ID);
	}

	@Override
	public int getAD_Zebra_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Zebra_Config_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BP_PrintFormat_ID (final int C_BP_PrintFormat_ID)
	{
		if (C_BP_PrintFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_PrintFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_PrintFormat_ID, C_BP_PrintFormat_ID);
	}

	@Override
	public int getC_BP_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_PrintFormat_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setDocumentCopies_Override (final int DocumentCopies_Override)
	{
		set_Value (COLUMNNAME_DocumentCopies_Override, DocumentCopies_Override);
	}

	@Override
	public int getDocumentCopies_Override() 
	{
		return get_ValueAsInt(COLUMNNAME_DocumentCopies_Override);
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