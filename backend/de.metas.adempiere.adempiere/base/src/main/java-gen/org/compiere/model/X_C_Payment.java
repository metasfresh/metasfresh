/**
 * Generated Model - DO NOT CHANGE
 */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Payment extends org.compiere.model.PO implements I_C_Payment, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1880893568L;

	/** Standard Constructor */
	public X_C_Payment(Properties ctx, int C_Payment_ID, String trxName)
	{
		super(ctx, C_Payment_ID, trxName);
	}

	/** Load Constructor */
	public X_C_Payment(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setA_City(java.lang.String A_City)
	{
		set_Value(COLUMNNAME_A_City, A_City);
	}

	@Override
	public java.lang.String getA_City()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_City);
	}

	@Override
	public void setA_Country(java.lang.String A_Country)
	{
		set_Value(COLUMNNAME_A_Country, A_Country);
	}

	@Override
	public java.lang.String getA_Country()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Country);
	}

	@Override
	public void setA_EMail(java.lang.String A_EMail)
	{
		set_Value(COLUMNNAME_A_EMail, A_EMail);
	}

	@Override
	public java.lang.String getA_EMail()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_EMail);
	}

	@Override
	public void setA_Ident_DL(java.lang.String A_Ident_DL)
	{
		set_Value(COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	@Override
	public java.lang.String getA_Ident_DL()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_DL);
	}

	@Override
	public void setA_Ident_SSN(java.lang.String A_Ident_SSN)
	{
		set_Value(COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	@Override
	public java.lang.String getA_Ident_SSN()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_SSN);
	}

	@Override
	public void setA_Name(java.lang.String A_Name)
	{
		set_Value(COLUMNNAME_A_Name, A_Name);
	}

	@Override
	public java.lang.String getA_Name()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Name);
	}

	@Override
	public void setA_State(java.lang.String A_State)
	{
		set_Value(COLUMNNAME_A_State, A_State);
	}

	@Override
	public java.lang.String getA_State()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_State);
	}

	@Override
	public void setA_Street(java.lang.String A_Street)
	{
		set_Value(COLUMNNAME_A_Street, A_Street);
	}

	@Override
	public java.lang.String getA_Street()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Street);
	}

	@Override
	public void setA_Zip(java.lang.String A_Zip)
	{
		set_Value(COLUMNNAME_A_Zip, A_Zip);
	}

	@Override
	public java.lang.String getA_Zip()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Zip);
	}

	@Override
	public void setAccountNo(java.lang.String AccountNo)
	{
		set_Value(COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	@Override
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1)
			set_Value(COLUMNNAME_AD_OrgTrx_ID, null);
		else
			set_Value(COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	@Override
	public int getAD_OrgTrx_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
	}

	@Override
	public void setC_Activity_ID(int C_Activity_ID)
	{
		if (C_Activity_ID < 1)
			set_Value(COLUMNNAME_C_Activity_ID, null);
		else
			set_Value(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	@Override
	public int getC_Activity_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_BankStatement_ID(int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1)
			set_Value(COLUMNNAME_C_BankStatement_ID, null);
		else
			set_Value(COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	@Override
	public int getC_BankStatement_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID(int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1)
			set_Value(COLUMNNAME_C_BankStatementLine_ID, null);
		else
			set_Value(COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	@Override
	public int getC_BankStatementLine_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public void setC_BankStatementLine_Ref_ID(int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1)
			set_Value(COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else
			set_Value(COLUMNNAME_C_BankStatementLine_Ref_ID, Integer.valueOf(C_BankStatementLine_Ref_ID));
	}

	@Override
	public int getC_BankStatementLine_Ref_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1)
			set_Value(COLUMNNAME_C_BP_BankAccount_ID, null);
		else
			set_Value(COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	@Override
	public int getC_BP_BankAccount_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID(int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1)
			set_Value(COLUMNNAME_C_Campaign_ID, null);
		else
			set_Value(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
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
	public void setC_CashBook(org.compiere.model.I_C_CashBook C_CashBook)
	{
		set_ValueFromPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class, C_CashBook);
	}

	@Override
	public void setC_CashBook_ID(int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1)
			set_Value(COLUMNNAME_C_CashBook_ID, null);
		else
			set_Value(COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	@Override
	public int getC_CashBook_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_CashBook_ID);
	}

	@Override
	public void setC_Charge_ID(int C_Charge_ID)
	{
		if (C_Charge_ID < 1)
			set_Value(COLUMNNAME_C_Charge_ID, null);
		else
			set_Value(COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	@Override
	public int getC_Charge_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public void setC_ConversionType_ID(int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1)
			set_Value(COLUMNNAME_C_ConversionType_ID, null);
		else
			set_Value(COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	@Override
	public int getC_ConversionType_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionType_ID);
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value(COLUMNNAME_C_Currency_ID, null);
		else
			set_Value(COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_DocType_ID(int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_Value(COLUMNNAME_C_DocType_ID, null);
		else
			set_Value(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID(int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1)
			set_Value(COLUMNNAME_C_Invoice_ID, null);
		else
			set_Value(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID(int C_Order_ID)
	{
		if (C_Order_ID < 1)
			set_Value(COLUMNNAME_C_Order_ID, null);
		else
			set_Value(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Payment_ID(int C_Payment_ID)
	{
		if (C_Payment_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_Payment_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	@Override
	public int getC_Payment_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public org.compiere.model.I_C_PaymentBatch getC_PaymentBatch()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class);
	}

	@Override
	public void setC_PaymentBatch(org.compiere.model.I_C_PaymentBatch C_PaymentBatch)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class, C_PaymentBatch);
	}

	@Override
	public void setC_PaymentBatch_ID(int C_PaymentBatch_ID)
	{
		if (C_PaymentBatch_ID < 1)
			set_Value(COLUMNNAME_C_PaymentBatch_ID, null);
		else
			set_Value(COLUMNNAME_C_PaymentBatch_ID, Integer.valueOf(C_PaymentBatch_ID));
	}

	@Override
	public int getC_PaymentBatch_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentBatch_ID);
	}

	@Override
	public void setC_Project_ID(int C_Project_ID)
	{
		if (C_Project_ID < 1)
			set_Value(COLUMNNAME_C_Project_ID, null);
		else
			set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	@Override
	public int getC_Project_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setChargeAmt(java.math.BigDecimal ChargeAmt)
	{
		set_Value(COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	@Override
	public java.math.BigDecimal getChargeAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCheckNo(java.lang.String CheckNo)
	{
		set_Value(COLUMNNAME_CheckNo, CheckNo);
	}

	@Override
	public java.lang.String getCheckNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CheckNo);
	}

	@Override
	public void setCreditCardExpMM(int CreditCardExpMM)
	{
		set_Value(COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
	}

	@Override
	public int getCreditCardExpMM()
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpMM);
	}

	@Override
	public void setCreditCardExpYY(int CreditCardExpYY)
	{
		set_Value(COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
	}

	@Override
	public int getCreditCardExpYY()
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpYY);
	}

	@Override
	public void setCreditCardNumber(java.lang.String CreditCardNumber)
	{
		set_Value(COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	@Override
	public java.lang.String getCreditCardNumber()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardNumber);
	}

	/**
	 * CreditCardType AD_Reference_ID=149
	 * Reference name: C_Payment CreditCard Type
	 */
	public static final int CREDITCARDTYPE_AD_Reference_ID = 149;
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
	public void setCreditCardType(java.lang.String CreditCardType)
	{

		set_Value(COLUMNNAME_CreditCardType, CreditCardType);
	}

	@Override
	public java.lang.String getCreditCardType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardType);
	}

	@Override
	public void setCreditCardVV(java.lang.String CreditCardVV)
	{
		set_Value(COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	@Override
	public java.lang.String getCreditCardVV()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardVV);
	}

	@Override
	public void setDateAcct(java.sql.Timestamp DateAcct)
	{
		set_Value(COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateTrx(java.sql.Timestamp DateTrx)
	{
		set_Value(COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	@Override
	public void setDescription(java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setDiscountAmt(java.math.BigDecimal DiscountAmt)
	{
		set_Value(COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	@Override
	public java.math.BigDecimal getDiscountAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/**
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID = 135;
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
	public void setDocAction(java.lang.String DocAction)
	{

		set_Value(COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/**
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID = 131;
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
	public void setDocStatus(java.lang.String DocStatus)
	{

		set_Value(COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo(java.lang.String DocumentNo)
	{
		set_ValueNoCheck(COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setExternalOrderId(java.lang.String ExternalOrderId)
	{
		set_Value(COLUMNNAME_ExternalOrderId, ExternalOrderId);
	}

	@Override
	public java.lang.String getExternalOrderId()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalOrderId);
	}

	@Override
	public void setIsAllocated(boolean IsAllocated)
	{
		set_Value(COLUMNNAME_IsAllocated, Boolean.valueOf(IsAllocated));
	}

	@Override
	public boolean isAllocated()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllocated);
	}

	@Override
	public void setIsApproved(boolean IsApproved)
	{
		set_ValueNoCheck(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	@Override
	public boolean isApproved()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsAutoAllocateAvailableAmt(boolean IsAutoAllocateAvailableAmt)
	{
		set_Value(COLUMNNAME_IsAutoAllocateAvailableAmt, Boolean.valueOf(IsAutoAllocateAvailableAmt));
	}

	@Override
	public boolean isAutoAllocateAvailableAmt()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoAllocateAvailableAmt);
	}

	@Override
	public void setIsDelayedCapture(boolean IsDelayedCapture)
	{
		set_Value(COLUMNNAME_IsDelayedCapture, Boolean.valueOf(IsDelayedCapture));
	}

	@Override
	public boolean isDelayedCapture()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelayedCapture);
	}

	@Override
	public void setIsOnline(boolean IsOnline)
	{
		set_Value(COLUMNNAME_IsOnline, Boolean.valueOf(IsOnline));
	}

	@Override
	public boolean isOnline()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnline);
	}

	@Override
	public void setIsOnlineApproved(boolean IsOnlineApproved)
	{
		set_Value(COLUMNNAME_IsOnlineApproved, Boolean.valueOf(IsOnlineApproved));
	}

	@Override
	public boolean isOnlineApproved()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOnlineApproved);
	}

	@Override
	public void setIsOverUnderPayment(boolean IsOverUnderPayment)
	{
		set_Value(COLUMNNAME_IsOverUnderPayment, Boolean.valueOf(IsOverUnderPayment));
	}

	@Override
	public boolean isOverUnderPayment()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverUnderPayment);
	}

	@Override
	public void setIsPrepayment(boolean IsPrepayment)
	{
		set_Value(COLUMNNAME_IsPrepayment, Boolean.valueOf(IsPrepayment));
	}

	@Override
	public boolean isPrepayment()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrepayment);
	}

	@Override
	public void setIsReceipt(boolean IsReceipt)
	{
		set_Value(COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	@Override
	public boolean isReceipt()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceipt);
	}

	@Override
	public void setIsReconciled(boolean IsReconciled)
	{
		set_Value(COLUMNNAME_IsReconciled, Boolean.valueOf(IsReconciled));
	}

	@Override
	public boolean isReconciled()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReconciled);
	}

	@Override
	public void setIsSelfService(boolean IsSelfService)
	{
		set_Value(COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	@Override
	public boolean isSelfService()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelfService);
	}

	@Override
	public void setMicr(java.lang.String Micr)
	{
		set_Value(COLUMNNAME_Micr, Micr);
	}

	@Override
	public java.lang.String getMicr()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Micr);
	}

	@Override
	public void setOrig_TrxID(java.lang.String Orig_TrxID)
	{
		set_Value(COLUMNNAME_Orig_TrxID, Orig_TrxID);
	}

	@Override
	public java.lang.String getOrig_TrxID()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Orig_TrxID);
	}

	@Override
	public void setOverUnderAmt(java.math.BigDecimal OverUnderAmt)
	{
		set_Value(COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	@Override
	public java.math.BigDecimal getOverUnderAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OverUnderAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPayAmt(java.math.BigDecimal PayAmt)
	{
		set_Value(COLUMNNAME_PayAmt, PayAmt);
	}

	@Override
	public java.math.BigDecimal getPayAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PayAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/**
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID = 195;
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

	@Override
	public void setPaymentRule(java.lang.String PaymentRule)
	{

		set_Value(COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setPONum(java.lang.String PONum)
	{
		set_Value(COLUMNNAME_PONum, PONum);
	}

	@Override
	public java.lang.String getPONum()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PONum);
	}

	@Override
	public void setPosted(boolean Posted)
	{
		set_Value(COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	@Override
	public boolean isPosted()
	{
		return get_ValueAsBoolean(COLUMNNAME_Posted);
	}

	@Override
	public org.compiere.model.I_AD_Issue getPostingError_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_PostingError_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setPostingError_Issue(org.compiere.model.I_AD_Issue PostingError_Issue)
	{
		set_ValueFromPO(COLUMNNAME_PostingError_Issue_ID, org.compiere.model.I_AD_Issue.class, PostingError_Issue);
	}

	@Override
	public void setPostingError_Issue_ID(int PostingError_Issue_ID)
	{
		if (PostingError_Issue_ID < 1)
			set_Value(COLUMNNAME_PostingError_Issue_ID, null);
		else
			set_Value(COLUMNNAME_PostingError_Issue_ID, Integer.valueOf(PostingError_Issue_ID));
	}

	@Override
	public int getPostingError_Issue_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PostingError_Issue_ID);
	}

	@Override
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed()
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing(boolean Processing)
	{
		set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing()
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setR_AuthCode(java.lang.String R_AuthCode)
	{
		set_ValueNoCheck(COLUMNNAME_R_AuthCode, R_AuthCode);
	}

	@Override
	public java.lang.String getR_AuthCode()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AuthCode);
	}

	@Override
	public void setR_AuthCode_DC(java.lang.String R_AuthCode_DC)
	{
		set_ValueNoCheck(COLUMNNAME_R_AuthCode_DC, R_AuthCode_DC);
	}

	@Override
	public java.lang.String getR_AuthCode_DC()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AuthCode_DC);
	}

	/**
	 * R_AvsAddr AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSADDR_AD_Reference_ID = 213;
	/** Match = Y */
	public static final String R_AVSADDR_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSADDR_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSADDR_Unavailable = "X";

	@Override
	public void setR_AvsAddr(java.lang.String R_AvsAddr)
	{

		set_ValueNoCheck(COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	@Override
	public java.lang.String getR_AvsAddr()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AvsAddr);
	}

	/**
	 * R_AvsZip AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSZIP_AD_Reference_ID = 213;
	/** Match = Y */
	public static final String R_AVSZIP_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSZIP_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSZIP_Unavailable = "X";

	@Override
	public void setR_AvsZip(java.lang.String R_AvsZip)
	{

		set_ValueNoCheck(COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	@Override
	public java.lang.String getR_AvsZip()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AvsZip);
	}

	@Override
	public void setR_CVV2Match(boolean R_CVV2Match)
	{
		set_ValueNoCheck(COLUMNNAME_R_CVV2Match, Boolean.valueOf(R_CVV2Match));
	}

	@Override
	public boolean isR_CVV2Match()
	{
		return get_ValueAsBoolean(COLUMNNAME_R_CVV2Match);
	}

	@Override
	public void setR_Info(java.lang.String R_Info)
	{
		set_ValueNoCheck(COLUMNNAME_R_Info, R_Info);
	}

	@Override
	public java.lang.String getR_Info()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Info);
	}

	@Override
	public void setR_PnRef(java.lang.String R_PnRef)
	{
		set_Value(COLUMNNAME_R_PnRef, R_PnRef);
	}

	@Override
	public java.lang.String getR_PnRef()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_PnRef);
	}

	@Override
	public void setR_PnRef_DC(java.lang.String R_PnRef_DC)
	{
		set_ValueNoCheck(COLUMNNAME_R_PnRef_DC, R_PnRef_DC);
	}

	@Override
	public java.lang.String getR_PnRef_DC()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_PnRef_DC);
	}

	@Override
	public void setR_RespMsg(java.lang.String R_RespMsg)
	{
		set_ValueNoCheck(COLUMNNAME_R_RespMsg, R_RespMsg);
	}

	@Override
	public java.lang.String getR_RespMsg()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_RespMsg);
	}

	@Override
	public void setR_Result(java.lang.String R_Result)
	{
		set_ValueNoCheck(COLUMNNAME_R_Result, R_Result);
	}

	@Override
	public java.lang.String getR_Result()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Result);
	}

	@Override
	public void setRef_Payment_ID(int Ref_Payment_ID)
	{
		if (Ref_Payment_ID < 1)
			set_ValueNoCheck(COLUMNNAME_Ref_Payment_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_Ref_Payment_ID, Integer.valueOf(Ref_Payment_ID));
	}

	@Override
	public int getRef_Payment_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Ref_Payment_ID);
	}

	@Override
	public void setReversal_ID(int Reversal_ID)
	{
		if (Reversal_ID < 1)
			set_Value(COLUMNNAME_Reversal_ID, null);
		else
			set_Value(COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	@Override
	public int getReversal_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Reversal_ID);
	}

	@Override
	public void setRoutingNo(java.lang.String RoutingNo)
	{
		set_Value(COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setSwipe(java.lang.String Swipe)
	{
		set_ValueNoCheck(COLUMNNAME_Swipe, Swipe);
	}

	@Override
	public java.lang.String getSwipe()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Swipe);
	}

	@Override
	public void setTaxAmt(java.math.BigDecimal TaxAmt)
	{
		set_Value(COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public java.math.BigDecimal getTaxAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/**
	 * TenderType AD_Reference_ID=214
	 * Reference name: C_Payment Tender Type
	 */
	public static final int TENDERTYPE_AD_Reference_ID = 214;
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
	public void setTenderType(java.lang.String TenderType)
	{

		set_Value(COLUMNNAME_TenderType, TenderType);
	}

	@Override
	public java.lang.String getTenderType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TenderType);
	}

	/**
	 * TrxType AD_Reference_ID=215
	 * Reference name: C_Payment Trx Type
	 */
	public static final int TRXTYPE_AD_Reference_ID = 215;
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
	public void setTrxType(java.lang.String TrxType)
	{

		set_Value(COLUMNNAME_TrxType, TrxType);
	}

	@Override
	public java.lang.String getTrxType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrxType);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID(int User1_ID)
	{
		if (User1_ID < 1)
			set_Value(COLUMNNAME_User1_ID, null);
		else
			set_Value(COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
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
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID(int User2_ID)
	{
		if (User2_ID < 1)
			set_Value(COLUMNNAME_User2_ID, null);
		else
			set_Value(COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	@Override
	public int getUser2_ID()
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}

	@Override
	public void setVoiceAuthCode(java.lang.String VoiceAuthCode)
	{
		set_Value(COLUMNNAME_VoiceAuthCode, VoiceAuthCode);
	}

	@Override
	public java.lang.String getVoiceAuthCode()
	{
		return (java.lang.String)get_Value(COLUMNNAME_VoiceAuthCode);
	}

	@Override
	public void setWriteOffAmt(java.math.BigDecimal WriteOffAmt)
	{
		set_Value(COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	@Override
	public java.math.BigDecimal getWriteOffAmt()
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WriteOffAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}
