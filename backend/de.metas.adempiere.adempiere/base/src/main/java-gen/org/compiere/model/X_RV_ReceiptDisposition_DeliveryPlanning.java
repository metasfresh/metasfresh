// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_ReceiptDisposition_DeliveryPlanning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_ReceiptDisposition_DeliveryPlanning extends org.compiere.model.PO implements I_RV_ReceiptDisposition_DeliveryPlanning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1097496608L;

    /** Standard Constructor */
    public X_RV_ReceiptDisposition_DeliveryPlanning (final Properties ctx, final int RV_ReceiptDisposition_DeliveryPlanning_ID, @Nullable final String trxName)
    {
      super (ctx, RV_ReceiptDisposition_DeliveryPlanning_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_ReceiptDisposition_DeliveryPlanning (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setATA (final @Nullable java.sql.Timestamp ATA)
	{
		set_ValueNoCheck (COLUMNNAME_ATA, ATA);
	}

	@Override
	public java.sql.Timestamp getATA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATA);
	}

	@Override
	public void setATD (final @Nullable java.sql.Timestamp ATD)
	{
		set_ValueNoCheck (COLUMNNAME_ATD, ATD);
	}

	@Override
	public java.sql.Timestamp getATD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATD);
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_ValueNoCheck (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setCalendarWeek (final @Nullable BigDecimal CalendarWeek)
	{
		set_ValueNoCheck (COLUMNNAME_CalendarWeek, CalendarWeek);
	}

	@Override
	public BigDecimal getCalendarWeek() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CalendarWeek);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setContainerNo (final @Nullable java.lang.String ContainerNo)
	{
		set_ValueNoCheck (COLUMNNAME_ContainerNo, ContainerNo);
	}

	@Override
	public java.lang.String getContainerNo() 
	{
		return get_ValueAsString(COLUMNNAME_ContainerNo);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDatePromised_Effective (final @Nullable java.sql.Timestamp DatePromised_Effective)
	{
		set_ValueNoCheck (COLUMNNAME_DatePromised_Effective, DatePromised_Effective);
	}

	@Override
	public java.sql.Timestamp getDatePromised_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised_Effective);
	}

	@Override
	public void setETA (final @Nullable java.sql.Timestamp ETA)
	{
		set_ValueNoCheck (COLUMNNAME_ETA, ETA);
	}

	@Override
	public java.sql.Timestamp getETA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETA);
	}

	@Override
	public void setETD (final @Nullable java.sql.Timestamp ETD)
	{
		set_ValueNoCheck (COLUMNNAME_ETD, ETD);
	}

	@Override
	public java.sql.Timestamp getETD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETD);
	}

	@Override
	public void setIsBLReceived (final boolean IsBLReceived)
	{
		set_ValueNoCheck (COLUMNNAME_IsBLReceived, IsBLReceived);
	}

	@Override
	public boolean isBLReceived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBLReceived);
	}

	@Override
	public void setIsBookingConfirmed (final boolean IsBookingConfirmed)
	{
		set_ValueNoCheck (COLUMNNAME_IsBookingConfirmed, IsBookingConfirmed);
	}

	@Override
	public boolean isBookingConfirmed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBookingConfirmed);
	}

	@Override
	public void setIsConfirmedBySupplier (final boolean IsConfirmedBySupplier)
	{
		set_ValueNoCheck (COLUMNNAME_IsConfirmedBySupplier, IsConfirmedBySupplier);
	}

	@Override
	public boolean isConfirmedBySupplier() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsConfirmedBySupplier);
	}

	@Override
	public void setIsPlanned (final boolean IsPlanned)
	{
		set_ValueNoCheck (COLUMNNAME_IsPlanned, IsPlanned);
	}

	@Override
	public boolean isPlanned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPlanned);
	}

	@Override
	public void setIsWENotice (final boolean IsWENotice)
	{
		set_ValueNoCheck (COLUMNNAME_IsWENotice, IsWENotice);
	}

	@Override
	public boolean isWENotice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWENotice);
	}

	@Override
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_ReceiptSchedule_ID (final int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, M_ReceiptSchedule_ID);
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQtyOrdered (final @Nullable BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove (final @Nullable BigDecimal QtyToMove)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToMove, QtyToMove);
	}

	@Override
	public BigDecimal getQtyToMove() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRV_ReceiptDisposition_DeliveryPlanning_ID (final int RV_ReceiptDisposition_DeliveryPlanning_ID)
	{
		if (RV_ReceiptDisposition_DeliveryPlanning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RV_ReceiptDisposition_DeliveryPlanning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RV_ReceiptDisposition_DeliveryPlanning_ID, RV_ReceiptDisposition_DeliveryPlanning_ID);
	}

	@Override
	public int getRV_ReceiptDisposition_DeliveryPlanning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RV_ReceiptDisposition_DeliveryPlanning_ID);
	}
}