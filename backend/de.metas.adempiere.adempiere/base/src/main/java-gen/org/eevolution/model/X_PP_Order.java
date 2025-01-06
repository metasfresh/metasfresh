// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order extends org.compiere.model.PO implements I_PP_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1714280892L;

    /** Standard Constructor */
    public X_PP_Order (final Properties ctx, final int PP_Order_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public void setAssay (final @Nullable BigDecimal Assay)
	{
		set_Value (COLUMNNAME_Assay, Assay);
	}

	@Override
	public BigDecimal getAssay() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Assay);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_DocTypeTarget_ID (final int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, C_DocTypeTarget_ID);
	}

	@Override
	public int getC_DocTypeTarget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeTarget_ID);
	}

	@Override
	public void setCopyFrom (final @Nullable java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	@Override
	public java.lang.String getCopyFrom()
	{
		return get_ValueAsString(COLUMNNAME_CopyFrom);
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
		throw new IllegalArgumentException ("C_Order_ID is virtual column");	}

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
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_MTO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_MTO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_MTO(final org.compiere.model.I_C_OrderLine C_OrderLine_MTO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_MTO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_MTO);
	}

	@Override
	public void setC_OrderLine_MTO_ID (final int C_OrderLine_MTO_ID)
	{
		if (C_OrderLine_MTO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_MTO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_MTO_ID, C_OrderLine_MTO_ID);
	}

	@Override
	public int getC_OrderLine_MTO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_MTO_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
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
	public void setCurrent_Receiving_LU_HU_ID (final int Current_Receiving_LU_HU_ID)
	{
		if (Current_Receiving_LU_HU_ID < 1) 
			set_Value (COLUMNNAME_Current_Receiving_LU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_Current_Receiving_LU_HU_ID, Current_Receiving_LU_HU_ID);
	}

	@Override
	public int getCurrent_Receiving_LU_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Current_Receiving_LU_HU_ID);
	}

	@Override
	public void setCurrent_Receiving_TU_PI_Item_Product_ID (final int Current_Receiving_TU_PI_Item_Product_ID)
	{
		if (Current_Receiving_TU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID, Current_Receiving_TU_PI_Item_Product_ID);
	}

	@Override
	public int getCurrent_Receiving_TU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID);
	}

	@Override
	public void setCurrentScaleDeviceId (final @Nullable java.lang.String CurrentScaleDeviceId)
	{
		set_Value (COLUMNNAME_CurrentScaleDeviceId, CurrentScaleDeviceId);
	}

	@Override
	public java.lang.String getCurrentScaleDeviceId() 
	{
		return get_ValueAsString(COLUMNNAME_CurrentScaleDeviceId);
	}

	@Override
	public void setDateConfirm (final @Nullable java.sql.Timestamp DateConfirm)
	{
		set_ValueNoCheck (COLUMNNAME_DateConfirm, DateConfirm);
	}

	@Override
	public java.sql.Timestamp getDateConfirm() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateConfirm);
	}

	@Override
	public void setDateDelivered (final @Nullable java.sql.Timestamp DateDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_DateDelivered, DateDelivered);
	}

	@Override
	public java.sql.Timestamp getDateDelivered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDelivered);
	}

	@Override
	public void setDateFinish (final @Nullable java.sql.Timestamp DateFinish)
	{
		set_ValueNoCheck (COLUMNNAME_DateFinish, DateFinish);
	}

	@Override
	public java.sql.Timestamp getDateFinish() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinish);
	}

	@Override
	public void setDateFinishSchedule (final @Nullable java.sql.Timestamp DateFinishSchedule)
	{
		set_Value (COLUMNNAME_DateFinishSchedule, DateFinishSchedule);
	}

	@Override
	public java.sql.Timestamp getDateFinishSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinishSchedule);
	}

	@Override
	public void setDateOrdered (final java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
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
	public void setDateStart (final @Nullable java.sql.Timestamp DateStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
	}

	@Override
	public void setDateStartSchedule (final java.sql.Timestamp DateStartSchedule)
	{
		set_Value (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	@Override
	public java.sql.Timestamp getDateStartSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartSchedule);
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
	 * DocBaseType AD_Reference_ID=183
	 * Reference name: C_DocType DocBaseType
	 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** GLJournal = GLJ */
	public static final String DOCBASETYPE_GLJournal = "GLJ";
	/** GLDocument = GLD */
	public static final String DOCBASETYPE_GLDocument = "GLD";
	/** APInvoice = API */
	public static final String DOCBASETYPE_APInvoice = "API";
	/** APPayment = APP */
	public static final String DOCBASETYPE_APPayment = "APP";
	/** ARInvoice = ARI */
	public static final String DOCBASETYPE_ARInvoice = "ARI";
	/** ARReceipt = ARR */
	public static final String DOCBASETYPE_ARReceipt = "ARR";
	/** SalesOrder = SOO */
	public static final String DOCBASETYPE_SalesOrder = "SOO";
	/** ARProFormaInvoice = ARF */
	public static final String DOCBASETYPE_ARProFormaInvoice = "ARF";
	/** MaterialDelivery = MMS */
	public static final String DOCBASETYPE_MaterialDelivery = "MMS";
	/** MaterialReceipt = MMR */
	public static final String DOCBASETYPE_MaterialReceipt = "MMR";
	/** MaterialMovement = MMM */
	public static final String DOCBASETYPE_MaterialMovement = "MMM";
	/** PurchaseOrder = POO */
	public static final String DOCBASETYPE_PurchaseOrder = "POO";
	/** PurchaseRequisition = POR */
	public static final String DOCBASETYPE_PurchaseRequisition = "POR";
	/** MaterialPhysicalInventory = MMI */
	public static final String DOCBASETYPE_MaterialPhysicalInventory = "MMI";
	/** APCreditMemo = APC */
	public static final String DOCBASETYPE_APCreditMemo = "APC";
	/** ARCreditMemo = ARC */
	public static final String DOCBASETYPE_ARCreditMemo = "ARC";
	/** BankStatement = CMB */
	public static final String DOCBASETYPE_BankStatement = "CMB";
	/** CashJournal = CMC */
	public static final String DOCBASETYPE_CashJournal = "CMC";
	/** PaymentAllocation = CMA */
	public static final String DOCBASETYPE_PaymentAllocation = "CMA";
	/** MatchInvoice = MXI */
	public static final String DOCBASETYPE_MatchInvoice = "MXI";
	/** MatchPO = MXP */
	public static final String DOCBASETYPE_MatchPO = "MXP";
	/** ProjectIssue = PJI */
	public static final String DOCBASETYPE_ProjectIssue = "PJI";
	/** MaintenanceOrder = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** ManufacturingOrder = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** QualityOrder = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** DistributionOrder = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** ManufacturingCostCollector = MCC */
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MCC";
	/** Gehaltsrechnung (Angestellter) = AEI */
	public static final String DOCBASETYPE_GehaltsrechnungAngestellter = "AEI";
	/** Interne Rechnung (Lieferant) = AVI */
	public static final String DOCBASETYPE_InterneRechnungLieferant = "AVI";
	/** Speditionsauftrag/Ladeliste = MST */
	public static final String DOCBASETYPE_SpeditionsauftragLadeliste = "MST";
	/** CustomerContract = CON */
	public static final String DOCBASETYPE_CustomerContract = "CON";
	/** DunningDoc = DUN */
	public static final String DOCBASETYPE_DunningDoc = "DUN";
	/** Shipment Declaration = SDD */
	public static final String DOCBASETYPE_ShipmentDeclaration = "SDD";
	/** Shipment Declaration Correction = SDC */
	public static final String DOCBASETYPE_ShipmentDeclarationCorrection = "SDC";
	/** Customs Invoice = CUI */
	public static final String DOCBASETYPE_CustomsInvoice = "CUI";
	/** ServiceRepairOrder = MRO */
	public static final String DOCBASETYPE_ServiceRepairOrder = "MRO";
	/** Remittance Advice = RMA */
	public static final String DOCBASETYPE_RemittanceAdvice = "RMA";
	/** BOM & Formula = BOM */
	public static final String DOCBASETYPE_BOMFormula = "BOM";
	@Override
	public void setDocBaseType (final @Nullable java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public java.lang.String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
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
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
	public void setFloatAfter (final @Nullable BigDecimal FloatAfter)
	{
		set_Value (COLUMNNAME_FloatAfter, FloatAfter);
	}

	@Override
	public BigDecimal getFloatAfter() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FloatAfter);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFloatBefored (final @Nullable BigDecimal FloatBefored)
	{
		set_Value (COLUMNNAME_FloatBefored, FloatBefored);
	}

	@Override
	public BigDecimal getFloatBefored() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FloatBefored);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setIsPickingOrder (final boolean IsPickingOrder)
	{
		set_Value (COLUMNNAME_IsPickingOrder, IsPickingOrder);
	}

	@Override
	public boolean isPickingOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingOrder);
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
	public void setIsQtyPercentage (final boolean IsQtyPercentage)
	{
		set_Value (COLUMNNAME_IsQtyPercentage, IsQtyPercentage);
	}

	@Override
	public boolean isQtyPercentage() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQtyPercentage);
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
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLot (final @Nullable java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	@Override
	public java.lang.String getLot() 
	{
		return get_ValueAsString(COLUMNNAME_Lot);
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
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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

	// @Override
	// public void setMRP_AllowCleanup (final boolean MRP_AllowCleanup)
	// {
	// 	set_Value (COLUMNNAME_MRP_AllowCleanup, MRP_AllowCleanup);
	// }
	//
	// @Override
	// public boolean isMRP_AllowCleanup()
	// {
	// 	return get_ValueAsBoolean(COLUMNNAME_MRP_AllowCleanup);
	// }
	//
	// @Override
	// public void setMRP_Generated (final boolean MRP_Generated)
	// {
	// 	set_Value (COLUMNNAME_MRP_Generated, MRP_Generated);
	// }
	//
	// @Override
	// public boolean isMRP_Generated()
	// {
	// 	return get_ValueAsBoolean(COLUMNNAME_MRP_Generated);
	// }
	//
	// @Override
	// public void setMRP_ToDelete (final boolean MRP_ToDelete)
	// {
	// 	set_Value (COLUMNNAME_MRP_ToDelete, MRP_ToDelete);
	// }
	//
	// @Override
	// public boolean isMRP_ToDelete()
	// {
	// 	return get_ValueAsBoolean(COLUMNNAME_MRP_ToDelete);
	// }

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
	public void setModular_Flatrate_Term_ID (final int Modular_Flatrate_Term_ID)
	{
		if (Modular_Flatrate_Term_ID < 1)
			set_Value (COLUMNNAME_Modular_Flatrate_Term_ID, null);
		else
			set_Value (COLUMNNAME_Modular_Flatrate_Term_ID, Modular_Flatrate_Term_ID);
	}

	@Override
	public int getModular_Flatrate_Term_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Modular_Flatrate_Term_ID);
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
	public void setOrderType (final @Nullable java.lang.String OrderType)
	{
		set_Value (COLUMNNAME_OrderType, OrderType);
	}

	@Override
	public java.lang.String getOrderType() 
	{
		return get_ValueAsString(COLUMNNAME_OrderType);
	}

	@Override
	public void setPlanner_ID (final int Planner_ID)
	{
		if (Planner_ID < 1) 
			set_Value (COLUMNNAME_Planner_ID, null);
		else 
			set_Value (COLUMNNAME_Planner_ID, Planner_ID);
	}

	@Override
	public int getPlanner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Planner_ID);
	}

	/** 
	 * PlanningStatus AD_Reference_ID=540714
	 * Reference name: PP_Order_PlanningStatus
	 */
	public static final int PLANNINGSTATUS_AD_Reference_ID=540714;
	/** Planning = P */
	public static final String PLANNINGSTATUS_Planning = "P";
	/** Review = R */
	public static final String PLANNINGSTATUS_Review = "R";
	/** Complete = C */
	public static final String PLANNINGSTATUS_Complete = "C";
	@Override
	public void setPlanningStatus (final java.lang.String PlanningStatus)
	{
		set_Value (COLUMNNAME_PlanningStatus, PlanningStatus);
	}

	@Override
	public java.lang.String getPlanningStatus() 
	{
		return get_ValueAsString(COLUMNNAME_PlanningStatus);
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
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(final org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	@Override
	public void setPP_Product_BOM_ID (final int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
	}

	@Override
	public int getPP_Product_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOM_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
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

	/** 
	 * ProblemCode AD_Reference_ID=541259
	 * Reference name: ProblemCode
	 */
	public static final int PROBLEMCODE_AD_Reference_ID=541259;
	/** Electrical surge damage = ED */
	public static final String PROBLEMCODE_ElectricalSurgeDamage = "ED";
	/** No detactable fault = NF */
	public static final String PROBLEMCODE_NoDetactableFault = "NF";
	/** Configuration Error = CE */
	public static final String PROBLEMCODE_ConfigurationError = "CE";
	/** Software Fault = SF */
	public static final String PROBLEMCODE_SoftwareFault = "SF";
	/** Hardware Fault = HF */
	public static final String PROBLEMCODE_HardwareFault = "HF";
	@Override
	public void setProblemCode (final @Nullable java.lang.String ProblemCode)
	{
		set_Value (COLUMNNAME_ProblemCode, ProblemCode);
	}

	@Override
	public java.lang.String getProblemCode() 
	{
		return get_ValueAsString(COLUMNNAME_ProblemCode);
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
	public void setQtyBatchs (final @Nullable BigDecimal QtyBatchs)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatchs, QtyBatchs);
	}

	@Override
	public BigDecimal getQtyBatchs() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatchs);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBatchSize (final @Nullable BigDecimal QtyBatchSize)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	@Override
	public BigDecimal getQtyBatchSize() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatchSize);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBeforeClose (final BigDecimal QtyBeforeClose)
	{
		set_Value (COLUMNNAME_QtyBeforeClose, QtyBeforeClose);
	}

	@Override
	public BigDecimal getQtyBeforeClose() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBeforeClose);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDelivered (final BigDecimal QtyDelivered)
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
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
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
	public void setQtyReject (final BigDecimal QtyReject)
	{
		set_Value (COLUMNNAME_QtyReject, QtyReject);
	}

	@Override
	public BigDecimal getQtyReject() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReject);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyScrap (final BigDecimal QtyScrap)
	{
		set_Value (COLUMNNAME_QtyScrap, QtyScrap);
	}

	@Override
	public BigDecimal getQtyScrap() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyScrap);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRepairOrderSummary (final @Nullable java.lang.String RepairOrderSummary)
	{
		set_Value (COLUMNNAME_RepairOrderSummary, RepairOrderSummary);
	}

	@Override
	public java.lang.String getRepairOrderSummary() 
	{
		return get_ValueAsString(COLUMNNAME_RepairOrderSummary);
	}

	@Override
	public void setRepairServicePerformed_Product_ID (final int RepairServicePerformed_Product_ID)
	{
		if (RepairServicePerformed_Product_ID < 1) 
			set_Value (COLUMNNAME_RepairServicePerformed_Product_ID, null);
		else 
			set_Value (COLUMNNAME_RepairServicePerformed_Product_ID, RepairServicePerformed_Product_ID);
	}

	@Override
	public int getRepairServicePerformed_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RepairServicePerformed_Product_ID);
	}

	@Override
	public void setScheduleType (final @Nullable java.lang.String ScheduleType)
	{
		set_Value (COLUMNNAME_ScheduleType, ScheduleType);
	}

	@Override
	public java.lang.String getScheduleType()
	{
		return get_ValueAsString(COLUMNNAME_ScheduleType);
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
	public void setSerNo (final @Nullable java.lang.String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	@Override
	public java.lang.String getSerNo()
	{
		return get_ValueAsString(COLUMNNAME_SerNo);
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(final org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
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
	public org.compiere.model.I_S_Resource getWorkStation()
	{
		return get_ValueAsPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setWorkStation(final org.compiere.model.I_S_Resource WorkStation)
	{
		set_ValueFromPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class, WorkStation);
	}

	@Override
	public void setWorkStation_ID (final int WorkStation_ID)
	{
		if (WorkStation_ID < 1)
			set_Value (COLUMNNAME_WorkStation_ID, null);
		else
			set_Value (COLUMNNAME_WorkStation_ID, WorkStation_ID);
	}

	@Override
	public int getWorkStation_ID()
	{
		return get_ValueAsInt(COLUMNNAME_WorkStation_ID);
	}

	@Override
	public void setYield (final BigDecimal Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public BigDecimal getYield() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Yield);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}