/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Cockpit_DocumentDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Cockpit_DocumentDetail extends org.compiere.model.PO implements I_MD_Cockpit_DocumentDetail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1451599594L;

    /** Standard Constructor */
    public X_MD_Cockpit_DocumentDetail (Properties ctx, int MD_Cockpit_DocumentDetail_ID, String trxName)
    {
      super (ctx, MD_Cockpit_DocumentDetail_ID, trxName);
      /** if (MD_Cockpit_DocumentDetail_ID == 0)
        {
			setMD_Cockpit_DocumentDetails_ID (0);
			setMD_Cockpit_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Cockpit_DocumentDetail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abo-Verlauf.
		@param C_SubscriptionProgress_ID Abo-Verlauf	  */
	@Override
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, Integer.valueOf(C_SubscriptionProgress_ID));
	}

	/** Get Abo-Verlauf.
		@return Abo-Verlauf	  */
	@Override
	public int getC_SubscriptionProgress_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubscriptionProgress_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DocumentDetails.
		@param MD_Cockpit_DocumentDetails_ID DocumentDetails	  */
	@Override
	public void setMD_Cockpit_DocumentDetails_ID (int MD_Cockpit_DocumentDetails_ID)
	{
		if (MD_Cockpit_DocumentDetails_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DocumentDetails_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DocumentDetails_ID, Integer.valueOf(MD_Cockpit_DocumentDetails_ID));
	}

	/** Get DocumentDetails.
		@return DocumentDetails	  */
	@Override
	public int getMD_Cockpit_DocumentDetails_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Cockpit_DocumentDetails_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class);
	}

	@Override
	public void setMD_Cockpit(de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit)
	{
		set_ValueFromPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class, MD_Cockpit);
	}

	/** Set Materialcockpit.
		@param MD_Cockpit_ID Materialcockpit	  */
	@Override
	public void setMD_Cockpit_ID (int MD_Cockpit_ID)
	{
		if (MD_Cockpit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, Integer.valueOf(MD_Cockpit_ID));
	}

	/** Get Materialcockpit.
		@return Materialcockpit	  */
	@Override
	public int getMD_Cockpit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Cockpit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}