/** Generated Model - DO NOT CHANGE */
package de.metas.elasticsearch.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Index
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ES_FTS_Index extends org.compiere.model.PO implements I_ES_FTS_Index, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -156491767L;

    /** Standard Constructor */
    public X_ES_FTS_Index (Properties ctx, int ES_FTS_Index_ID, String trxName)
    {
      super (ctx, ES_FTS_Index_ID, trxName);
      /** if (ES_FTS_Index_ID == 0)
        {
			setAD_Table_ID (0);
			setES_FTS_Index_ID (0);
			setES_Index (null);
        } */
    }

    /** Load Constructor */
    public X_ES_FTS_Index (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Template getES_FTS_Template() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Template_ID, de.metas.elasticsearch.model.I_ES_FTS_Template.class);
	}

	@Override
	public void setES_FTS_Template(de.metas.elasticsearch.model.I_ES_FTS_Template ES_FTS_Template)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Template_ID, de.metas.elasticsearch.model.I_ES_FTS_Template.class, ES_FTS_Template);
	}

	/** Set Full Text Search Template.
		@param ES_FTS_Template_ID Full Text Search Template	  */
	@Override
	public void setES_FTS_Template_ID (int ES_FTS_Template_ID)
	{
		if (ES_FTS_Template_ID < 1) 
			set_Value (COLUMNNAME_ES_FTS_Template_ID, null);
		else 
			set_Value (COLUMNNAME_ES_FTS_Template_ID, Integer.valueOf(ES_FTS_Template_ID));
	}

	/** Get Full Text Search Template.
		@return Full Text Search Template	  */
	@Override
	public int getES_FTS_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ES_FTS_Template_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Elasticsearch Index.
		@param ES_Index Elasticsearch Index	  */
	@Override
	public void setES_Index (java.lang.String ES_Index)
	{
		set_Value (COLUMNNAME_ES_Index, ES_Index);
	}

	/** Get Elasticsearch Index.
		@return Elasticsearch Index	  */
	@Override
	public java.lang.String getES_Index () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ES_Index);
	}
}