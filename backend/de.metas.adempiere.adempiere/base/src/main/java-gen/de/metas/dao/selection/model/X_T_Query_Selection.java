/** Generated Model - DO NOT CHANGE */
package de.metas.dao.selection.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for T_Query_Selection
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_T_Query_Selection extends org.compiere.model.PO implements I_T_Query_Selection, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -518361247L;

    /** Standard Constructor */
    public X_T_Query_Selection (Properties ctx, int T_Query_Selection_ID, String trxName)
    {
      super (ctx, T_Query_Selection_ID, trxName);
      /** if (T_Query_Selection_ID == 0)
        {
			setLine (0);
			setRecord_ID (0);
			setUUID (null);
        } */
    }

    /** Load Constructor */
    public X_T_Query_Selection (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
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

	/** Set UUID.
		@param UUID UUID	  */
	@Override
	public void setUUID (java.lang.String UUID)
	{
		set_ValueNoCheck (COLUMNNAME_UUID, UUID);
	}

	/** Get UUID.
		@return UUID	  */
	@Override
	public java.lang.String getUUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UUID);
	}
}