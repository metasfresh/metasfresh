// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_M_Material_Tracking_HU_Details
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_M_Material_Tracking_HU_Details extends org.compiere.model.PO implements I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1759764899L;

    /** Standard Constructor */
    public X_RV_M_Material_Tracking_HU_Details (final Properties ctx, final int RV_M_Material_Tracking_HU_Details_ID, @Nullable final String trxName)
    {
      super (ctx, RV_M_Material_Tracking_HU_Details_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_M_Material_Tracking_HU_Details (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	@Override
	public void setHUStatus (final @Nullable java.lang.String HUStatus)
	{
		set_ValueNoCheck (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
	}

	@Override
	public void setIsQualityInspection (final boolean IsQualityInspection)
	{
		set_ValueNoCheck (COLUMNNAME_IsQualityInspection, IsQualityInspection);
	}

	@Override
	public boolean isQualityInspection() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQualityInspection);
	}

	@Override
	public void setLot (final @Nullable java.lang.String Lot)
	{
		set_ValueNoCheck (COLUMNNAME_Lot, Lot);
	}

	@Override
	public java.lang.String getLot() 
	{
		return get_ValueAsString(COLUMNNAME_Lot);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut_Receipt()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_Receipt_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut_Receipt(final org.compiere.model.I_M_InOut M_InOut_Receipt)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_Receipt_ID, org.compiere.model.I_M_InOut.class, M_InOut_Receipt);
	}

	@Override
	public void setM_InOut_Receipt_ID (final int M_InOut_Receipt_ID)
	{
		if (M_InOut_Receipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Receipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Receipt_ID, M_InOut_Receipt_ID);
	}

	@Override
	public int getM_InOut_Receipt_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_Receipt_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut_Shipment()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_Shipment_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut_Shipment(final org.compiere.model.I_M_InOut M_InOut_Shipment)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_Shipment_ID, org.compiere.model.I_M_InOut.class, M_InOut_Shipment);
	}

	@Override
	public void setM_InOut_Shipment_ID (final int M_InOut_Shipment_ID)
	{
		if (M_InOut_Shipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Shipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Shipment_ID, M_InOut_Shipment_ID);
	}

	@Override
	public int getM_InOut_Shipment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_Shipment_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public void setM_Material_Tracking_ID (final int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, M_Material_Tracking_ID);
	}

	@Override
	public int getM_Material_Tracking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Material_Tracking_ID);
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

	/** 
	 * PP_Order_Issue_DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int PP_ORDER_ISSUE_DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String PP_ORDER_ISSUE_DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setPP_Order_Issue_DocStatus (final @Nullable java.lang.String PP_Order_Issue_DocStatus)
	{
		set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocStatus, PP_Order_Issue_DocStatus);
	}

	@Override
	public java.lang.String getPP_Order_Issue_DocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_PP_Order_Issue_DocStatus);
	}

	@Override
	public void setPP_Order_Issue_DocType_ID (final int PP_Order_Issue_DocType_ID)
	{
		if (PP_Order_Issue_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocType_ID, PP_Order_Issue_DocType_ID);
	}

	@Override
	public int getPP_Order_Issue_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Issue_DocType_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Issue_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order_Issue(final org.eevolution.model.I_PP_Order PP_Order_Issue)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Issue_ID, org.eevolution.model.I_PP_Order.class, PP_Order_Issue);
	}

	@Override
	public void setPP_Order_Issue_ID (final int PP_Order_Issue_ID)
	{
		if (PP_Order_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_ID, PP_Order_Issue_ID);
	}

	@Override
	public int getPP_Order_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Issue_ID);
	}

	/** 
	 * PP_Order_Receipt_DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int PP_ORDER_RECEIPT_DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String PP_ORDER_RECEIPT_DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setPP_Order_Receipt_DocStatus (final @Nullable java.lang.String PP_Order_Receipt_DocStatus)
	{
		set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocStatus, PP_Order_Receipt_DocStatus);
	}

	@Override
	public java.lang.String getPP_Order_Receipt_DocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_PP_Order_Receipt_DocStatus);
	}

	@Override
	public void setPP_Order_Receipt_DocType_ID (final int PP_Order_Receipt_DocType_ID)
	{
		if (PP_Order_Receipt_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocType_ID, PP_Order_Receipt_DocType_ID);
	}

	@Override
	public int getPP_Order_Receipt_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Receipt_DocType_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order_Receipt()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Receipt_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order_Receipt(final org.eevolution.model.I_PP_Order PP_Order_Receipt)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Receipt_ID, org.eevolution.model.I_PP_Order.class, PP_Order_Receipt);
	}

	@Override
	public void setPP_Order_Receipt_ID (final int PP_Order_Receipt_ID)
	{
		if (PP_Order_Receipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_ID, PP_Order_Receipt_ID);
	}

	@Override
	public int getPP_Order_Receipt_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Receipt_ID);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityInspectionCycle (final @Nullable java.lang.String QualityInspectionCycle)
	{
		set_ValueNoCheck (COLUMNNAME_QualityInspectionCycle, QualityInspectionCycle);
	}

	@Override
	public java.lang.String getQualityInspectionCycle() 
	{
		return get_ValueAsString(COLUMNNAME_QualityInspectionCycle);
	}

	@Override
	public void setRV_M_Material_Tracking_HU_Details_ID (final int RV_M_Material_Tracking_HU_Details_ID)
	{
		if (RV_M_Material_Tracking_HU_Details_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID, RV_M_Material_Tracking_HU_Details_ID);
	}

	@Override
	public int getRV_M_Material_Tracking_HU_Details_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID);
	}
}