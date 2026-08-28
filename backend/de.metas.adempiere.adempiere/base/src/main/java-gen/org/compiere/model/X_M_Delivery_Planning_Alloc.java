// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Delivery_Planning_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Delivery_Planning_Alloc extends org.compiere.model.PO implements I_M_Delivery_Planning_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 938334227L;

    /** Standard Constructor */
    public X_M_Delivery_Planning_Alloc (final Properties ctx, final int M_Delivery_Planning_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, M_Delivery_Planning_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Delivery_Planning_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDateRemoved (final @Nullable java.sql.Timestamp DateRemoved)
	{
		set_Value (COLUMNNAME_DateRemoved, DateRemoved);
	}

	@Override
	public java.sql.Timestamp getDateRemoved() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateRemoved);
	}

	@Override
	public void setLineNo (final int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, LineNo);
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setM_Delivery_Planning_Alloc_ID (final int M_Delivery_Planning_Alloc_ID)
	{
		if (M_Delivery_Planning_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Alloc_ID, M_Delivery_Planning_Alloc_ID);
	}

	@Override
	public int getM_Delivery_Planning_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_Alloc_ID);
	}

	@Override
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	@Override
	public void setM_ShipperTransportation_ID (final int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setM_ShippingPackage_ID (final int M_ShippingPackage_ID)
	{
		if (M_ShippingPackage_ID < 1) 
			set_Value (COLUMNNAME_M_ShippingPackage_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShippingPackage_ID, M_ShippingPackage_ID);
	}

	@Override
	public int getM_ShippingPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShippingPackage_ID);
	}
}