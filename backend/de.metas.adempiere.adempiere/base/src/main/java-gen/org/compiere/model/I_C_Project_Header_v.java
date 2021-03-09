package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Header_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Header_v 
{

	String Table_Name = "C_Project_Header_v";

//	/** AD_Table_ID=618 */
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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_Project_Header_v.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPContactGreeting (@Nullable java.lang.String BPContactGreeting);

	/**
	 * Get Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPContactGreeting();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_BPContactGreeting = new ModelColumn<>(I_C_Project_Header_v.class, "BPContactGreeting", null);
	String COLUMNNAME_BPContactGreeting = "BPContactGreeting";

	/**
	 * Set Geschäftspartner-Anrede.
	 * Greeting for Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPGreeting (@Nullable java.lang.String BPGreeting);

	/**
	 * Get Geschäftspartner-Anrede.
	 * Greeting for Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPGreeting();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_BPGreeting = new ModelColumn<>(I_C_Project_Header_v.class, "BPGreeting", null);
	String COLUMNNAME_BPGreeting = "BPGreeting";

	/**
	 * Set Partner Tax ID.
	 * Tax ID of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPTaxID (@Nullable java.lang.String BPTaxID);

	/**
	 * Get Partner Tax ID.
	 * Tax ID of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPTaxID();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_BPTaxID = new ModelColumn<>(I_C_Project_Header_v.class, "BPTaxID", null);
	String COLUMNNAME_BPTaxID = "BPTaxID";

	/**
	 * Set Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPValue (@Nullable java.lang.String BPValue);

	/**
	 * Get Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPValue();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_BPValue = new ModelColumn<>(I_C_Project_Header_v.class, "BPValue", null);
	String COLUMNNAME_BPValue = "BPValue";

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

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Project_Header_v.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
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
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_C_Project_Header_v.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

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

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_C_Phase> COLUMN_C_Phase_ID = new ModelColumn<>(I_C_Project_Header_v.class, "C_Phase_ID", org.compiere.model.I_C_Phase.class);
	String COLUMNNAME_C_Phase_ID = "C_Phase_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

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

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_C_ProjectType> COLUMN_C_ProjectType_ID = new ModelColumn<>(I_C_Project_Header_v.class, "C_ProjectType_ID", org.compiere.model.I_C_ProjectType.class);
	String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_CommittedAmt = new ModelColumn<>(I_C_Project_Header_v.class, "CommittedAmt", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_CommittedQty = new ModelColumn<>(I_C_Project_Header_v.class, "CommittedQty", null);
	String COLUMNNAME_CommittedQty = "CommittedQty";

	/**
	 * Set Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactName (@Nullable java.lang.String ContactName);

	/**
	 * Get Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContactName();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ContactName = new ModelColumn<>(I_C_Project_Header_v.class, "ContactName", null);
	String COLUMNNAME_ContactName = "ContactName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Header_v.class, "Created", null);
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
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateContract (@Nullable java.sql.Timestamp DateContract);

	/**
	 * Get Date of Contract.
	 * The (planned) effective date of this document.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateContract();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_DateContract = new ModelColumn<>(I_C_Project_Header_v.class, "DateContract", null);
	String COLUMNNAME_DateContract = "DateContract";

	/**
	 * Set Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinish (@Nullable java.sql.Timestamp DateFinish);

	/**
	 * Get Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinish();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_DateFinish = new ModelColumn<>(I_C_Project_Header_v.class, "DateFinish", null);
	String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Description = new ModelColumn<>(I_C_Project_Header_v.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDUNS (@Nullable java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDUNS();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_DUNS = new ModelColumn<>(I_C_Project_Header_v.class, "DUNS", null);
	String COLUMNNAME_DUNS = "DUNS";

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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_InvoicedAmt = new ModelColumn<>(I_C_Project_Header_v.class, "InvoicedAmt", null);
	String COLUMNNAME_InvoicedAmt = "InvoicedAmt";

	/**
	 * Set Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedQty (BigDecimal InvoicedQty);

	/**
	 * Get Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedQty();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_InvoicedQty = new ModelColumn<>(I_C_Project_Header_v.class, "InvoicedQty", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Header_v.class, "IsActive", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_IsCommitCeiling = new ModelColumn<>(I_C_Project_Header_v.class, "IsCommitCeiling", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_IsCommitment = new ModelColumn<>(I_C_Project_Header_v.class, "IsCommitment", null);
	String COLUMNNAME_IsCommitment = "IsCommitment";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_IsSummary = new ModelColumn<>(I_C_Project_Header_v.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLogo_ID();

	@Nullable org.compiere.model.I_AD_Image getLogo();

	void setLogo(@Nullable org.compiere.model.I_AD_Image Logo);

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new ModelColumn<>(I_C_Project_Header_v.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_Logo_ID = "Logo_ID";

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
	 * Set NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNAICS (@Nullable java.lang.String NAICS);

	/**
	 * Get NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNAICS();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_NAICS = new ModelColumn<>(I_C_Project_Header_v.class, "NAICS", null);
	String COLUMNNAME_NAICS = "NAICS";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Name = new ModelColumn<>(I_C_Project_Header_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName2();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Name2 = new ModelColumn<>(I_C_Project_Header_v.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Note = new ModelColumn<>(I_C_Project_Header_v.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrg_Location_ID (int Org_Location_ID);

	/**
	 * Get Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrg_Location_ID();

	@Nullable org.compiere.model.I_C_Location getOrg_Location();

	void setOrg_Location(@Nullable org.compiere.model.I_C_Location Org_Location);

	ModelColumn<I_C_Project_Header_v, org.compiere.model.I_C_Location> COLUMN_Org_Location_ID = new ModelColumn<>(I_C_Project_Header_v.class, "Org_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Org_Location_ID = "Org_Location_ID";

	/**
	 * Set Zahlungskondition.
	 * Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentTerm (@Nullable java.lang.String PaymentTerm);

	/**
	 * Get Zahlungskondition.
	 * Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentTerm();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_PaymentTerm = new ModelColumn<>(I_C_Project_Header_v.class, "PaymentTerm", null);
	String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/**
	 * Set Payment Term Note.
	 * Note of a Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentTermNote (@Nullable java.lang.String PaymentTermNote);

	/**
	 * Get Payment Term Note.
	 * Note of a Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentTermNote();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_PaymentTermNote = new ModelColumn<>(I_C_Project_Header_v.class, "PaymentTermNote", null);
	String COLUMNNAME_PaymentTermNote = "PaymentTermNote";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone (@Nullable java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Phone = new ModelColumn<>(I_C_Project_Header_v.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

	/**
	 * Set VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedAmt (BigDecimal PlannedAmt);

	/**
	 * Get VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedAmt();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_Project_Header_v.class, "PlannedAmt", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_PlannedMarginAmt = new ModelColumn<>(I_C_Project_Header_v.class, "PlannedMarginAmt", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_PlannedQty = new ModelColumn<>(I_C_Project_Header_v.class, "PlannedQty", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_POReference = new ModelColumn<>(I_C_Project_Header_v.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ProjectBalanceAmt = new ModelColumn<>(I_C_Project_Header_v.class, "ProjectBalanceAmt", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ProjectCategory = new ModelColumn<>(I_C_Project_Header_v.class, "ProjectCategory", null);
	String COLUMNNAME_ProjectCategory = "ProjectCategory";

	/**
	 * Set Projekt.
	 * Name of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectName (java.lang.String ProjectName);

	/**
	 * Get Projekt.
	 * Name of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjectName();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ProjectName = new ModelColumn<>(I_C_Project_Header_v.class, "ProjectName", null);
	String COLUMNNAME_ProjectName = "ProjectName";

	/**
	 * Set Projekt-Phase.
	 * Name of the Project Phase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProjectPhaseName (@Nullable java.lang.String ProjectPhaseName);

	/**
	 * Get Projekt-Phase.
	 * Name of the Project Phase
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProjectPhaseName();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ProjectPhaseName = new ModelColumn<>(I_C_Project_Header_v.class, "ProjectPhaseName", null);
	String COLUMNNAME_ProjectPhaseName = "ProjectPhaseName";

	/**
	 * Set Projekt - Projektart.
	 * Name of the Project Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProjectTypeName (@Nullable java.lang.String ProjectTypeName);

	/**
	 * Get Projekt - Projektart.
	 * Name of the Project Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProjectTypeName();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ProjectTypeName = new ModelColumn<>(I_C_Project_Header_v.class, "ProjectTypeName", null);
	String COLUMNNAME_ProjectTypeName = "ProjectTypeName";

	/**
	 * Set Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceNo (@Nullable java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferenceNo();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_C_Project_Header_v.class, "ReferenceNo", null);
	String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Vertriebsbeauftragter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_Name (@Nullable java.lang.String SalesRep_Name);

	/**
	 * Get Vertriebsbeauftragter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSalesRep_Name();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_SalesRep_Name = new ModelColumn<>(I_C_Project_Header_v.class, "SalesRep_Name", null);
	String COLUMNNAME_SalesRep_Name = "SalesRep_Name";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxID (java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTaxID();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_TaxID = new ModelColumn<>(I_C_Project_Header_v.class, "TaxID", null);
	String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTitle (@Nullable java.lang.String Title);

	/**
	 * Get Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTitle();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Title = new ModelColumn<>(I_C_Project_Header_v.class, "Title", null);
	String COLUMNNAME_Title = "Title";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Header_v.class, "Updated", null);
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

	ModelColumn<I_C_Project_Header_v, Object> COLUMN_Value = new ModelColumn<>(I_C_Project_Header_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
