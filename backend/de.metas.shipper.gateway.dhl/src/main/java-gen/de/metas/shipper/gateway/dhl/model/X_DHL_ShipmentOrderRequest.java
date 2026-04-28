// Generated Model - DO NOT CHANGE
package de.metas.shipper.gateway.dhl.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DHL_ShipmentOrderRequest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DHL_ShipmentOrderRequest extends org.compiere.model.PO implements I_DHL_ShipmentOrderRequest, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2065344209L;

    /** Standard Constructor */
    public X_DHL_ShipmentOrderRequest (final Properties ctx, final int DHL_ShipmentOrderRequest_ID, @Nullable final String trxName)
    {
      super (ctx, DHL_ShipmentOrderRequest_ID, trxName);
    }

    /** Load Constructor */
    public X_DHL_ShipmentOrderRequest (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDHL_ShipmentOrderRequest_ID (final int DHL_ShipmentOrderRequest_ID)
	{
		if (DHL_ShipmentOrderRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrderRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrderRequest_ID, DHL_ShipmentOrderRequest_ID);
	}

	@Override
	public int getDHL_ShipmentOrderRequest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_ShipmentOrderRequest_ID);
	}
}