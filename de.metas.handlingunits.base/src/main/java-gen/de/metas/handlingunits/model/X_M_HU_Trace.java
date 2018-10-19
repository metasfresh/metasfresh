/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Trace
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Trace extends org.compiere.model.PO implements I_M_HU_Trace, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 649082030L;

    /** Standard Constructor */
    public X_M_HU_Trace (Properties ctx, int M_HU_Trace_ID, String trxName)
    {
      super (ctx, M_HU_Trace_ID, trxName);
      /** if (M_HU_Trace_ID == 0)
        {
			setEventTime (new Timestamp( System.currentTimeMillis() ));
			setHUTraceType (null);
			setM_HU_ID (0);
			setM_HU_Trace_ID (0);
			setM_Product_ID (0);
			setQty (BigDecimal.ZERO);
			setVHU_ID (0);
			setVHUStatus (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Trace (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Zeitpunkt.
		@param EventTime Zeitpunkt	  */
	@Override
	public void setEventTime (java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	/** Get Zeitpunkt.
		@return Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getEventTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EventTime);
	}

	/** 
	 * HUTraceType AD_Reference_ID=540729
	 * Reference name: HUTraceType
	 */
	public static final int HUTRACETYPE_AD_Reference_ID=540729;
	/** MATERIAL_SHIPMENT = MATERIAL_SHIPMENT */
	public static final String HUTRACETYPE_MATERIAL_SHIPMENT = "MATERIAL_SHIPMENT";
	/** MATERIAL_RECEIPT = MATERIAL_RECEIPT */
	public static final String HUTRACETYPE_MATERIAL_RECEIPT = "MATERIAL_RECEIPT";
	/** MATERIAL_MOVEMENT = MATERIAL_MOVEMENT */
	public static final String HUTRACETYPE_MATERIAL_MOVEMENT = "MATERIAL_MOVEMENT";
	/** MATERIAL_PICKING = MATERIAL_PICKING */
	public static final String HUTRACETYPE_MATERIAL_PICKING = "MATERIAL_PICKING";
	/** PRODUCTION_ISSUE = PRODUCTION_ISSUE */
	public static final String HUTRACETYPE_PRODUCTION_ISSUE = "PRODUCTION_ISSUE";
	/** PRODUCTION_RECEIPT = PRODUCTION_RECEIPT */
	public static final String HUTRACETYPE_PRODUCTION_RECEIPT = "PRODUCTION_RECEIPT";
	/** TRANSFORM_LOAD = TRANSFORM_LOAD */
	public static final String HUTRACETYPE_TRANSFORM_LOAD = "TRANSFORM_LOAD";
	/** TRANSFORM_PARENT = TRANSFORM_PARENT */
	public static final String HUTRACETYPE_TRANSFORM_PARENT = "TRANSFORM_PARENT";
	/** Set Typ.
		@param HUTraceType Typ	  */
	@Override
	public void setHUTraceType (java.lang.String HUTraceType)
	{

		set_Value (COLUMNNAME_HUTraceType, HUTraceType);
	}

	/** Get Typ.
		@return Typ	  */
	@Override
	public java.lang.String getHUTraceType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUTraceType);
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
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

	/** Set Rückverfolgbarkeit.
		@param M_HU_Trace_ID Rückverfolgbarkeit	  */
	@Override
	public void setM_HU_Trace_ID (int M_HU_Trace_ID)
	{
		if (M_HU_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, Integer.valueOf(M_HU_Trace_ID));
	}

	/** Get Rückverfolgbarkeit.
		@return Rückverfolgbarkeit	  */
	@Override
	public int getM_HU_Trace_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trace_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setM_HU_Trx_Line(de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, M_HU_Trx_Line);
	}

	/** Set HU-Transaktionszeile.
		@param M_HU_Trx_Line_ID HU-Transaktionszeile	  */
	@Override
	public void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, Integer.valueOf(M_HU_Trx_Line_ID));
	}

	/** Get HU-Transaktionszeile.
		@return HU-Transaktionszeile	  */
	@Override
	public int getM_HU_Trx_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class);
	}

	@Override
	public void setM_Movement(org.compiere.model.I_M_Movement M_Movement)
	{
		set_ValueFromPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class, M_Movement);
	}

	/** Set Warenbewegung.
		@param M_Movement_ID 
		Bewegung von Warenbestand
	  */
	@Override
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Warenbewegung.
		@return Bewegung von Warenbestand
	  */
	@Override
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
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

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	/** Set Manufacturing Cost Collector.
		@param PP_Cost_Collector_ID Manufacturing Cost Collector	  */
	@Override
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, Integer.valueOf(PP_Cost_Collector_ID));
	}

	/** Get Manufacturing Cost Collector.
		@return Manufacturing Cost Collector	  */
	@Override
	public int getPP_Cost_Collector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Cost_Collector_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
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
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	/** Set CU Handling Unit (VHU).
		@param VHU_ID CU Handling Unit (VHU)	  */
	@Override
	public void setVHU_ID (int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, Integer.valueOf(VHU_ID));
	}

	/** Get CU Handling Unit (VHU).
		@return CU Handling Unit (VHU)	  */
	@Override
	public int getVHU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VHU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU_Source() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_VHU_Source_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU_Source(de.metas.handlingunits.model.I_M_HU VHU_Source)
	{
		set_ValueFromPO(COLUMNNAME_VHU_Source_ID, de.metas.handlingunits.model.I_M_HU.class, VHU_Source);
	}

	/** Set Ursprungs-VHU.
		@param VHU_Source_ID Ursprungs-VHU	  */
	@Override
	public void setVHU_Source_ID (int VHU_Source_ID)
	{
		if (VHU_Source_ID < 1) 
			set_Value (COLUMNNAME_VHU_Source_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_Source_ID, Integer.valueOf(VHU_Source_ID));
	}

	/** Get Ursprungs-VHU.
		@return Ursprungs-VHU	  */
	@Override
	public int getVHU_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VHU_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * VHUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int VHUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String VHUSTATUS_Planning = "P";
	/** Active = A */
	public static final String VHUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String VHUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String VHUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String VHUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String VHUSTATUS_Issued = "I";
	/** Set CU (VHU) Gebindestatus.
		@param VHUStatus CU (VHU) Gebindestatus	  */
	@Override
	public void setVHUStatus (java.lang.String VHUStatus)
	{

		set_ValueNoCheck (COLUMNNAME_VHUStatus, VHUStatus);
	}

	/** Get CU (VHU) Gebindestatus.
		@return CU (VHU) Gebindestatus	  */
	@Override
	public java.lang.String getVHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VHUStatus);
	}
}