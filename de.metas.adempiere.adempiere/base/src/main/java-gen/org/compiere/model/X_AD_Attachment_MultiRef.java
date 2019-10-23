/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Attachment_MultiRef
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Attachment_MultiRef extends org.compiere.model.PO implements I_AD_Attachment_MultiRef, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1922426857L;

    /** Standard Constructor */
    public X_AD_Attachment_MultiRef (Properties ctx, int AD_Attachment_MultiRef_ID, String trxName)
    {
      super (ctx, AD_Attachment_MultiRef_ID, trxName);
      /** if (AD_Attachment_MultiRef_ID == 0)
        {
			setAD_AttachmentEntry_ID (0);
			setAD_Attachment_MultiRef_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Attachment_MultiRef (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class);
	}

	@Override
	public void setAD_AttachmentEntry(org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry)
	{
		set_ValueFromPO(COLUMNNAME_AD_AttachmentEntry_ID, org.compiere.model.I_AD_AttachmentEntry.class, AD_AttachmentEntry);
	}

	/** Set Attachment entry.
		@param AD_AttachmentEntry_ID Attachment entry	  */
	@Override
	public void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID)
	{
		if (AD_AttachmentEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ID, Integer.valueOf(AD_AttachmentEntry_ID));
	}

	/** Get Attachment entry.
		@return Attachment entry	  */
	@Override
	public int getAD_AttachmentEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AttachmentEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Attachment_MultiRef.
		@param AD_Attachment_MultiRef_ID AD_Attachment_MultiRef	  */
	@Override
	public void setAD_Attachment_MultiRef_ID (int AD_Attachment_MultiRef_ID)
	{
		if (AD_Attachment_MultiRef_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_MultiRef_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_MultiRef_ID, Integer.valueOf(AD_Attachment_MultiRef_ID));
	}

	/** Get AD_Attachment_MultiRef.
		@return AD_Attachment_MultiRef	  */
	@Override
	public int getAD_Attachment_MultiRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Attachment_MultiRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}