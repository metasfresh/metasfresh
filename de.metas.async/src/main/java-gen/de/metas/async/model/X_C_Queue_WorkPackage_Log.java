/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_WorkPackage_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_WorkPackage_Log extends org.compiere.model.PO implements I_C_Queue_WorkPackage_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2025646833L;

    /** Standard Constructor */
    public X_C_Queue_WorkPackage_Log (Properties ctx, int C_Queue_WorkPackage_Log_ID, String trxName)
    {
      super (ctx, C_Queue_WorkPackage_Log_ID, trxName);
      /** if (C_Queue_WorkPackage_Log_ID == 0)
        {
			setC_Queue_WorkPackage_ID (0);
			setC_Queue_WorkPackage_Log_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_WorkPackage_Log (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, C_Queue_WorkPackage);
	}

	/** Set WorkPackage Queue.
		@param C_Queue_WorkPackage_ID WorkPackage Queue	  */
	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	/** Get WorkPackage Queue.
		@return WorkPackage Queue	  */
	@Override
	public int getC_Queue_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Workpackage audit/log table.
		@param C_Queue_WorkPackage_Log_ID Workpackage audit/log table	  */
	@Override
	public void setC_Queue_WorkPackage_Log_ID (int C_Queue_WorkPackage_Log_ID)
	{
		if (C_Queue_WorkPackage_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Log_ID, Integer.valueOf(C_Queue_WorkPackage_Log_ID));
	}

	/** Get Workpackage audit/log table.
		@return Workpackage audit/log table	  */
	@Override
	public int getC_Queue_WorkPackage_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Message Text.
		@param MsgText 
		Textual Informational, Menu or Error Message
	  */
	@Override
	public void setMsgText (java.lang.String MsgText)
	{
		set_ValueNoCheck (COLUMNNAME_MsgText, MsgText);
	}

	/** Get Message Text.
		@return Textual Informational, Menu or Error Message
	  */
	@Override
	public java.lang.String getMsgText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MsgText);
	}
}