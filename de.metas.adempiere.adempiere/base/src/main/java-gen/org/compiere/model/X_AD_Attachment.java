/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Attachment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Attachment extends org.compiere.model.PO implements I_AD_Attachment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 80107471L;

    /** Standard Constructor */
    public X_AD_Attachment (Properties ctx, int AD_Attachment_ID, String trxName)
    {
      super (ctx, AD_Attachment_ID, trxName);
      /** if (AD_Attachment_ID == 0)
        {
			setAD_Attachment_ID (0);
			setAD_Table_ID (0);
			setRecord_ID (0);
			setTitle (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Attachment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Anlage.
		@param AD_Attachment_ID 
		Attachment for the document
	  */
	@Override
	public void setAD_Attachment_ID (int AD_Attachment_ID)
	{
		if (AD_Attachment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Attachment_ID, Integer.valueOf(AD_Attachment_ID));
	}

	/** Get Anlage.
		@return Attachment for the document
	  */
	@Override
	public int getAD_Attachment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Attachment_ID);
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
		Binary Data
	  */
	@Override
	public void setBinaryData (byte[] BinaryData)
	{
		set_ValueNoCheck (COLUMNNAME_BinaryData, BinaryData);
	}

	/** Get Binärwert.
		@return Binary Data
	  */
	@Override
	public byte[] getBinaryData () 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
	}

	/** Set Migriert am.
		@param MigrationDate Migriert am	  */
	@Override
	public void setMigrationDate (java.sql.Timestamp MigrationDate)
	{
		set_Value (COLUMNNAME_MigrationDate, MigrationDate);
	}

	/** Get Migriert am.
		@return Migriert am	  */
	@Override
	public java.sql.Timestamp getMigrationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MigrationDate);
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

	/** Set Mitteilung.
		@param TextMsg 
		Text Message
	  */
	@Override
	public void setTextMsg (java.lang.String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Mitteilung.
		@return Text Message
	  */
	@Override
	public java.lang.String getTextMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TextMsg);
	}

	/** Set Titel.
		@param Title 
		Name this entity is referred to as
	  */
	@Override
	public void setTitle (java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Titel.
		@return Name this entity is referred to as
	  */
	@Override
	public java.lang.String getTitle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}
}