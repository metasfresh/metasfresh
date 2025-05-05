package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Project
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project 
{

	String Table_Name = "C_Project";

//	/** AD_Table_ID=203 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Business partner department.
	 * Abteilung / Kostenträger des Geschäftspartners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerDepartment (@Nullable java.lang.String BPartnerDepartment);

	/**
	 * Get Business partner department.
	 * Abteilung / Kostenträger des Geschäftspartners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerDepartment();

	ModelColumn<I_C_Project, Object> COLUMN_BPartnerDepartment = new ModelColumn<>(I_C_Project.class, "BPartnerDepartment", null);
	String COLUMNNAME_BPartnerDepartment = "BPartnerDepartment";

	/**
	 * Set Target date of business-partner.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerTargetDate (@Nullable java.sql.Timestamp BPartnerTargetDate);

	/**
	 * Get Target date of business-partner.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBPartnerTargetDate();

	ModelColumn<I_C_Project, Object> COLUMN_BPartnerTargetDate = new ModelColumn<>(I_C_Project.class, "BPartnerTargetDate", null);
	String COLUMNNAME_BPartnerTargetDate = "BPartnerTargetDate";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set BPartner (Agent).
	 * Business Partner (Agent or Sales Rep)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartnerSR_ID (int C_BPartnerSR_ID);

	/**
	 * Get BPartner (Agent).
	 * Business Partner (Agent or Sales Rep)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartnerSR_ID();

	String COLUMNNAME_C_BPartnerSR_ID = "C_BPartnerSR_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_Project, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Project.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommittedAmt (BigDecimal CommittedAmt);

	/**
	 * Get Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedAmt();

	ModelColumn<I_C_Project, Object> COLUMN_CommittedAmt = new ModelColumn<>(I_C_Project.class, "CommittedAmt", null);
	String COLUMNNAME_CommittedAmt = "CommittedAmt";

	/**
	 * Set Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommittedQty (BigDecimal CommittedQty);

	/**
	 * Get Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedQty();

	ModelColumn<I_C_Project, Object> COLUMN_CommittedQty = new ModelColumn<>(I_C_Project.class, "CommittedQty", null);
	String COLUMNNAME_CommittedQty = "CommittedQty";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (@Nullable java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFrom();

	ModelColumn<I_C_Project, Object> COLUMN_CopyFrom = new ModelColumn<>(I_C_Project.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Standard-Phase.
	 * Standard Phase of the Project Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Phase_ID (int C_Phase_ID);

	/**
	 * Get Standard-Phase.
	 * Standard Phase of the Project Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Phase_ID();

	@Nullable org.compiere.model.I_C_Phase getC_Phase();

	void setC_Phase(@Nullable org.compiere.model.I_C_Phase C_Phase);

	ModelColumn<I_C_Project, org.compiere.model.I_C_Phase> COLUMN_C_Phase_ID = new ModelColumn<>(I_C_Project.class, "C_Phase_ID", org.compiere.model.I_C_Phase.class);
	String COLUMNNAME_C_Phase_ID = "C_Phase_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	ModelColumn<I_C_Project, Object> COLUMN_C_Project_ID = new ModelColumn<>(I_C_Project.class, "C_Project_ID", null);
	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Projekt Label.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_Label_ID (int C_Project_Label_ID);

	/**
	 * Get Projekt Label.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_Label_ID();

	@Nullable org.compiere.model.I_C_Project_Label getC_Project_Label();

	void setC_Project_Label(@Nullable org.compiere.model.I_C_Project_Label C_Project_Label);

	ModelColumn<I_C_Project, org.compiere.model.I_C_Project_Label> COLUMN_C_Project_Label_ID = new ModelColumn<>(I_C_Project.class, "C_Project_Label_ID", org.compiere.model.I_C_Project_Label.class);
	String COLUMNNAME_C_Project_Label_ID = "C_Project_Label_ID";

	/**
	 * Set Parent Project.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_Parent_ID (int C_Project_Parent_ID);

	/**
	 * Get Parent Project.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_Parent_ID();

	String COLUMNNAME_C_Project_Parent_ID = "C_Project_Parent_ID";

	/**
	 * Set External project reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_Reference_Ext (@Nullable java.lang.String C_Project_Reference_Ext);

	/**
	 * Get External project reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_Project_Reference_Ext();

	ModelColumn<I_C_Project, Object> COLUMN_C_Project_Reference_Ext = new ModelColumn<>(I_C_Project.class, "C_Project_Reference_Ext", null);
	String COLUMNNAME_C_Project_Reference_Ext = "C_Project_Reference_Ext";

	/**
	 * Set Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectType_ID (int C_ProjectType_ID);

	/**
	 * Get Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectType_ID();

	@Nullable org.compiere.model.I_C_ProjectType getC_ProjectType();

	void setC_ProjectType(@Nullable org.compiere.model.I_C_ProjectType C_ProjectType);

	ModelColumn<I_C_Project, org.compiere.model.I_C_ProjectType> COLUMN_C_ProjectType_ID = new ModelColumn<>(I_C_Project.class, "C_ProjectType_ID", org.compiere.model.I_C_ProjectType.class);
	String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project, Object> COLUMN_Created = new ModelColumn<>(I_C_Project.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date of Contract.
	 * The (planned) effective date of this document.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateContract (@Nullable java.sql.Timestamp DateContract);

	/**
	 * Get Date of Contract.
	 * The (planned) effective date of this document.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateContract();

	ModelColumn<I_C_Project, Object> COLUMN_DateContract = new ModelColumn<>(I_C_Project.class, "DateContract", null);
	String COLUMNNAME_DateContract = "DateContract";

	/**
	 * Set Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinish (@Nullable java.sql.Timestamp DateFinish);

	/**
	 * Get Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinish();

	ModelColumn<I_C_Project, Object> COLUMN_DateFinish = new ModelColumn<>(I_C_Project.class, "DateFinish", null);
	String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set Date of provision.
	 * Provision date planned by the business partner for the required resources.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOfProvisionByBPartner (@Nullable java.sql.Timestamp DateOfProvisionByBPartner);

	/**
	 * Get Date of provision.
	 * Provision date planned by the business partner for the required resources.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOfProvisionByBPartner();

	ModelColumn<I_C_Project, Object> COLUMN_DateOfProvisionByBPartner = new ModelColumn<>(I_C_Project.class, "DateOfProvisionByBPartner", null);
	String COLUMNNAME_DateOfProvisionByBPartner = "DateOfProvisionByBPartner";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Project, Object> COLUMN_Description = new ModelColumn<>(I_C_Project.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_Project, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Project.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGenerateTo (@Nullable java.lang.String GenerateTo);

	/**
	 * Get Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGenerateTo();

	ModelColumn<I_C_Project, Object> COLUMN_GenerateTo = new ModelColumn<>(I_C_Project.class, "GenerateTo", null);
	String COLUMNNAME_GenerateTo = "GenerateTo";

	/**
	 * Set Internal Priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalPriority (@Nullable java.lang.String InternalPriority);

	/**
	 * Get Internal Priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalPriority();

	ModelColumn<I_C_Project, Object> COLUMN_InternalPriority = new ModelColumn<>(I_C_Project.class, "InternalPriority", null);
	String COLUMNNAME_InternalPriority = "InternalPriority";

	/**
	 * Set Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedAmt (BigDecimal InvoicedAmt);

	/**
	 * Get Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedAmt();

	ModelColumn<I_C_Project, Object> COLUMN_InvoicedAmt = new ModelColumn<>(I_C_Project.class, "InvoicedAmt", null);
	String COLUMNNAME_InvoicedAmt = "InvoicedAmt";

	/**
	 * Set Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedQty (BigDecimal InvoicedQty);

	/**
	 * Get Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedQty();

	ModelColumn<I_C_Project, Object> COLUMN_InvoicedQty = new ModelColumn<>(I_C_Project.class, "InvoicedQty", null);
	String COLUMNNAME_InvoicedQty = "InvoicedQty";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_Project, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zusage ist Obergrenze.
	 * The commitment amount/quantity is the chargeable ceiling
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCommitCeiling (boolean IsCommitCeiling);

	/**
	 * Get Zusage ist Obergrenze.
	 * The commitment amount/quantity is the chargeable ceiling
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCommitCeiling();

	ModelColumn<I_C_Project, Object> COLUMN_IsCommitCeiling = new ModelColumn<>(I_C_Project.class, "IsCommitCeiling", null);
	String COLUMNNAME_IsCommitCeiling = "IsCommitCeiling";

	/**
	 * Set Commitment.
	 * Is this document a (legal) commitment?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCommitment (boolean IsCommitment);

	/**
	 * Get Commitment.
	 * Is this document a (legal) commitment?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCommitment();

	ModelColumn<I_C_Project, Object> COLUMN_IsCommitment = new ModelColumn<>(I_C_Project.class, "IsCommitment", null);
	String COLUMNNAME_IsCommitment = "IsCommitment";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_C_Project, Object> COLUMN_IsSummary = new ModelColumn<>(I_C_Project.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Project, Object> COLUMN_Name = new ModelColumn<>(I_C_Project.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Project, Object> COLUMN_Note = new ModelColumn<>(I_C_Project.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Planned Amount.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedAmt (BigDecimal PlannedAmt);

	/**
	 * Get Planned Amount.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedAmt();

	ModelColumn<I_C_Project, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_Project.class, "PlannedAmt", null);
	String COLUMNNAME_PlannedAmt = "PlannedAmt";

	/**
	 * Set DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedMarginAmt (BigDecimal PlannedMarginAmt);

	/**
	 * Get DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedMarginAmt();

	ModelColumn<I_C_Project, Object> COLUMN_PlannedMarginAmt = new ModelColumn<>(I_C_Project.class, "PlannedMarginAmt", null);
	String COLUMNNAME_PlannedMarginAmt = "PlannedMarginAmt";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedQty (BigDecimal PlannedQty);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQty();

	ModelColumn<I_C_Project, Object> COLUMN_PlannedQty = new ModelColumn<>(I_C_Project.class, "PlannedQty", null);
	String COLUMNNAME_PlannedQty = "PlannedQty";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_C_Project, Object> COLUMN_POReference = new ModelColumn<>(I_C_Project.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Project, Object> COLUMN_Processed = new ModelColumn<>(I_C_Project.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Project, Object> COLUMN_Processing = new ModelColumn<>(I_C_Project.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Project Balance.
	 * Total Project Balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectBalanceAmt (BigDecimal ProjectBalanceAmt);

	/**
	 * Get Project Balance.
	 * Total Project Balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getProjectBalanceAmt();

	ModelColumn<I_C_Project, Object> COLUMN_ProjectBalanceAmt = new ModelColumn<>(I_C_Project.class, "ProjectBalanceAmt", null);
	String COLUMNNAME_ProjectBalanceAmt = "ProjectBalanceAmt";

	/**
	 * Set Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProjectCategory (@Nullable java.lang.String ProjectCategory);

	/**
	 * Get Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProjectCategory();

	ModelColumn<I_C_Project, Object> COLUMN_ProjectCategory = new ModelColumn<>(I_C_Project.class, "ProjectCategory", null);
	String COLUMNNAME_ProjectCategory = "ProjectCategory";

	/**
	 * Set Line Level.
	 * Project Line Level
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectLineLevel (java.lang.String ProjectLineLevel);

	/**
	 * Get Line Level.
	 * Project Line Level
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjectLineLevel();

	ModelColumn<I_C_Project, Object> COLUMN_ProjectLineLevel = new ModelColumn<>(I_C_Project.class, "ProjectLineLevel", null);
	String COLUMNNAME_ProjectLineLevel = "ProjectLineLevel";

	/**
	 * Set Rechnungsstellung.
	 * Invoice Rule for the project
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjInvoiceRule (java.lang.String ProjInvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * Invoice Rule for the project
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjInvoiceRule();

	ModelColumn<I_C_Project, Object> COLUMN_ProjInvoiceRule = new ModelColumn<>(I_C_Project.class, "ProjInvoiceRule", null);
	String COLUMNNAME_ProjInvoiceRule = "ProjInvoiceRule";

	/**
	 * Set Project Status.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_Project_Status_ID (int R_Project_Status_ID);

	/**
	 * Get Project Status.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_Project_Status_ID();

	@Nullable org.compiere.model.I_R_Status getR_Project_Status();

	void setR_Project_Status(@Nullable org.compiere.model.I_R_Status R_Project_Status);

	ModelColumn<I_C_Project, org.compiere.model.I_R_Status> COLUMN_R_Project_Status_ID = new ModelColumn<>(I_C_Project.class, "R_Project_Status_ID", org.compiere.model.I_R_Status.class);
	String COLUMNNAME_R_Project_Status_ID = "R_Project_Status_ID";

	/**
	 * Set Status Category.
	 * Request Status Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setR_StatusCategory_ID (int R_StatusCategory_ID);

	/**
	 * Get Status Category.
	 * Request Status Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_StatusCategory_ID();

	@Nullable org.compiere.model.I_R_StatusCategory getR_StatusCategory();

	void setR_StatusCategory(@Nullable org.compiere.model.I_R_StatusCategory R_StatusCategory);

	ModelColumn<I_C_Project, org.compiere.model.I_R_StatusCategory> COLUMN_R_StatusCategory_ID = new ModelColumn<>(I_C_Project.class, "R_StatusCategory_ID", org.compiere.model.I_R_StatusCategory.class);
	String COLUMNNAME_R_StatusCategory_ID = "R_StatusCategory_ID";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Specialist consultant.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSpecialist_Consultant_ID (int Specialist_Consultant_ID);

	/**
	 * Get Specialist consultant.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSpecialist_Consultant_ID();

	String COLUMNNAME_Specialist_Consultant_ID = "Specialist_Consultant_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_Project, Object> COLUMN_Value = new ModelColumn<>(I_C_Project.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Creator of test order.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOOwner (@Nullable java.lang.String WOOwner);

	/**
	 * Get Creator of test order.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOOwner();

	ModelColumn<I_C_Project, Object> COLUMN_WOOwner = new ModelColumn<>(I_C_Project.class, "WOOwner", null);
	String COLUMNNAME_WOOwner = "WOOwner";

	/**
	 * Set Project created.
	 * Date on which the test project was created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOProjectCreatedDate (@Nullable java.sql.Timestamp WOProjectCreatedDate);

	/**
	 * Get Project created.
	 * Date on which the test project was created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWOProjectCreatedDate();

	ModelColumn<I_C_Project, Object> COLUMN_WOProjectCreatedDate = new ModelColumn<>(I_C_Project.class, "WOProjectCreatedDate", null);
	String COLUMNNAME_WOProjectCreatedDate = "WOProjectCreatedDate";
}
