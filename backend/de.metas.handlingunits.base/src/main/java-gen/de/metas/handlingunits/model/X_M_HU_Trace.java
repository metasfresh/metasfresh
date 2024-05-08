// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Trace extends org.compiere.model.PO implements I_M_HU_Trace, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 974446899L;

    /** Standard Constructor */
    public X_M_HU_Trace (final Properties ctx, final int M_HU_Trace_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Trace_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Trace (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
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
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setEventTime (final java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	@Override
	public java.sql.Timestamp getEventTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EventTime);
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
	@Override
	public void setHUTraceType (final java.lang.String HUTraceType)
	{
		set_Value (COLUMNNAME_HUTraceType, HUTraceType);
	}

	@Override
	public java.lang.String getHUTraceType() 
	{
		return get_ValueAsString(COLUMNNAME_HUTraceType);
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_Trace_ID (final int M_HU_Trace_ID)
	{
		if (M_HU_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, M_HU_Trace_ID);
	}

	@Override
	public int getM_HU_Trace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trace_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setM_HU_Trx_Line(final de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, M_HU_Trx_Line);
	}

	@Override
	public void setM_HU_Trx_Line_ID (final int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, M_HU_Trx_Line_ID);
	}

	@Override
	public int getM_HU_Trx_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Line_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
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
	public org.compiere.model.I_M_Movement getM_Movement()
	{
		return get_ValueAsPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class);
	}

	@Override
	public void setM_Movement(final org.compiere.model.I_M_Movement M_Movement)
	{
		set_ValueFromPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class, M_Movement);
	}

	@Override
	public void setM_Movement_ID (final int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, M_Movement_ID);
	}

	@Override
	public int getM_Movement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Movement_ID);
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
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(final org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	@Override
	public void setPP_Cost_Collector_ID (final int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, PP_Cost_Collector_ID);
	}

	@Override
	public int getPP_Cost_Collector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setQty (final BigDecimal Qty)
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
	public de.metas.handlingunits.model.I_M_HU getVHU()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(final de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	@Override
	public void setVHU_ID (final int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, VHU_ID);
	}

	@Override
	public int getVHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU_Source()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_Source_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU_Source(final de.metas.handlingunits.model.I_M_HU VHU_Source)
	{
		set_ValueFromPO(COLUMNNAME_VHU_Source_ID, de.metas.handlingunits.model.I_M_HU.class, VHU_Source);
	}

	@Override
	public void setVHU_Source_ID (final int VHU_Source_ID)
	{
		if (VHU_Source_ID < 1) 
			set_Value (COLUMNNAME_VHU_Source_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_Source_ID, VHU_Source_ID);
	}

	@Override
	public int getVHU_Source_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_Source_ID);
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
	@Override
	public void setVHUStatus (final java.lang.String VHUStatus)
	{
		set_ValueNoCheck (COLUMNNAME_VHUStatus, VHUStatus);
	}

	@Override
	public java.lang.String getVHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_VHUStatus);
	}
}