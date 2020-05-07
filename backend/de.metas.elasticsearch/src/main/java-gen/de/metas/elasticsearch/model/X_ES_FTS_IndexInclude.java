/** Generated Model - DO NOT CHANGE */
package de.metas.elasticsearch.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_IndexInclude
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ES_FTS_IndexInclude extends org.compiere.model.PO implements I_ES_FTS_IndexInclude, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1401996421L;

    /** Standard Constructor */
    public X_ES_FTS_IndexInclude (Properties ctx, int ES_FTS_IndexInclude_ID, String trxName)
    {
      super (ctx, ES_FTS_IndexInclude_ID, trxName);
      /** if (ES_FTS_IndexInclude_ID == 0)
        {
			setAttributeName (null);
			setES_FTS_Index_ID (0);
			setES_FTS_IndexInclude_ID (0);
			setInclude_LinkColumn_ID (0);
			setInclude_Table_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ES_FTS_IndexInclude (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Attribute Name.
		@param AttributeName 
		Name of the Attribute
	  */
	@Override
	public void setAttributeName (java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	@Override
	public java.lang.String getAttributeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeName);
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

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Index getES_FTS_Index() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Index_ID, de.metas.elasticsearch.model.I_ES_FTS_Index.class);
	}

	@Override
	public void setES_FTS_Index(de.metas.elasticsearch.model.I_ES_FTS_Index ES_FTS_Index)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Index_ID, de.metas.elasticsearch.model.I_ES_FTS_Index.class, ES_FTS_Index);
	}

	/** Set FTS Index.
		@param ES_FTS_Index_ID FTS Index	  */
	@Override
	public void setES_FTS_Index_ID (int ES_FTS_Index_ID)
	{
		if (ES_FTS_Index_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_ID, Integer.valueOf(ES_FTS_Index_ID));
	}

	/** Get FTS Index.
		@return FTS Index	  */
	@Override
	public int getES_FTS_Index_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ES_FTS_Index_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set FTS Index Include.
		@param ES_FTS_IndexInclude_ID FTS Index Include	  */
	@Override
	public void setES_FTS_IndexInclude_ID (int ES_FTS_IndexInclude_ID)
	{
		if (ES_FTS_IndexInclude_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_IndexInclude_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_IndexInclude_ID, Integer.valueOf(ES_FTS_IndexInclude_ID));
	}

	/** Get FTS Index Include.
		@return FTS Index Include	  */
	@Override
	public int getES_FTS_IndexInclude_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ES_FTS_IndexInclude_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Column getInclude_LinkColumn() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Include_LinkColumn_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setInclude_LinkColumn(org.compiere.model.I_AD_Column Include_LinkColumn)
	{
		set_ValueFromPO(COLUMNNAME_Include_LinkColumn_ID, org.compiere.model.I_AD_Column.class, Include_LinkColumn);
	}

	/** Set Link column.
		@param Include_LinkColumn_ID Link column	  */
	@Override
	public void setInclude_LinkColumn_ID (int Include_LinkColumn_ID)
	{
		if (Include_LinkColumn_ID < 1) 
			set_Value (COLUMNNAME_Include_LinkColumn_ID, null);
		else 
			set_Value (COLUMNNAME_Include_LinkColumn_ID, Integer.valueOf(Include_LinkColumn_ID));
	}

	/** Get Link column.
		@return Link column	  */
	@Override
	public int getInclude_LinkColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Include_LinkColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getInclude_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Include_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setInclude_Table(org.compiere.model.I_AD_Table Include_Table)
	{
		set_ValueFromPO(COLUMNNAME_Include_Table_ID, org.compiere.model.I_AD_Table.class, Include_Table);
	}

	/** Set Include table.
		@param Include_Table_ID Include table	  */
	@Override
	public void setInclude_Table_ID (int Include_Table_ID)
	{
		if (Include_Table_ID < 1) 
			set_Value (COLUMNNAME_Include_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Include_Table_ID, Integer.valueOf(Include_Table_ID));
	}

	/** Get Include table.
		@return Include table	  */
	@Override
	public int getInclude_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Include_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}