/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Indication
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Indication extends org.compiere.model.PO implements I_M_Indication, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1020806982L;

    /** Standard Constructor */
    public X_M_Indication (Properties ctx, int M_Indication_ID, String trxName)
    {
      super (ctx, M_Indication_ID, trxName);
      /** if (M_Indication_ID == 0)
        {
			setM_Indication_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Indication (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Indication.
		@param M_Indication_ID Indication	  */
	@Override
	public void setM_Indication_ID (int M_Indication_ID)
	{
		if (M_Indication_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Indication_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Indication_ID, Integer.valueOf(M_Indication_ID));
	}

	/** Get Indication.
		@return Indication	  */
	@Override
	public int getM_Indication_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Indication_ID);
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