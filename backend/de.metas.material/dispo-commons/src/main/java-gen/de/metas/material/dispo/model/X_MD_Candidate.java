// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Candidate extends org.compiere.model.PO implements I_MD_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -782801888L;

    /** Standard Constructor */
    public X_MD_Candidate (final Properties ctx, final int MD_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(final org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID (final int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(final org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (final int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setDateProjected (final java.sql.Timestamp DateProjected)
	{
		set_Value (COLUMNNAME_DateProjected, DateProjected);
	}

	@Override
	public java.sql.Timestamp getDateProjected() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateProjected);
	}

	@Override
	public void setIsReservedForCustomer (final boolean IsReservedForCustomer)
	{
		set_Value (COLUMNNAME_IsReservedForCustomer, IsReservedForCustomer);
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
	public org.compiere.model.I_M_Forecast getM_Forecast()
	{
		return get_ValueAsPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class);
	}

	@Override
	public void setM_Forecast(final org.compiere.model.I_M_Forecast M_Forecast)
	{
		set_ValueFromPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class, M_Forecast);
	}

	@Override
	public void setM_Forecast_ID (final int M_Forecast_ID)
	{
		if (M_Forecast_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, M_Forecast_ID);
	}

	@Override
	public int getM_Forecast_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Forecast_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
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
	/** STOCK_CHANGE = STOCK_CHANGE */
	public static final String MD_CANDIDATE_BUSINESSCASE_STOCK_CHANGE = "STOCK_CHANGE";
	@Override
	public void setMD_Candidate_BusinessCase (final @Nullable String MD_Candidate_BusinessCase)
	{
		set_Value (COLUMNNAME_MD_Candidate_BusinessCase, MD_Candidate_BusinessCase);
	}

	@Override
	public String getMD_Candidate_BusinessCase()
	{
		return get_ValueAsString(COLUMNNAME_MD_Candidate_BusinessCase);
	}

	@Override
	public void setMD_Candidate_GroupId (final int MD_Candidate_GroupId)
	{
		set_Value (COLUMNNAME_MD_Candidate_GroupId, MD_Candidate_GroupId);
	}

	@Override
	public int getMD_Candidate_GroupId() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_GroupId);
	}

	@Override
	public void setMD_Candidate_ID (final int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public I_MD_Candidate getMD_Candidate_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_Parent_ID, I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate_Parent(final I_MD_Candidate MD_Candidate_Parent)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_Parent_ID, I_MD_Candidate.class, MD_Candidate_Parent);
	}

	@Override
	public void setMD_Candidate_Parent_ID (final int MD_Candidate_Parent_ID)
	{
		if (MD_Candidate_Parent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Parent_ID, MD_Candidate_Parent_ID);
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
	/** simulated = simulated */
	public static final String MD_CANDIDATE_STATUS_Simulated = "simulated";
	@Override
	public void setMD_Candidate_Status (final @Nullable String MD_Candidate_Status)
	{
		set_Value (COLUMNNAME_MD_Candidate_Status, MD_Candidate_Status);
	}

	@Override
	public String getMD_Candidate_Status()
	{
		return get_ValueAsString(COLUMNNAME_MD_Candidate_Status);
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
	public void setMD_Candidate_Type (final String MD_Candidate_Type)
	{
		set_Value (COLUMNNAME_MD_Candidate_Type, MD_Candidate_Type);
	}

	@Override
	public String getMD_Candidate_Type()
	{
		return get_ValueAsString(COLUMNNAME_MD_Candidate_Type);
	}

	@Override
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_AvailableToPromise (final @Nullable BigDecimal Qty_AvailableToPromise)
	{
		throw new IllegalArgumentException ("Qty_AvailableToPromise is virtual column");	}

	@Override
	public BigDecimal getQty_AvailableToPromise() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_AvailableToPromise);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_Display (final @Nullable BigDecimal Qty_Display)
	{
		throw new IllegalArgumentException ("Qty_Display is virtual column");	}

	@Override
	public BigDecimal getQty_Display() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Display);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_Planned_Display (final @Nullable BigDecimal Qty_Planned_Display)
	{
		throw new IllegalArgumentException ("Qty_Planned_Display is virtual column");	}

	@Override
	public BigDecimal getQty_Planned_Display() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Planned_Display);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyFulfilled (final @Nullable BigDecimal QtyFulfilled)
	{
		set_Value (COLUMNNAME_QtyFulfilled, QtyFulfilled);
	}

	@Override
	public BigDecimal getQtyFulfilled() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyFulfilled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyFulfilled_Display (final @Nullable BigDecimal QtyFulfilled_Display)
	{
		throw new IllegalArgumentException ("QtyFulfilled_Display is virtual column");	}

	@Override
	public BigDecimal getQtyFulfilled_Display() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyFulfilled_Display);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReplenish_MaxQty (final BigDecimal Replenish_MaxQty)
	{
		set_Value (COLUMNNAME_Replenish_MaxQty, Replenish_MaxQty);
	}

	@Override
	public BigDecimal getReplenish_MaxQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Replenish_MaxQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReplenish_MinQty (final BigDecimal Replenish_MinQty)
	{
		set_Value (COLUMNNAME_Replenish_MinQty, Replenish_MinQty);
	}

	@Override
	public BigDecimal getReplenish_MinQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Replenish_MinQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setStorageAttributesKey (final String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public String getStorageAttributesKey()
	{
		return get_ValueAsString(COLUMNNAME_StorageAttributesKey);
	}

	@Override
	public void setUserElementString1 (final @Nullable String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public String getUserElementString1()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public String getUserElementString2()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public String getUserElementString3()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public String getUserElementString4()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public String getUserElementString5()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public String getUserElementString6()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public String getUserElementString7()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}
}