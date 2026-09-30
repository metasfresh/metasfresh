// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_Carrier_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_Carrier_Service extends org.compiere.model.PO implements I_C_Order_Carrier_Service, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1555653450L;

    /** Standard Constructor */
    public X_C_Order_Carrier_Service (final Properties ctx, final int C_Order_Carrier_Service_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_Carrier_Service_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_Carrier_Service (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Order_Carrier_Service_ID (final int C_Order_Carrier_Service_ID)
	{
		if (C_Order_Carrier_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Carrier_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Carrier_Service_ID, C_Order_Carrier_Service_ID);
	}

	@Override
	public int getC_Order_Carrier_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Carrier_Service_ID);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}
}