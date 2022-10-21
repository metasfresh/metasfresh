// Generated Model - DO NOT CHANGE
package org.adempiere.banking.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BankStatement_Import_File
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BankStatement_Import_File extends org.compiere.model.PO implements I_C_BankStatement_Import_File, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -988637955L;

    /** Standard Constructor */
    public X_C_BankStatement_Import_File (final Properties ctx, final int C_BankStatement_Import_File_ID, @Nullable final String trxName)
    {
      super (ctx, C_BankStatement_Import_File_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BankStatement_Import_File (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BankStatement_Import_File_ID (final int C_BankStatement_Import_File_ID)
	{
		if (C_BankStatement_Import_File_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_Import_File_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_Import_File_ID, C_BankStatement_Import_File_ID);
	}

	@Override
	public int getC_BankStatement_Import_File_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_Import_File_ID);
	}

	@Override
	public void setFileName (final String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setImported (final @Nullable java.sql.Timestamp Imported)
	{
		set_Value (COLUMNNAME_Imported, Imported);
	}

	@Override
	public java.sql.Timestamp getImported() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Imported);
	}

	@Override
	public void setIsMatchAmounts (final boolean IsMatchAmounts)
	{
		set_Value (COLUMNNAME_IsMatchAmounts, IsMatchAmounts);
	}

	@Override
	public boolean isMatchAmounts() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMatchAmounts);
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