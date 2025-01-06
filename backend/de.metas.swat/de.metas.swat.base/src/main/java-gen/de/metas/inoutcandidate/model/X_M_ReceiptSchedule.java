// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ReceiptSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ReceiptSchedule extends org.compiere.model.PO implements I_M_ReceiptSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -92158745L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule (final Properties ctx, final int M_ReceiptSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, M_ReceiptSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
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
	public void setBlockedBPartner (final boolean BlockedBPartner)
	{
		throw new IllegalArgumentException ("BlockedBPartner is virtual column");	}

	@Override
	public boolean isBlockedBPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_BlockedBPartner);
	}

	@Override
	public void setBPartnerAddress (final @Nullable java.lang.String BPartnerAddress)
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
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
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
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
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
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
	public void setExternalResourceURL (final @Nullable java.lang.String ExternalResourceURL)
	{
		set_Value (COLUMNNAME_ExternalResourceURL, ExternalResourceURL);
	}

	@Override
	public java.lang.String getExternalResourceURL() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalResourceURL);
	}

	@Override
	public void setFilteredItemsWithSameC_Order_ID (final int FilteredItemsWithSameC_Order_ID)
	{
		throw new IllegalArgumentException ("FilteredItemsWithSameC_Order_ID is virtual column");	}

	@Override
	public int getFilteredItemsWithSameC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_FilteredItemsWithSameC_Order_ID);
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
	public void setIsPackagingMaterial (final boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, IsPackagingMaterial);
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
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
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
	public void setM_ReceiptSchedule_ID (final int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, M_ReceiptSchedule_ID);
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
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
	public void setM_Warehouse_Dest_ID (final int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, M_Warehouse_Dest_ID);
	}

	@Override
	public int getM_Warehouse_Dest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Dest_ID);
	}

	@Override
	public void setM_Warehouse_Effective_ID (final int M_Warehouse_Effective_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_Effective_ID is virtual column");	}

	@Override
	public int getM_Warehouse_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Effective_ID);
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
	public void setM_Warehouse_Override_ID (final int M_Warehouse_Override_ID)
	{
		if (M_Warehouse_Override_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, M_Warehouse_Override_ID);
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
	public void setOnMaterialReceiptWithDestWarehouse (final @Nullable java.lang.String OnMaterialReceiptWithDestWarehouse)
	{
		set_Value (COLUMNNAME_OnMaterialReceiptWithDestWarehouse, OnMaterialReceiptWithDestWarehouse);
	}

	@Override
	public java.lang.String getOnMaterialReceiptWithDestWarehouse() 
	{
		return get_ValueAsString(COLUMNNAME_OnMaterialReceiptWithDestWarehouse);
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
	public void setQtyMoved (final @Nullable BigDecimal QtyMoved)
	{
		set_Value (COLUMNNAME_QtyMoved, QtyMoved);
	}

	@Override
	public BigDecimal getQtyMoved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMoved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedInCatchUOM (final @Nullable BigDecimal QtyMovedInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyMovedInCatchUOM, QtyMovedInCatchUOM);
	}

	@Override
	public BigDecimal getQtyMovedInCatchUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedInCatchUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedWithIssues (final BigDecimal QtyMovedWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMovedWithIssues, QtyMovedWithIssues);
	}

	@Override
	public BigDecimal getQtyMovedWithIssues() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedWithIssues);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyMovedWithIssuesInCatchUOM (final @Nullable BigDecimal QtyMovedWithIssuesInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyMovedWithIssuesInCatchUOM, QtyMovedWithIssuesInCatchUOM);
	}

	@Override
	public BigDecimal getQtyMovedWithIssuesInCatchUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMovedWithIssuesInCatchUOM);
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
	public void setQtyOrderedOverUnder (final @Nullable BigDecimal QtyOrderedOverUnder)
	{
		set_Value (COLUMNNAME_QtyOrderedOverUnder, QtyOrderedOverUnder);
	}

	@Override
	public BigDecimal getQtyOrderedOverUnder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrderedOverUnder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrderedTU (final @Nullable BigDecimal QtyOrderedTU)
	{
		throw new IllegalArgumentException ("QtyOrderedTU is virtual column");	}

	@Override
	public BigDecimal getQtyOrderedTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrderedTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove (final @Nullable BigDecimal QtyToMove)
	{
		set_Value (COLUMNNAME_QtyToMove, QtyToMove);
	}

	@Override
	public BigDecimal getQtyToMove() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove_Override (final @Nullable BigDecimal QtyToMove_Override)
	{
		set_Value (COLUMNNAME_QtyToMove_Override, QtyToMove_Override);
	}

	@Override
	public BigDecimal getQtyToMove_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent (final @Nullable BigDecimal QualityDiscountPercent)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent, QualityDiscountPercent);
	}

	@Override
	public BigDecimal getQualityDiscountPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityNote (final @Nullable java.lang.String QualityNote)
	{
		set_Value (COLUMNNAME_QualityNote, QualityNote);
	}

	@Override
	public java.lang.String getQualityNote() 
	{
		return get_ValueAsString(COLUMNNAME_QualityNote);
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

	@Override
	public void setStatus (final @Nullable java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference()
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}
}