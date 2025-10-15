// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_Carrier_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipmentSchedule_Carrier_Service extends org.compiere.model.PO implements I_M_ShipmentSchedule_Carrier_Service, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1460375029L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_Carrier_Service (final Properties ctx, final int M_ShipmentSchedule_Carrier_Service_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipmentSchedule_Carrier_Service_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_Carrier_Service (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_ShipmentSchedule_Carrier_Service_ID (final int M_ShipmentSchedule_Carrier_Service_ID)
	{
		if (M_ShipmentSchedule_Carrier_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_Carrier_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_Carrier_Service_ID, M_ShipmentSchedule_Carrier_Service_ID);
	}

	@Override
	public int getM_ShipmentSchedule_Carrier_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_Carrier_Service_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}
}