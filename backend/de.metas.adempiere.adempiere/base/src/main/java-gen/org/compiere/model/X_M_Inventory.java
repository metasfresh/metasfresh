// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Inventory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Inventory extends org.compiere.model.PO implements I_M_Inventory, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -355940860L;

	/** Standard Constructor */
	public X_M_Inventory (final Properties ctx, final int M_Inventory_ID, @Nullable final String trxName)
	{
		super (ctx, M_Inventory_ID, trxName);
	}

	/** Load Constructor */
	public X_M_Inventory (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApprovalAmt (final @Nullable BigDecimal ApprovalAmt)
	{
		set_Value (COLUMNNAME_ApprovalAmt, ApprovalAmt);
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ApprovalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_C_Order getC_PO_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_PO_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_PO_Order(final org.compiere.model.I_C_Order C_PO_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_PO_Order_ID, org.compiere.model.I_C_Order.class, C_PO_Order);
	}

	@Override
	public void setC_PO_Order_ID (final int C_PO_Order_ID)
	{
		if (C_PO_Order_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PO_Order_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PO_Order_ID, C_PO_Order_ID);
	}

	@Override
	public int getC_PO_Order_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_PO_Order_ID);
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
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
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
	public void setGenerateList (final @Nullable java.lang.String GenerateList)
	{
		set_Value (COLUMNNAME_GenerateList, GenerateList);
	}

	@Override
	public java.lang.String getGenerateList()
	{
		return get_ValueAsString(COLUMNNAME_GenerateList);
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
	public void setM_Inventory_ID (final int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, M_Inventory_ID);
	}

	@Override
	public int getM_Inventory_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public org.compiere.model.I_M_PerpetualInv getM_PerpetualInv()
	{
		return get_ValueAsPO(COLUMNNAME_M_PerpetualInv_ID, org.compiere.model.I_M_PerpetualInv.class);
	}

	@Override
	public void setM_PerpetualInv(final org.compiere.model.I_M_PerpetualInv M_PerpetualInv)
	{
		set_ValueFromPO(COLUMNNAME_M_PerpetualInv_ID, org.compiere.model.I_M_PerpetualInv.class, M_PerpetualInv);
	}

	@Override
	public void setM_PerpetualInv_ID (final int M_PerpetualInv_ID)
	{
		if (M_PerpetualInv_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_PerpetualInv_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_PerpetualInv_ID, M_PerpetualInv_ID);
	}

	@Override
	public int getM_PerpetualInv_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_PerpetualInv_ID);
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

	@Override
	public void setMovementDate (final java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
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
	public org.compiere.model.I_M_Inventory getReversal()
	{
		return get_ValueAsPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setReversal(final org.compiere.model.I_M_Inventory Reversal)
	{
		set_ValueFromPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_M_Inventory.class, Reversal);
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
	public void setUpdateQty (final @Nullable java.lang.String UpdateQty)
	{
		set_Value (COLUMNNAME_UpdateQty, UpdateQty);
	}

	@Override
	public java.lang.String getUpdateQty()
	{
		return get_ValueAsString(COLUMNNAME_UpdateQty);
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
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1)
			set_Value (COLUMNNAME_M_Picking_Job_ID, null);
		else
			set_Value (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
	}
}