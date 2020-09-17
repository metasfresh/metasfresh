/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Packageable_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Packageable_V extends org.compiere.model.PO implements I_M_Packageable_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -521904266L;

    /** Standard Constructor */
    public X_M_Packageable_V (Properties ctx, int M_Packageable_V_ID, String trxName)
    {
      super (ctx, M_Packageable_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Packageable_V (Properties ctx, ResultSet rs, String trxName)
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
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	@Override
	public java.lang.String getBPartnerAddress_Override() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress_Override);
	}

	@Override
	public void setBPartnerLocationName (java.lang.String BPartnerLocationName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerLocationName, BPartnerLocationName);
	}

	@Override
	public java.lang.String getBPartnerLocationName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerLocationName);
	}

	@Override
	public void setBPartnerName (java.lang.String BPartnerName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setBPartnerValue (java.lang.String BPartnerValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	@Override
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLineSO_ID, Integer.valueOf(C_OrderLineSO_ID));
	}

	@Override
	public int getC_OrderLineSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLineSO_ID);
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
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
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
	 * DocSubType AD_Reference_ID=148
	 * Reference name: C_DocType SubType
	 */
	public static final int DOCSUBTYPE_AD_Reference_ID=148;
	/** OnCreditOrder = WI */
	public static final String DOCSUBTYPE_OnCreditOrder = "WI";
	/** POSOrder = WR */
	public static final String DOCSUBTYPE_POSOrder = "WR";
	/** WarehouseOrder = WP */
	public static final String DOCSUBTYPE_WarehouseOrder = "WP";
	/** StandardOrder = SO */
	public static final String DOCSUBTYPE_StandardOrder = "SO";
	/** Proposal = ON */
	public static final String DOCSUBTYPE_Proposal = "ON";
	/** Quotation = OB */
	public static final String DOCSUBTYPE_Quotation = "OB";
	/** ReturnMaterial = RM */
	public static final String DOCSUBTYPE_ReturnMaterial = "RM";
	/** PrepayOrder = PR */
	public static final String DOCSUBTYPE_PrepayOrder = "PR";
	/** Provisionskorrektur = CC */
	public static final String DOCSUBTYPE_Provisionskorrektur = "CC";
	/** CommissionSettlement = CA */
	public static final String DOCSUBTYPE_CommissionSettlement = "CA";
	/** FlatFee = FF */
	public static final String DOCSUBTYPE_FlatFee = "FF";
	/** HoldingFee = HF */
	public static final String DOCSUBTYPE_HoldingFee = "HF";
	/** Subscription = SU */
	public static final String DOCSUBTYPE_Subscription = "SU";
	/** AQ = AQ */
	public static final String DOCSUBTYPE_AQ = "AQ";
	/** AP = AP */
	public static final String DOCSUBTYPE_AP = "AP";
	/** GS - Lieferdifferenz = CQ */
	public static final String DOCSUBTYPE_GS_Lieferdifferenz = "CQ";
	/** GS - Preisdifferenz = CR */
	public static final String DOCSUBTYPE_GS_Preisdifferenz = "CR";
	/** QualityInspection = QI */
	public static final String DOCSUBTYPE_QualityInspection = "QI";
	/** Leergutanlieferung = ER */
	public static final String DOCSUBTYPE_Leergutanlieferung = "ER";
	/** Produktanlieferung = MR */
	public static final String DOCSUBTYPE_Produktanlieferung = "MR";
	/** Produktauslieferung = MS */
	public static final String DOCSUBTYPE_Produktauslieferung = "MS";
	/** Leergutausgabe = ES */
	public static final String DOCSUBTYPE_Leergutausgabe = "ES";
	/** GS - Retoure = CS */
	public static final String DOCSUBTYPE_GS_Retoure = "CS";
	/** VendorInvoice = VI */
	public static final String DOCSUBTYPE_VendorInvoice = "VI";
	/** DownPayment = DP */
	public static final String DOCSUBTYPE_DownPayment = "DP";
	/** Saldokorektur = EC */
	public static final String DOCSUBTYPE_Saldokorektur = "EC";
	/** Internal Use Inventory = IUI */
	public static final String DOCSUBTYPE_InternalUseInventory = "IUI";
	/** Rückvergütungsrechnung = RI */
	public static final String DOCSUBTYPE_Rueckverguetungsrechnung = "RI";
	/** Rückvergütungsgutschrift = RC */
	public static final String DOCSUBTYPE_Rueckverguetungsgutschrift = "RC";
	/** Healthcare_CH-GM = GM */
	public static final String DOCSUBTYPE_Healthcare_CH_GM = "GM";
	/** Healthcare_CH-EA = EA */
	public static final String DOCSUBTYPE_Healthcare_CH_EA = "EA";
	/** Healthcare_CH-KV = KV */
	public static final String DOCSUBTYPE_Healthcare_CH_KV = "KV";
	/** Healthcare_CH-KT = KT */
	public static final String DOCSUBTYPE_Healthcare_CH_KT = "KT";
	/** AggregatedHUInventory = IAH */
	public static final String DOCSUBTYPE_AggregatedHUInventory = "IAH";
	/** SingleHUInventory = ISH */
	public static final String DOCSUBTYPE_SingleHUInventory = "ISH";
	/** NAR = NAR */
	public static final String DOCSUBTYPE_NAR = "NAR";
	/** Cashbook = CB */
	public static final String DOCSUBTYPE_Cashbook = "CB";
	/** Bankstatement = BS */
	public static final String DOCSUBTYPE_Bankstatement = "BS";
	/** Virtual inventory = VIY */
	public static final String DOCSUBTYPE_VirtualInventory = "VIY";
	@Override
	public void setDocSubType (java.lang.String DocSubType)
	{

		set_ValueNoCheck (COLUMNNAME_DocSubType, DocSubType);
	}

	@Override
	public java.lang.String getDocSubType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocSubType);
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	@Override
	public void setFreightCostRule (java.lang.String FreightCostRule)
	{

		set_ValueNoCheck (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	@Override
	public java.lang.String getFreightCostRule() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FreightCostRule);
	}

	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_ValueNoCheck (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public java.math.BigDecimal getLineNetAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLockedBy_User_ID (int LockedBy_User_ID)
	{
		if (LockedBy_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LockedBy_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LockedBy_User_ID, Integer.valueOf(LockedBy_User_ID));
	}

	@Override
	public int getLockedBy_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LockedBy_User_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
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
	public org.compiere.model.I_M_Warehouse_Type getM_Warehouse_Type()
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_Type_ID, org.compiere.model.I_M_Warehouse_Type.class);
	}

	@Override
	public void setM_Warehouse_Type(org.compiere.model.I_M_Warehouse_Type M_Warehouse_Type)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Type_ID, org.compiere.model.I_M_Warehouse_Type.class, M_Warehouse_Type);
	}

	@Override
	public void setM_Warehouse_Type_ID (int M_Warehouse_Type_ID)
	{
		if (M_Warehouse_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Type_ID, Integer.valueOf(M_Warehouse_Type_ID));
	}

	@Override
	public int getM_Warehouse_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Type_ID);
	}

	@Override
	public void setOrderDocumentNo (java.lang.String OrderDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_OrderDocumentNo, OrderDocumentNo);
	}

	@Override
	public java.lang.String getOrderDocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderDocumentNo);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPickFrom_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPickFrom_Order(org.eevolution.model.I_PP_Order PickFrom_Order)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class, PickFrom_Order);
	}

	@Override
	public void setPickFrom_Order_ID (int PickFrom_Order_ID)
	{
		if (PickFrom_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Order_ID, Integer.valueOf(PickFrom_Order_ID));
	}

	@Override
	public int getPickFrom_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Order_ID);
	}

	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference()
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	@Override
	public void setPreparationDate (java.sql.Timestamp PreparationDate)
	{
		set_ValueNoCheck (COLUMNNAME_PreparationDate, PreparationDate);
	}

	@Override
	public java.sql.Timestamp getPreparationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate);
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

	@Override
	public void setProductName (java.lang.String ProductName)
	{
		set_ValueNoCheck (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductName);
	}

	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public java.math.BigDecimal getQtyDelivered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
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
	public void setQtyPickedAndDelivered (java.math.BigDecimal QtyPickedAndDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedAndDelivered, QtyPickedAndDelivered);
	}

	@Override
	public java.math.BigDecimal getQtyPickedAndDelivered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedAndDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedNotDelivered (java.math.BigDecimal QtyPickedNotDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedNotDelivered, QtyPickedNotDelivered);
	}

	@Override
	public java.math.BigDecimal getQtyPickedNotDelivered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedNotDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedOrDelivered (java.math.BigDecimal QtyPickedOrDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedOrDelivered, QtyPickedOrDelivered);
	}

	@Override
	public java.math.BigDecimal getQtyPickedOrDelivered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedOrDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedPlanned (java.math.BigDecimal QtyPickedPlanned)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedPlanned, QtyPickedPlanned);
	}

	@Override
	public java.math.BigDecimal getQtyPickedPlanned() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedPlanned);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	@Override
	public java.math.BigDecimal getQtyToDeliver() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ShipmentAllocation_BestBefore_Policy AD_Reference_ID=541043
	 * Reference name: ShipmentAllocation_BestBefore_Policy
	 */
	public static final int SHIPMENTALLOCATION_BESTBEFORE_POLICY_AD_Reference_ID=541043;
	/** Newest_First = N */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Newest_First = "N";
	/** Expiring_First = E */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Expiring_First = "E";
	@Override
	public void setShipmentAllocation_BestBefore_Policy (java.lang.String ShipmentAllocation_BestBefore_Policy)
	{

		set_ValueNoCheck (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setShipperName (java.lang.String ShipperName)
	{
		set_ValueNoCheck (COLUMNNAME_ShipperName, ShipperName);
	}

	@Override
	public java.lang.String getShipperName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipperName);
	}

	@Override
	public void setWarehouseName (java.lang.String WarehouseName)
	{
		set_ValueNoCheck (COLUMNNAME_WarehouseName, WarehouseName);
	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseName);
	}
}