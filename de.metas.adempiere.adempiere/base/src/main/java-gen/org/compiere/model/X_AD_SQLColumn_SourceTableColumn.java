/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_SQLColumn_SourceTableColumn
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_SQLColumn_SourceTableColumn extends org.compiere.model.PO implements I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1560371965L;

    /** Standard Constructor */
    public X_AD_SQLColumn_SourceTableColumn (Properties ctx, int AD_SQLColumn_SourceTableColumn_ID, String trxName)
    {
      super (ctx, AD_SQLColumn_SourceTableColumn_ID, trxName);
      /** if (AD_SQLColumn_SourceTableColumn_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_SQLColumn_SourceTableColumn_ID (0);
			setAD_Table_ID (0); // @AD_Table_ID@
			setFetchTargetRecordsMethod (null); // L
			setSource_Table_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_SQLColumn_SourceTableColumn (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Spalte in der Tabelle
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Spalte in der Tabelle
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SQL Column's Source Table Column.
		@param AD_SQLColumn_SourceTableColumn_ID SQL Column's Source Table Column	  */
	@Override
	public void setAD_SQLColumn_SourceTableColumn_ID (int AD_SQLColumn_SourceTableColumn_ID)
	{
		if (AD_SQLColumn_SourceTableColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SQLColumn_SourceTableColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SQLColumn_SourceTableColumn_ID, Integer.valueOf(AD_SQLColumn_SourceTableColumn_ID));
	}

	/** Get SQL Column's Source Table Column.
		@return SQL Column's Source Table Column	  */
	@Override
	public int getAD_SQLColumn_SourceTableColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SQLColumn_SourceTableColumn_ID);
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

	/** 
	 * FetchTargetRecordsMethod AD_Reference_ID=541123
	 * Reference name: FetchTargetRecordsMethod
	 */
	public static final int FETCHTARGETRECORDSMETHOD_AD_Reference_ID=541123;
	/** SQL = S */
	public static final String FETCHTARGETRECORDSMETHOD_SQL = "S";
	/** Link Column = L */
	public static final String FETCHTARGETRECORDSMETHOD_LinkColumn = "L";
	/** Set Fetch Target Records Method.
		@param FetchTargetRecordsMethod Fetch Target Records Method	  */
	@Override
	public void setFetchTargetRecordsMethod (java.lang.String FetchTargetRecordsMethod)
	{

		set_Value (COLUMNNAME_FetchTargetRecordsMethod, FetchTargetRecordsMethod);
	}

	/** Get Fetch Target Records Method.
		@return Fetch Target Records Method	  */
	@Override
	public java.lang.String getFetchTargetRecordsMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FetchTargetRecordsMethod);
	}

	@Override
	public org.compiere.model.I_AD_Column getLink_Column()
	{
		return get_ValueAsPO(COLUMNNAME_Link_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setLink_Column(org.compiere.model.I_AD_Column Link_Column)
	{
		set_ValueFromPO(COLUMNNAME_Link_Column_ID, org.compiere.model.I_AD_Column.class, Link_Column);
	}

	/** Set Link Column.
		@param Link_Column_ID Link Column	  */
	@Override
	public void setLink_Column_ID (int Link_Column_ID)
	{
		if (Link_Column_ID < 1) 
			set_Value (COLUMNNAME_Link_Column_ID, null);
		else 
			set_Value (COLUMNNAME_Link_Column_ID, Integer.valueOf(Link_Column_ID));
	}

	/** Get Link Column.
		@return Link Column	  */
	@Override
	public int getLink_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Link_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Column getSource_Column()
	{
		return get_ValueAsPO(COLUMNNAME_Source_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setSource_Column(org.compiere.model.I_AD_Column Source_Column)
	{
		set_ValueFromPO(COLUMNNAME_Source_Column_ID, org.compiere.model.I_AD_Column.class, Source_Column);
	}

	/** Set Source Column.
		@param Source_Column_ID Source Column	  */
	@Override
	public void setSource_Column_ID (int Source_Column_ID)
	{
		if (Source_Column_ID < 1) 
			set_Value (COLUMNNAME_Source_Column_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Column_ID, Integer.valueOf(Source_Column_ID));
	}

	/** Get Source Column.
		@return Source Column	  */
	@Override
	public int getSource_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Source_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Table.
		@param Source_Table_ID Source Table	  */
	@Override
	public void setSource_Table_ID (int Source_Table_ID)
	{
		if (Source_Table_ID < 1) 
			set_Value (COLUMNNAME_Source_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Table_ID, Integer.valueOf(Source_Table_ID));
	}

	/** Get Source Table.
		@return Source Table	  */
	@Override
	public int getSource_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Source_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SQL to get Target Record IDs by Source Record IDs.
		@param SQL_GetTargetRecordIdBySourceRecordId SQL to get Target Record IDs by Source Record IDs	  */
	@Override
	public void setSQL_GetTargetRecordIdBySourceRecordId (java.lang.String SQL_GetTargetRecordIdBySourceRecordId)
	{
		set_Value (COLUMNNAME_SQL_GetTargetRecordIdBySourceRecordId, SQL_GetTargetRecordIdBySourceRecordId);
	}

	/** Get SQL to get Target Record IDs by Source Record IDs.
		@return SQL to get Target Record IDs by Source Record IDs	  */
	@Override
	public java.lang.String getSQL_GetTargetRecordIdBySourceRecordId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SQL_GetTargetRecordIdBySourceRecordId);
	}
}