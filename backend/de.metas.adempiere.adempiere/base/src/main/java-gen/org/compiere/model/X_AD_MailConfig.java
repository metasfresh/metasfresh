// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_MailConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_MailConfig extends org.compiere.model.PO implements I_AD_MailConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 596320848L;

    /** Standard Constructor */
    public X_AD_MailConfig (final Properties ctx, final int AD_MailConfig_ID, @Nullable final String trxName)
    {
      super (ctx, AD_MailConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_MailConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_MailBox getAD_MailBox()
	{
		return get_ValueAsPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class);
	}

	@Override
	public void setAD_MailBox(final org.compiere.model.I_AD_MailBox AD_MailBox)
	{
		set_ValueFromPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class, AD_MailBox);
	}

	@Override
	public void setAD_MailBox_ID (final int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_Value (COLUMNNAME_AD_MailBox_ID, null);
		else 
			set_Value (COLUMNNAME_AD_MailBox_ID, AD_MailBox_ID);
	}

	@Override
	public int getAD_MailBox_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_MailBox_ID);
	}

	@Override
	public void setAD_MailConfig_ID (final int AD_MailConfig_ID)
	{
		if (AD_MailConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, AD_MailConfig_ID);
	}

	@Override
	public int getAD_MailConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_MailConfig_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	/** 
	 * ColumnUserTo AD_Reference_ID=540662
	 * Reference name: AD_User choices
	 */
	public static final int COLUMNUSERTO_AD_Reference_ID=540662;
	/** AD_User_ID = AD_User_ID */
	public static final String COLUMNUSERTO_AD_User_ID = "AD_User_ID";
	/** Bill_User_ID = Bill_User_ID */
	public static final String COLUMNUSERTO_Bill_User_ID = "Bill_User_ID";
	@Override
	public void setColumnUserTo (final @Nullable java.lang.String ColumnUserTo)
	{
		set_Value (COLUMNNAME_ColumnUserTo, ColumnUserTo);
	}

	@Override
	public java.lang.String getColumnUserTo() 
	{
		return get_ValueAsString(COLUMNNAME_ColumnUserTo);
	}

	/** 
	 * CustomType AD_Reference_ID=540142
	 * Reference name: AD_MailConfig_CustomType
	 */
	public static final int CUSTOMTYPE_AD_Reference_ID=540142;
	/** org.compiere.util.Login = L */
	public static final String CUSTOMTYPE_OrgCompiereUtilLogin = "L";
	/** InvoiceRejection = InvRej */
	public static final String CUSTOMTYPE_InvoiceRejection = "InvRej";
	/** MassDunning = DUN */
	public static final String CUSTOMTYPE_MassDunning = "DUN";
	@Override
	public void setCustomType (final @Nullable java.lang.String CustomType)
	{
		set_Value (COLUMNNAME_CustomType, CustomType);
	}

	@Override
	public java.lang.String getCustomType() 
	{
		return get_ValueAsString(COLUMNNAME_CustomType);
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
		set_Value (COLUMNNAME_DocSubType, DocSubType);
	}

	@Override
	public java.lang.String getDocSubType() 
	{
		return get_ValueAsString(COLUMNNAME_DocSubType);
	}
}