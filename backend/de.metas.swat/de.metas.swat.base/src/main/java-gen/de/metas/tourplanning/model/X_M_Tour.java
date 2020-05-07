/** Generated Model - DO NOT CHANGE */
package de.metas.tourplanning.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Tour
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Tour extends org.compiere.model.PO implements I_M_Tour, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -29589527L;

    /** Standard Constructor */
    public X_M_Tour (Properties ctx, int M_Tour_ID, String trxName)
    {
      super (ctx, M_Tour_ID, trxName);
      /** if (M_Tour_ID == 0)
        {
			setM_Tour_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Tour (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Tour.
		@param M_Tour_ID Tour	  */
	@Override
	public void setM_Tour_ID (int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Tour_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Tour_ID, Integer.valueOf(M_Tour_ID));
	}

	/** Get Tour.
		@return Tour	  */
	@Override
	public int getM_Tour_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_ID);
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