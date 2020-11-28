/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dhl.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DHL_ShipmentOrderRequest
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DHL_ShipmentOrderRequest extends org.compiere.model.PO implements I_DHL_ShipmentOrderRequest, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1924496998L;

    /** Standard Constructor */
    public X_DHL_ShipmentOrderRequest (Properties ctx, int DHL_ShipmentOrderRequest_ID, String trxName)
    {
      super (ctx, DHL_ShipmentOrderRequest_ID, trxName);
      /** if (DHL_ShipmentOrderRequest_ID == 0)
        {
			setDHL_ShipmentOrderRequest_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DHL_ShipmentOrderRequest (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DHL Shipment Order Request.
		@param DHL_ShipmentOrderRequest_ID DHL Shipment Order Request	  */
	@Override
	public void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID)
	{
		if (DHL_ShipmentOrderRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrderRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrderRequest_ID, Integer.valueOf(DHL_ShipmentOrderRequest_ID));
	}

	/** Get DHL Shipment Order Request.
		@return DHL Shipment Order Request	  */
	@Override
	public int getDHL_ShipmentOrderRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_ShipmentOrderRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}