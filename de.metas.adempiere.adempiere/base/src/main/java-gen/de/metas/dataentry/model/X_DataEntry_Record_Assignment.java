/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Record_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Record_Assignment extends org.compiere.model.PO implements I_DataEntry_Record_Assignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1451757060L;

    /** Standard Constructor */
    public X_DataEntry_Record_Assignment (Properties ctx, int DataEntry_Record_Assignment_ID, String trxName)
    {
      super (ctx, DataEntry_Record_Assignment_ID, trxName);
      /** if (DataEntry_Record_Assignment_ID == 0)
        {
			setDataEntry_Record_Assignment_ID (0);
			setDataEntry_Record_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Record_Assignment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DataEntry_Record_Assignment.
		@param DataEntry_Record_Assignment_ID DataEntry_Record_Assignment	  */
	@Override
	public void setDataEntry_Record_Assignment_ID (int DataEntry_Record_Assignment_ID)
	{
		if (DataEntry_Record_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_Assignment_ID, Integer.valueOf(DataEntry_Record_Assignment_ID));
	}

	/** Get DataEntry_Record_Assignment.
		@return DataEntry_Record_Assignment	  */
	@Override
	public int getDataEntry_Record_Assignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Record_Assignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Record getDataEntry_Record() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Record_ID, de.metas.dataentry.model.I_DataEntry_Record.class);
	}

	@Override
	public void setDataEntry_Record(de.metas.dataentry.model.I_DataEntry_Record DataEntry_Record)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Record_ID, de.metas.dataentry.model.I_DataEntry_Record.class, DataEntry_Record);
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
}