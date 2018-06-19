/** Generated Model - DO NOT CHANGE */
package de.metas.elasticsearch.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Template
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ES_FTS_Template extends org.compiere.model.PO implements I_ES_FTS_Template, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1737913424L;

    /** Standard Constructor */
    public X_ES_FTS_Template (Properties ctx, int ES_FTS_Template_ID, String trxName)
    {
      super (ctx, ES_FTS_Template_ID, trxName);
      /** if (ES_FTS_Template_ID == 0)
        {
			setES_FTS_Template_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_ES_FTS_Template (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Settings.
		@param ES_FTS_Settings Settings	  */
	@Override
	public void setES_FTS_Settings (java.lang.String ES_FTS_Settings)
	{
		set_Value (COLUMNNAME_ES_FTS_Settings, ES_FTS_Settings);
	}

	/** Get Settings.
		@return Settings	  */
	@Override
	public java.lang.String getES_FTS_Settings () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ES_FTS_Settings);
	}

	/** Set Full Text Search Template.
		@param ES_FTS_Template_ID Full Text Search Template	  */
	@Override
	public void setES_FTS_Template_ID (int ES_FTS_Template_ID)
	{
		if (ES_FTS_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Template_ID, Integer.valueOf(ES_FTS_Template_ID));
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}