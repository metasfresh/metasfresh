/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Record
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Record extends org.compiere.model.PO implements I_DataEntry_Record, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 774645598L;

    /** Standard Constructor */
    public X_DataEntry_Record (Properties ctx, int DataEntry_Record_ID, String trxName)
    {
      super (ctx, DataEntry_Record_ID, trxName);
      /** if (DataEntry_Record_ID == 0)
        {
			setAD_Table_ID (0);
			setDataEntry_Record_ID (0);
			setDataEntry_SubTab_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Record (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DataEntry_Record.
		@param DataEntry_Record_ID DataEntry_Record	  */
	@Override
	public void setDataEntry_Record_ID (int DataEntry_Record_ID)
	{
		if (DataEntry_Record_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_ID, Integer.valueOf(DataEntry_Record_ID));
	}

	/** Get DataEntry_Record.
		@return DataEntry_Record	  */
	@Override
	public int getDataEntry_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DataEntry_RecordData.
		@param DataEntry_RecordData 
		Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display
	  */
	@Override
	public void setDataEntry_RecordData (java.lang.String DataEntry_RecordData)
	{
		set_Value (COLUMNNAME_DataEntry_RecordData, DataEntry_RecordData);
	}

	/** Get DataEntry_RecordData.
		@return Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display
	  */
	@Override
	public java.lang.String getDataEntry_RecordData () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_RecordData);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class);
	}

	@Override
	public void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class, DataEntry_SubTab);
	}

	/** Set Unterregister.
		@param DataEntry_SubTab_ID Unterregister	  */
	@Override
	public void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID)
	{
		if (DataEntry_SubTab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubTab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubTab_ID, Integer.valueOf(DataEntry_SubTab_ID));
	}

	/** Get Unterregister.
		@return Unterregister	  */
	@Override
	public int getDataEntry_SubTab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_SubTab_ID);
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
}