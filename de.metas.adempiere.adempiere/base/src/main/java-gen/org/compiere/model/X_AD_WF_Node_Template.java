/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_WF_Node_Template
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_WF_Node_Template extends org.compiere.model.PO implements I_AD_WF_Node_Template, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1769605510L;

    /** Standard Constructor */
    public X_AD_WF_Node_Template (Properties ctx, int AD_WF_Node_Template_ID, String trxName)
    {
      super (ctx, AD_WF_Node_Template_ID, trxName);
      /** if (AD_WF_Node_Template_ID == 0)
        {
			setAD_WF_Node_Template_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_WF_Node_Template (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Workflow Steps Template.
		@param AD_WF_Node_Template_ID Workflow Steps Template	  */
	@Override
	public void setAD_WF_Node_Template_ID (int AD_WF_Node_Template_ID)
	{
		if (AD_WF_Node_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Template_ID, Integer.valueOf(AD_WF_Node_Template_ID));
	}

	/** Get Workflow Steps Template.
		@return Workflow Steps Template	  */
	@Override
	public int getAD_WF_Node_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_Template_ID);
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

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}