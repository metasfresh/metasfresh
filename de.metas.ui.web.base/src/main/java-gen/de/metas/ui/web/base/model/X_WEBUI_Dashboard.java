/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Dashboard
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Dashboard extends org.compiere.model.PO implements I_WEBUI_Dashboard, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 453204059L;

    /** Standard Constructor */
    public X_WEBUI_Dashboard (Properties ctx, int WEBUI_Dashboard_ID, String trxName)
    {
      super (ctx, WEBUI_Dashboard_ID, trxName);
      /** if (WEBUI_Dashboard_ID == 0)
        {
			setIsDefault (false);
// N
			setName (null);
			setWEBUI_Dashboard_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Dashboard (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Dashboard.
		@param WEBUI_Dashboard_ID Dashboard	  */
	@Override
	public void setWEBUI_Dashboard_ID (int WEBUI_Dashboard_ID)
	{
		if (WEBUI_Dashboard_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, Integer.valueOf(WEBUI_Dashboard_ID));
	}

	/** Get Dashboard.
		@return Dashboard	  */
	@Override
	public int getWEBUI_Dashboard_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Dashboard_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}