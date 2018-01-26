/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_PurchaseOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_PurchaseOrderLine extends org.compiere.model.PO implements I_MSV3_PurchaseOrderLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1663189226L;

    /** Standard Constructor */
    public X_MSV3_PurchaseOrderLine (Properties ctx, int MSV3_PurchaseOrderLine_ID, String trxName)
    {
      super (ctx, MSV3_PurchaseOrderLine_ID, trxName);
      /** if (MSV3_PurchaseOrderLine_ID == 0)
        {
			setMSV3_PurchaseOrder_ID (0);
			setMSV3_PurchaseOrderLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_PurchaseOrderLine (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder getMSV3_PurchaseOrder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_PurchaseOrder_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder.class);
	}

	@Override
	public void setMSV3_PurchaseOrder(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder MSV3_PurchaseOrder)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_PurchaseOrder_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder.class, MSV3_PurchaseOrder);
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

	/** Set MSV3_PurchaseOrderLine.
		@param MSV3_PurchaseOrderLine_ID MSV3_PurchaseOrderLine	  */
	@Override
	public void setMSV3_PurchaseOrderLine_ID (int MSV3_PurchaseOrderLine_ID)
	{
		if (MSV3_PurchaseOrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_PurchaseOrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_PurchaseOrderLine_ID, Integer.valueOf(MSV3_PurchaseOrderLine_ID));
	}

	/** Get MSV3_PurchaseOrderLine.
		@return MSV3_PurchaseOrderLine	  */
	@Override
	public int getMSV3_PurchaseOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_PurchaseOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}