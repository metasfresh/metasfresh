/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ColumnCallout
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_ColumnCallout extends org.compiere.model.PO implements I_AD_ColumnCallout, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -764840173L;

    /** Standard Constructor */
    public X_AD_ColumnCallout (Properties ctx, int AD_ColumnCallout_ID, String trxName)
    {
      super (ctx, AD_ColumnCallout_ID, trxName);
      /** if (AD_ColumnCallout_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_ColumnCallout_ID (0);
			setAD_Table_ID (0);
			setClassname (null);
			setEntityType (null);
// U
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM AD_ColumnCallout WHERE AD_Column_ID=@AD_Column_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_ColumnCallout (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column Callout.
		@param AD_ColumnCallout_ID Column Callout	  */
	@Override
	public void setAD_ColumnCallout_ID (int AD_ColumnCallout_ID)
	{
		if (AD_ColumnCallout_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ColumnCallout_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ColumnCallout_ID, Integer.valueOf(AD_ColumnCallout_ID));
	}

	/** Get Column Callout.
		@return Column Callout	  */
	@Override
	public int getAD_ColumnCallout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ColumnCallout_ID);
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

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name der Spalte in der Datenbank
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		throw new IllegalArgumentException ("ColumnName is virtual column");	}

	/** Get Spaltenname.
		@return Name der Spalte in der Datenbank
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
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