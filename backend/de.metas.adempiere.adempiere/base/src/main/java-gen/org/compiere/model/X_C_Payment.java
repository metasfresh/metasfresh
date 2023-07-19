// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Payment extends org.compiere.model.PO implements I_C_Payment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1819883599L;

    /** Standard Constructor */
    public X_C_Payment (final Properties ctx, final int C_Payment_ID, @Nullable final String trxName)
    {
      super (ctx, C_Payment_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Payment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountNo (final @Nullable java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_AccountNo);
	}

	@Override
	public void setA_City (final @Nullable java.lang.String A_City)
	{
		set_Value (COLUMNNAME_A_City, A_City);
	}

	@Override
	public java.lang.String getA_City() 
	{
		return get_ValueAsString(COLUMNNAME_A_City);
	}

	@Override
	public void setA_Country (final @Nullable java.lang.String A_Country)
	{
		set_Value (COLUMNNAME_A_Country, A_Country);
	}

	@Override
	public java.lang.String getA_Country() 
	{
		return get_ValueAsString(COLUMNNAME_A_Country);
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
	public void setA_EMail (final @Nullable java.lang.String A_EMail)
	{
		set_Value (COLUMNNAME_A_EMail, A_EMail);
	}

	@Override
	public java.lang.String getA_EMail() 
	{
		return get_ValueAsString(COLUMNNAME_A_EMail);
	}

	@Override
	public void setA_Ident_DL (final @Nullable java.lang.String A_Ident_DL)
	{
		set_Value (COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	@Override
	public java.lang.String getA_Ident_DL() 
	{
		return get_ValueAsString(COLUMNNAME_A_Ident_DL);
	}

	@Override
	public void setA_Ident_SSN (final @Nullable java.lang.String A_Ident_SSN)
	{
		set_Value (COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	@Override
	public java.lang.String getA_Ident_SSN() 
	{
		return get_ValueAsString(COLUMNNAME_A_Ident_SSN);
	}

	@Override
	public void setA_Name (final @Nullable java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	@Override
	public java.lang.String getA_Name() 
	{
		return get_ValueAsString(COLUMNNAME_A_Name);
	}

	@Override
	public void setA_State (final @Nullable java.lang.String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	@Override
	public java.lang.String getA_State() 
	{
		return get_ValueAsString(COLUMNNAME_A_State);
	}

	@Override
	public void setA_Street (final @Nullable java.lang.String A_Street)
	{
		set_Value (COLUMNNAME_A_Street, A_Street);
	}

	@Override
	public java.lang.String getA_Street() 
	{
		return get_ValueAsString(COLUMNNAME_A_Street);
	}

	@Override
	public void setA_Zip (final @Nullable java.lang.String A_Zip)
	{
		set_Value (COLUMNNAME_A_Zip, A_Zip);
	}

	@Override
	public java.lang.String getA_Zip() 
	{
		return get_ValueAsString(COLUMNNAME_A_Zip);
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
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID (final int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, C_BankStatementLine_ID);
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public void setC_BankStatementLine_Ref_ID (final int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, C_BankStatementLine_Ref_ID);
	}

	@Override
	public int getC_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
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
	public org.compiere.model.I_C_CashBook getC_CashBook()
	{
		return get_ValueAsPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class);
	}

	@Override
	public void setC_CashBook(final org.compiere.model.I_C_CashBook C_CashBook)
	{
		set_ValueFromPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class, C_CashBook);
	}

	@Override
	public void setC_CashBook_ID (final int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, C_CashBook_ID);
	}

	@Override
	public int getC_CashBook_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CashBook_ID);
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
	public void setCheckNo (final @Nullable java.lang.String CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, CheckNo);
	}

	@Override
	public java.lang.String getCheckNo() 
	{
		return get_ValueAsString(COLUMNNAME_CheckNo);
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
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_PaymentBatch getC_PaymentBatch()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class);
	}

	@Override
	public void setC_PaymentBatch(final org.compiere.model.I_C_PaymentBatch C_PaymentBatch)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class, C_PaymentBatch);
	}

	@Override
	public void setC_PaymentBatch_ID (final int C_PaymentBatch_ID)
	{
		if (C_PaymentBatch_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, C_PaymentBatch_ID);
	}

	@Override
	public int getC_PaymentBatch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentBatch_ID);
	}

	@Override
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
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
	public void setCreditCardExpMM (final int CreditCardExpMM)
	{
		set_Value (COLUMNNAME_CreditCardExpMM, CreditCardExpMM);
	}

	@Override
	public int getCreditCardExpMM() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpMM);
	}

	@Override
	public void setCreditCardExpYY (final int CreditCardExpYY)
	{
		set_Value (COLUMNNAME_CreditCardExpYY, CreditCardExpYY);
	}

	@Override
	public int getCreditCardExpYY() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpYY);
	}

	@Override
	public void setCreditCardNumber (final @Nullable java.lang.String CreditCardNumber)
	{
		set_Value (COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	@Override
	public java.lang.String getCreditCardNumber() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardNumber);
	}

	/** 
	 * CreditCardType AD_Reference_ID=149
	 * Reference name: C_Payment CreditCard Type
	 */
	public static final int CREDITCARDTYPE_AD_Reference_ID=149;
	/** Amex = A */
	public static final String CREDITCARDTYPE_Amex = "A";
	/** MasterCard = M */
	public static final String CREDITCARDTYPE_MasterCard = "M";
	/** Visa = V */
	public static final String CREDITCARDTYPE_Visa = "V";
	/** ATM = C */
	public static final String CREDITCARDTYPE_ATM = "C";
	/** Diners = D */
	public static final String CREDITCARDTYPE_Diners = "D";
	/** Discover = N */
	public static final String CREDITCARDTYPE_Discover = "N";
	/** Purchase Card = P */
	public static final String CREDITCARDTYPE_PurchaseCard = "P";
	@Override
	public void setCreditCardType (final @Nullable java.lang.String CreditCardType)
	{
		set_Value (COLUMNNAME_CreditCardType, CreditCardType);
	}

	@Override
	public java.lang.String getCreditCardType() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardType);
	}

	@Override
	public void setCreditCardVV (final @Nullable java.lang.String CreditCardVV)
	{
		set_Value (COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	@Override
	public java.lang.String getCreditCardVV() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardVV);
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
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
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

	@Override
	public void setDiscountAmt (final @Nullable BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	@Override
	public BigDecimal getDiscountAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_ValueNoCheck (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setExternalOrderId (final @Nullable java.lang.String ExternalOrderId)
	{
		set_Value (COLUMNNAME_ExternalOrderId, ExternalOrderId);
	}

	@Override
	public java.lang.String getExternalOrderId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalOrderId);
	}

	@Override
	public void setIsAllocated (final boolean IsAllocated)
	{
		set_Value (COLUMNNAME_IsAllocated, IsAllocated);
	}

	@Override
	public boolean isAllocated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllocated);
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
	public void setIsAutoAllocateAvailableAmt (final boolean IsAutoAllocateAvailableAmt)
	{
		set_Value (COLUMNNAME_IsAutoAllocateAvailableAmt, IsAutoAllocateAvailableAmt);
	}

	@Override
	public boolean isAutoAllocateAvailableAmt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoAllocateAvailableAmt);
	}

	@Override
	public void setIsDelayedCapture (final boolean IsDelayedCapture)
	{
		set_Value (COLUMNNAME_IsDelayedCapture, IsDelayedCapture);
	}

	@Override
	public boolean isDelayedCapture() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelayedCapture);
	}

	@Override
	public void setIsOnline (final boolean IsOnline)
	{
		set_Value (COLUMNNAME_IsOnline, IsOnline);
	}

	@Override
	public boolean isOnline() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnline);
	}

	@Override
	public void setIsOnlineApproved (final boolean IsOnlineApproved)
	{
		set_Value (COLUMNNAME_IsOnlineApproved, IsOnlineApproved);
	}

	@Override
	public boolean isOnlineApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnlineApproved);
	}

	@Override
	public void setIsOverUnderPayment (final boolean IsOverUnderPayment)
	{
		set_Value (COLUMNNAME_IsOverUnderPayment, IsOverUnderPayment);
	}

	@Override
	public boolean isOverUnderPayment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverUnderPayment);
	}

	@Override
	public void setIsPrepayment (final boolean IsPrepayment)
	{
		set_Value (COLUMNNAME_IsPrepayment, IsPrepayment);
	}

	@Override
	public boolean isPrepayment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrepayment);
	}

	@Override
	public void setIsReceipt (final boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, IsReceipt);
	}

	@Override
	public boolean isReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceipt);
	}

	@Override
	public void setIsReconciled (final boolean IsReconciled)
	{
		set_Value (COLUMNNAME_IsReconciled, IsReconciled);
	}

	@Override
	public boolean isReconciled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReconciled);
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
	public void setMicr (final @Nullable java.lang.String Micr)
	{
		set_Value (COLUMNNAME_Micr, Micr);
	}

	@Override
	public java.lang.String getMicr() 
	{
		return get_ValueAsString(COLUMNNAME_Micr);
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
	public void setOrig_TrxID (final @Nullable java.lang.String Orig_TrxID)
	{
		set_Value (COLUMNNAME_Orig_TrxID, Orig_TrxID);
	}

	@Override
	public java.lang.String getOrig_TrxID() 
	{
		return get_ValueAsString(COLUMNNAME_Orig_TrxID);
	}

	@Override
	public void setOverUnderAmt (final @Nullable BigDecimal OverUnderAmt)
	{
		set_Value (COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	@Override
	public BigDecimal getOverUnderAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OverUnderAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPayAmt (final BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	@Override
	public BigDecimal getPayAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PayAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPONum (final @Nullable java.lang.String PONum)
	{
		set_Value (COLUMNNAME_PONum, PONum);
	}

	@Override
	public java.lang.String getPONum() 
	{
		return get_ValueAsString(COLUMNNAME_PONum);
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
	public void setR_AuthCode (final @Nullable java.lang.String R_AuthCode)
	{
		set_ValueNoCheck (COLUMNNAME_R_AuthCode, R_AuthCode);
	}

	@Override
	public java.lang.String getR_AuthCode() 
	{
		return get_ValueAsString(COLUMNNAME_R_AuthCode);
	}

	@Override
	public void setR_AuthCode_DC (final @Nullable java.lang.String R_AuthCode_DC)
	{
		set_ValueNoCheck (COLUMNNAME_R_AuthCode_DC, R_AuthCode_DC);
	}

	@Override
	public java.lang.String getR_AuthCode_DC() 
	{
		return get_ValueAsString(COLUMNNAME_R_AuthCode_DC);
	}

	/** 
	 * R_AvsAddr AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSADDR_AD_Reference_ID=213;
	/** Match = Y */
	public static final String R_AVSADDR_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSADDR_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSADDR_Unavailable = "X";
	@Override
	public void setR_AvsAddr (final @Nullable java.lang.String R_AvsAddr)
	{
		set_ValueNoCheck (COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	@Override
	public java.lang.String getR_AvsAddr() 
	{
		return get_ValueAsString(COLUMNNAME_R_AvsAddr);
	}

	/** 
	 * R_AvsZip AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSZIP_AD_Reference_ID=213;
	/** Match = Y */
	public static final String R_AVSZIP_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSZIP_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSZIP_Unavailable = "X";
	@Override
	public void setR_AvsZip (final @Nullable java.lang.String R_AvsZip)
	{
		set_ValueNoCheck (COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	@Override
	public java.lang.String getR_AvsZip() 
	{
		return get_ValueAsString(COLUMNNAME_R_AvsZip);
	}

	@Override
	public void setR_CVV2Match (final boolean R_CVV2Match)
	{
		set_ValueNoCheck (COLUMNNAME_R_CVV2Match, R_CVV2Match);
	}

	@Override
	public boolean isR_CVV2Match() 
	{
		return get_ValueAsBoolean(COLUMNNAME_R_CVV2Match);
	}

	@Override
	public void setReconciledBy_SAP_GLJournal_ID (final int ReconciledBy_SAP_GLJournal_ID)
	{
		if (ReconciledBy_SAP_GLJournal_ID < 1) 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournal_ID, null);
		else 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournal_ID, ReconciledBy_SAP_GLJournal_ID);
	}

	@Override
	public int getReconciledBy_SAP_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReconciledBy_SAP_GLJournal_ID);
	}

	@Override
	public void setReconciledBy_SAP_GLJournalLine_ID (final int ReconciledBy_SAP_GLJournalLine_ID)
	{
		if (ReconciledBy_SAP_GLJournalLine_ID < 1) 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID, ReconciledBy_SAP_GLJournalLine_ID);
	}

	@Override
	public int getReconciledBy_SAP_GLJournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID);
	}

	@Override
	public void setRef_Payment_ID (final int Ref_Payment_ID)
	{
		if (Ref_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Ref_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Ref_Payment_ID, Ref_Payment_ID);
	}

	@Override
	public int getRef_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_Payment_ID);
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
	public void setR_Info (final @Nullable java.lang.String R_Info)
	{
		set_ValueNoCheck (COLUMNNAME_R_Info, R_Info);
	}

	@Override
	public java.lang.String getR_Info() 
	{
		return get_ValueAsString(COLUMNNAME_R_Info);
	}

	@Override
	public void setRoutingNo (final @Nullable java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return get_ValueAsString(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setR_PnRef (final @Nullable java.lang.String R_PnRef)
	{
		set_Value (COLUMNNAME_R_PnRef, R_PnRef);
	}

	@Override
	public java.lang.String getR_PnRef() 
	{
		return get_ValueAsString(COLUMNNAME_R_PnRef);
	}

	@Override
	public void setR_PnRef_DC (final @Nullable java.lang.String R_PnRef_DC)
	{
		set_ValueNoCheck (COLUMNNAME_R_PnRef_DC, R_PnRef_DC);
	}

	@Override
	public java.lang.String getR_PnRef_DC() 
	{
		return get_ValueAsString(COLUMNNAME_R_PnRef_DC);
	}

	@Override
	public void setR_RespMsg (final @Nullable java.lang.String R_RespMsg)
	{
		set_ValueNoCheck (COLUMNNAME_R_RespMsg, R_RespMsg);
	}

	@Override
	public java.lang.String getR_RespMsg() 
	{
		return get_ValueAsString(COLUMNNAME_R_RespMsg);
	}

	@Override
	public void setR_Result (final @Nullable java.lang.String R_Result)
	{
		set_ValueNoCheck (COLUMNNAME_R_Result, R_Result);
	}

	@Override
	public java.lang.String getR_Result() 
	{
		return get_ValueAsString(COLUMNNAME_R_Result);
	}

	@Override
	public void setSource_Currency_ID (final int Source_Currency_ID)
	{
		if (Source_Currency_ID < 1) 
			set_Value (COLUMNNAME_Source_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Currency_ID, Source_Currency_ID);
	}

	@Override
	public int getSource_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Currency_ID);
	}

	@Override
	public void setSwipe (final @Nullable java.lang.String Swipe)
	{
		set_ValueNoCheck (COLUMNNAME_Swipe, Swipe);
	}

	@Override
	public java.lang.String getSwipe() 
	{
		return get_ValueAsString(COLUMNNAME_Swipe);
	}

	@Override
	public void setTaxAmt (final @Nullable BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * TenderType AD_Reference_ID=214
	 * Reference name: C_Payment Tender Type
	 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** CreditCard = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** DirectDeposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** DirectDebit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	@Override
	public void setTenderType (final java.lang.String TenderType)
	{
		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	@Override
	public java.lang.String getTenderType() 
	{
		return get_ValueAsString(COLUMNNAME_TenderType);
	}

	/** 
	 * TrxType AD_Reference_ID=215
	 * Reference name: C_Payment Trx Type
	 */
	public static final int TRXTYPE_AD_Reference_ID=215;
	/** Sales = S */
	public static final String TRXTYPE_Sales = "S";
	/** DelayedCapture = D */
	public static final String TRXTYPE_DelayedCapture = "D";
	/** CreditPayment = C */
	public static final String TRXTYPE_CreditPayment = "C";
	/** VoiceAuthorization = F */
	public static final String TRXTYPE_VoiceAuthorization = "F";
	/** Authorization = A */
	public static final String TRXTYPE_Authorization = "A";
	/** Void = V */
	public static final String TRXTYPE_Void = "V";
	/** Rückzahlung = R */
	public static final String TRXTYPE_Rueckzahlung = "R";
	@Override
	public void setTrxType (final java.lang.String TrxType)
	{
		set_Value (COLUMNNAME_TrxType, TrxType);
	}

	@Override
	public java.lang.String getTrxType() 
	{
		return get_ValueAsString(COLUMNNAME_TrxType);
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
	public void setVoiceAuthCode (final @Nullable java.lang.String VoiceAuthCode)
	{
		set_Value (COLUMNNAME_VoiceAuthCode, VoiceAuthCode);
	}

	@Override
	public java.lang.String getVoiceAuthCode() 
	{
		return get_ValueAsString(COLUMNNAME_VoiceAuthCode);
	}

	@Override
	public void setWriteOffAmt (final @Nullable BigDecimal WriteOffAmt)
	{
		set_Value (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	@Override
	public BigDecimal getWriteOffAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WriteOffAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}