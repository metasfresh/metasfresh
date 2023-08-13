package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_Order_Header_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_Order_Header_v 
{

	String Table_Name = "DD_Order_Header_v";

//	/** AD_Table_ID=53039 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_AD_Language = new ModelColumn<>(I_DD_Order_Header_v.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_BPContactGreeting = new ModelColumn<>(I_DD_Order_Header_v.class, "BPContactGreeting", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_BPGreeting = new ModelColumn<>(I_DD_Order_Header_v.class, "BPGreeting", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_BPTaxID = new ModelColumn<>(I_DD_Order_Header_v.class, "BPTaxID", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_BPValue = new ModelColumn<>(I_DD_Order_Header_v.class, "BPValue", null);
	String COLUMNNAME_BPValue = "BPValue";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (@Nullable BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeAmt();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_DD_Order_Header_v.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_ContactName = new ModelColumn<>(I_DD_Order_Header_v.class, "ContactName", null);
	String COLUMNNAME_ContactName = "ContactName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Created = new ModelColumn<>(I_DD_Order_Header_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DateOrdered = new ModelColumn<>(I_DD_Order_Header_v.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised (@Nullable java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DatePromised = new ModelColumn<>(I_DD_Order_Header_v.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_Order_ID();

	@Nullable org.eevolution.model.I_DD_Order getDD_Order();

	void setDD_Order(@Nullable org.eevolution.model.I_DD_Order DD_Order);

	ModelColumn<I_DD_Order_Header_v, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
	String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (@Nullable java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryRule();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_DD_Order_Header_v.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (@Nullable java.lang.String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_DD_Order_Header_v.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Description = new ModelColumn<>(I_DD_Order_Header_v.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DocStatus = new ModelColumn<>(I_DD_Order_Header_v.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DocumentNo = new ModelColumn<>(I_DD_Order_Header_v.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Belegart.
	 * Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentType (@Nullable java.lang.String DocumentType);

	/**
	 * Get Belegart.
	 * Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentType();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DocumentType = new ModelColumn<>(I_DD_Order_Header_v.class, "DocumentType", null);
	String COLUMNNAME_DocumentType = "DocumentType";

	/**
	 * Set Document Type Note.
	 * Optional note of a document type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentTypeNote (@Nullable java.lang.String DocumentTypeNote);

	/**
	 * Get Document Type Note.
	 * Optional note of a document type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentTypeNote();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DocumentTypeNote = new ModelColumn<>(I_DD_Order_Header_v.class, "DocumentTypeNote", null);
	String COLUMNNAME_DocumentTypeNote = "DocumentTypeNote";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_DUNS = new ModelColumn<>(I_DD_Order_Header_v.class, "DUNS", null);
	String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_Order_Header_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_DD_Order_Header_v.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_NAICS = new ModelColumn<>(I_DD_Order_Header_v.class, "NAICS", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Name = new ModelColumn<>(I_DD_Order_Header_v.class, "Name", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Name2 = new ModelColumn<>(I_DD_Order_Header_v.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrg_Location_ID (int Org_Location_ID);

	/**
	 * Get Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrg_Location_ID();

	@Nullable org.compiere.model.I_C_Location getOrg_Location();

	void setOrg_Location(@Nullable org.compiere.model.I_C_Location Org_Location);

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_C_Location> COLUMN_Org_Location_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "Org_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Org_Location_ID = "Org_Location_ID";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Phone = new ModelColumn<>(I_DD_Order_Header_v.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_POReference = new ModelColumn<>(I_DD_Order_Header_v.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal (@Nullable java.lang.String Postal);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Postal = new ModelColumn<>(I_DD_Order_Header_v.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (@Nullable java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityRule();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_PriorityRule = new ModelColumn<>(I_DD_Order_Header_v.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_DD_Order_Header_v.class, "ReferenceNo", null);
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

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_SalesRep_Name = new ModelColumn<>(I_DD_Order_Header_v.class, "SalesRep_Name", null);
	String COLUMNNAME_SalesRep_Name = "SalesRep_Name";

	/**
	 * Set Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxID (@Nullable java.lang.String TaxID);

	/**
	 * Get Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxID();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_TaxID = new ModelColumn<>(I_DD_Order_Header_v.class, "TaxID", null);
	String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTitle (@Nullable java.lang.String Title);

	/**
	 * Get Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTitle();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Title = new ModelColumn<>(I_DD_Order_Header_v.class, "Title", null);
	String COLUMNNAME_Title = "Title";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Updated = new ModelColumn<>(I_DD_Order_Header_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Volume.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVolume (@Nullable BigDecimal Volume);

	/**
	 * Get Volume.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getVolume();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Volume = new ModelColumn<>(I_DD_Order_Header_v.class, "Volume", null);
	String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Warehouse Address.
	 * Warehouse Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouse_Location_ID (int Warehouse_Location_ID);

	/**
	 * Get Warehouse Address.
	 * Warehouse Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWarehouse_Location_ID();

	@Nullable org.compiere.model.I_C_Location getWarehouse_Location();

	void setWarehouse_Location(@Nullable org.compiere.model.I_C_Location Warehouse_Location);

	ModelColumn<I_DD_Order_Header_v, org.compiere.model.I_C_Location> COLUMN_Warehouse_Location_ID = new ModelColumn<>(I_DD_Order_Header_v.class, "Warehouse_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Warehouse_Location_ID = "Warehouse_Location_ID";

	/**
	 * Set Weight.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWeight (@Nullable BigDecimal Weight);

	/**
	 * Get Weight.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWeight();

	ModelColumn<I_DD_Order_Header_v, Object> COLUMN_Weight = new ModelColumn<>(I_DD_Order_Header_v.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";
}
