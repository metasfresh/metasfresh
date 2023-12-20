// Generated Model - DO NOT CHANGE
package de.metas.datev.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_DATEV_Export_Fact_Acct_Invoice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_DATEV_Export_Fact_Acct_Invoice extends org.compiere.model.PO implements I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1787706845L;

    /** Standard Constructor */
    public X_RV_DATEV_Export_Fact_Acct_Invoice (final Properties ctx, final int RV_DATEV_Export_Fact_Acct_Invoice_ID, @Nullable final String trxName)
    {
      super (ctx, RV_DATEV_Export_Fact_Acct_Invoice_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_DATEV_Export_Fact_Acct_Invoice (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActivityName (final @Nullable java.lang.String ActivityName)
	{
		set_ValueNoCheck (COLUMNNAME_ActivityName, ActivityName);
	}

	@Override
	public java.lang.String getActivityName() 
	{
		return get_ValueAsString(COLUMNNAME_ActivityName);
	}

	@Override
	public void setAmt (final @Nullable BigDecimal Amt)
	{
		set_ValueNoCheck (COLUMNNAME_Amt, Amt);
	}

	@Override
	public BigDecimal getAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBPName (final @Nullable java.lang.String BPName)
	{
		set_ValueNoCheck (COLUMNNAME_BPName, BPName);
	}

	@Override
	public java.lang.String getBPName() 
	{
		return get_ValueAsString(COLUMNNAME_BPName);
	}

	@Override
	public void setBPValue (final @Nullable java.lang.String BPValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPValue, BPValue);
	}

	@Override
	public java.lang.String getBPValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPValue);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Activity_ID, C_Activity_ID);
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
	public void setC_DocType_Name (final @Nullable java.lang.String C_DocType_Name)
	{
		set_ValueNoCheck (COLUMNNAME_C_DocType_Name, C_DocType_Name);
	}

	@Override
	public java.lang.String getC_DocType_Name() 
	{
		return get_ValueAsString(COLUMNNAME_C_DocType_Name);
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
	public void setCR_Account (final @Nullable java.lang.String CR_Account)
	{
		set_ValueNoCheck (COLUMNNAME_CR_Account, CR_Account);
	}

	@Override
	public java.lang.String getCR_Account() 
	{
		return get_ValueAsString(COLUMNNAME_CR_Account);
	}

	@Override
	public void setC_Tax_Rate (final @Nullable BigDecimal C_Tax_Rate)
	{
		set_ValueNoCheck (COLUMNNAME_C_Tax_Rate, C_Tax_Rate);
	}

	@Override
	public BigDecimal getC_Tax_Rate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_C_Tax_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrency (final @Nullable java.lang.String Currency)
	{
		set_ValueNoCheck (COLUMNNAME_Currency, Currency);
	}

	@Override
	public java.lang.String getCurrency() 
	{
		return get_ValueAsString(COLUMNNAME_Currency);
	}

	@Override
	public void setDateAcct (final @Nullable java.sql.Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDebitOrCreditIndicator (final boolean DebitOrCreditIndicator)
	{
		set_ValueNoCheck (COLUMNNAME_DebitOrCreditIndicator, DebitOrCreditIndicator);
	}

	@Override
	public boolean isDebitOrCreditIndicator() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DebitOrCreditIndicator);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
		set_ValueNoCheck (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public java.lang.String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDR_Account (final @Nullable java.lang.String DR_Account)
	{
		set_ValueNoCheck (COLUMNNAME_DR_Account, DR_Account);
	}

	@Override
	public java.lang.String getDR_Account() 
	{
		return get_ValueAsString(COLUMNNAME_DR_Account);
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_ValueNoCheck (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public org.compiere.model.I_Fact_Acct getFact_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class);
	}

	@Override
	public void setFact_Acct(final org.compiere.model.I_Fact_Acct Fact_Acct)
	{
		set_ValueFromPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class, Fact_Acct);
	}

	@Override
	public void setFact_Acct_ID (final int Fact_Acct_ID)
	{
		if (Fact_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_ID, Fact_Acct_ID);
	}

	@Override
	public int getFact_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Fact_Acct_ID);
	}

	@Override
	public void setGrandTotal (final BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRV_DATEV_Export_Fact_Acct_Invoice_ID (final int RV_DATEV_Export_Fact_Acct_Invoice_ID)
	{
		if (RV_DATEV_Export_Fact_Acct_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RV_DATEV_Export_Fact_Acct_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RV_DATEV_Export_Fact_Acct_Invoice_ID, RV_DATEV_Export_Fact_Acct_Invoice_ID);
	}

	@Override
	public int getRV_DATEV_Export_Fact_Acct_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RV_DATEV_Export_Fact_Acct_Invoice_ID);
	}

	@Override
	public void setTaxAmt (final BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}