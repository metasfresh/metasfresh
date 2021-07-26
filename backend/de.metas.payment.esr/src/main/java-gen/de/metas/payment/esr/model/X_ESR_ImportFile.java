// Generated Model - DO NOT CHANGE
package de.metas.payment.esr.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ESR_ImportFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ESR_ImportFile extends org.compiere.model.PO implements I_ESR_ImportFile, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1840449226L;

    /** Standard Constructor */
    public X_ESR_ImportFile (final Properties ctx, final int ESR_ImportFile_ID, @Nullable final String trxName)
    {
      super (ctx, ESR_ImportFile_ID, trxName);
    }

    /** Load Constructor */
    public X_ESR_ImportFile (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry()
	{
		return get_ValueAsPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class);
	}

	@Override
	public void setAD_AttachmentEntry(final org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry)
	{
		set_ValueFromPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class, AD_AttachmentEntry);
	}

	@Override
	public void setAD_AttachmentEntry_ID (final int AD_AttachmentEntry_ID)
	{
		if (AD_AttachmentEntry_ID < 1) 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, null);
		else 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, AD_AttachmentEntry_ID);
	}

	@Override
	public int getAD_AttachmentEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_AttachmentEntry_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	/** 
	 * DataType AD_Reference_ID=540728
	 * Reference name: ESR_Import_DataType
	 */
	public static final int DATATYPE_AD_Reference_ID=540728;
	/** V11 = V11 */
	public static final String DATATYPE_V11 = "V11";
	/** camt.54 = camt.54 */
	public static final String DATATYPE_Camt54 = "camt.54";
	@Override
	public void setDataType (final @Nullable java.lang.String DataType)
	{
		set_Value (COLUMNNAME_DataType, DataType);
	}

	@Override
	public java.lang.String getDataType() 
	{
		return get_ValueAsString(COLUMNNAME_DataType);
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

	@Override
	public void setESR_Control_Amount (final BigDecimal ESR_Control_Amount)
	{
		set_Value (COLUMNNAME_ESR_Control_Amount, ESR_Control_Amount);
	}

	@Override
	public BigDecimal getESR_Control_Amount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Control_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_Control_Trx_Qty (final @Nullable BigDecimal ESR_Control_Trx_Qty)
	{
		set_Value (COLUMNNAME_ESR_Control_Trx_Qty, ESR_Control_Trx_Qty);
	}

	@Override
	public BigDecimal getESR_Control_Trx_Qty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Control_Trx_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_ImportFile_ID (final int ESR_ImportFile_ID)
	{
		if (ESR_ImportFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportFile_ID, ESR_ImportFile_ID);
	}

	@Override
	public int getESR_ImportFile_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_ImportFile_ID);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import()
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(final de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	@Override
	public void setESR_Import_ID (final int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_Value (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_Value (COLUMNNAME_ESR_Import_ID, ESR_Import_ID);
	}

	@Override
	public int getESR_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_Import_ID);
	}

	@Override
	public void setFileName (final @Nullable java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setHash (final @Nullable java.lang.String Hash)
	{
		set_Value (COLUMNNAME_Hash, Hash);
	}

	@Override
	public java.lang.String getHash() 
	{
		return get_ValueAsString(COLUMNNAME_Hash);
	}

	@Override
	public void setIsReceipt (final boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, IsReceipt);
	}

	@Override
	public boolean isReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceipt);
	}

	@Override
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
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
}