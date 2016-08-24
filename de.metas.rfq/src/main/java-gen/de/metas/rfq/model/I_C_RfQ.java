package de.metas.rfq.model;


/** Generated Interface for C_RfQ
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQ 
{

    /** TableName=C_RfQ */
    public static final String Table_Name = "C_RfQ";

    /** AD_Table_ID=677 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_Client>(I_C_RfQ.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_Org>(I_C_RfQ.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User>(I_C_RfQ.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_BPartner>(I_C_RfQ.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_BPartner_Location>(I_C_RfQ.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_Currency>(I_C_RfQ.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_C_Order>(I_C_RfQ.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/**
	 * Get Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_ID();

    /** Column definition for C_RfQ_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "C_RfQ_ID", null);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID);

	/**
	 * Get Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_Topic_ID();

	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic();

	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic);

    /** Column definition for C_RfQ_Topic_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, de.metas.rfq.model.I_C_RfQ_Topic> COLUMN_C_RfQ_Topic_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, de.metas.rfq.model.I_C_RfQ_Topic>(I_C_RfQ.class, "C_RfQ_Topic_ID", de.metas.rfq.model.I_C_RfQ_Topic.class);
    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User>(I_C_RfQ.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DateResponse", null);
    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

	/**
	 * Set Arbeit fertiggestellt.
	 * Date when work is (planned to be) complete
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkComplete (java.sql.Timestamp DateWorkComplete);

	/**
	 * Get Arbeit fertiggestellt.
	 * Date when work is (planned to be) complete
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkComplete();

    /** Column definition for DateWorkComplete */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DateWorkComplete", null);
    /** Column name DateWorkComplete */
    public static final String COLUMNNAME_DateWorkComplete = "DateWorkComplete";

	/**
	 * Set Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart);

	/**
	 * Get Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkStart();

    /** Column definition for DateWorkStart */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DateWorkStart", null);
    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/**
	 * Set Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDays (int DeliveryDays);

	/**
	 * Get Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryDays();

    /** Column definition for DeliveryDays */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DeliveryDays", null);
    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invited Vendors Only.
	 * Only invited vendors can respond to an RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvitedVendorsOnly (boolean IsInvitedVendorsOnly);

	/**
	 * Get Invited Vendors Only.
	 * Only invited vendors can respond to an RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvitedVendorsOnly();

    /** Column definition for IsInvitedVendorsOnly */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsInvitedVendorsOnly = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsInvitedVendorsOnly", null);
    /** Column name IsInvitedVendorsOnly */
    public static final String COLUMNNAME_IsInvitedVendorsOnly = "IsInvitedVendorsOnly";

	/**
	 * Set Quote All Quantities.
	 * Suppliers are requested to provide responses for all quantities
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQuoteAllQty (boolean IsQuoteAllQty);

	/**
	 * Get Quote All Quantities.
	 * Suppliers are requested to provide responses for all quantities
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQuoteAllQty();

    /** Column definition for IsQuoteAllQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsQuoteAllQty = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsQuoteAllQty", null);
    /** Column name IsQuoteAllQty */
    public static final String COLUMNNAME_IsQuoteAllQty = "IsQuoteAllQty";

	/**
	 * Set Quote Total Amt.
	 * The respnse can have just the total amount for the RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQuoteTotalAmt (boolean IsQuoteTotalAmt);

	/**
	 * Get Quote Total Amt.
	 * The respnse can have just the total amount for the RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQuoteTotalAmt();

    /** Column definition for IsQuoteTotalAmt */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsQuoteTotalAmt = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsQuoteTotalAmt", null);
    /** Column name IsQuoteTotalAmt */
    public static final String COLUMNNAME_IsQuoteTotalAmt = "IsQuoteTotalAmt";

	/**
	 * Set Responses Accepted.
	 * Are Resonses to the Request for Quotation accepted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRfQResponseAccepted (boolean IsRfQResponseAccepted);

	/**
	 * Get Responses Accepted.
	 * Are Resonses to the Request for Quotation accepted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRfQResponseAccepted();

    /** Column definition for IsRfQResponseAccepted */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsRfQResponseAccepted = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsRfQResponseAccepted", null);
    /** Column name IsRfQResponseAccepted */
    public static final String COLUMNNAME_IsRfQResponseAccepted = "IsRfQResponseAccepted";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMargin (java.math.BigDecimal Margin);

	/**
	 * Get Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMargin();

    /** Column definition for Margin */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Margin = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Margin", null);
    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set RfQ Type.
	 * Request for Quotation Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQuoteType (java.lang.String QuoteType);

	/**
	 * Get RfQ Type.
	 * Request for Quotation Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQuoteType();

    /** Column definition for QuoteType */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_QuoteType = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "QuoteType", null);
    /** Column name QuoteType */
    public static final String COLUMNNAME_QuoteType = "QuoteType";

	/**
	 * Set Bid end date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfq_BidEndDate (java.sql.Timestamp Rfq_BidEndDate);

	/**
	 * Get Bid end date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRfq_BidEndDate();

    /** Column definition for Rfq_BidEndDate */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Rfq_BidEndDate = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Rfq_BidEndDate", null);
    /** Column name Rfq_BidEndDate */
    public static final String COLUMNNAME_Rfq_BidEndDate = "Rfq_BidEndDate";

	/**
	 * Set Bid start date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfq_BidStartDate (java.sql.Timestamp Rfq_BidStartDate);

	/**
	 * Get Bid start date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRfq_BidStartDate();

    /** Column definition for Rfq_BidStartDate */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Rfq_BidStartDate = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Rfq_BidStartDate", null);
    /** Column name Rfq_BidStartDate */
    public static final String COLUMNNAME_Rfq_BidStartDate = "Rfq_BidStartDate";

	/**
	 * Set Ausschreibung Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRfQType (java.lang.String RfQType);

	/**
	 * Get Ausschreibung Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRfQType();

    /** Column definition for RfQType */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_RfQType = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "RfQType", null);
    /** Column name RfQType */
    public static final String COLUMNNAME_RfQType = "RfQType";

	/**
	 * Set Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User>(I_C_RfQ.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQ, Object>(I_C_RfQ.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ, org.compiere.model.I_AD_User>(I_C_RfQ.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
