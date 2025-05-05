// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DD_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_Order extends org.compiere.model.PO implements I_DD_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 548969864L;

    /** Standard Constructor */
    public X_DD_Order (final Properties ctx, final int DD_Order_ID, @Nullable final String trxName)
    {
      super (ctx, DD_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
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
	public void setAD_User_Responsible_ID (final int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, AD_User_Responsible_ID);
	}

	@Override
	public int getAD_User_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Responsible_ID);
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
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
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
	public void setChargeAmt (final @Nullable BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	@Override
	public BigDecimal getChargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCreateConfirm (final @Nullable java.lang.String CreateConfirm)
	{
		set_Value (COLUMNNAME_CreateConfirm, CreateConfirm);
	}

	@Override
	public java.lang.String getCreateConfirm() 
	{
		return get_ValueAsString(COLUMNNAME_CreateConfirm);
	}

	@Override
	public void setCreateFrom (final @Nullable java.lang.String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	@Override
	public java.lang.String getCreateFrom() 
	{
		return get_ValueAsString(COLUMNNAME_CreateFrom);
	}

	@Override
	public void setDateOrdered (final java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDatePrinted (final @Nullable java.sql.Timestamp DatePrinted)
	{
		set_Value (COLUMNNAME_DatePrinted, DatePrinted);
	}

	@Override
	public java.sql.Timestamp getDatePrinted() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePrinted);
	}

	@Override
	public void setDatePromised (final java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setDateReceived (final @Nullable java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	@Override
	public java.sql.Timestamp getDateReceived() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateReceived);
	}

	@Override
	public void setDD_Order_ID (final int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, DD_Order_ID);
	}

	@Override
	public int getDD_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_ID);
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
		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule);
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
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
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
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFreightAmt (final @Nullable BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	@Override
	public BigDecimal getFreightAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FreightAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setFreightCostRule (final java.lang.String FreightCostRule)
	{
		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	@Override
	public java.lang.String getFreightCostRule() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCostRule);
	}

	@Override
	public void setGenerateTo (final @Nullable java.lang.String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	@Override
	public java.lang.String getGenerateTo() 
	{
		return get_ValueAsString(COLUMNNAME_GenerateTo);
	}

	@Override
	public void setIsApproved (final boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsDelivered (final boolean IsDelivered)
	{
		set_Value (COLUMNNAME_IsDelivered, IsDelivered);
	}

	@Override
	public boolean isDelivered() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelivered);
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
	public void setIsInDispute (final boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, IsInDispute);
	}

	@Override
	public boolean isInDispute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInDispute);
	}

	@Override
	public void setIsInTransit (final boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, IsInTransit);
	}

	@Override
	public boolean isInTransit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInTransit);
	}

	@Override
	public void setIsPrinted (final boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, IsPrinted);
	}

	@Override
	public boolean isPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrinted);
	}

	@Override
	public void setIsSelected (final boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, IsSelected);
	}

	@Override
	public boolean isSelected() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelected);
	}

	@Override
	public void setIsSimulated (final boolean IsSimulated)
	{
		set_Value (COLUMNNAME_IsSimulated, IsSimulated);
	}

	@Override
	public boolean isSimulated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulated);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
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
	public void setM_Warehouse_From_ID (final int M_Warehouse_From_ID)
	{
		if (M_Warehouse_From_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_From_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_From_ID, M_Warehouse_From_ID);
	}

	@Override
	public int getM_Warehouse_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_From_ID);
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
	public void setM_Warehouse_To_ID (final int M_Warehouse_To_ID)
	{
		if (M_Warehouse_To_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_To_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_To_ID, M_Warehouse_To_ID);
	}

	@Override
	public int getM_Warehouse_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_To_ID);
	}

	@Override
	public void setMRP_AllowCleanup (final boolean MRP_AllowCleanup)
	{
		set_Value (COLUMNNAME_MRP_AllowCleanup, MRP_AllowCleanup);
	}

	@Override
	public boolean isMRP_AllowCleanup() 
	{
		return get_ValueAsBoolean(COLUMNNAME_MRP_AllowCleanup);
	}

	@Override
	public void setMRP_Generated (final boolean MRP_Generated)
	{
		set_Value (COLUMNNAME_MRP_Generated, MRP_Generated);
	}

	@Override
	public boolean isMRP_Generated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_MRP_Generated);
	}

	@Override
	public void setMRP_ToDelete (final boolean MRP_ToDelete)
	{
		set_Value (COLUMNNAME_MRP_ToDelete, MRP_ToDelete);
	}

	@Override
	public boolean isMRP_ToDelete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_MRP_ToDelete);
	}

	@Override
	public void setPickDate (final @Nullable java.sql.Timestamp PickDate)
	{
		set_Value (COLUMNNAME_PickDate, PickDate);
	}

	@Override
	public java.sql.Timestamp getPickDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PickDate);
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

	@Override
	public void setPosted (final boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Posted);
	}

	@Override
	public boolean isPosted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Posted);
	}

	@Override
	public void setPostingError_Issue_ID (final int PostingError_Issue_ID)
	{
		if (PostingError_Issue_ID < 1) 
			set_Value (COLUMNNAME_PostingError_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_PostingError_Issue_ID, PostingError_Issue_ID);
	}

	@Override
	public int getPostingError_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostingError_Issue_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(final org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (final int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, PP_Plant_ID);
	}

	@Override
	public int getPP_Plant_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
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
	public void setPriorityRule (final java.lang.String PriorityRule)
	{
		set_Value (COLUMNNAME_PriorityRule, PriorityRule);
	}

	@Override
	public java.lang.String getPriorityRule() 
	{
		return get_ValueAsString(COLUMNNAME_PriorityRule);
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
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public org.eevolution.model.I_DD_Order getRef_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setRef_Order(final org.eevolution.model.I_DD_Order Ref_Order)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Order_ID, org.eevolution.model.I_DD_Order.class, Ref_Order);
	}

	@Override
	public void setRef_Order_ID (final int Ref_Order_ID)
	{
		if (Ref_Order_ID < 1) 
			set_Value (COLUMNNAME_Ref_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Order_ID, Ref_Order_ID);
	}

	@Override
	public int getRef_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_Order_ID);
	}

	@Override
	public void setSalesRep_ID (final int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
	}

	@Override
	public void setSendEMail (final boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, SendEMail);
	}

	@Override
	public boolean isSendEMail() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SendEMail);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(final org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(final org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}

	@Override
	public void setVolume (final @Nullable BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	@Override
	public BigDecimal getVolume() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Volume);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWeight (final @Nullable BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	@Override
	public BigDecimal getWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Weight);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}