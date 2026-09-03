// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for I_GLJournal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_GLJournal extends org.compiere.model.PO implements I_I_GLJournal, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 579747510L;

    /** Standard Constructor */
    public X_I_GLJournal (final Properties ctx, final int I_GLJournal_ID, @Nullable final String trxName)
    {
      super (ctx, I_GLJournal_ID, trxName);
    }

    /** Load Constructor */
    public X_I_GLJournal (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ElementValue getAccountFrom()
	{
		return get_ValueAsPO(COLUMNNAME_AccountFrom_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccountFrom(final org.compiere.model.I_C_ElementValue AccountFrom)
	{
		set_ValueFromPO(COLUMNNAME_AccountFrom_ID, org.compiere.model.I_C_ElementValue.class, AccountFrom);
	}

	@Override
	public void setAccountFrom_ID (final int AccountFrom_ID)
	{
		if (AccountFrom_ID < 1) 
			set_Value (COLUMNNAME_AccountFrom_ID, null);
		else 
			set_Value (COLUMNNAME_AccountFrom_ID, AccountFrom_ID);
	}

	@Override
	public int getAccountFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AccountFrom_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getAccountTo()
	{
		return get_ValueAsPO(COLUMNNAME_AccountTo_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccountTo(final org.compiere.model.I_C_ElementValue AccountTo)
	{
		set_ValueFromPO(COLUMNNAME_AccountTo_ID, org.compiere.model.I_C_ElementValue.class, AccountTo);
	}

	@Override
	public void setAccountTo_ID (final int AccountTo_ID)
	{
		if (AccountTo_ID < 1) 
			set_Value (COLUMNNAME_AccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_AccountTo_ID, AccountTo_ID);
	}

	@Override
	public int getAccountTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AccountTo_ID);
	}

	@Override
	public void setAccountValueFrom (final @Nullable java.lang.String AccountValueFrom)
	{
		set_Value (COLUMNNAME_AccountValueFrom, AccountValueFrom);
	}

	@Override
	public java.lang.String getAccountValueFrom() 
	{
		return get_ValueAsString(COLUMNNAME_AccountValueFrom);
	}

	@Override
	public void setAccountValueTo (final @Nullable java.lang.String AccountValueTo)
	{
		set_Value (COLUMNNAME_AccountValueTo, AccountValueTo);
	}

	@Override
	public java.lang.String getAccountValueTo() 
	{
		return get_ValueAsString(COLUMNNAME_AccountValueTo);
	}

	@Override
	public void setAcctSchemaName (final @Nullable java.lang.String AcctSchemaName)
	{
		set_Value (COLUMNNAME_AcctSchemaName, AcctSchemaName);
	}

	@Override
	public java.lang.String getAcctSchemaName() 
	{
		return get_ValueAsString(COLUMNNAME_AcctSchemaName);
	}

	@Override
	public void setActivityValue (final @Nullable java.lang.String ActivityValue)
	{
		set_Value (COLUMNNAME_ActivityValue, ActivityValue);
	}

	@Override
	public java.lang.String getActivityValue() 
	{
		return get_ValueAsString(COLUMNNAME_ActivityValue);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAD_OrgDoc_ID (final int AD_OrgDoc_ID)
	{
		if (AD_OrgDoc_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgDoc_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgDoc_ID, AD_OrgDoc_ID);
	}

	@Override
	public int getAD_OrgDoc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgDoc_ID);
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
	public void setAmtAcctCr (final @Nullable BigDecimal AmtAcctCr)
	{
		set_Value (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

	@Override
	public BigDecimal getAmtAcctCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcctDr (final @Nullable BigDecimal AmtAcctDr)
	{
		set_Value (COLUMNNAME_AmtAcctDr, AmtAcctDr);
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
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	@Override
	public BigDecimal getAmtSourceCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtSourceDr (final @Nullable BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	@Override
	public BigDecimal getAmtSourceDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBatchDescription (final @Nullable java.lang.String BatchDescription)
	{
		set_Value (COLUMNNAME_BatchDescription, BatchDescription);
	}

	@Override
	public java.lang.String getBatchDescription() 
	{
		return get_ValueAsString(COLUMNNAME_BatchDescription);
	}

	@Override
	public void setBatchDocumentNo (final @Nullable java.lang.String BatchDocumentNo)
	{
		set_Value (COLUMNNAME_BatchDocumentNo, BatchDocumentNo);
	}

	@Override
	public java.lang.String getBatchDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_BatchDocumentNo);
	}

	@Override
	public void setBPartnerValue (final @Nullable java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
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
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
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
	public void setCategoryName (final @Nullable java.lang.String CategoryName)
	{
		set_Value (COLUMNNAME_CategoryName, CategoryName);
	}

	@Override
	public java.lang.String getCategoryName() 
	{
		return get_ValueAsString(COLUMNNAME_CategoryName);
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
	public void setC_ConversionType_ID (final int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, C_ConversionType_ID);
	}

	@Override
	public int getC_ConversionType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionType_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setClientValue (final @Nullable java.lang.String ClientValue)
	{
		set_Value (COLUMNNAME_ClientValue, ClientValue);
	}

	@Override
	public java.lang.String getClientValue() 
	{
		return get_ValueAsString(COLUMNNAME_ClientValue);
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
			set_Value (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocFrom_ID, C_LocFrom_ID);
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
			set_Value (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocTo_ID, C_LocTo_ID);
	}

	@Override
	public int getC_LocTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LocTo_ID);
	}

	@Override
	public void setConversionTypeValue (final @Nullable java.lang.String ConversionTypeValue)
	{
		set_Value (COLUMNNAME_ConversionTypeValue, ConversionTypeValue);
	}

	@Override
	public java.lang.String getConversionTypeValue() 
	{
		return get_ValueAsString(COLUMNNAME_ConversionTypeValue);
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
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, C_Period_ID);
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
	public org.compiere.model.I_C_ValidCombination getCR_Tax_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCR_Tax_Acct(final org.compiere.model.I_C_ValidCombination CR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, CR_Tax_Acct);
	}

	@Override
	public void setCR_Tax_Acct_ID (final int CR_Tax_Acct_ID)
	{
		if (CR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, CR_Tax_Acct_ID);
	}

	@Override
	public int getCR_Tax_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_Tax_Acct_ID);
	}

	@Override
	public void setCR_Tax_ID (final int CR_Tax_ID)
	{
		if (CR_Tax_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_ID, CR_Tax_ID);
	}

	@Override
	public int getCR_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_Tax_ID);
	}

	@Override
	public void setCR_TaxName (final @Nullable java.lang.String CR_TaxName)
	{
		set_Value (COLUMNNAME_CR_TaxName, CR_TaxName);
	}

	@Override
	public java.lang.String getCR_TaxName() 
	{
		return get_ValueAsString(COLUMNNAME_CR_TaxName);
	}

	@Override
	public void setCR_TaxTotalAmt (final BigDecimal CR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_CR_TaxTotalAmt, CR_TaxTotalAmt);
	}

	@Override
	public BigDecimal getCR_TaxTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CR_TaxTotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, C_SalesRegion_ID);
	}

	@Override
	public int getC_SalesRegion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SalesRegion_ID);
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
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationFrom()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationFrom_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationFrom(final org.compiere.model.I_C_ElementValue C_ValidCombinationFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationFrom_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationFrom);
	}

	@Override
	public void setC_ValidCombinationFrom_ID (final int C_ValidCombinationFrom_ID)
	{
		if (C_ValidCombinationFrom_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationFrom_ID, C_ValidCombinationFrom_ID);
	}

	@Override
	public int getC_ValidCombinationFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombinationFrom_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationTaxFrom()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationTaxFrom_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationTaxFrom(final org.compiere.model.I_C_ElementValue C_ValidCombinationTaxFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationTaxFrom_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationTaxFrom);
	}

	@Override
	public void setC_ValidCombinationTaxFrom_ID (final int C_ValidCombinationTaxFrom_ID)
	{
		if (C_ValidCombinationTaxFrom_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationTaxFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationTaxFrom_ID, C_ValidCombinationTaxFrom_ID);
	}

	@Override
	public int getC_ValidCombinationTaxFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombinationTaxFrom_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationTaxTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationTaxTo_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationTaxTo(final org.compiere.model.I_C_ElementValue C_ValidCombinationTaxTo)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationTaxTo_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationTaxTo);
	}

	@Override
	public void setC_ValidCombinationTaxTo_ID (final int C_ValidCombinationTaxTo_ID)
	{
		if (C_ValidCombinationTaxTo_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationTaxTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationTaxTo_ID, C_ValidCombinationTaxTo_ID);
	}

	@Override
	public int getC_ValidCombinationTaxTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombinationTaxTo_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ValidCombinationTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombinationTo_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ValidCombinationTo(final org.compiere.model.I_C_ElementValue C_ValidCombinationTo)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombinationTo_ID, org.compiere.model.I_C_ElementValue.class, C_ValidCombinationTo);
	}

	@Override
	public void setC_ValidCombinationTo_ID (final int C_ValidCombinationTo_ID)
	{
		if (C_ValidCombinationTo_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombinationTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombinationTo_ID, C_ValidCombinationTo_ID);
	}

	@Override
	public int getC_ValidCombinationTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombinationTo_ID);
	}

	@Override
	public void setDateAcct (final @Nullable java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
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
	public void setDocTypeName (final @Nullable java.lang.String DocTypeName)
	{
		set_Value (COLUMNNAME_DocTypeName, DocTypeName);
	}

	@Override
	public java.lang.String getDocTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_DocTypeName);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getDR_Tax_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setDR_Tax_Acct(final org.compiere.model.I_C_ValidCombination DR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, DR_Tax_Acct);
	}

	@Override
	public void setDR_Tax_Acct_ID (final int DR_Tax_Acct_ID)
	{
		if (DR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, DR_Tax_Acct_ID);
	}

	@Override
	public int getDR_Tax_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_Tax_Acct_ID);
	}

	@Override
	public void setDR_Tax_ID (final int DR_Tax_ID)
	{
		if (DR_Tax_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_ID, DR_Tax_ID);
	}

	@Override
	public int getDR_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_Tax_ID);
	}

	@Override
	public void setDR_TaxName (final @Nullable java.lang.String DR_TaxName)
	{
		set_Value (COLUMNNAME_DR_TaxName, DR_TaxName);
	}

	@Override
	public java.lang.String getDR_TaxName() 
	{
		return get_ValueAsString(COLUMNNAME_DR_TaxName);
	}

	@Override
	public void setDR_TaxTotalAmt (final @Nullable BigDecimal DR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_DR_TaxTotalAmt, DR_TaxTotalAmt);
	}

	@Override
	public BigDecimal getDR_TaxTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DR_TaxTotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Budget_ID, GL_Budget_ID);
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
			set_Value (COLUMNNAME_GL_Category_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Category_ID, GL_Category_ID);
	}

	@Override
	public int getGL_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Category_ID);
	}

	@Override
	public org.compiere.model.I_GL_JournalBatch getGL_JournalBatch()
	{
		return get_ValueAsPO(COLUMNNAME_GL_JournalBatch_ID, org.compiere.model.I_GL_JournalBatch.class);
	}

	@Override
	public void setGL_JournalBatch(final org.compiere.model.I_GL_JournalBatch GL_JournalBatch)
	{
		set_ValueFromPO(COLUMNNAME_GL_JournalBatch_ID, org.compiere.model.I_GL_JournalBatch.class, GL_JournalBatch);
	}

	@Override
	public void setGL_JournalBatch_ID (final int GL_JournalBatch_ID)
	{
		if (GL_JournalBatch_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalBatch_ID, GL_JournalBatch_ID);
	}

	@Override
	public int getGL_JournalBatch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_JournalBatch_ID);
	}

	@Override
	public org.compiere.model.I_GL_Journal getGL_Journal()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class);
	}

	@Override
	public void setGL_Journal(final org.compiere.model.I_GL_Journal GL_Journal)
	{
		set_ValueFromPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class, GL_Journal);
	}

	@Override
	public void setGL_Journal_ID (final int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_Value (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Journal_ID, GL_Journal_ID);
	}

	@Override
	public int getGL_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Journal_ID);
	}

	@Override
	public org.compiere.model.I_GL_JournalLine getGL_JournalLine()
	{
		return get_ValueAsPO(COLUMNNAME_GL_JournalLine_ID, org.compiere.model.I_GL_JournalLine.class);
	}

	@Override
	public void setGL_JournalLine(final org.compiere.model.I_GL_JournalLine GL_JournalLine)
	{
		set_ValueFromPO(COLUMNNAME_GL_JournalLine_ID, org.compiere.model.I_GL_JournalLine.class, GL_JournalLine);
	}

	@Override
	public void setGL_JournalLine_ID (final int GL_JournalLine_ID)
	{
		if (GL_JournalLine_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalLine_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalLine_ID, GL_JournalLine_ID);
	}

	@Override
	public int getGL_JournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_JournalLine_ID);
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
	public void setI_GLJournal_ID (final int I_GLJournal_ID)
	{
		if (I_GLJournal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_GLJournal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_GLJournal_ID, I_GLJournal_ID);
	}

	@Override
	public int getI_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_GLJournal_ID);
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

	@Override
	public void setIsCreateNewBatch (final boolean IsCreateNewBatch)
	{
		set_Value (COLUMNNAME_IsCreateNewBatch, IsCreateNewBatch);
	}

	@Override
	public boolean isCreateNewBatch() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateNewBatch);
	}

	@Override
	public void setIsCreateNewJournal (final boolean IsCreateNewJournal)
	{
		set_Value (COLUMNNAME_IsCreateNewJournal, IsCreateNewJournal);
	}

	@Override
	public boolean isCreateNewJournal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateNewJournal);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setJournalDocumentNo (final @Nullable java.lang.String JournalDocumentNo)
	{
		set_Value (COLUMNNAME_JournalDocumentNo, JournalDocumentNo);
	}

	@Override
	public java.lang.String getJournalDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_JournalDocumentNo);
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
	public void setOrgTrxValue (final @Nullable java.lang.String OrgTrxValue)
	{
		set_Value (COLUMNNAME_OrgTrxValue, OrgTrxValue);
	}

	@Override
	public java.lang.String getOrgTrxValue() 
	{
		return get_ValueAsString(COLUMNNAME_OrgTrxValue);
	}

	@Override
	public void setOrgValue (final @Nullable java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	@Override
	public java.lang.String getOrgValue() 
	{
		return get_ValueAsString(COLUMNNAME_OrgValue);
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
	public void setPostingType (final @Nullable java.lang.String PostingType)
	{
		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	@Override
	public java.lang.String getPostingType() 
	{
		return get_ValueAsString(COLUMNNAME_PostingType);
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
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setProjectValue (final @Nullable java.lang.String ProjectValue)
	{
		set_Value (COLUMNNAME_ProjectValue, ProjectValue);
	}

	@Override
	public java.lang.String getProjectValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectValue);
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
	public void setSKU (final @Nullable java.lang.String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	@Override
	public java.lang.String getSKU() 
	{
		return get_ValueAsString(COLUMNNAME_SKU);
	}

	@Override
	public void setTaxAccountValueFrom (final @Nullable java.lang.String TaxAccountValueFrom)
	{
		set_Value (COLUMNNAME_TaxAccountValueFrom, TaxAccountValueFrom);
	}

	@Override
	public java.lang.String getTaxAccountValueFrom() 
	{
		return get_ValueAsString(COLUMNNAME_TaxAccountValueFrom);
	}

	@Override
	public void setTaxAccountValueTo (final @Nullable java.lang.String TaxAccountValueTo)
	{
		set_Value (COLUMNNAME_TaxAccountValueTo, TaxAccountValueTo);
	}

	@Override
	public java.lang.String getTaxAccountValueTo() 
	{
		return get_ValueAsString(COLUMNNAME_TaxAccountValueTo);
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
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
}