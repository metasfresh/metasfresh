package de.metas.rfq.model;


/** Generated Interface for C_RfQResponse
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponse 
{

    /** TableName=C_RfQResponse */
    public static final String Table_Name = "C_RfQResponse";

    /** AD_Table_ID=674 */
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_Client>(I_C_RfQResponse.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_Org>(I_C_RfQResponse.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User>(I_C_RfQResponse.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_BPartner>(I_C_RfQResponse.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_BPartner_Location>(I_C_RfQResponse.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_Currency>(I_C_RfQResponse.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_C_Order>(I_C_RfQResponse.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/**
	 * Get Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_ID();

	public de.metas.rfq.model.I_C_RfQ getC_RfQ();

	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ);

    /** Column definition for C_RfQ_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, de.metas.rfq.model.I_C_RfQ>(I_C_RfQResponse.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/**
	 * Get Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponse_ID();

    /** Column definition for C_RfQResponse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_C_RfQResponse_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "C_RfQResponse_ID", null);
    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User>(I_C_RfQResponse.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Invited.
	 * Date when (last) invitation was sent
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvited (java.sql.Timestamp DateInvited);

	/**
	 * Get Invited.
	 * Date when (last) invitation was sent
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvited();

    /** Column definition for DateInvited */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DateInvited = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DateInvited", null);
    /** Column name DateInvited */
    public static final String COLUMNNAME_DateInvited = "DateInvited";

	/**
	 * Set Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DateResponse", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DateWorkComplete", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DateWorkStart", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DeliveryDays", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Selected Winner.
	 * The resonse is the selected winner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelectedWinner (boolean IsSelectedWinner);

	/**
	 * Get Selected Winner.
	 * The resonse is the selected winner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelectedWinner();

    /** Column definition for IsSelectedWinner */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_IsSelectedWinner = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "IsSelectedWinner", null);
    /** Column name IsSelectedWinner */
    public static final String COLUMNNAME_IsSelectedWinner = "IsSelectedWinner";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRanking (int Ranking);

	/**
	 * Get Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRanking();

    /** Column definition for Ranking */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Ranking = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Ranking", null);
    /** Column name Ranking */
    public static final String COLUMNNAME_Ranking = "Ranking";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponse, Object>(I_C_RfQResponse.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponse, org.compiere.model.I_AD_User>(I_C_RfQResponse.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
