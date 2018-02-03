/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Bestellung
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Bestellung extends org.compiere.model.PO implements I_MSV3_Bestellung, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -823969793L;

    /** Standard Constructor */
    public X_MSV3_Bestellung (Properties ctx, int MSV3_Bestellung_ID, String trxName)
    {
      super (ctx, MSV3_Bestellung_ID, trxName);
      /** if (MSV3_Bestellung_ID == 0)
        {
			setMSV3_BestellSupportId (0);
			setMSV3_Bestellung_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Bestellung (Properties ctx, ResultSet rs, String trxName)
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

	/** Set BestellSupportId.
		@param MSV3_BestellSupportId BestellSupportId	  */
	@Override
	public void setMSV3_BestellSupportId (int MSV3_BestellSupportId)
	{
		set_Value (COLUMNNAME_MSV3_BestellSupportId, Integer.valueOf(MSV3_BestellSupportId));
	}

	/** Get BestellSupportId.
		@return BestellSupportId	  */
	@Override
	public int getMSV3_BestellSupportId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellSupportId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_Bestellung.
		@param MSV3_Bestellung_ID MSV3_Bestellung	  */
	@Override
	public void setMSV3_Bestellung_ID (int MSV3_Bestellung_ID)
	{
		if (MSV3_Bestellung_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_ID, Integer.valueOf(MSV3_Bestellung_ID));
	}

	/** Get MSV3_Bestellung.
		@return MSV3_Bestellung	  */
	@Override
	public int getMSV3_Bestellung_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Bestellung_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Id.
		@param MSV3_Id Id	  */
	@Override
	public void setMSV3_Id (java.lang.String MSV3_Id)
	{
		set_Value (COLUMNNAME_MSV3_Id, MSV3_Id);
	}

	/** Get Id.
		@return Id	  */
	@Override
	public java.lang.String getMSV3_Id () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Id);
	}
}