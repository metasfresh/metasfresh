// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Header_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Header_v extends org.compiere.model.PO implements I_C_Project_Header_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1944270135L;

    /** Standard Constructor */
    public X_C_Project_Header_v (final Properties ctx, final int C_Project_Header_v_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Header_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Header_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setBPContactGreeting (final @Nullable java.lang.String BPContactGreeting)
	{
		set_ValueNoCheck (COLUMNNAME_BPContactGreeting, BPContactGreeting);
	}

	@Override
	public java.lang.String getBPContactGreeting() 
	{
		return get_ValueAsString(COLUMNNAME_BPContactGreeting);
	}

	@Override
	public void setBPGreeting (final @Nullable java.lang.String BPGreeting)
	{
		set_ValueNoCheck (COLUMNNAME_BPGreeting, BPGreeting);
	}

	@Override
	public java.lang.String getBPGreeting() 
	{
		return get_ValueAsString(COLUMNNAME_BPGreeting);
	}

	@Override
	public void setBPTaxID (final @Nullable java.lang.String BPTaxID)
	{
		set_ValueNoCheck (COLUMNNAME_BPTaxID, BPTaxID);
	}

	@Override
	public java.lang.String getBPTaxID() 
	{
		return get_ValueAsString(COLUMNNAME_BPTaxID);
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
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, C_Phase_ID);
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
	public void setCommittedAmt (final BigDecimal CommittedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CommittedAmt, CommittedAmt);
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
		set_ValueNoCheck (COLUMNNAME_CommittedQty, CommittedQty);
	}

	@Override
	public BigDecimal getCommittedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setContactName (final @Nullable java.lang.String ContactName)
	{
		set_ValueNoCheck (COLUMNNAME_ContactName, ContactName);
	}

	@Override
	public java.lang.String getContactName() 
	{
		return get_ValueAsString(COLUMNNAME_ContactName);
	}

	@Override
	public void setDateContract (final @Nullable java.sql.Timestamp DateContract)
	{
		set_ValueNoCheck (COLUMNNAME_DateContract, DateContract);
	}

	@Override
	public java.sql.Timestamp getDateContract() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateContract);
	}

	@Override
	public void setDateFinish (final @Nullable java.sql.Timestamp DateFinish)
	{
		set_ValueNoCheck (COLUMNNAME_DateFinish, DateFinish);
	}

	@Override
	public java.sql.Timestamp getDateFinish() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinish);
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

	@Override
	public void setDUNS (final @Nullable java.lang.String DUNS)
	{
		set_ValueNoCheck (COLUMNNAME_DUNS, DUNS);
	}

	@Override
	public java.lang.String getDUNS() 
	{
		return get_ValueAsString(COLUMNNAME_DUNS);
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
		set_ValueNoCheck (COLUMNNAME_IsCommitCeiling, IsCommitCeiling);
	}

	@Override
	public boolean isCommitCeiling() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommitCeiling);
	}

	@Override
	public void setIsCommitment (final boolean IsCommitment)
	{
		set_ValueNoCheck (COLUMNNAME_IsCommitment, IsCommitment);
	}

	@Override
	public boolean isCommitment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommitment);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_ValueNoCheck (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo()
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(final org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	@Override
	public void setLogo_ID (final int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Logo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Logo_ID, Logo_ID);
	}

	@Override
	public int getLogo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Logo_ID);
	}

	@Override
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setNAICS (final @Nullable java.lang.String NAICS)
	{
		set_ValueNoCheck (COLUMNNAME_NAICS, NAICS);
	}

	@Override
	public java.lang.String getNAICS() 
	{
		return get_ValueAsString(COLUMNNAME_NAICS);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (final @Nullable java.lang.String Name2)
	{
		set_ValueNoCheck (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return get_ValueAsString(COLUMNNAME_Name2);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_ValueNoCheck (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public org.compiere.model.I_C_Location getOrg_Location()
	{
		return get_ValueAsPO(COLUMNNAME_Org_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setOrg_Location(final org.compiere.model.I_C_Location Org_Location)
	{
		set_ValueFromPO(COLUMNNAME_Org_Location_ID, org.compiere.model.I_C_Location.class, Org_Location);
	}

	@Override
	public void setOrg_Location_ID (final int Org_Location_ID)
	{
		if (Org_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Org_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Org_Location_ID, Org_Location_ID);
	}

	@Override
	public int getOrg_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_Location_ID);
	}

	@Override
	public void setPaymentTerm (final @Nullable java.lang.String PaymentTerm)
	{
		set_ValueNoCheck (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	@Override
	public java.lang.String getPaymentTerm() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentTerm);
	}

	@Override
	public void setPaymentTermNote (final @Nullable java.lang.String PaymentTermNote)
	{
		set_ValueNoCheck (COLUMNNAME_PaymentTermNote, PaymentTermNote);
	}

	@Override
	public java.lang.String getPaymentTermNote() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentTermNote);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_ValueNoCheck (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return get_ValueAsString(COLUMNNAME_Phone);
	}

	@Override
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedAmt, PlannedAmt);
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
		set_ValueNoCheck (COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
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
		set_ValueNoCheck (COLUMNNAME_PlannedQty, PlannedQty);
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
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
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
	@Override
	public void setProjectCategory (final @Nullable java.lang.String ProjectCategory)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectCategory, ProjectCategory);
	}

	@Override
	public java.lang.String getProjectCategory() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectCategory);
	}

	@Override
	public void setProjectName (final java.lang.String ProjectName)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectName, ProjectName);
	}

	@Override
	public java.lang.String getProjectName() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectName);
	}

	@Override
	public void setProjectPhaseName (final @Nullable java.lang.String ProjectPhaseName)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectPhaseName, ProjectPhaseName);
	}

	@Override
	public java.lang.String getProjectPhaseName() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectPhaseName);
	}

	@Override
	public void setProjectTypeName (final @Nullable java.lang.String ProjectTypeName)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectTypeName, ProjectTypeName);
	}

	@Override
	public java.lang.String getProjectTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectTypeName);
	}

	@Override
	public void setReferenceNo (final @Nullable java.lang.String ReferenceNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setSalesRep_ID (final int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
	}

	@Override
	public void setSalesRep_Name (final @Nullable java.lang.String SalesRep_Name)
	{
		set_ValueNoCheck (COLUMNNAME_SalesRep_Name, SalesRep_Name);
	}

	@Override
	public java.lang.String getSalesRep_Name() 
	{
		return get_ValueAsString(COLUMNNAME_SalesRep_Name);
	}

	@Override
	public void setTaxID (final java.lang.String TaxID)
	{
		set_ValueNoCheck (COLUMNNAME_TaxID, TaxID);
	}

	@Override
	public java.lang.String getTaxID() 
	{
		return get_ValueAsString(COLUMNNAME_TaxID);
	}

	@Override
	public void setTitle (final @Nullable java.lang.String Title)
	{
		set_ValueNoCheck (COLUMNNAME_Title, Title);
	}

	@Override
	public java.lang.String getTitle() 
	{
		return get_ValueAsString(COLUMNNAME_Title);
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
}