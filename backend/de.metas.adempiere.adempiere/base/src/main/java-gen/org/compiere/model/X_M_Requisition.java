// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Requisition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Requisition extends org.compiere.model.PO implements I_M_Requisition, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2138257910L;

    /** Standard Constructor */
    public X_M_Requisition (final Properties ctx, final int M_Requisition_ID, @Nullable final String trxName)
    {
      super (ctx, M_Requisition_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Requisition (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAuthor_ID (final int Author_ID)
	{
		if (Author_ID < 1) 
			set_Value (COLUMNNAME_Author_ID, null);
		else 
			set_Value (COLUMNNAME_Author_ID, Author_ID);
	}

	@Override
	public int getAuthor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Author_ID);
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
	public org.compiere.model.I_C_ProjectType getC_ProjectType()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectType_ID, org.compiere.model.I_C_ProjectType.class);
	}

	@Override
	public void setC_ProjectType(final org.compiere.model.I_C_ProjectType C_ProjectType)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectType_ID, org.compiere.model.I_C_ProjectType.class, C_ProjectType);
	}

	@Override
	public void setC_ProjectType_ID (final int C_ProjectType_ID)
	{
		if (C_ProjectType_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectType_ID, C_ProjectType_ID);
	}

	@Override
	public int getC_ProjectType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectType_ID);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDateRequired (final java.sql.Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	@Override
	public java.sql.Timestamp getDateRequired() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateRequired);
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
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
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
	public void setIsBudgetPlanned (final boolean IsBudgetPlanned)
	{
		set_Value (COLUMNNAME_IsBudgetPlanned, IsBudgetPlanned);
	}

	@Override
	public boolean isBudgetPlanned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBudgetPlanned);
	}

	@Override
	public void setIsQuotesExist (final boolean IsQuotesExist)
	{
		set_Value (COLUMNNAME_IsQuotesExist, IsQuotesExist);
	}

	@Override
	public boolean isQuotesExist() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQuotesExist);
	}

	@Override
	public void setMissingBudgetNote (final @Nullable java.lang.String MissingBudgetNote)
	{
		set_Value (COLUMNNAME_MissingBudgetNote, MissingBudgetNote);
	}

	@Override
	public java.lang.String getMissingBudgetNote() 
	{
		return get_ValueAsString(COLUMNNAME_MissingBudgetNote);
	}

	@Override
	public void setMissingQuoteNote (final @Nullable java.lang.String MissingQuoteNote)
	{
		set_Value (COLUMNNAME_MissingQuoteNote, MissingQuoteNote);
	}

	@Override
	public java.lang.String getMissingQuoteNote() 
	{
		return get_ValueAsString(COLUMNNAME_MissingQuoteNote);
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
	public void setM_Requisition_ID (final int M_Requisition_ID)
	{
		if (M_Requisition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, M_Requisition_ID);
	}

	@Override
	public int getM_Requisition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Requisition_ID);
	}

	@Override
	public void setM_Requisition_includedTab (final @Nullable java.lang.String M_Requisition_includedTab)
	{
		set_ValueNoCheck (COLUMNNAME_M_Requisition_includedTab, M_Requisition_includedTab);
	}

	@Override
	public java.lang.String getM_Requisition_includedTab() 
	{
		return get_ValueAsString(COLUMNNAME_M_Requisition_includedTab);
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
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setOrderNote (final @Nullable java.lang.String OrderNote)
	{
		set_Value (COLUMNNAME_OrderNote, OrderNote);
	}

	@Override
	public java.lang.String getOrderNote() 
	{
		return get_ValueAsString(COLUMNNAME_OrderNote);
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
	public void setPurchaseOrderNo (final @Nullable java.lang.String PurchaseOrderNo)
	{
		set_Value (COLUMNNAME_PurchaseOrderNo, PurchaseOrderNo);
	}

	@Override
	public java.lang.String getPurchaseOrderNo() 
	{
		return get_ValueAsString(COLUMNNAME_PurchaseOrderNo);
	}

	@Override
	public void setQuoteDate (final @Nullable java.sql.Timestamp QuoteDate)
	{
		set_Value (COLUMNNAME_QuoteDate, QuoteDate);
	}

	@Override
	public java.sql.Timestamp getQuoteDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_QuoteDate);
	}

	@Override
	public void setQuoteNumber (final @Nullable java.lang.String QuoteNumber)
	{
		set_Value (COLUMNNAME_QuoteNumber, QuoteNumber);
	}

	@Override
	public java.lang.String getQuoteNumber() 
	{
		return get_ValueAsString(COLUMNNAME_QuoteNumber);
	}

	@Override
	public void setReceiver_ID (final int Receiver_ID)
	{
		if (Receiver_ID < 1) 
			set_Value (COLUMNNAME_Receiver_ID, null);
		else 
			set_Value (COLUMNNAME_Receiver_ID, Receiver_ID);
	}

	@Override
	public int getReceiver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Receiver_ID);
	}

	@Override
	public void setTotalLines (final BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	@Override
	public BigDecimal getTotalLines() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWarehouse_Location_ID (final int Warehouse_Location_ID)
	{
		if (Warehouse_Location_ID < 1) 
			set_Value (COLUMNNAME_Warehouse_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Warehouse_Location_ID, Warehouse_Location_ID);
	}

	@Override
	public int getWarehouse_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Warehouse_Location_ID);
	}
}