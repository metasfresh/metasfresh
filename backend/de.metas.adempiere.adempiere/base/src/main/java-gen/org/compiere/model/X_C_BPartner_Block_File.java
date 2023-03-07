// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Block_File
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Block_File extends org.compiere.model.PO implements I_C_BPartner_Block_File, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1142145841L;

    /** Standard Constructor */
    public X_C_BPartner_Block_File (final Properties ctx, final int C_BPartner_Block_File_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Block_File_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Block_File (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Block_File_ID (final int C_BPartner_Block_File_ID)
	{
		if (C_BPartner_Block_File_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Block_File_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Block_File_ID, C_BPartner_Block_File_ID);
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
	public void setFileName (final java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		throw new IllegalArgumentException ("IsError is virtual column");	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		throw new IllegalArgumentException ("Processed is virtual column");	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}