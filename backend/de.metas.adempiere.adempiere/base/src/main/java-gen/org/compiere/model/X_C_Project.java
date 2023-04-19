// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Project
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project extends org.compiere.model.PO implements I_C_Project, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 949373025L;

    /** Standard Constructor */
    public X_C_Project (final Properties ctx, final int C_Project_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerDepartment (final @Nullable java.lang.String BPartnerDepartment)
	{
		set_Value (COLUMNNAME_BPartnerDepartment, BPartnerDepartment);
	}

	@Override
	public java.lang.String getBPartnerDepartment()
	{
		return get_ValueAsString(COLUMNNAME_BPartnerDepartment);
	}

	@Override
	public void setBPartnerTargetDate (final @Nullable java.sql.Timestamp BPartnerTargetDate)
	{
		set_Value (COLUMNNAME_BPartnerTargetDate, BPartnerTargetDate);
	}

	@Override
	public java.sql.Timestamp getBPartnerTargetDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_BPartnerTargetDate);
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
	public void setC_BPartnerSR_ID (final int C_BPartnerSR_ID)
	{
		if (C_BPartnerSR_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, C_BPartnerSR_ID);
	}

	@Override
	public int getC_BPartnerSR_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerSR_ID);
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
	public void setCommittedAmt (final BigDecimal CommittedAmt)
	{
		set_Value (COLUMNNAME_CommittedAmt, CommittedAmt);
	}

	@Override
	public BigDecimal getCommittedAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCommittedQty (final BigDecimal CommittedQty)
	{
		set_Value (COLUMNNAME_CommittedQty, CommittedQty);
	}

	@Override
	public BigDecimal getCommittedQty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedQty);
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
	public org.compiere.model.I_C_Phase getC_Phase()
	{
		return get_ValueAsPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class);
	}

	@Override
	public void setC_Phase(final org.compiere.model.I_C_Phase C_Phase)
	{
		set_ValueFromPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class, C_Phase);
	}

	@Override
	public void setC_Phase_ID (final int C_Phase_ID)
	{
		if (C_Phase_ID < 1) 
			set_Value (COLUMNNAME_C_Phase_ID, null);
		else 
			set_Value (COLUMNNAME_C_Phase_ID, C_Phase_ID);
	}

	@Override
	public int getC_Phase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Phase_ID);
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
	public org.compiere.model.I_C_Project_Label getC_Project_Label()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_Label_ID, org.compiere.model.I_C_Project_Label.class);
	}

	@Override
	public void setC_Project_Label(final org.compiere.model.I_C_Project_Label C_Project_Label)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_Label_ID, org.compiere.model.I_C_Project_Label.class, C_Project_Label);
	}

	@Override
	public void setC_Project_Label_ID (final int C_Project_Label_ID)
	{
		if (C_Project_Label_ID < 1) 
			set_Value (COLUMNNAME_C_Project_Label_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_Label_ID, C_Project_Label_ID);
	}

	@Override
	public int getC_Project_Label_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Label_ID);
	}

	@Override
	public void setC_Project_Parent_ID (final int C_Project_Parent_ID)
	{
		if (C_Project_Parent_ID < 1)
			set_Value (COLUMNNAME_C_Project_Parent_ID, null);
		else
			set_Value (COLUMNNAME_C_Project_Parent_ID, C_Project_Parent_ID);
	}

	@Override
	public int getC_Project_Parent_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Parent_ID);
	}

	@Override
	public void setC_Project_Reference_Ext (final @Nullable java.lang.String C_Project_Reference_Ext)
	{
		set_Value (COLUMNNAME_C_Project_Reference_Ext, C_Project_Reference_Ext);
	}

	@Override
	public java.lang.String getC_Project_Reference_Ext()
	{
		return get_ValueAsString(COLUMNNAME_C_Project_Reference_Ext);
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
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, C_ProjectType_ID);
	}

	@Override
	public int getC_ProjectType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectType_ID);
	}

	@Override
	public void setDateContract (final @Nullable java.sql.Timestamp DateContract)
	{
		set_Value (COLUMNNAME_DateContract, DateContract);
	}

	@Override
	public java.sql.Timestamp getDateContract() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateContract);
	}

	@Override
	public void setDateFinish (final @Nullable java.sql.Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	@Override
	public java.sql.Timestamp getDateFinish() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinish);
	}

	@Override
	public void setDateOfProvisionByBPartner (final @Nullable java.sql.Timestamp DateOfProvisionByBPartner)
	{
		set_Value (COLUMNNAME_DateOfProvisionByBPartner, DateOfProvisionByBPartner);
	}

	@Override
	public java.sql.Timestamp getDateOfProvisionByBPartner()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOfProvisionByBPartner);
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
	public void setGenerateTo (final @Nullable java.lang.String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	@Override
	public java.lang.String getGenerateTo() 
	{
		return get_ValueAsString(COLUMNNAME_GenerateTo);
	}

	/**
	 * InternalPriority AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int INTERNALPRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String INTERNALPRIORITY_High = "3";
	/** Medium = 5 */
	public static final String INTERNALPRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String INTERNALPRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String INTERNALPRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String INTERNALPRIORITY_Minor = "9";
	@Override
	public void setInternalPriority (final @Nullable java.lang.String InternalPriority)
	{
		set_Value (COLUMNNAME_InternalPriority, InternalPriority);
	}

	@Override
	public java.lang.String getInternalPriority()
	{
		return get_ValueAsString(COLUMNNAME_InternalPriority);
	}

	@Override
	public void setInvoicedAmt (final BigDecimal InvoicedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicedAmt, InvoicedAmt);
	}

	@Override
	public BigDecimal getInvoicedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoicedQty (final BigDecimal InvoicedQty)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicedQty, InvoicedQty);
	}

	@Override
	public BigDecimal getInvoicedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsCommitCeiling (final boolean IsCommitCeiling)
	{
		set_Value (COLUMNNAME_IsCommitCeiling, IsCommitCeiling);
	}

	@Override
	public boolean isCommitCeiling() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommitCeiling);
	}

	@Override
	public void setIsCommitment (final boolean IsCommitment)
	{
		set_Value (COLUMNNAME_IsCommitment, IsCommitment);
	}

	@Override
	public boolean isCommitment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommitment);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_Value (COLUMNNAME_PlannedAmt, PlannedAmt);
	}

	@Override
	public BigDecimal getPlannedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedMarginAmt (final BigDecimal PlannedMarginAmt)
	{
		set_Value (COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
	}

	@Override
	public BigDecimal getPlannedMarginAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedMarginAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedQty (final BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	@Override
	public BigDecimal getPlannedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQty);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setProjectBalanceAmt (final BigDecimal ProjectBalanceAmt)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectBalanceAmt, ProjectBalanceAmt);
	}

	@Override
	public BigDecimal getProjectBalanceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProjectBalanceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ProjectCategory AD_Reference_ID=288
	 * Reference name: C_ProjectType Category
	 */
	public static final int PROJECTCATEGORY_AD_Reference_ID=288;
	/** General = N */
	public static final String PROJECTCATEGORY_General = "N";
	/** AssetProject = A */
	public static final String PROJECTCATEGORY_AssetProject = "A";
	/** WorkOrderJob = W */
	public static final String PROJECTCATEGORY_WorkOrderJob = "W";
	/** ServiceChargeProject = S */
	public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
	/** ServiceOrRepair = R */
	public static final String PROJECTCATEGORY_ServiceOrRepair = "R";
	/** Budget = B */
	public static final String PROJECTCATEGORY_Budget = "B";
	@Override
	public void setProjectCategory (final @Nullable java.lang.String ProjectCategory)
	{
		set_Value (COLUMNNAME_ProjectCategory, ProjectCategory);
	}

	@Override
	public java.lang.String getProjectCategory() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectCategory);
	}

	/** 
	 * ProjectLineLevel AD_Reference_ID=384
	 * Reference name: C_Project LineLevel
	 */
	public static final int PROJECTLINELEVEL_AD_Reference_ID=384;
	/** Project = P */
	public static final String PROJECTLINELEVEL_Project = "P";
	/** Phase = A */
	public static final String PROJECTLINELEVEL_Phase = "A";
	/** Task = T */
	public static final String PROJECTLINELEVEL_Task = "T";
	@Override
	public void setProjectLineLevel (final java.lang.String ProjectLineLevel)
	{
		set_Value (COLUMNNAME_ProjectLineLevel, ProjectLineLevel);
	}

	@Override
	public java.lang.String getProjectLineLevel() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectLineLevel);
	}

	/** 
	 * ProjInvoiceRule AD_Reference_ID=383
	 * Reference name: C_Project InvoiceRule
	 */
	public static final int PROJINVOICERULE_AD_Reference_ID=383;
	/** None = - */
	public static final String PROJINVOICERULE_None = "-";
	/** Committed Amount = C */
	public static final String PROJINVOICERULE_CommittedAmount = "C";
	/** Time&Material max Comitted = c */
	public static final String PROJINVOICERULE_TimeMaterialMaxComitted = "c";
	/** Time&Material = T */
	public static final String PROJINVOICERULE_TimeMaterial = "T";
	/** Product  Quantity = P */
	public static final String PROJINVOICERULE_ProductQuantity = "P";
	@Override
	public void setProjInvoiceRule (final java.lang.String ProjInvoiceRule)
	{
		set_Value (COLUMNNAME_ProjInvoiceRule, ProjInvoiceRule);
	}

	@Override
	public java.lang.String getProjInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_ProjInvoiceRule);
	}

	@Override
	public org.compiere.model.I_R_Status getR_Project_Status()
	{
		return get_ValueAsPO(COLUMNNAME_R_Project_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setR_Project_Status(final org.compiere.model.I_R_Status R_Project_Status)
	{
		set_ValueFromPO(COLUMNNAME_R_Project_Status_ID, org.compiere.model.I_R_Status.class, R_Project_Status);
	}

	@Override
	public void setR_Project_Status_ID (final int R_Project_Status_ID)
	{
		if (R_Project_Status_ID < 1) 
			set_Value (COLUMNNAME_R_Project_Status_ID, null);
		else 
			set_Value (COLUMNNAME_R_Project_Status_ID, R_Project_Status_ID);
	}

	@Override
	public int getR_Project_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_Project_Status_ID);
	}

	@Override
	public org.compiere.model.I_R_StatusCategory getR_StatusCategory()
	{
		return get_ValueAsPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class);
	}

	@Override
	public void setR_StatusCategory(final org.compiere.model.I_R_StatusCategory R_StatusCategory)
	{
		set_ValueFromPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class, R_StatusCategory);
	}

	@Override
	public void setR_StatusCategory_ID (final int R_StatusCategory_ID)
	{
		if (R_StatusCategory_ID < 1)
			set_Value (COLUMNNAME_R_StatusCategory_ID, null);
		else
			set_Value (COLUMNNAME_R_StatusCategory_ID, R_StatusCategory_ID);
	}

	@Override
	public int getR_StatusCategory_ID()
	{
		return get_ValueAsInt(COLUMNNAME_R_StatusCategory_ID);
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
	public void setSpecialist_Consultant_ID (final int Specialist_Consultant_ID)
	{
		if (Specialist_Consultant_ID < 1)
			set_Value (COLUMNNAME_Specialist_Consultant_ID, null);
		else
			set_Value (COLUMNNAME_Specialist_Consultant_ID, Specialist_Consultant_ID);
	}

	@Override
	public int getSpecialist_Consultant_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Specialist_Consultant_ID);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setWOOwner (final @Nullable java.lang.String WOOwner)
	{
		set_Value (COLUMNNAME_WOOwner, WOOwner);
	}

	@Override
	public java.lang.String getWOOwner()
	{
		return get_ValueAsString(COLUMNNAME_WOOwner);
	}

	@Override
	public void setWOProjectCreatedDate (final @Nullable java.sql.Timestamp WOProjectCreatedDate)
	{
		set_Value (COLUMNNAME_WOProjectCreatedDate, WOProjectCreatedDate);
	}

	@Override
	public java.sql.Timestamp getWOProjectCreatedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOProjectCreatedDate);
	}
}