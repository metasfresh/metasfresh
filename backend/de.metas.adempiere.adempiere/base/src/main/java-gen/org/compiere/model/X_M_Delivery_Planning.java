// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Delivery_Planning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Delivery_Planning extends org.compiere.model.PO implements I_M_Delivery_Planning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1456037581L;

    /** Standard Constructor */
    public X_M_Delivery_Planning (final Properties ctx, final int M_Delivery_Planning_ID, @Nullable final String trxName)
    {
      super (ctx, M_Delivery_Planning_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Delivery_Planning (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActualDischargeQuantity (final BigDecimal ActualDischargeQuantity)
	{
		set_Value (COLUMNNAME_ActualDischargeQuantity, ActualDischargeQuantity);
	}

	@Override
	public BigDecimal getActualDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualLoadQty (final BigDecimal ActualLoadQty)
	{
		set_Value (COLUMNNAME_ActualLoadQty, ActualLoadQty);
	}

	@Override
	public BigDecimal getActualLoadQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLoadQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setATA (final @Nullable java.sql.Timestamp ATA)
	{
		set_Value (COLUMNNAME_ATA, ATA);
	}

	@Override
	public java.sql.Timestamp getATA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATA);
	}

	@Override
	public void setATD (final @Nullable java.sql.Timestamp ATD)
	{
		set_Value (COLUMNNAME_ATD, ATD);
	}

	@Override
	public java.sql.Timestamp getATD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATD);
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_Value (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		throw new IllegalArgumentException ("BPartnerName is virtual column");	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_DestinationCountry_ID (final int C_DestinationCountry_ID)
	{
		if (C_DestinationCountry_ID < 1) 
			set_Value (COLUMNNAME_C_DestinationCountry_ID, null);
		else 
			set_Value (COLUMNNAME_C_DestinationCountry_ID, C_DestinationCountry_ID);
	}

	@Override
	public int getC_DestinationCountry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DestinationCountry_ID);
	}

	@Override
	public void setC_Incoterms_ID (final int C_Incoterms_ID)
	{
		if (C_Incoterms_ID < 1) 
			set_Value (COLUMNNAME_C_Incoterms_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_ID, C_Incoterms_ID);
	}

	@Override
	public int getC_Incoterms_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_ID);
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
	public void setC_OriginCountry_ID (final int C_OriginCountry_ID)
	{
		if (C_OriginCountry_ID < 1) 
			set_Value (COLUMNNAME_C_OriginCountry_ID, null);
		else 
			set_Value (COLUMNNAME_C_OriginCountry_ID, C_OriginCountry_ID);
	}

	@Override
	public int getC_OriginCountry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OriginCountry_ID);
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
	public void setDeliveryStatus_Color_ID (final int DeliveryStatus_Color_ID)
	{
		throw new IllegalArgumentException ("DeliveryStatus_Color_ID is virtual column");	}

	@Override
	public int getDeliveryStatus_Color_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DeliveryStatus_Color_ID);
	}

	@Override
	public void setDeliveryTime (final @Nullable java.lang.String DeliveryTime)
	{
		set_Value (COLUMNNAME_DeliveryTime, DeliveryTime);
	}

	@Override
	public java.lang.String getDeliveryTime() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryTime);
	}

	@Override
	public void setETA (final @Nullable java.sql.Timestamp ETA)
	{
		set_Value (COLUMNNAME_ETA, ETA);
	}

	@Override
	public java.sql.Timestamp getETA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETA);
	}

	@Override
	public void setETD (final @Nullable java.sql.Timestamp ETD)
	{
		set_Value (COLUMNNAME_ETD, ETD);
	}

	@Override
	public java.sql.Timestamp getETD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETD);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setIsAllocated (final boolean IsAllocated)
	{
		set_ValueNoCheck (COLUMNNAME_IsAllocated, IsAllocated);
	}

	@Override
	public boolean isAllocated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllocated);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setIsDelivered (final boolean IsDelivered)
	{
		throw new IllegalArgumentException ("IsDelivered is virtual column");	}

	@Override
	public boolean isDelivered() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelivered);
	}

	@Override
	public void setLoadingTime (final @Nullable java.lang.String LoadingTime)
	{
		set_Value (COLUMNNAME_LoadingTime, LoadingTime);
	}

	@Override
	public java.lang.String getLoadingTime() 
	{
		return get_ValueAsString(COLUMNNAME_LoadingTime);
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
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setM_MeansOfTransportation_ID (final int M_MeansOfTransportation_ID)
	{
		if (M_MeansOfTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_MeansOfTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_MeansOfTransportation_ID, M_MeansOfTransportation_ID);
	}

	@Override
	public int getM_MeansOfTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MeansOfTransportation_ID);
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
	public void setM_ReceiptSchedule_ID (final int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, M_ReceiptSchedule_ID);
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
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
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setOrderDocumentNo (final @Nullable java.lang.String OrderDocumentNo)
	{
		throw new IllegalArgumentException ("OrderDocumentNo is virtual column");	}

	@Override
	public java.lang.String getOrderDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_OrderDocumentNo);
	}

	/** 
	 * OrderStatus AD_Reference_ID=541690
	 * Reference name: Order Statuses
	 */
	public static final int ORDERSTATUS_AD_Reference_ID=541690;
	/** Arranged = Arranged */
	public static final String ORDERSTATUS_Arranged = "Arranged";
	/** Loaded = Loaded */
	public static final String ORDERSTATUS_Loaded = "Loaded";
	/** Canceled = Canceled */
	public static final String ORDERSTATUS_Canceled = "Canceled";
	@Override
	public void setOrderStatus (final @Nullable java.lang.String OrderStatus)
	{
		set_Value (COLUMNNAME_OrderStatus, OrderStatus);
	}

	@Override
	public java.lang.String getOrderStatus() 
	{
		return get_ValueAsString(COLUMNNAME_OrderStatus);
	}

	@Override
	public void setOriginCountry (final @Nullable java.lang.String OriginCountry)
	{
		throw new IllegalArgumentException ("OriginCountry is virtual column");	}

	@Override
	public java.lang.String getOriginCountry() 
	{
		return get_ValueAsString(COLUMNNAME_OriginCountry);
	}

	@Override
	public void setPlannedDischargeQuantity (final BigDecimal PlannedDischargeQuantity)
	{
		set_Value (COLUMNNAME_PlannedDischargeQuantity, PlannedDischargeQuantity);
	}

	@Override
	public BigDecimal getPlannedDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedLoadedQuantity (final BigDecimal PlannedLoadedQuantity)
	{
		set_Value (COLUMNNAME_PlannedLoadedQuantity, PlannedLoadedQuantity);
	}

	@Override
	public BigDecimal getPlannedLoadedQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedLoadedQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		throw new IllegalArgumentException ("POReference is virtual column");	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
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

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		throw new IllegalArgumentException ("ProductName is virtual column");	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		throw new IllegalArgumentException ("ProductValue is virtual column");	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTotalOpen (final BigDecimal QtyTotalOpen)
	{
		set_Value (COLUMNNAME_QtyTotalOpen, QtyTotalOpen);
	}

	@Override
	public BigDecimal getQtyTotalOpen() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyTotalOpen);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTotalOpenPlanned (final @Nullable BigDecimal QtyTotalOpenPlanned)
	{
		set_Value (COLUMNNAME_QtyTotalOpenPlanned, QtyTotalOpenPlanned);
	}

	@Override
	public BigDecimal getQtyTotalOpenPlanned() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyTotalOpenPlanned);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReleaseNo (final @Nullable java.lang.String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setShipFrom_Location_ID (final int ShipFrom_Location_ID)
	{
		throw new IllegalArgumentException ("ShipFrom_Location_ID is virtual column");	}

	@Override
	public int getShipFrom_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ShipFrom_Location_ID);
	}

	@Override
	public void setShipTo_Location_ID (final int ShipTo_Location_ID)
	{
		throw new IllegalArgumentException ("ShipTo_Location_ID is virtual column");	}

	@Override
	public int getShipTo_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ShipTo_Location_ID);
	}

	@Override
	public void setShipToLocation_Name (final @Nullable java.lang.String ShipToLocation_Name)
	{
		throw new IllegalArgumentException ("ShipToLocation_Name is virtual column");	}

	@Override
	public java.lang.String getShipToLocation_Name() 
	{
		return get_ValueAsString(COLUMNNAME_ShipToLocation_Name);
	}

	@Override
	public void setTransportDetails (final @Nullable java.lang.String TransportDetails)
	{
		set_Value (COLUMNNAME_TransportDetails, TransportDetails);
	}

	@Override
	public java.lang.String getTransportDetails() 
	{
		return get_ValueAsString(COLUMNNAME_TransportDetails);
	}

	/** 
	 * TransportDirection AD_Reference_ID=541689
	 * Reference name: TransportDirection
	 */
	public static final int TRANSPORTDIRECTION_AD_Reference_ID=541689;
	/** Incoming = Incoming */
	public static final String TRANSPORTDIRECTION_Incoming = "Incoming";
	/** Outgoing = Outgoing */
	public static final String TRANSPORTDIRECTION_Outgoing = "Outgoing";
	/** Dropship = Dropship */
	public static final String TRANSPORTDIRECTION_Dropship = "Dropship";
	@Override
	public void setTransportDirection (final java.lang.String TransportDirection)
	{
		set_Value (COLUMNNAME_TransportDirection, TransportDirection);
	}

	@Override
	public java.lang.String getTransportDirection() 
	{
		return get_ValueAsString(COLUMNNAME_TransportDirection);
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		throw new IllegalArgumentException ("WarehouseName is virtual column");	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}

	@Override
	public void setWayBillNo (final @Nullable java.lang.String WayBillNo)
	{
		set_Value (COLUMNNAME_WayBillNo, WayBillNo);
	}

	@Override
	public java.lang.String getWayBillNo() 
	{
		return get_ValueAsString(COLUMNNAME_WayBillNo);
	}
}