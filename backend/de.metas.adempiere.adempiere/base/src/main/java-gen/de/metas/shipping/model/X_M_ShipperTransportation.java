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

	private static final long serialVersionUID = 1934635377L;

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
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
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