/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.go.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for GO_DeliveryOrder_Package
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GO_DeliveryOrder_Package extends org.compiere.model.PO implements I_GO_DeliveryOrder_Package, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1272843742L;

    /** Standard Constructor */
    public X_GO_DeliveryOrder_Package (Properties ctx, int GO_DeliveryOrder_Package_ID, String trxName)
    {
      super (ctx, GO_DeliveryOrder_Package_ID, trxName);
      /** if (GO_DeliveryOrder_Package_ID == 0)
        {
			setGO_DeliveryOrder_ID (0);
			setGO_DeliveryOrder_Package_ID (0);
			setM_Package_ID (0);
        } */
    }

    /** Load Constructor */
    public X_GO_DeliveryOrder_Package (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder getGO_DeliveryOrder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_DeliveryOrder_ID, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class);
	}

	@Override
	public void setGO_DeliveryOrder(de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder GO_DeliveryOrder)
	{
		set_ValueFromPO(COLUMNNAME_GO_DeliveryOrder_ID, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class, GO_DeliveryOrder);
	}

	/** Set GO Delivery Order.
		@param GO_DeliveryOrder_ID GO Delivery Order	  */
	@Override
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID)
	{
		if (GO_DeliveryOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_ID, Integer.valueOf(GO_DeliveryOrder_ID));
	}

	/** Get GO Delivery Order.
		@return GO Delivery Order	  */
	@Override
	public int getGO_DeliveryOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliveryOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GO Delivery Order Package.
		@param GO_DeliveryOrder_Package_ID GO Delivery Order Package	  */
	@Override
	public void setGO_DeliveryOrder_Package_ID (int GO_DeliveryOrder_Package_ID)
	{
		if (GO_DeliveryOrder_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_Package_ID, Integer.valueOf(GO_DeliveryOrder_Package_ID));
	}

	/** Get GO Delivery Order Package.
		@return GO Delivery Order Package	  */
	@Override
	public int getGO_DeliveryOrder_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliveryOrder_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

	/** Set Packstück.
		@param M_Package_ID 
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Packstück.
		@return Shipment Package
	  */
	@Override
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}