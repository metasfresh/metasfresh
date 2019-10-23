/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Label
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Label extends org.compiere.model.PO implements I_M_Label, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1771734195L;

    /** Standard Constructor */
    public X_M_Label (Properties ctx, int M_Label_ID, String trxName)
    {
      super (ctx, M_Label_ID, trxName);
      /** if (M_Label_ID == 0)
        {
			setM_Label_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Label (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Label.
		@param Label Label	  */
	@Override
	public void setLabel (java.lang.String Label)
	{
		set_Value (COLUMNNAME_Label, Label);
	}

	/** Get Label.
		@return Label	  */
	@Override
	public java.lang.String getLabel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Label);
	}

	/** Set Label List.
		@param M_Label_ID Label List	  */
	@Override
	public void setM_Label_ID (int M_Label_ID)
	{
		if (M_Label_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Label_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Label_ID, Integer.valueOf(M_Label_ID));
	}

	/** Get Label List.
		@return Label List	  */
	@Override
	public int getM_Label_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Label_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}