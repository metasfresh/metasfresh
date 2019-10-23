/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_ListValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_ListValue extends org.compiere.model.PO implements I_DataEntry_ListValue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1274809317L;

    /** Standard Constructor */
    public X_DataEntry_ListValue (Properties ctx, int DataEntry_ListValue_ID, String trxName)
    {
      super (ctx, DataEntry_ListValue_ID, trxName);
      /** if (DataEntry_ListValue_ID == 0)
        {
			setDataEntry_Field_ID (0);
			setDataEntry_ListValue_ID (0);
			setName (null);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_ListValue WHERE DataEntry_Field_ID=@DataEntry_Field_ID/0@
        } */
    }

    /** Load Constructor */
    public X_DataEntry_ListValue (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.dataentry.model.I_DataEntry_Field getDataEntry_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class);
	}

	@Override
	public void setDataEntry_Field(de.metas.dataentry.model.I_DataEntry_Field DataEntry_Field)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class, DataEntry_Field);
	}

	/** Set Dateneingabefeld.
		@param DataEntry_Field_ID Dateneingabefeld	  */
	@Override
	public void setDataEntry_Field_ID (int DataEntry_Field_ID)
	{
		if (DataEntry_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, Integer.valueOf(DataEntry_Field_ID));
	}

	/** Get Dateneingabefeld.
		@return Dateneingabefeld	  */
	@Override
	public int getDataEntry_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eingabefeldwert.
		@param DataEntry_ListValue_ID Eingabefeldwert	  */
	@Override
	public void setDataEntry_ListValue_ID (int DataEntry_ListValue_ID)
	{
		if (DataEntry_ListValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_ListValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_ListValue_ID, Integer.valueOf(DataEntry_ListValue_ID));
	}

	/** Get Eingabefeldwert.
		@return Eingabefeldwert	  */
	@Override
	public int getDataEntry_ListValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_ListValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
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