// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Invoice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice extends org.compiere.model.PO implements I_C_Invoice, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1369968982L;

    /** Standard Constructor */
    public X_C_Invoice (final Properties ctx, final int C_Invoice_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBeneficiary_BPartner_ID (final int Beneficiary_BPartner_ID)
	{
		if (Beneficiary_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_BPartner_ID, Beneficiary_BPartner_ID);
	}

	@Override
	public int getBeneficiary_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Beneficiary_BPartner_ID);
	}

	@Override
	public void setBeneficiary_Contact_ID (final int Beneficiary_Contact_ID)
	{
		if (Beneficiary_Contact_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_Contact_ID, Beneficiary_Contact_ID);
	}

	@Override
	public int getBeneficiary_Contact_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Beneficiary_Contact_ID);
	}

	@Override
	public void setBeneficiary_Location_ID (final int Beneficiary_Location_ID)
	{
		if (Beneficiary_Location_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_Location_ID, Beneficiary_Location_ID);
	}

	@Override
	public int getBeneficiary_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Beneficiary_Location_ID);
	}

	@Override
	public void setBPartnerAddress (final @Nullable java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress);
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
	public org.compiere.model.I_C_Auction getC_Auction()
	{
		return get_ValueAsPO(COLUMNNAME_C_Auction_ID, org.compiere.model.I_C_Auction.class);
	}

	@Override
	public void setC_Auction(final org.compiere.model.I_C_Auction C_Auction)
	{
		set_ValueFromPO(COLUMNNAME_C_Auction_ID, org.compiere.model.I_C_Auction.class, C_Auction);
	}

	@Override
	public void setC_Auction_ID (final int C_Auction_ID)
	{
		if (C_Auction_ID < 1) 
			set_Value (COLUMNNAME_C_Auction_ID, null);
		else 
			set_Value (COLUMNNAME_C_Auction_ID, C_Auction_ID);
	}

	@Override
	public int getC_Auction_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Auction_ID);
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
	public void setC_BPartner_SalesRep_ID (final int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, C_BPartner_SalesRep_ID);
	}

	@Override
	public int getC_BPartner_SalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_SalesRep_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
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
	public org.compiere.model.I_C_CashLine getC_CashLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class);
	}

	@Override
	public void setC_CashLine(final org.compiere.model.I_C_CashLine C_CashLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class, C_CashLine);
	}

	@Override
	public void setC_CashLine_ID (final int C_CashLine_ID)
	{
		if (C_CashLine_ID < 1) 
			set_Value (COLUMNNAME_C_CashLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashLine_ID, C_CashLine_ID);
	}

	@Override
	public int getC_CashLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CashLine_ID);
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
	public void setC_DocTypeTarget_ID (final int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, C_DocTypeTarget_ID);
	}

	@Override
	public int getC_DocTypeTarget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeTarget_ID);
	}

	@Override
	public org.compiere.model.I_C_DunningLevel getC_DunningLevel()
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class);
	}

	@Override
	public void setC_DunningLevel(final org.compiere.model.I_C_DunningLevel C_DunningLevel)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class, C_DunningLevel);
	}

	@Override
	public void setC_DunningLevel_ID (final int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningLevel_ID, C_DunningLevel_ID);
	}

	@Override
	public int getC_DunningLevel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DunningLevel_ID);
	}

	@Override
	public org.compiere.model.I_C_ForeignExchangeContract getC_ForeignExchangeContract()
	{
		return get_ValueAsPO(COLUMNNAME_C_ForeignExchangeContract_ID, org.compiere.model.I_C_ForeignExchangeContract.class);
	}

	@Override
	public void setC_ForeignExchangeContract(final org.compiere.model.I_C_ForeignExchangeContract C_ForeignExchangeContract)
	{
		set_ValueFromPO(COLUMNNAME_C_ForeignExchangeContract_ID, org.compiere.model.I_C_ForeignExchangeContract.class, C_ForeignExchangeContract);
	}

	@Override
	public void setC_ForeignExchangeContract_ID (final int C_ForeignExchangeContract_ID)
	{
		if (C_ForeignExchangeContract_ID < 1) 
			set_Value (COLUMNNAME_C_ForeignExchangeContract_ID, null);
		else 
			set_Value (COLUMNNAME_C_ForeignExchangeContract_ID, C_ForeignExchangeContract_ID);
	}

	@Override
	public int getC_ForeignExchangeContract_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ForeignExchangeContract_ID);
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
	public org.compiere.model.I_C_Incoterms getC_Incoterms()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms(final org.compiere.model.I_C_Incoterms C_Incoterms)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms);
	}

	@Override
	public void setC_Incoterms_ID (final int C_Incoterms_ID)
	{
		if (C_Incoterms_ID < 1) 
			set_Value (COLUMNNAME_C_Incoterms_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_ID, C_Incoterms_ID);
	}

	@Override
	public int getC_Incoterms_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_ID);
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
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public org.compiere.model.I_C_PaymentInstruction getC_PaymentInstruction()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentInstruction_ID, org.compiere.model.I_C_PaymentInstruction.class);
	}

	@Override
	public void setC_PaymentInstruction(final org.compiere.model.I_C_PaymentInstruction C_PaymentInstruction)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentInstruction_ID, org.compiere.model.I_C_PaymentInstruction.class, C_PaymentInstruction);
	}

	@Override
	public void setC_PaymentInstruction_ID (final int C_PaymentInstruction_ID)
	{
		if (C_PaymentInstruction_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentInstruction_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentInstruction_ID, C_PaymentInstruction_ID);
	}

	@Override
	public int getC_PaymentInstruction_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentInstruction_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
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
	public void setCreateAdjustmentCharge (final @Nullable java.lang.String CreateAdjustmentCharge)
	{
		set_Value (COLUMNNAME_CreateAdjustmentCharge, CreateAdjustmentCharge);
	}

	@Override
	public java.lang.String getCreateAdjustmentCharge() 
	{
		return get_ValueAsString(COLUMNNAME_CreateAdjustmentCharge);
	}

	@Override
	public void setCreateCreditMemo (final @Nullable java.lang.String CreateCreditMemo)
	{
		set_Value (COLUMNNAME_CreateCreditMemo, CreateCreditMemo);
	}

	@Override
	public java.lang.String getCreateCreditMemo() 
	{
		return get_ValueAsString(COLUMNNAME_CreateCreditMemo);
	}

	@Override
	public void setCreateDta (final @Nullable java.lang.String CreateDta)
	{
		set_Value (COLUMNNAME_CreateDta, CreateDta);
	}

	@Override
	public java.lang.String getCreateDta() 
	{
		return get_ValueAsString(COLUMNNAME_CreateDta);
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

	/** 
	 * CreditMemoReason AD_Reference_ID=540014
	 * Reference name: C_CreditMemo_Reason
	 */
	public static final int CREDITMEMOREASON_AD_Reference_ID=540014;
	/** Falschlieferung = CMF */
	public static final String CREDITMEMOREASON_Falschlieferung = "CMF";
	/** Doppellieferung = CMD */
	public static final String CREDITMEMOREASON_Doppellieferung = "CMD";
	@Override
	public void setCreditMemoReason (final @Nullable java.lang.String CreditMemoReason)
	{
		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	@Override
	public java.lang.String getCreditMemoReason() 
	{
		return get_ValueAsString(COLUMNNAME_CreditMemoReason);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Tax_Departure_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Tax_Departure_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Tax_Departure_Country(final org.compiere.model.I_C_Country C_Tax_Departure_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Tax_Departure_Country_ID, org.compiere.model.I_C_Country.class, C_Tax_Departure_Country);
	}

	@Override
	public void setC_Tax_Departure_Country_ID (final int C_Tax_Departure_Country_ID)
	{
		if (C_Tax_Departure_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_Departure_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_Departure_Country_ID, C_Tax_Departure_Country_ID);
	}

	@Override
	public int getC_Tax_Departure_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_Departure_Country_ID);
	}

	@Override
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateInvoiced (final java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
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

	@Override
	public void setDocBaseType (final @Nullable java.lang.String DocBaseType)
	{
		throw new IllegalArgumentException ("DocBaseType is virtual column");	}

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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public void setDunningGrace (final @Nullable java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	@Override
	public java.sql.Timestamp getDunningGrace() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningGrace);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setFEC_CurrencyRate (final @Nullable BigDecimal FEC_CurrencyRate)
	{
		set_Value (COLUMNNAME_FEC_CurrencyRate, FEC_CurrencyRate);
	}

	@Override
	public BigDecimal getFEC_CurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FEC_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFEC_From_Currency_ID (final int FEC_From_Currency_ID)
	{
		if (FEC_From_Currency_ID < 1) 
			set_Value (COLUMNNAME_FEC_From_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_FEC_From_Currency_ID, FEC_From_Currency_ID);
	}

	@Override
	public int getFEC_From_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_FEC_From_Currency_ID);
	}

	@Override
	public void setFEC_Order_Currency_ID (final int FEC_Order_Currency_ID)
	{
		if (FEC_Order_Currency_ID < 1) 
			set_Value (COLUMNNAME_FEC_Order_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_FEC_Order_Currency_ID, FEC_Order_Currency_ID);
	}

	@Override
	public int getFEC_Order_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_FEC_Order_Currency_ID);
	}

	@Override
	public void setFEC_To_Currency_ID (final int FEC_To_Currency_ID)
	{
		if (FEC_To_Currency_ID < 1) 
			set_Value (COLUMNNAME_FEC_To_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_FEC_To_Currency_ID, FEC_To_Currency_ID);
	}

	@Override
	public int getFEC_To_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_FEC_To_Currency_ID);
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
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setInvoiceAdditionalText (final @Nullable java.lang.String InvoiceAdditionalText)
	{
		set_Value (COLUMNNAME_InvoiceAdditionalText, InvoiceAdditionalText);
	}

	@Override
	public java.lang.String getInvoiceAdditionalText() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceAdditionalText);
	}

	/** 
	 * InvoiceCollectionType AD_Reference_ID=394
	 * Reference name: C_Invoice InvoiceCollectionType
	 */
	public static final int INVOICECOLLECTIONTYPE_AD_Reference_ID=394;
	/** Dunning = D */
	public static final String INVOICECOLLECTIONTYPE_Dunning = "D";
	/** CollectionAgency = C */
	public static final String INVOICECOLLECTIONTYPE_CollectionAgency = "C";
	/** LegalProcedure = L */
	public static final String INVOICECOLLECTIONTYPE_LegalProcedure = "L";
	/** Uncollectable = U */
	public static final String INVOICECOLLECTIONTYPE_Uncollectable = "U";
	@Override
	public void setInvoiceCollectionType (final @Nullable java.lang.String InvoiceCollectionType)
	{
		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	@Override
	public java.lang.String getInvoiceCollectionType() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceCollectionType);
	}

	@Override
	public void setInvoice_includedTab (final @Nullable java.lang.String Invoice_includedTab)
	{
		set_Value (COLUMNNAME_Invoice_includedTab, Invoice_includedTab);
	}

	@Override
	public java.lang.String getInvoice_includedTab() 
	{
		return get_ValueAsString(COLUMNNAME_Invoice_includedTab);
	}

	@Override
	public void setIsApproved (final boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsDiscountPrinted (final boolean IsDiscountPrinted)
	{
		set_Value (COLUMNNAME_IsDiscountPrinted, IsDiscountPrinted);
	}

	@Override
	public boolean isDiscountPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountPrinted);
	}

	@Override
	public void setIsFEC (final boolean IsFEC)
	{
		set_Value (COLUMNNAME_IsFEC, IsFEC);
	}

	@Override
	public boolean isFEC() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFEC);
	}

	@Override
	public void setIsFixedInvoice (final boolean IsFixedInvoice)
	{
		set_ValueNoCheck (COLUMNNAME_IsFixedInvoice, IsFixedInvoice);
	}

	@Override
	public boolean isFixedInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFixedInvoice);
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
	public void setIsNotShowOriginCountry (final boolean IsNotShowOriginCountry)
	{
		set_Value (COLUMNNAME_IsNotShowOriginCountry, IsNotShowOriginCountry);
	}

	@Override
	public boolean isNotShowOriginCountry() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNotShowOriginCountry);
	}

	@Override
	public void setIsPaid (final boolean IsPaid)
	{
		set_Value (COLUMNNAME_IsPaid, IsPaid);
	}

	@Override
	public boolean isPaid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPaid);
	}

	@Override
	public void setIsPayScheduleValid (final boolean IsPayScheduleValid)
	{
		set_ValueNoCheck (COLUMNNAME_IsPayScheduleValid, IsPayScheduleValid);
	}

	@Override
	public boolean isPayScheduleValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPayScheduleValid);
	}

	@Override
	public void setIsPrinted (final boolean IsPrinted)
	{
		set_ValueNoCheck (COLUMNNAME_IsPrinted, IsPrinted);
	}

	@Override
	public boolean isPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrinted);
	}

	/** 
	 * IsPrintLocalCurrencyInfo AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISPRINTLOCALCURRENCYINFO_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISPRINTLOCALCURRENCYINFO_Yes = "Y";
	/** No = N */
	public static final String ISPRINTLOCALCURRENCYINFO_No = "N";
	@Override
	public void setIsPrintLocalCurrencyInfo (final @Nullable java.lang.String IsPrintLocalCurrencyInfo)
	{
		set_Value (COLUMNNAME_IsPrintLocalCurrencyInfo, IsPrintLocalCurrencyInfo);
	}

	@Override
	public java.lang.String getIsPrintLocalCurrencyInfo() 
	{
		return get_ValueAsString(COLUMNNAME_IsPrintLocalCurrencyInfo);
	}

	@Override
	public void setIsSalesPartnerRequired (final boolean IsSalesPartnerRequired)
	{
		set_Value (COLUMNNAME_IsSalesPartnerRequired, IsSalesPartnerRequired);
	}

	@Override
	public boolean isSalesPartnerRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesPartnerRequired);
	}

	@Override
	public void setIsSelfService (final boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, IsSelfService);
	}

	@Override
	public boolean isSelfService() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelfService);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
	}

	@Override
	public void setIsTransferred (final boolean IsTransferred)
	{
		set_ValueNoCheck (COLUMNNAME_IsTransferred, IsTransferred);
	}

	@Override
	public boolean isTransferred() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTransferred);
	}

	@Override
	public void setIsUseBPartnerAddress (final boolean IsUseBPartnerAddress)
	{
		set_Value (COLUMNNAME_IsUseBPartnerAddress, IsUseBPartnerAddress);
	}

	@Override
	public boolean isUseBPartnerAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBPartnerAddress);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public org.compiere.model.I_M_RMA getM_RMA()
	{
		return get_ValueAsPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class);
	}

	@Override
	public void setM_RMA(final org.compiere.model.I_M_RMA M_RMA)
	{
		set_ValueFromPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class, M_RMA);
	}

	@Override
	public void setM_RMA_ID (final int M_RMA_ID)
	{
		if (M_RMA_ID < 1) 
			set_Value (COLUMNNAME_M_RMA_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMA_ID, M_RMA_ID);
	}

	@Override
	public int getM_RMA_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_RMA_ID);
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

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
	@Override
	public void setPaymentRule (final java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
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
	public void setProcessed (final boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Processed);
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
	public org.compiere.model.I_C_Invoice getRef_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setRef_Invoice(final org.compiere.model.I_C_Invoice Ref_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Invoice_ID, org.compiere.model.I_C_Invoice.class, Ref_Invoice);
	}

	@Override
	public void setRef_Invoice_ID (final int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Ref_Invoice_ID);
	}

	@Override
	public int getRef_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getReversal()
	{
		return get_ValueAsPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setReversal(final org.compiere.model.I_C_Invoice Reversal)
	{
		set_ValueFromPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_C_Invoice.class, Reversal);
	}

	@Override
	public void setReversal_ID (final int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Reversal_ID);
	}

	@Override
	public int getReversal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Reversal_ID);
	}

	@Override
	public void setSales_Invoice_Count (final int Sales_Invoice_Count)
	{
		throw new IllegalArgumentException ("Sales_Invoice_Count is virtual column");	}

	@Override
	public int getSales_Invoice_Count() 
	{
		return get_ValueAsInt(COLUMNNAME_Sales_Invoice_Count);
	}

	@Override
	public void setSalesPartnerCode (final @Nullable java.lang.String SalesPartnerCode)
	{
		set_Value (COLUMNNAME_SalesPartnerCode, SalesPartnerCode);
	}

	@Override
	public java.lang.String getSalesPartnerCode() 
	{
		return get_ValueAsString(COLUMNNAME_SalesPartnerCode);
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
	public void setTotalLines (final BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
	}

	@Override
	public BigDecimal getTotalLines() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setUserFlag (final @Nullable java.lang.String UserFlag)
	{
		set_Value (COLUMNNAME_UserFlag, UserFlag);
	}

	@Override
	public java.lang.String getUserFlag() 
	{
		return get_ValueAsString(COLUMNNAME_UserFlag);
	}
}