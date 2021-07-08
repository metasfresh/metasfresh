/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule extends org.compiere.model.PO implements I_M_ShipmentSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 814957481L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule (Properties ctx, int M_ShipmentSchedule_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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
	public void setAllowConsolidateInOut (boolean AllowConsolidateInOut)
	{
		set_Value (COLUMNNAME_AllowConsolidateInOut, Boolean.valueOf(AllowConsolidateInOut));
	}

	@Override
	public boolean isAllowConsolidateInOut() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowConsolidateInOut);
	}

	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	@Override
	public int getBill_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, Integer.valueOf(C_BPartner_Vendor_ID));
	}

	@Override
	public int getC_BPartner_Vendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID);
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
	public void setC_Currency_ID (int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
		throw new IllegalArgumentException ("C_UOM_ID is virtual column");	}

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
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDeliveryDate_Effective (java.sql.Timestamp DeliveryDate_Effective)
	{
		throw new IllegalArgumentException ("DeliveryDate_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getDeliveryDate_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate_Effective);
	}

	@Override
	public void setDeliveryDate_Override (java.sql.Timestamp DeliveryDate_Override)
	{
		set_Value (COLUMNNAME_DeliveryDate_Override, DeliveryDate_Override);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate_Override() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate_Override);
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
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setIsDeliveryStop (boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, Boolean.valueOf(IsDeliveryStop));
	}

	@Override
	public boolean isDeliveryStop() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeliveryStop);
	}

	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	@Override
	public boolean isDropShip() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDropShip);
	}

	@Override
	public void setIsEdiDesadvRecipient (boolean IsEdiDesadvRecipient)
	{
		throw new IllegalArgumentException ("IsEdiDesadvRecipient is virtual column");	}

	@Override
	public boolean isEdiDesadvRecipient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiDesadvRecipient);
	}

	@Override
	public void setIsToRecompute (boolean IsToRecompute)
	{
		throw new IllegalArgumentException ("IsToRecompute is virtual column");	}

	@Override
	public boolean isToRecompute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToRecompute);
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
	public de.metas.inoutcandidate.model.I_M_Shipment_Constraint getM_Shipment_Constraint()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class);
	}

	@Override
	public void setM_Shipment_Constraint(de.metas.inoutcandidate.model.I_M_Shipment_Constraint M_Shipment_Constraint)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class, M_Shipment_Constraint);
	}

	@Override
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, Integer.valueOf(M_Shipment_Constraint_ID));
	}

	@Override
	public int getM_Shipment_Constraint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipment_Constraint_ID);
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
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Tour_ID (int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, Integer.valueOf(M_Tour_ID));
	}

	@Override
	public int getM_Tour_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Tour_ID);
	}

	@Override
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_Dest_ID is virtual column");	}

	@Override
	public int getM_Warehouse_Dest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Dest_ID);
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
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID)
	{
		if (M_Warehouse_Override_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, Integer.valueOf(M_Warehouse_Override_ID));
	}

	@Override
	public int getM_Warehouse_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Override_ID);
	}

	@Override
	public void setNrOfOLCandsWithSamePOReference (int NrOfOLCandsWithSamePOReference)
	{
		set_Value (COLUMNNAME_NrOfOLCandsWithSamePOReference, Integer.valueOf(NrOfOLCandsWithSamePOReference));
	}

	@Override
	public int getNrOfOLCandsWithSamePOReference() 
	{
		return get_ValueAsInt(COLUMNNAME_NrOfOLCandsWithSamePOReference);
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
			set_Value (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Order_ID, Integer.valueOf(PickFrom_Order_ID));
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
		set_Value (COLUMNNAME_PreparationDate, PreparationDate);
	}

	@Override
	public java.sql.Timestamp getPreparationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate);
	}

	@Override
	public void setPreparationDate_Effective (java.sql.Timestamp PreparationDate_Effective)
	{
		throw new IllegalArgumentException ("PreparationDate_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getPreparationDate_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate_Effective);
	}

	@Override
	public void setPreparationDate_Override (java.sql.Timestamp PreparationDate_Override)
	{
		set_Value (COLUMNNAME_PreparationDate_Override, PreparationDate_Override);
	}

	@Override
	public java.sql.Timestamp getPreparationDate_Override() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate_Override);
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
	public void setProcessing (boolean Processing)
	{
		throw new IllegalArgumentException ("Processing is virtual column");	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	/** 
	 * ProductDescription AD_Reference_ID=162
	 * Reference name: M_Product (no summary)
	 */
	public static final int PRODUCTDESCRIPTION_AD_Reference_ID=162;
	@Override
	public void setProductDescription (java.lang.String ProductDescription)
	{

		set_ValueNoCheck (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public java.math.BigDecimal getQtyDelivered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public java.math.BigDecimal getQtyOnHand() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
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
	public void setQtyOrdered_Calculated (java.math.BigDecimal QtyOrdered_Calculated)
	{
		set_Value (COLUMNNAME_QtyOrdered_Calculated, QtyOrdered_Calculated);
	}

	@Override
	public java.math.BigDecimal getQtyOrdered_Calculated() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_Calculated);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered_Override (java.math.BigDecimal QtyOrdered_Override)
	{
		set_Value (COLUMNNAME_QtyOrdered_Override, QtyOrdered_Override);
	}

	@Override
	public java.math.BigDecimal getQtyOrdered_Override() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickList (java.math.BigDecimal QtyPickList)
	{
		set_Value (COLUMNNAME_QtyPickList, QtyPickList);
	}

	@Override
	public java.math.BigDecimal getQtyPickList() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public java.math.BigDecimal getQtyReserved() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	@Override
	public java.math.BigDecimal getQtyToDeliver() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliverCatch_Override (java.math.BigDecimal QtyToDeliverCatch_Override)
	{
		set_Value (COLUMNNAME_QtyToDeliverCatch_Override, QtyToDeliverCatch_Override);
	}

	@Override
	public java.math.BigDecimal getQtyToDeliverCatch_Override() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliverCatch_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver_Override (java.math.BigDecimal QtyToDeliver_Override)
	{
		set_Value (COLUMNNAME_QtyToDeliver_Override, QtyToDeliver_Override);
	}

	@Override
	public java.math.BigDecimal getQtyToDeliver_Override() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver_OverrideFulfilled (java.math.BigDecimal QtyToDeliver_OverrideFulfilled)
	{
		set_Value (COLUMNNAME_QtyToDeliver_OverrideFulfilled, QtyToDeliver_OverrideFulfilled);
	}

	@Override
	public java.math.BigDecimal getQtyToDeliver_OverrideFulfilled() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver_OverrideFulfilled);
		return bd != null ? bd : BigDecimal.ZERO;
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

		set_Value (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setSinglePriceTag_ID (java.lang.String SinglePriceTag_ID)
	{
		set_ValueNoCheck (COLUMNNAME_SinglePriceTag_ID, SinglePriceTag_ID);
	}

	@Override
	public java.lang.String getSinglePriceTag_ID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SinglePriceTag_ID);
	}

	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}