// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order extends org.compiere.model.PO implements I_C_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1880352232L;

    /** Standard Constructor */
    public X_C_Order (final Properties ctx, final int C_Order_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmountRefunded (final @Nullable BigDecimal AmountRefunded)
	{
		set_Value (COLUMNNAME_AmountRefunded, AmountRefunded);
	}

	@Override
	public BigDecimal getAmountRefunded() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmountRefunded);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmountTendered (final @Nullable BigDecimal AmountTendered)
	{
		set_Value (COLUMNNAME_AmountTendered, AmountTendered);
	}

	@Override
	public BigDecimal getAmountTendered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmountTendered);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setBill_BPartner_Memo (final @Nullable java.lang.String Bill_BPartner_Memo)
	{
		throw new IllegalArgumentException ("Bill_BPartner_Memo is virtual column");	}

	@Override
	public java.lang.String getBill_BPartner_Memo() 
	{
		return get_ValueAsString(COLUMNNAME_Bill_BPartner_Memo);
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
	public void setBillToAddress (final @Nullable java.lang.String BillToAddress)
	{
		set_Value (COLUMNNAME_BillToAddress, BillToAddress);
	}

	@Override
	public java.lang.String getBillToAddress() 
	{
		return get_ValueAsString(COLUMNNAME_BillToAddress);
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName()
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
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
	public void setC_BPartner_Memo (final @Nullable java.lang.String C_BPartner_Memo)
	{
		throw new IllegalArgumentException ("C_BPartner_Memo is virtual column");	}

	@Override
	public java.lang.String getC_BPartner_Memo() 
	{
		return get_ValueAsString(COLUMNNAME_C_BPartner_Memo);
	}

	@Override
	public void setC_BPartner_Pharmacy_ID (final int C_BPartner_Pharmacy_ID)
	{
		if (C_BPartner_Pharmacy_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Pharmacy_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Pharmacy_ID, C_BPartner_Pharmacy_ID);
	}

	@Override
	public int getC_BPartner_Pharmacy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Pharmacy_ID);
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
	public org.compiere.model.I_C_Order getC_FrameAgreement_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_FrameAgreement_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_FrameAgreement_Order(final org.compiere.model.I_C_Order C_FrameAgreement_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_FrameAgreement_Order_ID, org.compiere.model.I_C_Order.class, C_FrameAgreement_Order);
	}

	@Override
	public void setC_FrameAgreement_Order_ID (final int C_FrameAgreement_Order_ID)
	{
		if (C_FrameAgreement_Order_ID < 1) 
			set_Value (COLUMNNAME_C_FrameAgreement_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_FrameAgreement_Order_ID, C_FrameAgreement_Order_ID);
	}

	@Override
	public int getC_FrameAgreement_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_FrameAgreement_Order_ID);
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
	public void setCompleteOrderDiscount (final @Nullable BigDecimal CompleteOrderDiscount)
	{
		set_Value (COLUMNNAME_CompleteOrderDiscount, CompleteOrderDiscount);
	}

	@Override
	public BigDecimal getCompleteOrderDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CompleteOrderDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_C_POS getC_POS()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class);
	}

	@Override
	public void setC_POS(final org.compiere.model.I_C_POS C_POS)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class, C_POS);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_Value (COLUMNNAME_C_POS_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
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
	public void setCreateCopy (final @Nullable java.lang.String CreateCopy)
	{
		set_Value (COLUMNNAME_CreateCopy, CreateCopy);
	}

	@Override
	public java.lang.String getCreateCopy() 
	{
		return get_ValueAsString(COLUMNNAME_CreateCopy);
	}

	@Override
	public void setCreateNewFromProposal (final @Nullable java.lang.String CreateNewFromProposal)
	{
		set_Value (COLUMNNAME_CreateNewFromProposal, CreateNewFromProposal);
	}

	@Override
	public java.lang.String getCreateNewFromProposal() 
	{
		return get_ValueAsString(COLUMNNAME_CreateNewFromProposal);
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
	public void setDeliveryInfo (final @Nullable java.lang.String DeliveryInfo)
	{
		set_Value (COLUMNNAME_DeliveryInfo, DeliveryInfo);
	}

	@Override
	public java.lang.String getDeliveryInfo() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryInfo);
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

	@Override
	public void setDeliveryToAddress (final @Nullable java.lang.String DeliveryToAddress)
	{
		set_Value (COLUMNNAME_DeliveryToAddress, DeliveryToAddress);
	}

	@Override
	public java.lang.String getDeliveryToAddress() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryToAddress);
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
	public void setDescriptionBottom_BoilerPlate_ID (final int DescriptionBottom_BoilerPlate_ID)
	{
		if (DescriptionBottom_BoilerPlate_ID < 1)
			set_Value (COLUMNNAME_DescriptionBottom_BoilerPlate_ID, null);
		else
			set_Value (COLUMNNAME_DescriptionBottom_BoilerPlate_ID, DescriptionBottom_BoilerPlate_ID);
	}

	@Override
	public int getDescriptionBottom_BoilerPlate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_DescriptionBottom_BoilerPlate_ID);
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
	public void setDocSubType (final @Nullable java.lang.String DocSubType)
	{
		throw new IllegalArgumentException ("DocSubType is virtual column");	}

	@Override
	public java.lang.String getDocSubType() 
	{
		return get_ValueAsString(COLUMNNAME_DocSubType);
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
	public void setDropShip_BPartner_ID (final int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_BPartner_Memo (final @Nullable java.lang.String DropShip_BPartner_Memo)
	{
		throw new IllegalArgumentException ("DropShip_BPartner_Memo is virtual column");	}

	@Override
	public java.lang.String getDropShip_BPartner_Memo() 
	{
		return get_ValueAsString(COLUMNNAME_DropShip_BPartner_Memo);
	}

	@Override
	public void setDropShip_Location_ID (final int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getDropShip_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setDropShip_Location_Value(final org.compiere.model.I_C_Location DropShip_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_Value_ID, org.compiere.model.I_C_Location.class, DropShip_Location_Value);
	}

	@Override
	public void setDropShip_Location_Value_ID (final int DropShip_Location_Value_ID)
	{
		if (DropShip_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_Value_ID, DropShip_Location_Value_ID);
	}

	@Override
	public int getDropShip_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_Value_ID);
	}

	@Override
	public void setDropShip_User_ID (final int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, DropShip_User_ID);
	}

	@Override
	public int getDropShip_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_User_ID);
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
	public void setExternalPurchaseOrderURL (final @Nullable java.lang.String ExternalPurchaseOrderURL)
	{
		set_Value (COLUMNNAME_ExternalPurchaseOrderURL, ExternalPurchaseOrderURL);
	}

	@Override
	public java.lang.String getExternalPurchaseOrderURL() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalPurchaseOrderURL);
	}

	@Override
	public void setFreightAmt (final BigDecimal FreightAmt)
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
	public void setFullyDeliveredAndCompletelyInvoiced (final boolean FullyDeliveredAndCompletelyInvoiced)
	{
		throw new IllegalArgumentException ("FullyDeliveredAndCompletelyInvoiced is virtual column");	}

	@Override
	public boolean isFullyDeliveredAndCompletelyInvoiced()
	{
		return get_ValueAsBoolean(COLUMNNAME_FullyDeliveredAndCompletelyInvoiced);
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
	public void setHandOverAddress (final @Nullable java.lang.String HandOverAddress)
	{
		set_Value (COLUMNNAME_HandOverAddress, HandOverAddress);
	}

	@Override
	public java.lang.String getHandOverAddress() 
	{
		return get_ValueAsString(COLUMNNAME_HandOverAddress);
	}

	@Override
	public void setHandOver_BPartner_Memo (final @Nullable java.lang.String HandOver_BPartner_Memo)
	{
		throw new IllegalArgumentException ("HandOver_BPartner_Memo is virtual column");	}

	@Override
	public java.lang.String getHandOver_BPartner_Memo() 
	{
		return get_ValueAsString(COLUMNNAME_HandOver_BPartner_Memo);
	}

	@Override
	public void setHandOver_Location_ID (final int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getHandOver_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setHandOver_Location_Value(final org.compiere.model.I_C_Location HandOver_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Location_Value_ID, org.compiere.model.I_C_Location.class, HandOver_Location_Value);
	}

	@Override
	public void setHandOver_Location_Value_ID (final int HandOver_Location_Value_ID)
	{
		if (HandOver_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_Value_ID, HandOver_Location_Value_ID);
	}

	@Override
	public int getHandOver_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_Value_ID);
	}

	@Override
	public void setHandOver_Partner_ID (final int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
	}

	@Override
	public void setHandOver_User_ID (final int HandOver_User_ID)
	{
		if (HandOver_User_ID < 1) 
			set_Value (COLUMNNAME_HandOver_User_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_User_ID, HandOver_User_ID);
	}

	@Override
	public int getHandOver_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_User_ID);
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
	public void setInternalDescription (final @Nullable java.lang.String InternalDescription)
	{
		set_Value (COLUMNNAME_InternalDescription, InternalDescription);
	}

	@Override
	public java.lang.String getInternalDescription()
	{
		return get_ValueAsString(COLUMNNAME_InternalDescription);
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
	public void setInvoiceRule (final java.lang.String InvoiceRule)
	{
		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
	}

	/** 
	 * InvoiceStatus AD_Reference_ID=540560
	 * Reference name: Invoice Status
	 */
	public static final int INVOICESTATUS_AD_Reference_ID=540560;
	/** Open = O */
	public static final String INVOICESTATUS_Open = "O";
	/** Partly Invoiced = PI */
	public static final String INVOICESTATUS_PartlyInvoiced = "PI";
	/** Completely Invoiced = CI */
	public static final String INVOICESTATUS_CompletelyInvoiced = "CI";
	@Override
	public void setInvoiceStatus (final @Nullable java.lang.String InvoiceStatus)
	{
		throw new IllegalArgumentException ("InvoiceStatus is virtual column");	}

	@Override
	public java.lang.String getInvoiceStatus() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceStatus);
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
	public void setIsCreditApproved (final boolean IsCreditApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsCreditApproved, IsCreditApproved);
	}

	@Override
	public boolean isCreditApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreditApproved);
	}

	@Override
	public void setIsDelivered (final boolean IsDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_IsDelivered, IsDelivered);
	}

	@Override
	public boolean isDelivered() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelivered);
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
	public void setIsInvoiced (final boolean IsInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_IsInvoiced, IsInvoiced);
	}

	@Override
	public boolean isInvoiced() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiced);
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
	public void setIsOnConsignment (final boolean IsOnConsignment)
	{
		set_Value (COLUMNNAME_IsOnConsignment, IsOnConsignment);
	}

	@Override
	public boolean isOnConsignment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnConsignment);
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
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
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
	public void setIsUseBillToAddress (final boolean IsUseBillToAddress)
	{
		set_Value (COLUMNNAME_IsUseBillToAddress, IsUseBillToAddress);
	}

	@Override
	public boolean isUseBillToAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBillToAddress);
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
	public void setIsUseDeliveryToAddress (final boolean IsUseDeliveryToAddress)
	{
		set_Value (COLUMNNAME_IsUseDeliveryToAddress, IsUseDeliveryToAddress);
	}

	@Override
	public boolean isUseDeliveryToAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseDeliveryToAddress);
	}

	@Override
	public void setIsUseHandOver_Location (final boolean IsUseHandOver_Location)
	{
		set_Value (COLUMNNAME_IsUseHandOver_Location, IsUseHandOver_Location);
	}

	@Override
	public boolean isUseHandOver_Location() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseHandOver_Location);
	}

	@Override
	public org.compiere.model.I_C_Order getLink_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Link_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setLink_Order(final org.compiere.model.I_C_Order Link_Order)
	{
		set_ValueFromPO(COLUMNNAME_Link_Order_ID, org.compiere.model.I_C_Order.class, Link_Order);
	}

	@Override
	public void setLink_Order_ID (final int Link_Order_ID)
	{
		if (Link_Order_ID < 1) 
			set_Value (COLUMNNAME_Link_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Link_Order_ID, Link_Order_ID);
	}

	@Override
	public int getLink_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Link_Order_ID);
	}

	@Override
	public org.compiere.model.I_M_FreightCategory getM_FreightCategory()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class);
	}

	@Override
	public void setM_FreightCategory(final org.compiere.model.I_M_FreightCategory M_FreightCategory)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class, M_FreightCategory);
	}

	@Override
	public void setM_FreightCategory_ID (final int M_FreightCategory_ID)
	{
		if (M_FreightCategory_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCategory_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCategory_ID, M_FreightCategory_ID);
	}

	@Override
	public int getM_FreightCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCategory_ID);
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
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
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
	public void setOrderline_includedTab (final @Nullable java.lang.String Orderline_includedTab)
	{
		set_ValueNoCheck (COLUMNNAME_Orderline_includedTab, Orderline_includedTab);
	}

	@Override
	public java.lang.String getOrderline_includedTab() 
	{
		return get_ValueAsString(COLUMNNAME_Orderline_includedTab);
	}

	/**
	 * OrderStatus AD_Reference_ID=541809
	 * Reference name: Order Status
	 */
	public static final int ORDERSTATUS_AD_Reference_ID=541809;
	/** Drafted = DR */
	public static final String ORDERSTATUS_Drafted = "DR";
	/** Voided = VO */
	public static final String ORDERSTATUS_Voided = "VO";
	/** Completed = CO */
	public static final String ORDERSTATUS_Completed = "CO";
	/** Fully Delivered = FD */
	public static final String ORDERSTATUS_FullyDelivered = "FD";
	/** In Progress = IP */
	public static final String ORDERSTATUS_InProgress = "IP";
	@Override
	public void setOrderStatus (final @Nullable java.lang.String OrderStatus)
	{
		throw new IllegalArgumentException ("OrderStatus is virtual column");	}

	@Override
	public java.lang.String getOrderStatus()
	{
		return get_ValueAsString(COLUMNNAME_OrderStatus);
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
	public void setPay_BPartner_ID (final int Pay_BPartner_ID)
	{
		if (Pay_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Pay_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_BPartner_ID, Pay_BPartner_ID);
	}

	@Override
	public int getPay_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Pay_BPartner_ID);
	}

	@Override
	public void setPay_Location_ID (final int Pay_Location_ID)
	{
		if (Pay_Location_ID < 1) 
			set_Value (COLUMNNAME_Pay_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_Location_ID, Pay_Location_ID);
	}

	@Override
	public int getPay_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Pay_Location_ID);
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
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return get_ValueAsString(COLUMNNAME_Phone);
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
	 * PRINTER_OPTS_IsPrintTotals AD_Reference_ID=541220
	 * Reference name: PRINTER_OPTS_YesNoDefault
	 */
	public static final int PRINTER_OPTS_ISPRINTTOTALS_AD_Reference_ID=541220;
	/** Yes = Y */
	public static final String PRINTER_OPTS_ISPRINTTOTALS_Yes = "Y";
	/** No = N */
	public static final String PRINTER_OPTS_ISPRINTTOTALS_No = "N";
	/** Default = X */
	public static final String PRINTER_OPTS_ISPRINTTOTALS_Default = "X";
	@Override
	public void setPRINTER_OPTS_IsPrintTotals (final java.lang.String PRINTER_OPTS_IsPrintTotals)
	{
		set_Value (COLUMNNAME_PRINTER_OPTS_IsPrintTotals, PRINTER_OPTS_IsPrintTotals);
	}

	@Override
	public java.lang.String getPRINTER_OPTS_IsPrintTotals() 
	{
		return get_ValueAsString(COLUMNNAME_PRINTER_OPTS_IsPrintTotals);
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
	public void setProjectManager_ID (final int ProjectManager_ID)
	{
		if (ProjectManager_ID < 1) 
			set_Value (COLUMNNAME_ProjectManager_ID, null);
		else 
			set_Value (COLUMNNAME_ProjectManager_ID, ProjectManager_ID);
	}

	@Override
	public int getProjectManager_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ProjectManager_ID);
	}

	@Override
	public void setPromotionCode (final @Nullable java.lang.String PromotionCode)
	{
		set_Value (COLUMNNAME_PromotionCode, PromotionCode);
	}

	@Override
	public java.lang.String getPromotionCode() 
	{
		return get_ValueAsString(COLUMNNAME_PromotionCode);
	}

	@Override
	public void setQty_FastInput (final @Nullable BigDecimal Qty_FastInput)
	{
		set_Value (COLUMNNAME_Qty_FastInput, Qty_FastInput);
	}

	@Override
	public BigDecimal getQty_FastInput() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_FastInput);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ReceivedVia AD_Reference_ID=540088
	 * Reference name: ReceivedVia
	 */
	public static final int RECEIVEDVIA_AD_Reference_ID=540088;
	/** Fax = F */
	public static final String RECEIVEDVIA_Fax = "F";
	/** Telefon = T */
	public static final String RECEIVEDVIA_Telefon = "T";
	/** Webshop = W */
	public static final String RECEIVEDVIA_Webshop = "W";
	/** Barverkauf = B */
	public static final String RECEIVEDVIA_Barverkauf = "B";
	/** E-Mail = E */
	public static final String RECEIVEDVIA_E_Mail = "E";
	/** Seminar = S */
	public static final String RECEIVEDVIA_Seminar = "S";
	@Override
	public void setReceivedVia (final @Nullable java.lang.String ReceivedVia)
	{
		set_Value (COLUMNNAME_ReceivedVia, ReceivedVia);
	}

	@Override
	public java.lang.String getReceivedVia() 
	{
		return get_ValueAsString(COLUMNNAME_ReceivedVia);
	}

	@Override
	public void setRef_DateOrder (final @Nullable java.sql.Timestamp Ref_DateOrder)
	{
		throw new IllegalArgumentException ("Ref_DateOrder is virtual column");	}

	@Override
	public java.sql.Timestamp getRef_DateOrder() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Ref_DateOrder);
	}

	@Override
	public org.compiere.model.I_C_Order getRef_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setRef_Order(final org.compiere.model.I_C_Order Ref_Order)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Order_ID, org.compiere.model.I_C_Order.class, Ref_Order);
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
	public org.compiere.model.I_C_Order getRef_Proposal()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Proposal_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setRef_Proposal(final org.compiere.model.I_C_Order Ref_Proposal)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Proposal_ID, org.compiere.model.I_C_Order.class, Ref_Proposal);
	}

	@Override
	public void setRef_Proposal_ID (final int Ref_Proposal_ID)
	{
		if (Ref_Proposal_ID < 1) 
			set_Value (COLUMNNAME_Ref_Proposal_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Proposal_ID, Ref_Proposal_ID);
	}

	@Override
	public int getRef_Proposal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_Proposal_ID);
	}

	@Override
	public void setRequestor_ID (final int Requestor_ID)
	{
		if (Requestor_ID < 1) 
			set_Value (COLUMNNAME_Requestor_ID, null);
		else 
			set_Value (COLUMNNAME_Requestor_ID, Requestor_ID);
	}

	@Override
	public int getRequestor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Requestor_ID);
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
	public void setSalesRepIntern_ID (final int SalesRepIntern_ID)
	{
		if (SalesRepIntern_ID < 1) 
			set_Value (COLUMNNAME_SalesRepIntern_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRepIntern_ID, SalesRepIntern_ID);
	}

	@Override
	public int getSalesRepIntern_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRepIntern_ID);
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