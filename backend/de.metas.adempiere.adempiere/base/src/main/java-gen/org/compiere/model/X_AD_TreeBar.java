/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_TreeBar
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_TreeBar extends org.compiere.model.PO implements I_AD_TreeBar, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -180659685L;

    /** Standard Constructor */
    public X_AD_TreeBar (Properties ctx, int AD_TreeBar_ID, String trxName)
    {
      super (ctx, AD_TreeBar_ID, trxName);
      /** if (AD_TreeBar_ID == 0)
        {
			setAD_User_ID (0);
			setNode_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_TreeBar (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Node_ID.
		@param Node_ID Node_ID	  */
	@Override
	public void setNode_ID (int Node_ID)
	{
		if (Node_ID < 0) 
			set_Value (COLUMNNAME_Node_ID, null);
		else 
			set_Value (COLUMNNAME_Node_ID, Integer.valueOf(Node_ID));
	}

	/** Get Node_ID.
		@return Node_ID	  */
	@Override
	public int getNode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}