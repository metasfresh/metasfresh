package de.metas.rfq.model;


/** Generated Interface for C_RfQResponseLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponseLine 
{

    /** TableName=C_RfQResponseLine */
    public static final String Table_Name = "C_RfQResponseLine";

    /** AD_Table_ID=673 */
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_Client>(I_C_RfQResponseLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_Org>(I_C_RfQResponseLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_BPartner>(I_C_RfQResponseLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_Currency>(I_C_RfQResponseLine.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Ausschreibung.
	 * Anfrage für ein Angebot
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/**
	 * Get Ausschreibung.
	 * Anfrage für ein Angebot
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_ID();

	public de.metas.rfq.model.I_C_RfQ getC_RfQ();

	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ);

    /** Column definition for C_RfQ_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQ>(I_C_RfQResponseLine.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLine_ID (int C_RfQLine_ID);

	/**
	 * Get RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLine_ID();

	public de.metas.rfq.model.I_C_RfQLine getC_RfQLine();

	public void setC_RfQLine(de.metas.rfq.model.I_C_RfQLine C_RfQLine);

    /** Column definition for C_RfQLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQLine> COLUMN_C_RfQLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQLine>(I_C_RfQResponseLine.class, "C_RfQLine_ID", de.metas.rfq.model.I_C_RfQLine.class);
    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/**
	 * Set Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/**
	 * Get Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponse_ID();

	public de.metas.rfq.model.I_C_RfQResponse getC_RfQResponse();

	public void setC_RfQResponse(de.metas.rfq.model.I_C_RfQResponse C_RfQResponse);

    /** Column definition for C_RfQResponse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQResponse> COLUMN_C_RfQResponse_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, de.metas.rfq.model.I_C_RfQResponse>(I_C_RfQResponseLine.class, "C_RfQResponse_ID", de.metas.rfq.model.I_C_RfQResponse.class);
    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

	/**
	 * Set RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID);

	/**
	 * Get RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLine_ID();

    /** Column definition for C_RfQResponseLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_C_RfQResponseLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "C_RfQResponseLine_ID", null);
    /** Column name C_RfQResponseLine_ID */
    public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_C_UOM>(I_C_RfQResponseLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_User>(I_C_RfQResponseLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Invited.
	 * Date when (last) invitation was sent
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDateInvited (java.sql.Timestamp DateInvited);

	/**
	 * Get Invited.
	 * Date when (last) invitation was sent
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getDateInvited();

    /** Column definition for DateInvited */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DateInvited = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DateInvited", null);
    /** Column name DateInvited */
    public static final String COLUMNNAME_DateInvited = "DateInvited";

	/**
	 * Set Antwort-datum.
	 * Datum der Antwort
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Datum der Antwort
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DateResponse", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DateWorkComplete", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DateWorkStart", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DeliveryDays", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "DocStatus", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_IsSelectedWinner = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "IsSelectedWinner", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_M_Product>(I_C_RfQResponseLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Price", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPromised (java.math.BigDecimal QtyPromised);

	/**
	 * Get Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPromised();

    /** Column definition for QtyPromised */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_QtyPromised = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "QtyPromised", null);
    /** Column name QtyPromised */
    public static final String COLUMNNAME_QtyPromised = "QtyPromised";

	/**
	 * Set Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered);

	/**
	 * Get Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyRequiered();

    /** Column definition for QtyRequiered */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_QtyRequiered = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "QtyRequiered", null);
    /** Column name QtyRequiered */
    public static final String COLUMNNAME_QtyRequiered = "QtyRequiered";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, org.compiere.model.I_AD_User>(I_C_RfQResponseLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Use line quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseLineQty (boolean UseLineQty);

	/**
	 * Get Use line quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseLineQty();

    /** Column definition for UseLineQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object> COLUMN_UseLineQty = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine, Object>(I_C_RfQResponseLine.class, "UseLineQty", null);
    /** Column name UseLineQty */
    public static final String COLUMNNAME_UseLineQty = "UseLineQty";
}
