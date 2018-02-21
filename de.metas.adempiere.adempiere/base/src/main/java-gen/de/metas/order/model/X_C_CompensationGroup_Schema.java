/** Generated Model - DO NOT CHANGE */
package de.metas.order.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CompensationGroup_Schema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CompensationGroup_Schema extends org.compiere.model.PO implements I_C_CompensationGroup_Schema, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -322270064L;

    /** Standard Constructor */
    public X_C_CompensationGroup_Schema (Properties ctx, int C_CompensationGroup_Schema_ID, String trxName)
    {
      super (ctx, C_CompensationGroup_Schema_ID, trxName);
      /** if (C_CompensationGroup_Schema_ID == 0)
        {
			setC_CompensationGroup_Schema_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_CompensationGroup_Schema (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Compensation Group Schema.
		@param C_CompensationGroup_Schema_ID Compensation Group Schema	  */
	@Override
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, Integer.valueOf(C_CompensationGroup_Schema_ID));
	}

	/** Get Compensation Group Schema.
		@return Compensation Group Schema	  */
	@Override
	public int getC_CompensationGroup_Schema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CompensationGroup_Schema_ID);
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