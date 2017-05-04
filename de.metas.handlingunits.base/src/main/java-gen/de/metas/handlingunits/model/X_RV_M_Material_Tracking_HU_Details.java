/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for RV_M_Material_Tracking_HU_Details
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_RV_M_Material_Tracking_HU_Details extends org.compiere.model.PO implements I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1265720941L;

    /** Standard Constructor */
    public X_RV_M_Material_Tracking_HU_Details (Properties ctx, int RV_M_Material_Tracking_HU_Details_ID, String trxName)
    {
      super (ctx, RV_M_Material_Tracking_HU_Details_ID, trxName);
      /** if (RV_M_Material_Tracking_HU_Details_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_RV_M_Material_Tracking_HU_Details (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Gebinde Status.
		@param HUStatus Gebinde Status	  */
	@Override
	public void setHUStatus (java.lang.String HUStatus)
	{

		set_ValueNoCheck (COLUMNNAME_HUStatus, HUStatus);
	}

	/** Get Gebinde Status.
		@return Gebinde Status	  */
	@Override
	public java.lang.String getHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUStatus);
	}

	/** Set Waschprobe.
		@param IsQualityInspection Waschprobe	  */
	@Override
	public void setIsQualityInspection (boolean IsQualityInspection)
	{
		set_ValueNoCheck (COLUMNNAME_IsQualityInspection, Boolean.valueOf(IsQualityInspection));
	}

	/** Get Waschprobe.
		@return Waschprobe	  */
	@Override
	public boolean isQualityInspection () 
	{
		Object oo = get_Value(COLUMNNAME_IsQualityInspection);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Los-Nr..
		@param Lot 
		Los-Nummer (alphanumerisch)
	  */
	@Override
	public void setLot (java.lang.String Lot)
	{
		set_ValueNoCheck (COLUMNNAME_Lot, Lot);
	}

	/** Get Los-Nr..
		@return Los-Nummer (alphanumerisch)
	  */
	@Override
	public java.lang.String getLot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut_Receipt() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_Receipt_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut_Receipt(org.compiere.model.I_M_InOut M_InOut_Receipt)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_Receipt_ID, org.compiere.model.I_M_InOut.class, M_InOut_Receipt);
	}

	/** Set Material Receipt.
		@param M_InOut_Receipt_ID Material Receipt	  */
	@Override
	public void setM_InOut_Receipt_ID (int M_InOut_Receipt_ID)
	{
		if (M_InOut_Receipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Receipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Receipt_ID, Integer.valueOf(M_InOut_Receipt_ID));
	}

	/** Get Material Receipt.
		@return Material Receipt	  */
	@Override
	public int getM_InOut_Receipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_Receipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut_Shipment() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_Shipment_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut_Shipment(org.compiere.model.I_M_InOut M_InOut_Shipment)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_Shipment_ID, org.compiere.model.I_M_InOut.class, M_InOut_Shipment);
	}

	/** Set Shipment.
		@param M_InOut_Shipment_ID Shipment	  */
	@Override
	public void setM_InOut_Shipment_ID (int M_InOut_Shipment_ID)
	{
		if (M_InOut_Shipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Shipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_Shipment_ID, Integer.valueOf(M_InOut_Shipment_ID));
	}

	/** Get Shipment.
		@return Shipment	  */
	@Override
	public int getM_InOut_Shipment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_Shipment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.materialtracking.model.I_M_Material_Tracking getM_Material_Tracking() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Tracking_ID, de.metas.materialtracking.model.I_M_Material_Tracking.class);
	}

	@Override
	public void setM_Material_Tracking(de.metas.materialtracking.model.I_M_Material_Tracking M_Material_Tracking)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Tracking_ID, de.metas.materialtracking.model.I_M_Material_Tracking.class, M_Material_Tracking);
	}

	/** Set Material-Vorgang-ID.
		@param M_Material_Tracking_ID Material-Vorgang-ID	  */
	@Override
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, Integer.valueOf(M_Material_Tracking_ID));
	}

	/** Get Material-Vorgang-ID.
		@return Material-Vorgang-ID	  */
	@Override
	public int getM_Material_Tracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set MO Issue Doc Status.
		@param PP_Order_Issue_DocStatus MO Issue Doc Status	  */
	@Override
	public void setPP_Order_Issue_DocStatus (java.lang.String PP_Order_Issue_DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocStatus, PP_Order_Issue_DocStatus);
	}

	/** Get MO Issue Doc Status.
		@return MO Issue Doc Status	  */
	@Override
	public java.lang.String getPP_Order_Issue_DocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PP_Order_Issue_DocStatus);
	}

	@Override
	public org.compiere.model.I_C_DocType getPP_Order_Issue_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Issue_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setPP_Order_Issue_DocType(org.compiere.model.I_C_DocType PP_Order_Issue_DocType)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Issue_DocType_ID, org.compiere.model.I_C_DocType.class, PP_Order_Issue_DocType);
	}

	/** Set MO Issue DocType.
		@param PP_Order_Issue_DocType_ID MO Issue DocType	  */
	@Override
	public void setPP_Order_Issue_DocType_ID (int PP_Order_Issue_DocType_ID)
	{
		if (PP_Order_Issue_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_DocType_ID, Integer.valueOf(PP_Order_Issue_DocType_ID));
	}

	/** Get MO Issue DocType.
		@return MO Issue DocType	  */
	@Override
	public int getPP_Order_Issue_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Issue_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order_Issue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Issue_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order_Issue(org.eevolution.model.I_PP_Order PP_Order_Issue)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Issue_ID, org.eevolution.model.I_PP_Order.class, PP_Order_Issue);
	}

	/** Set Zugeteilt zu Prod.-Auftrag.
		@param PP_Order_Issue_ID Zugeteilt zu Prod.-Auftrag	  */
	@Override
	public void setPP_Order_Issue_ID (int PP_Order_Issue_ID)
	{
		if (PP_Order_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Issue_ID, Integer.valueOf(PP_Order_Issue_ID));
	}

	/** Get Zugeteilt zu Prod.-Auftrag.
		@return Zugeteilt zu Prod.-Auftrag	  */
	@Override
	public int getPP_Order_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set MO Receipt DocStatus.
		@param PP_Order_Receipt_DocStatus MO Receipt DocStatus	  */
	@Override
	public void setPP_Order_Receipt_DocStatus (java.lang.String PP_Order_Receipt_DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocStatus, PP_Order_Receipt_DocStatus);
	}

	/** Get MO Receipt DocStatus.
		@return MO Receipt DocStatus	  */
	@Override
	public java.lang.String getPP_Order_Receipt_DocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PP_Order_Receipt_DocStatus);
	}

	@Override
	public org.compiere.model.I_C_DocType getPP_Order_Receipt_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Receipt_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setPP_Order_Receipt_DocType(org.compiere.model.I_C_DocType PP_Order_Receipt_DocType)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Receipt_DocType_ID, org.compiere.model.I_C_DocType.class, PP_Order_Receipt_DocType);
	}

	/** Set MO Receipt DocType.
		@param PP_Order_Receipt_DocType_ID MO Receipt DocType	  */
	@Override
	public void setPP_Order_Receipt_DocType_ID (int PP_Order_Receipt_DocType_ID)
	{
		if (PP_Order_Receipt_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_DocType_ID, Integer.valueOf(PP_Order_Receipt_DocType_ID));
	}

	/** Get MO Receipt DocType.
		@return MO Receipt DocType	  */
	@Override
	public int getPP_Order_Receipt_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Receipt_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order_Receipt() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Receipt_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order_Receipt(org.eevolution.model.I_PP_Order PP_Order_Receipt)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Receipt_ID, org.eevolution.model.I_PP_Order.class, PP_Order_Receipt);
	}

	/** Set Empf. aus Prod.-Auftrag.
		@param PP_Order_Receipt_ID Empf. aus Prod.-Auftrag	  */
	@Override
	public void setPP_Order_Receipt_ID (int PP_Order_Receipt_ID)
	{
		if (PP_Order_Receipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Receipt_ID, Integer.valueOf(PP_Order_Receipt_ID));
	}

	/** Get Empf. aus Prod.-Auftrag.
		@return Empf. aus Prod.-Auftrag	  */
	@Override
	public int getPP_Order_Receipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Receipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Waschprobe.
		@param QualityInspectionCycle Waschprobe	  */
	@Override
	public void setQualityInspectionCycle (java.lang.String QualityInspectionCycle)
	{
		set_ValueNoCheck (COLUMNNAME_QualityInspectionCycle, QualityInspectionCycle);
	}

	/** Get Waschprobe.
		@return Waschprobe	  */
	@Override
	public java.lang.String getQualityInspectionCycle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityInspectionCycle);
	}

	/** Set RV_M_Material_Tracking_HU_Details_ID.
		@param RV_M_Material_Tracking_HU_Details_ID RV_M_Material_Tracking_HU_Details_ID	  */
	@Override
	public void setRV_M_Material_Tracking_HU_Details_ID (int RV_M_Material_Tracking_HU_Details_ID)
	{
		if (RV_M_Material_Tracking_HU_Details_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID, Integer.valueOf(RV_M_Material_Tracking_HU_Details_ID));
	}

	/** Get RV_M_Material_Tracking_HU_Details_ID.
		@return RV_M_Material_Tracking_HU_Details_ID	  */
	@Override
	public int getRV_M_Material_Tracking_HU_Details_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}