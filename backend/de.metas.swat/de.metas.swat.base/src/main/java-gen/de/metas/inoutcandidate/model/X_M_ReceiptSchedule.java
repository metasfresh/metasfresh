/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ReceiptSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ReceiptSchedule extends org.compiere.model.PO implements I_M_ReceiptSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 379829186L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule (Properties ctx, int M_ReceiptSchedule_ID, String trxName)
    {
      super (ctx, M_ReceiptSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_Override_ID (int AD_User_Override_ID)
	{
		if (AD_User_Override_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Override_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Override_ID, Integer.valueOf(AD_User_Override_ID));
	}

	@Override
	public int getAD_User_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Override_ID);
	}

	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
	}

	@Override
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	@Override
	public java.lang.String getBPartnerAddress_Override() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress_Override);
	}

	@Override
	public void setCanBeExportedFrom (java.sql.Timestamp CanBeExportedFrom)
	{
		set_Value (COLUMNNAME_CanBeExportedFrom, CanBeExportedFrom);
	}

	@Override
	public java.sql.Timestamp getCanBeExportedFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CanBeExportedFrom);
	}

	@Override
	public void setCatch_UOM_ID (int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Integer.valueOf(Catch_UOM_ID));
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID)
	{
		if (C_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, Integer.valueOf(C_BPartner_Override_ID));
	}

	@Override
	public int getC_BPartner_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Override_ID);
	}

	@Override
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID)
	{
		if (C_BP_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, Integer.valueOf(C_BP_Location_Override_ID));
	}

	@Override
	public int getC_BP_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Location_Override_ID);
	}

	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_ValueNoCheck (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryRule_Override AD_Reference_ID=540009
	 * Reference name: M_ShipmentSchedule DeliveryRule
	 */
	public static final int DELIVERYRULE_OVERRIDE_AD_Reference_ID=540009;
	/** Verfügbarkeit = A */
	public static final String DELIVERYRULE_OVERRIDE_Verfuegbarkeit = "A";
	/** Erzwungen = F */
	public static final String DELIVERYRULE_OVERRIDE_Erzwungen = "F";
	/** Position komplett = L */
	public static final String DELIVERYRULE_OVERRIDE_PositionKomplett = "L";
	/** Manuell = M */
	public static final String DELIVERYRULE_OVERRIDE_Manuell = "M";
	/** After Receipt = R */
	public static final String DELIVERYRULE_OVERRIDE_AfterReceipt = "R";
	/** Mit nächster Abolieferung = S */
	public static final String DELIVERYRULE_OVERRIDE_MitNaechsterAbolieferung = "S";
	@Override
	public void setDeliveryRule_Override (java.lang.String DeliveryRule_Override)
	{

		set_Value (COLUMNNAME_DeliveryRule_Override, DeliveryRule_Override);
	}

	@Override
	public java.lang.String getDeliveryRule_Override() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule_Override);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
	}

	/** 
	 * DeliveryViaRule_Override AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_OVERRIDE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_OVERRIDE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_OVERRIDE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_OVERRIDE_Shipper = "S";
	@Override
	public void setDeliveryViaRule_Override (java.lang.String DeliveryViaRule_Override)
	{

		set_Value (COLUMNNAME_DeliveryViaRule_Override, DeliveryViaRule_Override);
	}

	@Override
	public java.lang.String getDeliveryViaRule_Override() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule_Override);
	}

	/** 
	 * ExportStatus AD_Reference_ID=541161
	 * Reference name: API_ExportStatus
	 */
	public static final int EXPORTSTATUS_AD_Reference_ID=541161;
	/** PENDING = PENDING */
	public static final String EXPORTSTATUS_PENDING = "PENDING";
	/** EXPORTED = EXPORTED */
	public static final String EXPORTSTATUS_EXPORTED = "EXPORTED";
	/** EXPORTED_AND_FORWARDED = EXPORTED_AND_FORWARDED */
	public static final String EXPORTSTATUS_EXPORTED_AND_FORWARDED = "EXPORTED_AND_FORWARDED";
	/** EXPORTED_FORWARD_ERROR = EXPORTED_FORWARD_ERROR */
	public static final String EXPORTSTATUS_EXPORTED_FORWARD_ERROR = "EXPORTED_FORWARD_ERROR";
	/** EXPORT_ERROR = EXPORT_ERROR */
	public static final String EXPORTSTATUS_EXPORT_ERROR = "EXPORT_ERROR";
	/** DONT_EXPORT = DONT_EXPORT */
	public static final String EXPORTSTATUS_DONT_EXPORT = "DONT_EXPORT";
	@Override
	public void setExportStatus (java.lang.String ExportStatus)
	{

		set_Value (COLUMNNAME_ExportStatus, ExportStatus);
	}

	@Override
	public java.lang.String getExportStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportStatus);
	}

	@Override
	public void setFilteredItemsWithSameC_Order_ID (int FilteredItemsWithSameC_Order_ID)
	{
		throw new IllegalArgumentException ("FilteredItemsWithSameC_Order_ID is virtual column");	}

	@Override
	public int getFilteredItemsWithSameC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_FilteredItemsWithSameC_Order_ID);
	}

	@Override
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	@Override
	public java.lang.String getHeaderAggregationKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey);
	}

	@Override
	public void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_IsBPartnerAddress_Override, Boolean.valueOf(IsBPartnerAddress_Override));
	}

	@Override
	public boolean isBPartnerAddress_Override() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBPartnerAddress_Override);
	}

	@Override
	public void setIsPackagingMaterial (boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, Boolean.valueOf(IsPackagingMaterial));
	}

	@Override
	public boolean isPackagingMaterial() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPackagingMaterial);
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

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	@Override
	public int getM_IolCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_ID);
	}

	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
	}

	@Override
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, Integer.valueOf(M_Warehouse_Dest_ID));
	}

	@Override
	public int getM_Warehouse_Dest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Dest_ID);
	}

	@Override
	public void setM_Warehouse_Effective_ID (int M_Warehouse_Effective_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_Effective_ID is virtual column");	}

	@Override
	public int getM_Warehouse_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Effective_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID)
	{
		if (M_Warehouse_Override_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, Integer.valueOf(M_Warehouse_Override_ID));
	}

	@Override
	public int getM_Warehouse_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Override_ID);
	}

	/** 
	 * OnMaterialReceiptWithDestWarehouse AD_Reference_ID=540835
	 * Reference name: OnMaterialReceiptWithDestWarehouse_List
	 */
	public static final int ONMATERIALRECEIPTWITHDESTWAREHOUSE_AD_Reference_ID=540835;
	/** CreateMovement = M */
	public static final String ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement = "M";
	/** Create Distribution Order = D */
	public static final String ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder = "D";
	@Override
	public void setOnMaterialReceiptWithDestWarehouse (java.lang.String OnMaterialReceiptWithDestWarehouse)
	{

		set_Value (COLUMNNAME_OnMaterialReceiptWithDestWarehouse, OnMaterialReceiptWithDestWarehouse);
	}

	@Override
	public java.lang.String getOnMaterialReceiptWithDestWarehouse() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OnMaterialReceiptWithDestWarehouse);
	}

	/** 
	 * PriorityRule AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	@Override
	public void setPriorityRule (java.lang.String PriorityRule)
	{

		set_ValueNoCheck (COLUMNNAME_PriorityRule, PriorityRule);
	}

	@Override
	public java.lang.String getPriorityRule() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule);
	}

	/** 
	 * PriorityRule_Override AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_OVERRIDE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_OVERRIDE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_OVERRIDE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_OVERRIDE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_OVERRIDE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_OVERRIDE_Minor = "9";
	@Override
	public void setPriorityRule_Override (java.lang.String PriorityRule_Override)
	{

		set_Value (COLUMNNAME_PriorityRule_Override, PriorityRule_Override);
	}

	@Override
	public java.lang.String getPriorityRule_Override() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule_Override);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQtyMoved (java.math.BigDecimal QtyMoved)
	{
		set_Value (COLUMNNAME_QtyMoved, QtyMoved);
	}

	@Override
	public java.math.BigDecimal getQtyMoved() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMoved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedInCatchUOM (java.math.BigDecimal QtyMovedInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyMovedInCatchUOM, QtyMovedInCatchUOM);
	}

	@Override
	public java.math.BigDecimal getQtyMovedInCatchUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedInCatchUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedWithIssues (java.math.BigDecimal QtyMovedWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMovedWithIssues, QtyMovedWithIssues);
	}

	@Override
	public java.math.BigDecimal getQtyMovedWithIssues() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedWithIssues);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedWithIssuesInCatchUOM (java.math.BigDecimal QtyMovedWithIssuesInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyMovedWithIssuesInCatchUOM, QtyMovedWithIssuesInCatchUOM);
	}

	@Override
	public java.math.BigDecimal getQtyMovedWithIssuesInCatchUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedWithIssuesInCatchUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public java.math.BigDecimal getQtyOrdered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder)
	{
		set_Value (COLUMNNAME_QtyOrderedOverUnder, QtyOrderedOverUnder);
	}

	@Override
	public java.math.BigDecimal getQtyOrderedOverUnder() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrderedOverUnder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrderedTU (java.math.BigDecimal QtyOrderedTU)
	{
		throw new IllegalArgumentException ("QtyOrderedTU is virtual column");	}

	@Override
	public java.math.BigDecimal getQtyOrderedTU() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrderedTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove (java.math.BigDecimal QtyToMove)
	{
		set_Value (COLUMNNAME_QtyToMove, QtyToMove);
	}

	@Override
	public java.math.BigDecimal getQtyToMove() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove_Override (java.math.BigDecimal QtyToMove_Override)
	{
		set_Value (COLUMNNAME_QtyToMove_Override, QtyToMove_Override);
	}

	@Override
	public java.math.BigDecimal getQtyToMove_Override() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent, QualityDiscountPercent);
	}

	@Override
	public java.math.BigDecimal getQualityDiscountPercent() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityNote (java.lang.String QualityNote)
	{
		set_Value (COLUMNNAME_QualityNote, QualityNote);
	}

	@Override
	public java.lang.String getQualityNote() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityNote);
	}

	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setStatus (java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	@Override
	public void setExternalResourceURL (java.lang.String ExternalResourceURL)
	{
		set_Value (COLUMNNAME_ExternalResourceURL, ExternalResourceURL);
	}

	@Override
	public java.lang.String getExternalResourceURL()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalResourceURL);
	}

}
