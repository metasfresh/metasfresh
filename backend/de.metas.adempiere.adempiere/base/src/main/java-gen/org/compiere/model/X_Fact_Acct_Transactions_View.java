// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Fact_Acct_Transactions_View
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Fact_Acct_Transactions_View extends org.compiere.model.PO implements I_Fact_Acct_Transactions_View, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -645303719L;

    /** Standard Constructor */
    public X_Fact_Acct_Transactions_View (final Properties ctx, final int Fact_Acct_Transactions_View_ID, @Nullable final String trxName)
    {
      super (ctx, Fact_Acct_Transactions_View_ID, trxName);
    }

    /** Load Constructor */
    public X_Fact_Acct_Transactions_View (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_A_Asset getA_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(final org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	@Override
	public void setA_Asset_ID (final int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, A_Asset_ID);
	}

	@Override
	public int getA_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_ID);
	}

	@Override
	public void setAccount_ID (final int Account_ID)
	{
		if (Account_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Account_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Account_ID, Account_ID);
	}

	@Override
	public int getAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_ID);
	}

	@Override
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
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
	public void setAmtAcctCr (final BigDecimal AmtAcctCr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

	@Override
	public BigDecimal getAmtAcctCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcctDr (final BigDecimal AmtAcctDr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtAcctDr, AmtAcctDr);
	}

	@Override
	public BigDecimal getAmtAcctDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtSourceCr (final @Nullable BigDecimal AmtSourceCr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	@Override
	public BigDecimal getAmtSourceCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtSourceDr (final BigDecimal AmtSourceDr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	@Override
	public BigDecimal getAmtSourceDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBalance (final BigDecimal Balance)
	{
		set_ValueNoCheck (COLUMNNAME_Balance, Balance);
	}

	@Override
	public BigDecimal getBalance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Balance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
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
	public void setC_BPartner2_ID (final int C_BPartner2_ID)
	{
		if (C_BPartner2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner2_ID, C_BPartner2_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
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
	public org.compiere.model.I_C_Calendar getC_Harvesting_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Harvesting_Calendar(final org.compiere.model.I_C_Calendar C_Harvesting_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Harvesting_Calendar);
	}

	@Override
	public void setC_Harvesting_Calendar_ID (final int C_Harvesting_Calendar_ID)
	{
		if (C_Harvesting_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, C_Harvesting_Calendar_ID);
	}

	@Override
	public int getC_Harvesting_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Harvesting_Calendar_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocFrom()
	{
		return get_ValueAsPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocFrom(final org.compiere.model.I_C_Location C_LocFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class, C_LocFrom);
	}

	@Override
	public void setC_LocFrom_ID (final int C_LocFrom_ID)
	{
		if (C_LocFrom_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LocFrom_ID, C_LocFrom_ID);
	}

	@Override
	public int getC_LocFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LocFrom_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocTo(final org.compiere.model.I_C_Location C_LocTo)
	{
		set_ValueFromPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class, C_LocTo);
	}

	@Override
	public void setC_LocTo_ID (final int C_LocTo_ID)
	{
		if (C_LocTo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LocTo_ID, C_LocTo_ID);
	}

	@Override
	public int getC_LocTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LocTo_ID);
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
	public org.compiere.model.I_Fact_Acct_Transactions_View getCounterpart_Fact_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_Counterpart_Fact_Acct_ID, org.compiere.model.I_Fact_Acct_Transactions_View.class);
	}

	@Override
	public void setCounterpart_Fact_Acct(final org.compiere.model.I_Fact_Acct_Transactions_View Counterpart_Fact_Acct)
	{
		set_ValueFromPO(COLUMNNAME_Counterpart_Fact_Acct_ID, org.compiere.model.I_Fact_Acct_Transactions_View.class, Counterpart_Fact_Acct);
	}

	@Override
	public void setCounterpart_Fact_Acct_ID (final int Counterpart_Fact_Acct_ID)
	{
		if (Counterpart_Fact_Acct_ID < 1) 
			set_Value (COLUMNNAME_Counterpart_Fact_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_Counterpart_Fact_Acct_ID, Counterpart_Fact_Acct_ID);
	}

	@Override
	public int getCounterpart_Fact_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Counterpart_Fact_Acct_ID);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(final org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (final int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, C_Period_ID);
	}

	@Override
	public int getC_Period_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
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
	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class);
	}

	@Override
	public void setC_ProjectPhase(final org.compiere.model.I_C_ProjectPhase C_ProjectPhase)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class, C_ProjectPhase);
	}

	@Override
	public void setC_ProjectPhase_ID (final int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectPhase_ID, C_ProjectPhase_ID);
	}

	@Override
	public int getC_ProjectPhase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectPhase_ID);
	}

	@Override
	public org.compiere.model.I_C_ProjectTask getC_ProjectTask()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class);
	}

	@Override
	public void setC_ProjectTask(final org.compiere.model.I_C_ProjectTask C_ProjectTask)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class, C_ProjectTask);
	}

	@Override
	public void setC_ProjectTask_ID (final int C_ProjectTask_ID)
	{
		if (C_ProjectTask_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectTask_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectTask_ID, C_ProjectTask_ID);
	}

	@Override
	public int getC_ProjectTask_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectTask_ID);
	}

	@Override
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion()
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(final org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	@Override
	public void setC_SalesRegion_ID (final int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SalesRegion_ID, C_SalesRegion_ID);
	}

	@Override
	public int getC_SalesRegion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SalesRegion_ID);
	}

	@Override
	public org.compiere.model.I_C_SubAcct getC_SubAcct()
	{
		return get_ValueAsPO(COLUMNNAME_C_SubAcct_ID, org.compiere.model.I_C_SubAcct.class);
	}

	@Override
	public void setC_SubAcct(final org.compiere.model.I_C_SubAcct C_SubAcct)
	{
		set_ValueFromPO(COLUMNNAME_C_SubAcct_ID, org.compiere.model.I_C_SubAcct.class, C_SubAcct);
	}

	@Override
	public void setC_SubAcct_ID (final int C_SubAcct_ID)
	{
		if (C_SubAcct_ID < 1) 
			set_Value (COLUMNNAME_C_SubAcct_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubAcct_ID, C_SubAcct_ID);
	}

	@Override
	public int getC_SubAcct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubAcct_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
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
	public void setCurrencyRate (final @Nullable BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	@Override
	public BigDecimal getCurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
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
	/** ModularOrder = MMO */
	public static final String DOCBASETYPE_ModularOrder = "MMO";
	/** Shipping notification = SHN */
	public static final String DOCBASETYPE_ShippingNotification = "SHN";
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
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
	public org.compiere.model.I_GL_Budget getGL_Budget()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Budget_ID, org.compiere.model.I_GL_Budget.class);
	}

	@Override
	public void setGL_Budget(final org.compiere.model.I_GL_Budget GL_Budget)
	{
		set_ValueFromPO(COLUMNNAME_GL_Budget_ID, org.compiere.model.I_GL_Budget.class, GL_Budget);
	}

	@Override
	public void setGL_Budget_ID (final int GL_Budget_ID)
	{
		if (GL_Budget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Budget_ID, GL_Budget_ID);
	}

	@Override
	public int getGL_Budget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Budget_ID);
	}

	@Override
	public org.compiere.model.I_GL_Category getGL_Category()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class);
	}

	@Override
	public void setGL_Category(final org.compiere.model.I_GL_Category GL_Category)
	{
		set_ValueFromPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class, GL_Category);
	}

	@Override
	public void setGL_Category_ID (final int GL_Category_ID)
	{
		if (GL_Category_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_GL_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Category_ID, GL_Category_ID);
	}

	@Override
	public int getGL_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Category_ID);
	}

	@Override
	public org.compiere.model.I_C_Year getHarvesting_Year()
	{
		return get_ValueAsPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setHarvesting_Year(final org.compiere.model.I_C_Year Harvesting_Year)
	{
		set_ValueFromPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class, Harvesting_Year);
	}

	@Override
	public void setHarvesting_Year_ID (final int Harvesting_Year_ID)
	{
		if (Harvesting_Year_ID < 1) 
			set_Value (COLUMNNAME_Harvesting_Year_ID, null);
		else 
			set_Value (COLUMNNAME_Harvesting_Year_ID, Harvesting_Year_ID);
	}

	@Override
	public int getHarvesting_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Harvesting_Year_ID);
	}

	@Override
	public void setLine_ID (final int Line_ID)
	{
		if (Line_ID < 1) 
			set_Value (COLUMNNAME_Line_ID, null);
		else 
			set_Value (COLUMNNAME_Line_ID, Line_ID);
	}

	@Override
	public int getLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Line_ID);
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(final org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_Value (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
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

	/** 
	 * PostingType AD_Reference_ID=125
	 * Reference name: _Posting Type
	 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Actual Year End = Y */
	public static final String POSTINGTYPE_ActualYearEnd = "Y";
	@Override
	public void setPostingType (final java.lang.String PostingType)
	{
		set_ValueNoCheck (COLUMNNAME_PostingType, PostingType);
	}

	@Override
	public java.lang.String getPostingType() 
	{
		return get_ValueAsString(COLUMNNAME_PostingType);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSubLine_ID (final int SubLine_ID)
	{
		if (SubLine_ID < 1) 
			set_Value (COLUMNNAME_SubLine_ID, null);
		else 
			set_Value (COLUMNNAME_SubLine_ID, SubLine_ID);
	}

	@Override
	public int getSubLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SubLine_ID);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_User1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_User2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}

	@Override
	public void setUserElement1_ID (final int UserElement1_ID)
	{
		if (UserElement1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UserElement1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UserElement1_ID, UserElement1_ID);
	}

	@Override
	public int getUserElement1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_UserElement1_ID);
	}

	@Override
	public void setUserElement2_ID (final int UserElement2_ID)
	{
		if (UserElement2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UserElement2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UserElement2_ID, UserElement2_ID);
	}

	@Override
	public int getUserElement2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_UserElement2_ID);
	}

	@Override
	public void setUserElementNumber1 (final @Nullable BigDecimal UserElementNumber1)
	{
		set_Value (COLUMNNAME_UserElementNumber1, UserElementNumber1);
	}

	@Override
	public BigDecimal getUserElementNumber1() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber1);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementNumber2 (final @Nullable BigDecimal UserElementNumber2)
	{
		set_Value (COLUMNNAME_UserElementNumber2, UserElementNumber2);
	}

	@Override
	public BigDecimal getUserElementNumber2() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber2);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setVATCode (final @Nullable java.lang.String VATCode)
	{
		set_Value (COLUMNNAME_VATCode, VATCode);
	}

	@Override
	public java.lang.String getVATCode() 
	{
		return get_ValueAsString(COLUMNNAME_VATCode);
	}
}