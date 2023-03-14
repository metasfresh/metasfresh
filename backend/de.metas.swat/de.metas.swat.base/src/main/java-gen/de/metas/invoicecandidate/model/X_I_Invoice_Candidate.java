// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Invoice_Candidate
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_I_Invoice_Candidate extends org.compiere.model.PO implements I_I_Invoice_Candidate, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 2138569770L;

	/** Standard Constructor */
	public X_I_Invoice_Candidate (final Properties ctx, final int I_Invoice_Candidate_ID, @Nullable final String trxName)
	{
		super (ctx, I_Invoice_Candidate_ID, trxName);
	}

	/** Load Constructor */
	public X_I_Invoice_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1)
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
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
	public void setBill_BPartner_Value (final java.lang.String Bill_BPartner_Value)
	{
		set_Value (COLUMNNAME_Bill_BPartner_Value, Bill_BPartner_Value);
	}

	@Override
	public java.lang.String getBill_BPartner_Value()
	{
		return get_ValueAsString(COLUMNNAME_Bill_BPartner_Value);
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
	public void setC_Activity_Value (final @Nullable java.lang.String C_Activity_Value)
	{
		set_Value (COLUMNNAME_C_Activity_Value, C_Activity_Value);
	}

	@Override
	public java.lang.String getC_Activity_Value()
	{
		return get_ValueAsString(COLUMNNAME_C_Activity_Value);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1)
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1)
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
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
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDefault_OrgCode (final java.lang.String Default_OrgCode)
	{
		set_Value (COLUMNNAME_Default_OrgCode, Default_OrgCode);
	}

	@Override
	public java.lang.String getDefault_OrgCode()
	{
		return get_ValueAsString(COLUMNNAME_Default_OrgCode);
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

	@Override
	public void setDescriptionBottom (final @Nullable java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	@Override
	public java.lang.String getDescriptionBottom()
	{
		return get_ValueAsString(COLUMNNAME_DescriptionBottom);
	}

	@Override
	public void setDiscount (final BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
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
	/** ShipperTransportation = MST */
	public static final String DOCBASETYPE_ShipperTransportation = "MST";
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
	/** Cost Revaluation = CRD */
	public static final String DOCBASETYPE_CostRevaluation = "CRD";
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
	@Override
	public void setDocSubType (final @Nullable java.lang.String DocSubType)
	{
		set_Value (COLUMNNAME_DocSubType, DocSubType);
	}

	@Override
	public java.lang.String getDocSubType()
	{
		return get_ValueAsString(COLUMNNAME_DocSubType);
	}

	@Override
	public void setExternalHeaderId (final @Nullable java.lang.String ExternalHeaderId)
	{
		set_Value (COLUMNNAME_ExternalHeaderId, ExternalHeaderId);
	}

	@Override
	public java.lang.String getExternalHeaderId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalHeaderId);
	}

	@Override
	public void setExternalLineId (final @Nullable java.lang.String ExternalLineId)
	{
		set_Value (COLUMNNAME_ExternalLineId, ExternalLineId);
	}

	@Override
	public java.lang.String getExternalLineId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalLineId);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg()
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_Invoice_Candidate_ID (final int I_Invoice_Candidate_ID)
	{
		if (I_Invoice_Candidate_ID < 1)
			set_ValueNoCheck (COLUMNNAME_I_Invoice_Candidate_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_I_Invoice_Candidate_ID, I_Invoice_Candidate_ID);
	}

	@Override
	public int getI_Invoice_Candidate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_I_Invoice_Candidate_ID);
	}

	@Override
	public void setI_IsImported (final boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public boolean isI_IsImported()
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent()
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo()
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	/**
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_AfterPick = "P";
	@Override
	public void setInvoiceRule (final @Nullable java.lang.String InvoiceRule)
	{
		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule()
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
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
	public void setM_Product_Value (final java.lang.String M_Product_Value)
	{
		set_Value (COLUMNNAME_M_Product_Value, M_Product_Value);
	}

	@Override
	public java.lang.String getM_Product_Value()
	{
		return get_ValueAsString(COLUMNNAME_M_Product_Value);
	}

	@Override
	public void setOrgCode (final @Nullable java.lang.String OrgCode)
	{
		set_Value (COLUMNNAME_OrgCode, OrgCode);
	}

	@Override
	public java.lang.String getOrgCode()
	{
		return get_ValueAsString(COLUMNNAME_OrgCode);
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
	public void setPresetDateInvoiced (final @Nullable java.sql.Timestamp PresetDateInvoiced)
	{
		set_Value (COLUMNNAME_PresetDateInvoiced, PresetDateInvoiced);
	}

	@Override
	public java.sql.Timestamp getPresetDateInvoiced()
	{
		return get_ValueAsTimestamp(COLUMNNAME_PresetDateInvoiced);
	}

	@Override
	public void setPrice (final BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	@Override
	public BigDecimal getPrice()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Price);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUser_InCharge (final @Nullable java.lang.String User_InCharge)
	{
		set_Value (COLUMNNAME_User_InCharge, User_InCharge);
	}

	@Override
	public java.lang.String getUser_InCharge()
	{
		return get_ValueAsString(COLUMNNAME_User_InCharge);
	}

	@Override
	public void setX12DE355 (final @Nullable java.lang.String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	@Override
	public java.lang.String getX12DE355()
	{
		return get_ValueAsString(COLUMNNAME_X12DE355);
	}
}