/** Generated Model - DO NOT CHANGE */
package de.metas.datev.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DATEV_Export
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DATEV_Export extends org.compiere.model.PO implements I_DATEV_Export, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1095928501L;

    /** Standard Constructor */
    public X_DATEV_Export (Properties ctx, int DATEV_Export_ID, String trxName)
    {
      super (ctx, DATEV_Export_ID, trxName);
      /** if (DATEV_Export_ID == 0)
        {
			setDateAcctTo (new Timestamp( System.currentTimeMillis() ));
			setDATEV_Export_ID (0);
			setIsExcludeAlreadyExported (true); // Y
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_DATEV_Export (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Buchungsdatum von.
		@param DateAcctFrom Buchungsdatum von	  */
	@Override
	public void setDateAcctFrom (java.sql.Timestamp DateAcctFrom)
	{
		set_Value (COLUMNNAME_DateAcctFrom, DateAcctFrom);
	}

	/** Get Buchungsdatum von.
		@return Buchungsdatum von	  */
	@Override
	public java.sql.Timestamp getDateAcctFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcctFrom);
	}

	/** Set Buchungsdatum bis.
		@param DateAcctTo Buchungsdatum bis	  */
	@Override
	public void setDateAcctTo (java.sql.Timestamp DateAcctTo)
	{
		set_Value (COLUMNNAME_DateAcctTo, DateAcctTo);
	}

	/** Get Buchungsdatum bis.
		@return Buchungsdatum bis	  */
	@Override
	public java.sql.Timestamp getDateAcctTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcctTo);
	}

	/** Set DATEV Export.
		@param DATEV_Export_ID DATEV Export	  */
	@Override
	public void setDATEV_Export_ID (int DATEV_Export_ID)
	{
		if (DATEV_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, Integer.valueOf(DATEV_Export_ID));
	}

	/** Get DATEV Export.
		@return DATEV Export	  */
	@Override
	public int getDATEV_Export_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DATEV_Export_ID);
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

	/** Set Exclude already exported.
		@param IsExcludeAlreadyExported Exclude already exported	  */
	@Override
	public void setIsExcludeAlreadyExported (boolean IsExcludeAlreadyExported)
	{
		set_Value (COLUMNNAME_IsExcludeAlreadyExported, Boolean.valueOf(IsExcludeAlreadyExported));
	}

	/** Get Exclude already exported.
		@return Exclude already exported	  */
	@Override
	public boolean isExcludeAlreadyExported () 
	{
		Object oo = get_Value(COLUMNNAME_IsExcludeAlreadyExported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}