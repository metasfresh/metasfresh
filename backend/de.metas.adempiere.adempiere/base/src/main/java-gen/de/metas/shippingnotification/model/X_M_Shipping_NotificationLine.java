// Generated Model - DO NOT CHANGE
package de.metas.shippingnotification.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Shipping_NotificationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipping_NotificationLine extends org.compiere.model.PO implements I_M_Shipping_NotificationLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -698240775L;

    /** Standard Constructor */
    public X_M_Shipping_NotificationLine (final Properties ctx, final int M_Shipping_NotificationLine_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipping_NotificationLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipping_NotificationLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
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

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setMovementQty (final BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	@Override
	public BigDecimal getMovementQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MovementQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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

	@Override
	public de.metas.shippingnotification.model.I_M_Shipping_Notification getM_Shipping_Notification()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipping_Notification_ID, de.metas.shippingnotification.model.I_M_Shipping_Notification.class);
	}

	@Override
	public void setM_Shipping_Notification(final de.metas.shippingnotification.model.I_M_Shipping_Notification M_Shipping_Notification)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipping_Notification_ID, de.metas.shippingnotification.model.I_M_Shipping_Notification.class, M_Shipping_Notification);
	}

	@Override
	public void setM_Shipping_Notification_ID (final int M_Shipping_Notification_ID)
	{
		if (M_Shipping_Notification_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipping_Notification_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipping_Notification_ID, M_Shipping_Notification_ID);
	}

	@Override
	public int getM_Shipping_Notification_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipping_Notification_ID);
	}

	@Override
	public void setM_Shipping_NotificationLine_ID (final int M_Shipping_NotificationLine_ID)
	{
		if (M_Shipping_NotificationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipping_NotificationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipping_NotificationLine_ID, M_Shipping_NotificationLine_ID);
	}

	@Override
	public int getM_Shipping_NotificationLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipping_NotificationLine_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}