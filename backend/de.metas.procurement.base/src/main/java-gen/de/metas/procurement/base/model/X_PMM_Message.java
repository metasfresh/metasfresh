/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PMM_Message
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_Message extends org.compiere.model.PO implements I_PMM_Message, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1982151971L;

    /** Standard Constructor */
    public X_PMM_Message (Properties ctx, int PMM_Message_ID, String trxName)
    {
      super (ctx, PMM_Message_ID, trxName);
      /** if (PMM_Message_ID == 0)
        {
			setMsgText (null);
			setPMM_Message_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PMM_Message (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Message Text.
		@param MsgText 
		Textual Informational, Menu or Error Message
	  */
	@Override
	public void setMsgText (java.lang.String MsgText)
	{
		set_Value (COLUMNNAME_MsgText, MsgText);
	}

	/** Get Message Text.
		@return Textual Informational, Menu or Error Message
	  */
	@Override
	public java.lang.String getMsgText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MsgText);
	}

	/** Set PMM_Message.
		@param PMM_Message_ID PMM_Message	  */
	@Override
	public void setPMM_Message_ID (int PMM_Message_ID)
	{
		if (PMM_Message_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_Message_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_Message_ID, Integer.valueOf(PMM_Message_ID));
	}

	/** Get PMM_Message.
		@return PMM_Message	  */
	@Override
	public int getPMM_Message_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Message_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}