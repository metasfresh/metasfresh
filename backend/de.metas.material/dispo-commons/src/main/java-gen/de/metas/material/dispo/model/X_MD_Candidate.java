/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate extends org.compiere.model.PO implements I_MD_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1602991070L;

    /** Standard Constructor */
    public X_MD_Candidate (Properties ctx, int MD_Candidate_ID, String trxName)
    {
      super (ctx, MD_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderSO_ID, Integer.valueOf(C_OrderSO_ID));
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
	}

	@Override
	public void setDateProjected (java.sql.Timestamp DateProjected)
	{
		set_Value (COLUMNNAME_DateProjected, DateProjected);
	}

	@Override
	public java.sql.Timestamp getDateProjected() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateProjected);
	}

	@Override
	public void setIsReservedForCustomer (boolean IsReservedForCustomer)
	{
		set_Value (COLUMNNAME_IsReservedForCustomer, Boolean.valueOf(IsReservedForCustomer));
	}

	@Override
	public boolean isReservedForCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReservedForCustomer);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	/** 
	 * MD_Candidate_BusinessCase AD_Reference_ID=540709
	 * Reference name: MD_Candidate_BusinessCase
	 */
	public static final int MD_CANDIDATE_BUSINESSCASE_AD_Reference_ID=540709;
	/** DISTRIBUTION = DISTRIBUTION */
	public static final String MD_CANDIDATE_BUSINESSCASE_DISTRIBUTION = "DISTRIBUTION";
	/** PRODUCTION = PRODUCTION */
	public static final String MD_CANDIDATE_BUSINESSCASE_PRODUCTION = "PRODUCTION";
	/** RECEIPT = RECEIPT */
	public static final String MD_CANDIDATE_BUSINESSCASE_RECEIPT = "RECEIPT";
	/** SHIPMENT = SHIPMENT */
	public static final String MD_CANDIDATE_BUSINESSCASE_SHIPMENT = "SHIPMENT";
	/** FORECAST = FORECAST */
	public static final String MD_CANDIDATE_BUSINESSCASE_FORECAST = "FORECAST";
	/** PURCHASE = PURCHASE */
	public static final String MD_CANDIDATE_BUSINESSCASE_PURCHASE = "PURCHASE";
	/** INVENTORY = INVENTORY */
	public static final String MD_CANDIDATE_BUSINESSCASE_INVENTORY = "INVENTORY";
	@Override
	public void setMD_Candidate_BusinessCase (java.lang.String MD_Candidate_BusinessCase)
	{

		set_Value (COLUMNNAME_MD_Candidate_BusinessCase, MD_Candidate_BusinessCase);
	}

	@Override
	public java.lang.String getMD_Candidate_BusinessCase() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_BusinessCase);
	}

	@Override
	public void setMD_Candidate_GroupId (int MD_Candidate_GroupId)
	{
		set_Value (COLUMNNAME_MD_Candidate_GroupId, Integer.valueOf(MD_Candidate_GroupId));
	}

	@Override
	public int getMD_Candidate_GroupId() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_GroupId);
	}

	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_Parent_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate_Parent(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate_Parent)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_Parent_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate_Parent);
	}

	@Override
	public void setMD_Candidate_Parent_ID (int MD_Candidate_Parent_ID)
	{
		if (MD_Candidate_Parent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, Integer.valueOf(MD_Candidate_Parent_ID));
	}

	@Override
	public int getMD_Candidate_Parent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Parent_ID);
	}

	/** 
	 * MD_Candidate_Status AD_Reference_ID=540715
	 * Reference name: MD_Candidate_Status
	 */
	public static final int MD_CANDIDATE_STATUS_AD_Reference_ID=540715;
	/** doc_created = doc_created */
	public static final String MD_CANDIDATE_STATUS_Doc_created = "doc_created";
	/** doc_planned = doc_planned */
	public static final String MD_CANDIDATE_STATUS_Doc_planned = "doc_planned";
	/** doc_completed = doc_completed */
	public static final String MD_CANDIDATE_STATUS_Doc_completed = "doc_completed";
	/** doc_closed = doc_closed */
	public static final String MD_CANDIDATE_STATUS_Doc_closed = "doc_closed";
	/** planned = planned */
	public static final String MD_CANDIDATE_STATUS_Planned = "planned";
	/** processed = processed */
	public static final String MD_CANDIDATE_STATUS_Processed = "processed";
	@Override
	public void setMD_Candidate_Status (java.lang.String MD_Candidate_Status)
	{

		set_Value (COLUMNNAME_MD_Candidate_Status, MD_Candidate_Status);
	}

	@Override
	public java.lang.String getMD_Candidate_Status() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_Status);
	}

	/** 
	 * MD_Candidate_Type AD_Reference_ID=540707
	 * Reference name: MD_Candidate_Type
	 */
	public static final int MD_CANDIDATE_TYPE_AD_Reference_ID=540707;
	/** STOCK = STOCK */
	public static final String MD_CANDIDATE_TYPE_STOCK = "STOCK";
	/** DEMAND = DEMAND */
	public static final String MD_CANDIDATE_TYPE_DEMAND = "DEMAND";
	/** SUPPLY = SUPPLY */
	public static final String MD_CANDIDATE_TYPE_SUPPLY = "SUPPLY";
	/** STOCK_UP = STOCK_UP */
	public static final String MD_CANDIDATE_TYPE_STOCK_UP = "STOCK_UP";
	/** UNEXPECTED_INCREASE = UNEXPECTED_INCREASE */
	public static final String MD_CANDIDATE_TYPE_UNEXPECTED_INCREASE = "UNEXPECTED_INCREASE";
	/** UNEXPECTED_DECREASE = UNEXPECTED_DECREASE */
	public static final String MD_CANDIDATE_TYPE_UNEXPECTED_DECREASE = "UNEXPECTED_DECREASE";
	/** INVENTORY_UP = INVENTORY_UP */
	public static final String MD_CANDIDATE_TYPE_INVENTORY_UP = "INVENTORY_UP";
	/** INVENTORY_DOWN = INVENTORY_DOWN */
	public static final String MD_CANDIDATE_TYPE_INVENTORY_DOWN = "INVENTORY_DOWN";
	/** ATTRIBUTES_CHANGED_FROM = ATTRIBUTES_CHANGED_FROM */
	public static final String MD_CANDIDATE_TYPE_ATTRIBUTES_CHANGED_FROM = "ATTRIBUTES_CHANGED_FROM";
	/** ATTRIBUTES_CHANGED_TO = ATTRIBUTES_CHANGED_TO */
	public static final String MD_CANDIDATE_TYPE_ATTRIBUTES_CHANGED_TO = "ATTRIBUTES_CHANGED_TO";
	@Override
	public void setMD_Candidate_Type (java.lang.String MD_Candidate_Type)
	{

		set_Value (COLUMNNAME_MD_Candidate_Type, MD_Candidate_Type);
	}

	@Override
	public java.lang.String getMD_Candidate_Type() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MD_Candidate_Type);
	}

	@Override
	public org.compiere.model.I_M_Forecast getM_Forecast()
	{
		return get_ValueAsPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class);
	}

	@Override
	public void setM_Forecast(org.compiere.model.I_M_Forecast M_Forecast)
	{
		set_ValueFromPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class, M_Forecast);
	}

	@Override
	public void setM_Forecast_ID (int M_Forecast_ID)
	{
		if (M_Forecast_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, Integer.valueOf(M_Forecast_ID));
	}

	@Override
	public int getM_Forecast_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Forecast_ID);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public java.math.BigDecimal getQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_AvailableToPromise (java.math.BigDecimal Qty_AvailableToPromise)
	{
		throw new IllegalArgumentException ("Qty_AvailableToPromise is virtual column");	}

	@Override
	public java.math.BigDecimal getQty_AvailableToPromise() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_AvailableToPromise);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyFulfilled (java.math.BigDecimal QtyFulfilled)
	{
		set_Value (COLUMNNAME_QtyFulfilled, QtyFulfilled);
	}

	@Override
	public java.math.BigDecimal getQtyFulfilled() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyFulfilled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_Planned_Display (java.math.BigDecimal Qty_Planned_Display)
	{
		throw new IllegalArgumentException ("Qty_Planned_Display is virtual column");	}

	@Override
	public java.math.BigDecimal getQty_Planned_Display() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Planned_Display);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReplenish_MaxQty (java.math.BigDecimal Replenish_MaxQty)
	{
		set_Value (COLUMNNAME_Replenish_MaxQty, Replenish_MaxQty);
	}

	@Override
	public java.math.BigDecimal getReplenish_MaxQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Replenish_MaxQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReplenish_MinQty (java.math.BigDecimal Replenish_MinQty)
	{
		set_Value (COLUMNNAME_Replenish_MinQty, Replenish_MinQty);
	}

	@Override
	public java.math.BigDecimal getReplenish_MinQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Replenish_MinQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public java.lang.String getStorageAttributesKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageAttributesKey);
	}
}