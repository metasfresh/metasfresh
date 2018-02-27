/** Generated Model - DO NOT CHANGE */
package de.metas.event.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for AD_EventLog_Entry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_EventLog_Entry extends org.compiere.model.PO implements I_AD_EventLog_Entry, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1217799785L;

    /** Standard Constructor */
    public X_AD_EventLog_Entry (Properties ctx, int AD_EventLog_Entry_ID, String trxName)
    {
      super (ctx, AD_EventLog_Entry_ID, trxName);
      /** if (AD_EventLog_Entry_ID == 0)
        {
			setAD_EventLog_Entry_ID (0);
			setAD_EventLog_ID (0);
			setIsError (false);
        } */
    }

    /** Load Constructor */
    public X_AD_EventLog_Entry (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Even log entry.
		@param AD_EventLog_Entry_ID Even log entry	  */
	@Override
	public void setAD_EventLog_Entry_ID (int AD_EventLog_Entry_ID)
	{
		if (AD_EventLog_Entry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_Entry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_Entry_ID, Integer.valueOf(AD_EventLog_Entry_ID));
	}

	/** Get Even log entry.
		@return Even log entry	  */
	@Override
	public int getAD_EventLog_Entry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EventLog_Entry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_AD_EventLog getAD_EventLog() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_EventLog_ID, I_AD_EventLog.class);
	}

	@Override
	public void setAD_EventLog(I_AD_EventLog AD_EventLog)
	{
		set_ValueFromPO(COLUMNNAME_AD_EventLog_ID, I_AD_EventLog.class, AD_EventLog);
	}

	/** Set Event log.
		@param AD_EventLog_ID Event log	  */
	@Override
	public void setAD_EventLog_ID (int AD_EventLog_ID)
	{
		if (AD_EventLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, Integer.valueOf(AD_EventLog_ID));
	}

	/** Get Event log.
		@return Event log	  */
	@Override
	public int getAD_EventLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EventLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
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
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
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

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_ValueNoCheck (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}