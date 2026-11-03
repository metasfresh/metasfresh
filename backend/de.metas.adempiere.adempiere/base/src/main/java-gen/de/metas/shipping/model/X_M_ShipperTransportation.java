// Generated Model - DO NOT CHANGE
package de.metas.shipping.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ShipperTransportation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipperTransportation extends org.compiere.model.PO implements I_M_ShipperTransportation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -903883409L;

    /** Standard Constructor */
    public X_M_ShipperTransportation (final Properties ctx, final int M_ShipperTransportation_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipperTransportation_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipperTransportation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignAnonymouslyPickedHUs (final boolean AssignAnonymouslyPickedHUs)
	{
		set_Value (COLUMNNAME_AssignAnonymouslyPickedHUs, AssignAnonymouslyPickedHUs);
	}

	@Override
	public boolean isAssignAnonymouslyPickedHUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AssignAnonymouslyPickedHUs);
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
	public void setBLDate (final @Nullable java.sql.Timestamp BLDate)
	{
		set_Value (COLUMNNAME_BLDate, BLDate);
	}

	@Override
	public java.sql.Timestamp getBLDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_BLDate);
	}

	@Override
	public void setC_BPartner_Location_Delivery_ID (final int C_BPartner_Location_Delivery_ID)
	{
		if (C_BPartner_Location_Delivery_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Delivery_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Delivery_ID, C_BPartner_Location_Delivery_ID);
	}

	@Override
	public int getC_BPartner_Location_Delivery_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Delivery_ID);
	}

	@Override
	public void setC_BPartner_Location_Loading_ID (final int C_BPartner_Location_Loading_ID)
	{
		if (C_BPartner_Location_Loading_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Loading_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Loading_ID, C_BPartner_Location_Loading_ID);
	}

	@Override
	public int getC_BPartner_Location_Loading_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Loading_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public org.compiere.model.I_C_Incoterms getC_Incoterms()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms(final org.compiere.model.I_C_Incoterms C_Incoterms)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms);
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
	public void setContainerNo (final @Nullable java.lang.String ContainerNo)
	{
		set_Value (COLUMNNAME_ContainerNo, ContainerNo);
	}

	@Override
	public java.lang.String getContainerNo() 
	{
		return get_ValueAsString(COLUMNNAME_ContainerNo);
	}

	@Override
	public void setCRD (final @Nullable java.sql.Timestamp CRD)
	{
		set_Value (COLUMNNAME_CRD, CRD);
	}

	@Override
	public java.sql.Timestamp getCRD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CRD);
	}

	@Override
	public void setCreateShippingPackages (final @Nullable java.lang.String CreateShippingPackages)
	{
		set_Value (COLUMNNAME_CreateShippingPackages, CreateShippingPackages);
	}

	@Override
	public java.lang.String getCreateShippingPackages() 
	{
		return get_ValueAsString(COLUMNNAME_CreateShippingPackages);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDateToBeFetched (final @Nullable java.sql.Timestamp DateToBeFetched)
	{
		set_Value (COLUMNNAME_DateToBeFetched, DateToBeFetched);
	}

	@Override
	public java.sql.Timestamp getDateToBeFetched() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateToBeFetched);
	}

	/** 
	 * DeliveredState AD_Reference_ID=542137
	 * Reference name: DeliveredState
	 */
	public static final int DELIVEREDSTATE_AD_Reference_ID=542137;
	/** Not delivered = NotDelivered */
	public static final String DELIVEREDSTATE_NotDelivered = "NotDelivered";
	/** Partly delivered = PartlyDelivered */
	public static final String DELIVEREDSTATE_PartlyDelivered = "PartlyDelivered";
	/** Fully delivered = FullyDelivered */
	public static final String DELIVEREDSTATE_FullyDelivered = "FullyDelivered";
	@Override
	public void setDeliveredState (final java.lang.String DeliveredState)
	{
		set_Value (COLUMNNAME_DeliveredState, DeliveredState);
	}

	@Override
	public java.lang.String getDeliveredState() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveredState);
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
	public void setHasLines (final boolean HasLines)
	{
		throw new IllegalArgumentException ("HasLines is virtual column");	}

	@Override
	public boolean isHasLines() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasLines);
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
	public void setIsApproved (final boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsBLReceived (final boolean IsBLReceived)
	{
		set_Value (COLUMNNAME_IsBLReceived, IsBLReceived);
	}

	@Override
	public boolean isBLReceived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBLReceived);
	}

	@Override
	public void setIsBookingConfirmed (final boolean IsBookingConfirmed)
	{
		set_Value (COLUMNNAME_IsBookingConfirmed, IsBookingConfirmed);
	}

	@Override
	public boolean isBookingConfirmed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBookingConfirmed);
	}

	@Override
	public void setIsWENotice (final boolean IsWENotice)
	{
		set_Value (COLUMNNAME_IsWENotice, IsWENotice);
	}

	@Override
	public boolean isWENotice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWENotice);
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
	public org.compiere.model.I_M_MeansOfTransportation getM_MeansOfTransportation()
	{
		return get_ValueAsPO(COLUMNNAME_M_MeansOfTransportation_ID, org.compiere.model.I_M_MeansOfTransportation.class);
	}

	@Override
	public void setM_MeansOfTransportation(final org.compiere.model.I_M_MeansOfTransportation M_MeansOfTransportation)
	{
		set_ValueFromPO(COLUMNNAME_M_MeansOfTransportation_ID, org.compiere.model.I_M_MeansOfTransportation.class, M_MeansOfTransportation);
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
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
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setM_Tour_ID (final int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, M_Tour_ID);
	}

	@Override
	public int getM_Tour_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Tour_ID);
	}

	@Override
	public void setPackageNetTotal (final @Nullable BigDecimal PackageNetTotal)
	{
		set_Value (COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

	@Override
	public BigDecimal getPackageNetTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageNetTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPackageWeight (final @Nullable BigDecimal PackageWeight)
	{
		set_Value (COLUMNNAME_PackageWeight, PackageWeight);
	}

	@Override
	public BigDecimal getPackageWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPickupTimeFrom (final @Nullable java.sql.Timestamp PickupTimeFrom)
	{
		set_Value (COLUMNNAME_PickupTimeFrom, PickupTimeFrom);
	}

	@Override
	public java.sql.Timestamp getPickupTimeFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PickupTimeFrom);
	}

	@Override
	public void setPickupTimeTo (final @Nullable java.sql.Timestamp PickupTimeTo)
	{
		set_Value (COLUMNNAME_PickupTimeTo, PickupTimeTo);
	}

	@Override
	public java.sql.Timestamp getPickupTimeTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PickupTimeTo);
	}

	@Override
	public org.compiere.model.I_C_Postal getPOD()
	{
		return get_ValueAsPO(COLUMNNAME_POD_ID, org.compiere.model.I_C_Postal.class);
	}

	@Override
	public void setPOD(final org.compiere.model.I_C_Postal POD)
	{
		set_ValueFromPO(COLUMNNAME_POD_ID, org.compiere.model.I_C_Postal.class, POD);
	}

	@Override
	public void setPOD_ID (final int POD_ID)
	{
		if (POD_ID < 1) 
			set_Value (COLUMNNAME_POD_ID, null);
		else 
			set_Value (COLUMNNAME_POD_ID, POD_ID);
	}

	@Override
	public int getPOD_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_POD_ID);
	}

	@Override
	public org.compiere.model.I_C_Postal getPOL()
	{
		return get_ValueAsPO(COLUMNNAME_POL_ID, org.compiere.model.I_C_Postal.class);
	}

	@Override
	public void setPOL(final org.compiere.model.I_C_Postal POL)
	{
		set_ValueFromPO(COLUMNNAME_POL_ID, org.compiere.model.I_C_Postal.class, POL);
	}

	@Override
	public void setPOL_ID (final int POL_ID)
	{
		if (POL_ID < 1) 
			set_Value (COLUMNNAME_POL_ID, null);
		else 
			set_Value (COLUMNNAME_POL_ID, POL_ID);
	}

	@Override
	public int getPOL_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_POL_ID);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setSalesRep_ID (final int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
	}

	@Override
	public void setShipper_BPartner_ID (final int Shipper_BPartner_ID)
	{
		if (Shipper_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, Shipper_BPartner_ID);
	}

	@Override
	public int getShipper_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Shipper_BPartner_ID);
	}

	@Override
	public void setShipper_Location_ID (final int Shipper_Location_ID)
	{
		if (Shipper_Location_ID < 1) 
			set_Value (COLUMNNAME_Shipper_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_Location_ID, Shipper_Location_ID);
	}

	@Override
	public int getShipper_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Shipper_Location_ID);
	}

	@Override
	public void setTrackingID (final @Nullable java.lang.String TrackingID)
	{
		set_Value (COLUMNNAME_TrackingID, TrackingID);
	}

	@Override
	public java.lang.String getTrackingID() 
	{
		return get_ValueAsString(COLUMNNAME_TrackingID);
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
	public void setVesselName (final @Nullable java.lang.String VesselName)
	{
		set_Value (COLUMNNAME_VesselName, VesselName);
	}

	@Override
	public java.lang.String getVesselName() 
	{
		return get_ValueAsString(COLUMNNAME_VesselName);
	}
}