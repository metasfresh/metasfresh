/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_PurchaseOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_PurchaseOrder extends org.compiere.model.PO implements I_MSV3_PurchaseOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1892064210L;

    /** Standard Constructor */
    public X_MSV3_PurchaseOrder (Properties ctx, int MSV3_PurchaseOrder_ID, String trxName)
    {
      super (ctx, MSV3_PurchaseOrder_ID, trxName);
      /** if (MSV3_PurchaseOrder_ID == 0)
        {
			setMSV3_PurchaseOrder_ID (0);
			setUUID (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_PurchaseOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set MSV3_PurchaseOrder.
		@param MSV3_PurchaseOrder_ID MSV3_PurchaseOrder	  */
	@Override
	public void setMSV3_PurchaseOrder_ID (int MSV3_PurchaseOrder_ID)
	{
		if (MSV3_PurchaseOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_PurchaseOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_PurchaseOrder_ID, Integer.valueOf(MSV3_PurchaseOrder_ID));
	}

	/** Get MSV3_PurchaseOrder.
		@return MSV3_PurchaseOrder	  */
	@Override
	public int getMSV3_PurchaseOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_PurchaseOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SupportId.
		@param SupportId SupportId	  */
	@Override
	public void setSupportId (int SupportId)
	{
		set_Value (COLUMNNAME_SupportId, Integer.valueOf(SupportId));
	}

	/** Get SupportId.
		@return SupportId	  */
	@Override
	public int getSupportId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SupportId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UUID.
		@param UUID UUID	  */
	@Override
	public void setUUID (java.lang.String UUID)
	{
		set_ValueNoCheck (COLUMNNAME_UUID, UUID);
	}

	/** Get UUID.
		@return UUID	  */
	@Override
	public java.lang.String getUUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UUID);
	}
}