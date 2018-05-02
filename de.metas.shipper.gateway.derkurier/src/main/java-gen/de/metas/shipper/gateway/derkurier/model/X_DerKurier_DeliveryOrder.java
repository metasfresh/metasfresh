/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.derkurier.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DerKurier_DeliveryOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DerKurier_DeliveryOrder extends org.compiere.model.PO implements I_DerKurier_DeliveryOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 103241025L;

    /** Standard Constructor */
    public X_DerKurier_DeliveryOrder (Properties ctx, int DerKurier_DeliveryOrder_ID, String trxName)
    {
      super (ctx, DerKurier_DeliveryOrder_ID, trxName);
      /** if (DerKurier_DeliveryOrder_ID == 0)
        {
			setDerKurier_DeliveryOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DerKurier_DeliveryOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DerKurier_DeliveryOrder.
		@param DerKurier_DeliveryOrder_ID DerKurier_DeliveryOrder	  */
	@Override
	public void setDerKurier_DeliveryOrder_ID (int DerKurier_DeliveryOrder_ID)
	{
		if (DerKurier_DeliveryOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrder_ID, Integer.valueOf(DerKurier_DeliveryOrder_ID));
	}

	/** Get DerKurier_DeliveryOrder.
		@return DerKurier_DeliveryOrder	  */
	@Override
	public int getDerKurier_DeliveryOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_DeliveryOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}