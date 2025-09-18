// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Document_Acct_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Document_Acct_Log extends org.compiere.model.PO implements I_Document_Acct_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1817903262L;

    /** Standard Constructor */
    public X_Document_Acct_Log (final Properties ctx, final int Document_Acct_Log_ID, @Nullable final String trxName)
    {
      super (ctx, Document_Acct_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_Document_Acct_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setDocument_Acct_Log_ID (final int Document_Acct_Log_ID)
	{
		if (Document_Acct_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Document_Acct_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Document_Acct_Log_ID, Document_Acct_Log_ID);
	}

	@Override
	public int getDocument_Acct_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Document_Acct_Log_ID);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	/** 
	 * Type AD_Reference_ID=541967
	 * Reference name: Document_Acct_Log_Type
	 */
	public static final int TYPE_AD_Reference_ID=541967;
	/** Enqueued = enqueued */
	public static final String TYPE_Enqueued = "enqueued";
	/** Posting Done = posting_ok */
	public static final String TYPE_PostingDone = "posting_ok";
	/** Posting Error = posting_error */
	public static final String TYPE_PostingError = "posting_error";
	@Override
	public void setType (final @Nullable java.lang.String Type)
	{
		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}