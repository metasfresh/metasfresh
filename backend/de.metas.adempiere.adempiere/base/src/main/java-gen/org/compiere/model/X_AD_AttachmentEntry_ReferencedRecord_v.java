/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_AttachmentEntry_ReferencedRecord_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_AttachmentEntry_ReferencedRecord_v extends org.compiere.model.PO implements I_AD_AttachmentEntry_ReferencedRecord_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -47020446L;

    /** Standard Constructor */
    public X_AD_AttachmentEntry_ReferencedRecord_v (Properties ctx, int AD_AttachmentEntry_ReferencedRecord_v_ID, String trxName)
    {
      super (ctx, AD_AttachmentEntry_ReferencedRecord_v_ID, trxName);
      /** if (AD_AttachmentEntry_ReferencedRecord_v_ID == 0)
        {
			setAD_AttachmentEntry_ReferencedRecord_v_ID (0);
			setFileName (null);
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_AttachmentEntry_ReferencedRecord_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AD_AttachmentEntry_ReferencedRecord_v.
		@param AD_AttachmentEntry_ReferencedRecord_v_ID AD_AttachmentEntry_ReferencedRecord_v	  */
	@Override
	public void setAD_AttachmentEntry_ReferencedRecord_v_ID (int AD_AttachmentEntry_ReferencedRecord_v_ID)
	{
		if (AD_AttachmentEntry_ReferencedRecord_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ReferencedRecord_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_AttachmentEntry_ReferencedRecord_v_ID, Integer.valueOf(AD_AttachmentEntry_ReferencedRecord_v_ID));
	}

	/** Get AD_AttachmentEntry_ReferencedRecord_v.
		@return AD_AttachmentEntry_ReferencedRecord_v	  */
	@Override
	public int getAD_AttachmentEntry_ReferencedRecord_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AttachmentEntry_ReferencedRecord_v_ID);
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

	/** Set Binärwert.
		@param BinaryData 
		Binärer Wert
	  */
	@Override
	public void setBinaryData (byte[] BinaryData)
	{
		set_ValueNoCheck (COLUMNNAME_BinaryData, BinaryData);
	}

	/** Get Binärwert.
		@return Binärer Wert
	  */
	@Override
	public byte[] getBinaryData () 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
	}

	/** Set Content type.
		@param ContentType Content type	  */
	@Override
	public void setContentType (java.lang.String ContentType)
	{
		set_Value (COLUMNNAME_ContentType, ContentType);
	}

	/** Get Content type.
		@return Content type	  */
	@Override
	public java.lang.String getContentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContentType);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	@Override
	public void setFileName (java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	@Override
	public java.lang.String getFileName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FileName);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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

	/** 
	 * Type AD_Reference_ID=540751
	 * Reference name: AD_AttachmentEntry_Type
	 */
	public static final int TYPE_AD_Reference_ID=540751;
	/** Data = D */
	public static final String TYPE_Data = "D";
	/** URL = U */
	public static final String TYPE_URL = "U";
	/** Set Art.
		@param Type Art	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Art	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public void setURL (java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public java.lang.String getURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_URL);
	}
}