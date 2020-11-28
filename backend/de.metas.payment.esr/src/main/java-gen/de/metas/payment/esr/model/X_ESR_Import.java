/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ESR_Import
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ESR_Import extends org.compiere.model.PO implements I_ESR_Import, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 819668944L;

    /** Standard Constructor */
    public X_ESR_Import (Properties ctx, int ESR_Import_ID, String trxName)
    {
      super (ctx, ESR_Import_ID, trxName);
    }

    /** Load Constructor */
    public X_ESR_Import (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID)
	{
		if (AD_AttachmentEntry_ID < 1) 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, null);
		else 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, Integer.valueOf(AD_AttachmentEntry_ID));
	}

	@Override
	public int getAD_AttachmentEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_AttachmentEntry_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
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
	public void setDataType (java.lang.String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	@Override
	public java.lang.String getDataType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataType);
	}

	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setESR_Control_Amount (java.math.BigDecimal ESR_Control_Amount)
	{
		set_Value (COLUMNNAME_ESR_Control_Amount, ESR_Control_Amount);
	}

	@Override
	public java.math.BigDecimal getESR_Control_Amount() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Control_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_Control_Trx_Qty (java.math.BigDecimal ESR_Control_Trx_Qty)
	{
		set_Value (COLUMNNAME_ESR_Control_Trx_Qty, ESR_Control_Trx_Qty);
	}

	@Override
	public java.math.BigDecimal getESR_Control_Trx_Qty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Control_Trx_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	@Override
	public int getESR_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_Import_ID);
	}

	@Override
	public void setESR_Trx_Qty (java.math.BigDecimal ESR_Trx_Qty)
	{
		throw new IllegalArgumentException ("ESR_Trx_Qty is virtual column");	}

	@Override
	public java.math.BigDecimal getESR_Trx_Qty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Trx_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHash (java.lang.String Hash)
	{
		set_Value (COLUMNNAME_Hash, Hash);
	}

	@Override
	public java.lang.String getHash() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Hash);
	}

	@Override
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	@Override
	public boolean isReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceipt);
	}

	@Override
	public void setIsReconciled (boolean IsReconciled)
	{
		set_Value (COLUMNNAME_IsReconciled, Boolean.valueOf(IsReconciled));
	}

	@Override
	public boolean isReconciled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReconciled);
	}

	@Override
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (boolean Processing)
	{
		throw new IllegalArgumentException ("Processing is virtual column");	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}
}