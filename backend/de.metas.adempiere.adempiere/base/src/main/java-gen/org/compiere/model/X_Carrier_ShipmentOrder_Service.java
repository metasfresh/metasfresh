// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_ShipmentOrder_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_ShipmentOrder_Service extends org.compiere.model.PO implements I_Carrier_ShipmentOrder_Service, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 728679928L;

    /** Standard Constructor */
    public X_Carrier_ShipmentOrder_Service (final Properties ctx, final int Carrier_ShipmentOrder_Service_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_ShipmentOrder_Service_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_ShipmentOrder_Service (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setCarrier_Service_ID (final int Carrier_Service_ID)
	{
		if (Carrier_Service_ID < 1) 
			set_Value (COLUMNNAME_Carrier_Service_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_Service_ID, Carrier_Service_ID);
	}

	@Override
	public int getCarrier_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Service_ID);
	}

	@Override
	public void setCarrier_ShipmentOrder_ID (final int Carrier_ShipmentOrder_ID)
	{
		if (Carrier_ShipmentOrder_ID < 1) 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, Carrier_ShipmentOrder_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_ID);
	}

	@Override
	public void setCarrier_ShipmentOrder_Service_ID (final int Carrier_ShipmentOrder_Service_ID)
	{
		if (Carrier_ShipmentOrder_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Service_ID, Carrier_ShipmentOrder_Service_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_Service_ID);
	}
}