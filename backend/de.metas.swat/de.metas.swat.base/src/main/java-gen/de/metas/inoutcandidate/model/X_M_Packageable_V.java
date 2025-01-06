// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Packageable_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Packageable_V extends org.compiere.model.PO implements I_M_Packageable_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 476393024L;

    /** Standard Constructor */
    public X_M_Packageable_V (final Properties ctx, final int M_Packageable_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_Packageable_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Packageable_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerAddress_Override (final @Nullable java.lang.String BPartnerAddress_Override)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	@Override
	public java.lang.String getBPartnerAddress_Override() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress_Override);
	}

	@Override
	public void setBPartnerLocationName (final @Nullable java.lang.String BPartnerLocationName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerLocationName, BPartnerLocationName);
	}

	@Override
	public java.lang.String getBPartnerLocationName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerLocationName);
	}

	@Override
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setBPartnerValue (final @Nullable java.lang.String BPartnerValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setCatch_UOM_ID (final int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Catch_UOM_ID, Catch_UOM_ID);
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
	}

	@Override
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
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
	public void setC_OrderLineSO(final org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	@Override
	public void setC_OrderLineSO_ID (final int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLineSO_ID, C_OrderLineSO_ID);
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

	@Override
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDeliveryDate (final @Nullable java.sql.Timestamp DeliveryDate)
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
	/** Normalpost = NP */
	public static final String DELIVERYVIARULE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String DELIVERYVIARULE_Luftpost = "LU";
	@Override
	public void setDeliveryViaRule (final @Nullable java.lang.String DeliveryViaRule)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
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
	/** SR = SR */
	public static final String DOCSUBTYPE_SR = "SR";
	/** Requisition = REQ */
	public static final String DOCSUBTYPE_Requisition = "REQ";
	/** Frame Agrement = FA */
	public static final String DOCSUBTYPE_FrameAgrement = "FA";
	/** Order Call = OC */
	public static final String DOCSUBTYPE_OrderCall = "OC";
	/** Mediated = MED */
	public static final String DOCSUBTYPE_Mediated = "MED";
	/** RD = RD */
	public static final String DOCSUBTYPE_RD = "RD";
	/** Cost Estimate = CE */
	public static final String DOCSUBTYPE_CostEstimate = "CE";
	/** Kreditoren Nachbelastung = NBK */
	public static final String DOCSUBTYPE_KreditorenNachbelastung = "NBK";
	/** LS = LS */
	public static final String DOCSUBTYPE_LS = "LS";
	/** Payment service provider invoice = SI */
	public static final String DOCSUBTYPE_PaymentServiceProviderInvoice = "SI";
	/** CallOrder = CAO */
	public static final String DOCSUBTYPE_CallOrder = "CAO";
	@Override
	public void setDocSubType (final @Nullable java.lang.String DocSubType)
	{
		set_ValueNoCheck (COLUMNNAME_DocSubType, DocSubType);
	}

	@Override
	public java.lang.String getDocSubType() 
	{
		return get_ValueAsString(COLUMNNAME_DocSubType);
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
	public void setFreightCostRule (final @Nullable java.lang.String FreightCostRule)
	{
		set_ValueNoCheck (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	@Override
	public java.lang.String getFreightCostRule() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCostRule);
	}

	@Override
	public void setHandOver_Location_ID (final int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HandOver_Location_ID, HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public void setHandOver_Partner_ID (final int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HandOver_Partner_ID, HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
	}

	@Override
	public void setIsCatchWeight (final boolean IsCatchWeight)
	{
		set_ValueNoCheck (COLUMNNAME_IsCatchWeight, IsCatchWeight);
	}

	@Override
	public boolean isCatchWeight() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCatchWeight);
	}

	@Override
	public void setIsDisplayed (final boolean IsDisplayed)
	{
		set_ValueNoCheck (COLUMNNAME_IsDisplayed, IsDisplayed);
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setLineNetAmt (final @Nullable BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLockedBy_User_ID (final int LockedBy_User_ID)
	{
		if (LockedBy_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LockedBy_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LockedBy_User_ID, LockedBy_User_ID);
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
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
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
	public void setM_Warehouse_Type(final org.compiere.model.I_M_Warehouse_Type M_Warehouse_Type)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Type_ID, org.compiere.model.I_M_Warehouse_Type.class, M_Warehouse_Type);
	}

	@Override
	public void setM_Warehouse_Type_ID (final int M_Warehouse_Type_ID)
	{
		if (M_Warehouse_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Type_ID, M_Warehouse_Type_ID);
	}

	@Override
	public int getM_Warehouse_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Type_ID);
	}

	@Override
	public void setOrderDocumentNo (final @Nullable java.lang.String OrderDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_OrderDocumentNo, OrderDocumentNo);
	}

	@Override
	public java.lang.String getOrderDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_OrderDocumentNo);
	}

	@Override
	public void setPackTo_HU_PI_Item_Product_ID (final int PackTo_HU_PI_Item_Product_ID)
	{
		if (PackTo_HU_PI_Item_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PackTo_HU_PI_Item_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PackTo_HU_PI_Item_Product_ID, PackTo_HU_PI_Item_Product_ID);
	}

	@Override
	public int getPackTo_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PackTo_HU_PI_Item_Product_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPickFrom_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPickFrom_Order(final org.eevolution.model.I_PP_Order PickFrom_Order)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class, PickFrom_Order);
	}

	@Override
	public void setPickFrom_Order_ID (final int PickFrom_Order_ID)
	{
		if (PickFrom_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_Order_ID, PickFrom_Order_ID);
	}

	@Override
	public int getPickFrom_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Order_ID);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setPreparationDate (final @Nullable java.sql.Timestamp PreparationDate)
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
	public void setPriorityRule (final @Nullable java.lang.String PriorityRule)
	{
		set_ValueNoCheck (COLUMNNAME_PriorityRule, PriorityRule);
	}

	@Override
	public java.lang.String getPriorityRule() 
	{
		return get_ValueAsString(COLUMNNAME_PriorityRule);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_ValueNoCheck (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final @Nullable BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedAndDelivered (final @Nullable BigDecimal QtyPickedAndDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedAndDelivered, QtyPickedAndDelivered);
	}

	@Override
	public BigDecimal getQtyPickedAndDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedAndDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedNotDelivered (final @Nullable BigDecimal QtyPickedNotDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedNotDelivered, QtyPickedNotDelivered);
	}

	@Override
	public BigDecimal getQtyPickedNotDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedNotDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedOrDelivered (final @Nullable BigDecimal QtyPickedOrDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedOrDelivered, QtyPickedOrDelivered);
	}

	@Override
	public BigDecimal getQtyPickedOrDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedOrDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedPlanned (final @Nullable BigDecimal QtyPickedPlanned)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPickedPlanned, QtyPickedPlanned);
	}

	@Override
	public BigDecimal getQtyPickedPlanned() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedPlanned);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver (final @Nullable BigDecimal QtyToDeliver)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	@Override
	public BigDecimal getQtyToDeliver() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSetup_Place_No (final int Setup_Place_No)
	{
		set_ValueNoCheck (COLUMNNAME_Setup_Place_No, Setup_Place_No);
	}

	@Override
	public int getSetup_Place_No() 
	{
		return get_ValueAsInt(COLUMNNAME_Setup_Place_No);
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
	public void setShipmentAllocation_BestBefore_Policy (final @Nullable java.lang.String ShipmentAllocation_BestBefore_Policy)
	{
		set_ValueNoCheck (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return get_ValueAsString(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setShipperName (final @Nullable java.lang.String ShipperName)
	{
		set_ValueNoCheck (COLUMNNAME_ShipperName, ShipperName);
	}

	@Override
	public java.lang.String getShipperName() 
	{
		return get_ValueAsString(COLUMNNAME_ShipperName);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		set_ValueNoCheck (COLUMNNAME_WarehouseName, WarehouseName);
	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}
}