/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Table_AttachmentListener
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Table_AttachmentListener extends org.compiere.model.PO implements I_AD_Table_AttachmentListener, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -710797595L;

    /** Standard Constructor */
    public X_AD_Table_AttachmentListener (Properties ctx, int AD_Table_AttachmentListener_ID, String trxName)
    {
      super (ctx, AD_Table_AttachmentListener_ID, trxName);
      /** if (AD_Table_AttachmentListener_ID == 0)
        {
			setAD_JavaClass_ID (0);
			setAD_Table_AttachmentListener_ID (0);
			setAD_Table_ID (0);
			setIsSendNotification (false); // N
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Table_AttachmentListener WHERE AD_Table_ID=@AD_Table_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_Table_AttachmentListener (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AD_JavaClass.
		@param AD_JavaClass_ID AD_JavaClass	  */
	@Override
	public void setAD_JavaClass_ID (int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, Integer.valueOf(AD_JavaClass_ID));
	}

	/** Get AD_JavaClass.
		@return AD_JavaClass	  */
	@Override
	public int getAD_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Message getAD_Message()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class);
	}

	@Override
	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message)
	{
		set_ValueFromPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class, AD_Message);
	}

	/** Set Meldung.
		@param AD_Message_ID 
		System Message
	  */
	@Override
	public void setAD_Message_ID (int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, Integer.valueOf(AD_Message_ID));
	}

	/** Get Meldung.
		@return System Message
	  */
	@Override
	public int getAD_Message_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Message_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Table_AttachmentListener.
		@param AD_Table_AttachmentListener_ID AD_Table_AttachmentListener	  */
	@Override
	public void setAD_Table_AttachmentListener_ID (int AD_Table_AttachmentListener_ID)
	{
		if (AD_Table_AttachmentListener_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_AttachmentListener_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_AttachmentListener_ID, Integer.valueOf(AD_Table_AttachmentListener_ID));
	}

	/** Get AD_Table_AttachmentListener.
		@return AD_Table_AttachmentListener	  */
	@Override
	public int getAD_Table_AttachmentListener_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_AttachmentListener_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Send Notification.
		@param IsSendNotification Send Notification	  */
	@Override
	public void setIsSendNotification (boolean IsSendNotification)
	{
		set_Value (COLUMNNAME_IsSendNotification, Boolean.valueOf(IsSendNotification));
	}

	/** Get Send Notification.
		@return Send Notification	  */
	@Override
	public boolean isSendNotification () 
	{
		Object oo = get_Value(COLUMNNAME_IsSendNotification);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}