// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BPartner_BlockStatus
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_BPartner_BlockStatus extends org.compiere.model.PO implements I_I_BPartner_BlockStatus, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 508680666L;

    /** Standard Constructor */
    public X_I_BPartner_BlockStatus (final Properties ctx, final int I_BPartner_BlockStatus_ID, @Nullable final String trxName)
    {
      super (ctx, I_BPartner_BlockStatus_ID, trxName);
    }

    /** Load Constructor */
    public X_I_BPartner_BlockStatus (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAction (final java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	/** 
	 * BlockStatus AD_Reference_ID=541720
	 * Reference name: BlockStatus
	 */
	public static final int BLOCKSTATUS_AD_Reference_ID=541720;
	/** Blocked = B */
	public static final String BLOCKSTATUS_Blocked = "B";
	/** Unblocked = UB */
	public static final String BLOCKSTATUS_Unblocked = "UB";
	@Override
	public void setBlockStatus (final @Nullable java.lang.String BlockStatus)
	{
		set_Value (COLUMNNAME_BlockStatus, BlockStatus);
	}

	@Override
	public java.lang.String getBlockStatus() 
	{
		return get_ValueAsString(COLUMNNAME_BlockStatus);
	}

	@Override
	public org.compiere.model.I_C_BPartner_Block_File getC_BPartner_Block_File()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Block_File_ID, org.compiere.model.I_C_BPartner_Block_File.class);
	}

	@Override
	public void setC_BPartner_Block_File(final org.compiere.model.I_C_BPartner_Block_File C_BPartner_Block_File)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Block_File_ID, org.compiere.model.I_C_BPartner_Block_File.class, C_BPartner_Block_File);
	}

	@Override
	public void setC_BPartner_Block_File_ID (final int C_BPartner_Block_File_ID)
	{
		if (C_BPartner_Block_File_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Block_File_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Block_File_ID, C_BPartner_Block_File_ID);
	}

	@Override
	public int getC_BPartner_Block_File_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Block_File_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public void setI_BPartner_BlockStatus_ID (final int I_BPartner_BlockStatus_ID)
	{
		if (I_BPartner_BlockStatus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_BlockStatus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_BlockStatus_ID, I_BPartner_BlockStatus_ID);
	}

	@Override
	public int getI_BPartner_BlockStatus_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_BPartner_BlockStatus_ID);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	@Override
	public void setI_IsImported (final java.lang.String I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public java.lang.String getI_IsImported() 
	{
		return get_ValueAsString(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setReason (final @Nullable java.lang.String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	@Override
	public java.lang.String getReason() 
	{
		return get_ValueAsString(COLUMNNAME_Reason);
	}

	@Override
	public void setSAP_BPartnerCode (final java.lang.String SAP_BPartnerCode)
	{
		set_Value (COLUMNNAME_SAP_BPartnerCode, SAP_BPartnerCode);
	}

	@Override
	public java.lang.String getSAP_BPartnerCode() 
	{
		return get_ValueAsString(COLUMNNAME_SAP_BPartnerCode);
	}
}