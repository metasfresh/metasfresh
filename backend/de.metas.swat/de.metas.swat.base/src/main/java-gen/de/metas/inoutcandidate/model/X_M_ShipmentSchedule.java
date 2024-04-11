// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ShipmentSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipmentSchedule extends org.compiere.model.PO implements I_M_ShipmentSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1043342538L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule (final Properties ctx, final int M_ShipmentSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipmentSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_InputDataSource_ID (final int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, AD_InputDataSource_ID);
	}

	@Override
	public int getAD_InputDataSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_InputDataSource_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_Override_ID (final int AD_User_Override_ID)
	{
		if (AD_User_Override_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Override_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Override_ID, AD_User_Override_ID);
	}

	@Override
	public int getAD_User_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Override_ID);
	}

	@Override
	public void setAllowConsolidateInOut (final boolean AllowConsolidateInOut)
	{
		set_Value (COLUMNNAME_AllowConsolidateInOut, AllowConsolidateInOut);
	}

	@Override
	public boolean isAllowConsolidateInOut() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowConsolidateInOut);
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getBill_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setBill_Location_Value(final org.compiere.model.I_C_Location Bill_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class, Bill_Location_Value);
	}

	@Override
	public void setBill_Location_Value_ID (final int Bill_Location_Value_ID)
	{
		if (Bill_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, Bill_Location_Value_ID);
	}

	@Override
	public int getBill_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_Value_ID);
	}

	@Override
	public void setBill_User_ID (final int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Bill_User_ID);
	}

	@Override
	public int getBill_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
	}

	@Override
	public void setBlockedBPartner (final boolean BlockedBPartner)
	{
		throw new IllegalArgumentException ("BlockedBPartner is virtual column");	}

	@Override
	public boolean isBlockedBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_BlockedBPartner);
	}

	@Override
	public void setBPartnerAddress (final java.lang.String BPartnerAddress)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress);
	}

	@Override
	public void setBPartnerAddress_Override (final @Nullable java.lang.String BPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	@Override
	public java.lang.String getBPartnerAddress_Override() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress_Override);
	}

	@Override
	public void setCanBeExportedFrom (final @Nullable java.sql.Timestamp CanBeExportedFrom)
	{
		set_Value (COLUMNNAME_CanBeExportedFrom, CanBeExportedFrom);
	}

	@Override
	public java.sql.Timestamp getCanBeExportedFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CanBeExportedFrom);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setCatch_UOM_ID (final int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Catch_UOM_ID);
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
	}

	@Override
	public void setC_BPartner2_ID (final int C_BPartner2_ID)
	{
		if (C_BPartner2_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner2_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner2_ID, C_BPartner2_ID);
	}

	@Override
	public int getC_BPartner2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner2_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	public org.compiere.model.I_C_Location getC_BPartner_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_BPartner_Location_Value(final org.compiere.model.I_C_Location C_BPartner_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_Value_ID, org.compiere.model.I_C_Location.class, C_BPartner_Location_Value);
	}

	@Override
	public void setC_BPartner_Location_Value_ID (final int C_BPartner_Location_Value_ID)
	{
		if (C_BPartner_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Value_ID, C_BPartner_Location_Value_ID);
	}

	@Override
	public int getC_BPartner_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Value_ID);
	}

	@Override
	public void setC_BPartner_Override_ID (final int C_BPartner_Override_ID)
	{
		if (C_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, C_BPartner_Override_ID);
	}

	@Override
	public int getC_BPartner_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Override_ID);
	}

	@Override
	public void setC_BPartner_Vendor_ID (final int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, C_BPartner_Vendor_ID);
	}

	@Override
	public int getC_BPartner_Vendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID);
	}

	@Override
	public void setC_BP_Location_Override_ID (final int C_BP_Location_Override_ID)
	{
		if (C_BP_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, C_BP_Location_Override_ID);
	}

	@Override
	public int getC_BP_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Location_Override_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_BP_Location_Override_Value()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Location_Override_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_BP_Location_Override_Value(final org.compiere.model.I_C_Location C_BP_Location_Override_Value)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Location_Override_Value_ID, org.compiere.model.I_C_Location.class, C_BP_Location_Override_Value);
	}

	@Override
	public void setC_BP_Location_Override_Value_ID (final int C_BP_Location_Override_Value_ID)
	{
		if (C_BP_Location_Override_Value_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_Value_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_Value_ID, C_BP_Location_Override_Value_ID);
	}

	@Override
	public int getC_BP_Location_Override_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Location_Override_Value_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
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
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		throw new IllegalArgumentException ("C_UOM_ID is virtual column");	}

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
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDeliveryDate_Effective (final @Nullable java.sql.Timestamp DeliveryDate_Effective)
	{
		throw new IllegalArgumentException ("DeliveryDate_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getDeliveryDate_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate_Effective);
	}

	@Override
	public void setDeliveryDate_Override (final @Nullable java.sql.Timestamp DeliveryDate_Override)
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
	public void setDeliveryRule (final java.lang.String DeliveryRule)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule);
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
	public void setDeliveryRule_Override (final @Nullable java.lang.String DeliveryRule_Override)
	{
		set_Value (COLUMNNAME_DeliveryRule_Override, DeliveryRule_Override);
	}

	@Override
	public java.lang.String getDeliveryRule_Override() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule_Override);
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
	public void setDeliveryViaRule (final java.lang.String DeliveryViaRule)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
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
	/** Normalpost = NP */
	public static final String DELIVERYVIARULE_OVERRIDE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String DELIVERYVIARULE_OVERRIDE_Luftpost = "LU";
	@Override
	public void setDeliveryViaRule_Override (final @Nullable java.lang.String DeliveryViaRule_Override)
	{
		set_Value (COLUMNNAME_DeliveryViaRule_Override, DeliveryViaRule_Override);
	}

	@Override
	public java.lang.String getDeliveryViaRule_Override() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule_Override);
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
	/** InterimInvoice = II */
	public static final String DOCSUBTYPE_InterimInvoice = "II";
	/** Withholding = WH */
	public static final String DOCSUBTYPE_Withholding = "WH";
	/** InternalVendorInvoice = IVI */
	public static final String DOCSUBTYPE_InternalVendorInvoice = "IVI";
	/** Delivery Instruction = DI */
	public static final String DOCSUBTYPE_DeliveryInstruction = "DI";
	/** InventoryShortageDocument = ISD */
	public static final String DOCSUBTYPE_InventoryShortageDocument = "ISD";
	/** InventoryOverageDocument = IOD */
	public static final String DOCSUBTYPE_InventoryOverageDocument = "IOD";
	/** CorrectionInvoice = CI */
	public static final String DOCSUBTYPE_CorrectionInvoice = "CI";
	/** Provision = PRV */
	public static final String DOCSUBTYPE_Provision = "PRV";
	/** ProFormaSO = PF */
	public static final String DOCSUBTYPE_ProFormaSO = "PF";
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
	public void setExportStatus (final java.lang.String ExportStatus)
	{
		set_Value (COLUMNNAME_ExportStatus, ExportStatus);
	}

	@Override
	public java.lang.String getExportStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ExportStatus);
	}

	@Override
	public void setHeaderAggregationKey (final @Nullable java.lang.String HeaderAggregationKey)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	@Override
	public java.lang.String getHeaderAggregationKey() 
	{
		return get_ValueAsString(COLUMNNAME_HeaderAggregationKey);
	}

	@Override
	public void setIsBPartnerAddress_Override (final boolean IsBPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_IsBPartnerAddress_Override, IsBPartnerAddress_Override);
	}

	@Override
	public boolean isBPartnerAddress_Override() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBPartnerAddress_Override);
	}

	@Override
	public void setIsCatchWeight (final boolean IsCatchWeight)
	{
		set_Value (COLUMNNAME_IsCatchWeight, IsCatchWeight);
	}

	@Override
	public boolean isCatchWeight() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCatchWeight);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setIsDeliveryStop (final boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, IsDeliveryStop);
	}

	@Override
	public boolean isDeliveryStop() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeliveryStop);
	}

	@Override
	public void setIsDisplayed (final boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, IsDisplayed);
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setIsDropShip (final boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, IsDropShip);
	}

	@Override
	public boolean isDropShip() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDropShip);
	}

	@Override
	public void setIsEdiDesadvRecipient (final boolean IsEdiDesadvRecipient)
	{
		throw new IllegalArgumentException ("IsEdiDesadvRecipient is virtual column");	}

	@Override
	public boolean isEdiDesadvRecipient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiDesadvRecipient);
	}

	@Override
	public void setIsToRecompute (final boolean IsToRecompute)
	{
		throw new IllegalArgumentException ("IsToRecompute is virtual column");	}

	@Override
	public boolean isToRecompute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToRecompute);
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
	public void setM_HU_PI_Item_Product_Calculated_ID (final int M_HU_PI_Item_Product_Calculated_ID)
	{
		if (M_HU_PI_Item_Product_Calculated_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID, M_HU_PI_Item_Product_Calculated_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_Calculated_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_Override_ID (final int M_HU_PI_Item_Product_Override_ID)
	{
		if (M_HU_PI_Item_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, M_HU_PI_Item_Product_Override_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_Override_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(final de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	@Override
	public void setM_IolCandHandler_ID (final int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, M_IolCandHandler_ID);
	}

	@Override
	public int getM_IolCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_ID);
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
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_Shipment_Constraint getM_Shipment_Constraint()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class);
	}

	@Override
	public void setM_Shipment_Constraint(final de.metas.inoutcandidate.model.I_M_Shipment_Constraint M_Shipment_Constraint)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class, M_Shipment_Constraint);
	}

	@Override
	public void setM_Shipment_Constraint_ID (final int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, M_Shipment_Constraint_ID);
	}

	@Override
	public int getM_Shipment_Constraint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipment_Constraint_ID);
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
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Tour_ID (final int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, M_Tour_ID);
	}

	@Override
	public int getM_Tour_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Tour_ID);
	}

	@Override
	public void setM_Warehouse_Dest_ID (final int M_Warehouse_Dest_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_Dest_ID is virtual column");	}

	@Override
	public int getM_Warehouse_Dest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Dest_ID);
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

	@Override
	public void setM_Warehouse_Override_ID (final int M_Warehouse_Override_ID)
	{
		if (M_Warehouse_Override_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, M_Warehouse_Override_ID);
	}

	@Override
	public int getM_Warehouse_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Override_ID);
	}

	@Override
	public void setNrOfOLCandsWithSamePOReference (final int NrOfOLCandsWithSamePOReference)
	{
		set_Value (COLUMNNAME_NrOfOLCandsWithSamePOReference, NrOfOLCandsWithSamePOReference);
	}

	@Override
	public int getNrOfOLCandsWithSamePOReference() 
	{
		return get_ValueAsInt(COLUMNNAME_NrOfOLCandsWithSamePOReference);
	}

	@Override
	public void setPhysicalClearanceDate (final @Nullable java.sql.Timestamp PhysicalClearanceDate)
	{
		set_Value (COLUMNNAME_PhysicalClearanceDate, PhysicalClearanceDate);
	}

	@Override
	public java.sql.Timestamp getPhysicalClearanceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PhysicalClearanceDate);
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
			set_Value (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Order_ID, PickFrom_Order_ID);
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
		set_Value (COLUMNNAME_PreparationDate, PreparationDate);
	}

	@Override
	public java.sql.Timestamp getPreparationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate);
	}

	@Override
	public void setPreparationDate_Effective (final @Nullable java.sql.Timestamp PreparationDate_Effective)
	{
		throw new IllegalArgumentException ("PreparationDate_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getPreparationDate_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationDate_Effective);
	}

	@Override
	public void setPreparationDate_Override (final @Nullable java.sql.Timestamp PreparationDate_Override)
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
	public void setPriorityRule (final @Nullable java.lang.String PriorityRule)
	{
		set_ValueNoCheck (COLUMNNAME_PriorityRule, PriorityRule);
	}

	@Override
	public java.lang.String getPriorityRule() 
	{
		return get_ValueAsString(COLUMNNAME_PriorityRule);
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
	public void setPriorityRule_Override (final @Nullable java.lang.String PriorityRule_Override)
	{
		set_Value (COLUMNNAME_PriorityRule_Override, PriorityRule_Override);
	}

	@Override
	public java.lang.String getPriorityRule_Override() 
	{
		return get_ValueAsString(COLUMNNAME_PriorityRule_Override);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
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
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_ValueNoCheck (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
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
	public void setQtyOrdered_Calculated (final @Nullable BigDecimal QtyOrdered_Calculated)
	{
		set_Value (COLUMNNAME_QtyOrdered_Calculated, QtyOrdered_Calculated);
	}

	@Override
	public BigDecimal getQtyOrdered_Calculated() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_Calculated);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered_Override (final @Nullable BigDecimal QtyOrdered_Override)
	{
		set_Value (COLUMNNAME_QtyOrdered_Override, QtyOrdered_Override);
	}

	@Override
	public BigDecimal getQtyOrdered_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered_TU (final BigDecimal QtyOrdered_TU)
	{
		set_Value (COLUMNNAME_QtyOrdered_TU, QtyOrdered_TU);
	}

	@Override
	public BigDecimal getQtyOrdered_TU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_TU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickList (final @Nullable BigDecimal QtyPickList)
	{
		set_Value (COLUMNNAME_QtyPickList, QtyPickList);
	}

	@Override
	public BigDecimal getQtyPickList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver (final @Nullable BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	@Override
	public BigDecimal getQtyToDeliver() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliverCatch_Override (final @Nullable BigDecimal QtyToDeliverCatch_Override)
	{
		set_Value (COLUMNNAME_QtyToDeliverCatch_Override, QtyToDeliverCatch_Override);
	}

	@Override
	public BigDecimal getQtyToDeliverCatch_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliverCatch_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver_Override (final @Nullable BigDecimal QtyToDeliver_Override)
	{
		set_Value (COLUMNNAME_QtyToDeliver_Override, QtyToDeliver_Override);
	}

	@Override
	public BigDecimal getQtyToDeliver_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToDeliver_OverrideFulfilled (final @Nullable BigDecimal QtyToDeliver_OverrideFulfilled)
	{
		set_Value (COLUMNNAME_QtyToDeliver_OverrideFulfilled, QtyToDeliver_OverrideFulfilled);
	}

	@Override
	public BigDecimal getQtyToDeliver_OverrideFulfilled() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver_OverrideFulfilled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
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
	public void setShipmentAllocation_BestBefore_Policy (final @Nullable java.lang.String ShipmentAllocation_BestBefore_Policy)
	{
		set_Value (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return get_ValueAsString(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setSinglePriceTag_ID (final @Nullable java.lang.String SinglePriceTag_ID)
	{
		set_ValueNoCheck (COLUMNNAME_SinglePriceTag_ID, SinglePriceTag_ID);
	}

	@Override
	public java.lang.String getSinglePriceTag_ID() 
	{
		return get_ValueAsString(COLUMNNAME_SinglePriceTag_ID);
	}

	@Override
	public void setStatus (final @Nullable java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}