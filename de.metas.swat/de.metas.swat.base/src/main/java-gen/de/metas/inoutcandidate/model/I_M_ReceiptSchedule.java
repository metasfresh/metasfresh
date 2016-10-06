package de.metas.inoutcandidate.model;


/** Generated Interface for M_ReceiptSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ReceiptSchedule 
{

    /** TableName=M_ReceiptSchedule */
    public static final String Table_Name = "M_ReceiptSchedule";

    /** AD_Table_ID=540524 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Client>(I_M_ReceiptSchedule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Org>(I_M_ReceiptSchedule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_Table>(I_M_ReceiptSchedule.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User>(I_M_ReceiptSchedule.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Override_ID (int AD_User_Override_ID);

	/**
	 * Get Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Override_ID();

	public org.compiere.model.I_AD_User getAD_User_Override();

	public void setAD_User_Override(org.compiere.model.I_AD_User AD_User_Override);

    /** Column definition for AD_User_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User> COLUMN_AD_User_Override_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User>(I_M_ReceiptSchedule.class, "AD_User_Override_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_Override_ID */
    public static final String COLUMNNAME_AD_User_Override_ID = "AD_User_Override_ID";

	/**
	 * Set Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override);

	/**
	 * Get Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress_Override();

    /** Column definition for BPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "BPartnerAddress_Override", null);
    /** Column name BPartnerAddress_Override */
    public static final String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner>(I_M_ReceiptSchedule.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner_Location>(I_M_ReceiptSchedule.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Override_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Override();

	public void setC_BPartner_Override(org.compiere.model.I_C_BPartner C_BPartner_Override);

    /** Column definition for C_BPartner_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Override_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner>(I_M_ReceiptSchedule.class, "C_BPartner_Override_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Override_ID */
    public static final String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Location_Override_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BP_Location_Override();

	public void setC_BP_Location_Override(org.compiere.model.I_C_BPartner_Location C_BP_Location_Override);

    /** Column definition for C_BP_Location_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BP_Location_Override_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BPartner_Location>(I_M_ReceiptSchedule.class, "C_BP_Location_Override_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BP_Location_Override_ID */
    public static final String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_DocType>(I_M_ReceiptSchedule.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order>(I_M_ReceiptSchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_OrderLine>(I_M_ReceiptSchedule.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User>(I_M_ReceiptSchedule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_UOM>(I_M_ReceiptSchedule.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule_Override (java.lang.String DeliveryRule_Override);

	/**
	 * Get Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule_Override();

    /** Column definition for DeliveryRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryRule_Override", null);
    /** Column name DeliveryRule_Override */
    public static final String COLUMNNAME_DeliveryRule_Override = "DeliveryRule_Override";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule_Override (java.lang.String DeliveryViaRule_Override);

	/**
	 * Get Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule_Override();

    /** Column definition for DeliveryViaRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "DeliveryViaRule_Override", null);
    /** Column name DeliveryViaRule_Override */
    public static final String COLUMNNAME_DeliveryViaRule_Override = "DeliveryViaRule_Override";

	/**
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey();

    /** Column definition for HeaderAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_HeaderAggregationKey = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "HeaderAggregationKey", null);
    /** Column name HeaderAggregationKey */
    public static final String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override);

	/**
	 * Get abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBPartnerAddress_Override();

    /** Column definition for IsBPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsBPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsBPartnerAddress_Override", null);
    /** Column name IsBPartnerAddress_Override */
    public static final String COLUMNNAME_IsBPartnerAddress_Override = "IsBPartnerAddress_Override";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackagingMaterial();

    /** Column definition for IsPackagingMaterial */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsPackagingMaterial = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "IsPackagingMaterial", null);
    /** Column name IsPackagingMaterial */
    public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_AttributeSetInstance>(I_M_ReceiptSchedule.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_IolCandHandler_ID();

	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

    /** Column definition for M_IolCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler>(I_M_ReceiptSchedule.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
    /** Column name M_IolCandHandler_ID */
    public static final String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Product>(I_M_ReceiptSchedule.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Wareneingangsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Wareneingangsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_ID();

    /** Column definition for M_ReceiptSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_ReceiptSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "M_ReceiptSchedule_ID", null);
    /** Column name M_ReceiptSchedule_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Dest_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse_Dest();

	public void setM_Warehouse_Dest(org.compiere.model.I_M_Warehouse M_Warehouse_Dest);

    /** Column definition for M_Warehouse_Dest_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_Dest_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse>(I_M_ReceiptSchedule.class, "M_Warehouse_Dest_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_Dest_ID */
    public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse>(I_M_ReceiptSchedule.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID);

	/**
	 * Get Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Override_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse_Override();

	public void setM_Warehouse_Override(org.compiere.model.I_M_Warehouse M_Warehouse_Override);

    /** Column definition for M_Warehouse_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_Override_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_Warehouse>(I_M_ReceiptSchedule.class, "M_Warehouse_Override_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_Override_ID */
    public static final String COLUMNNAME_M_Warehouse_Override_ID = "M_Warehouse_Override_ID";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule_Override (java.lang.String PriorityRule_Override);

	/**
	 * Get Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule_Override();

    /** Column definition for PriorityRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "PriorityRule_Override", null);
    /** Column name PriorityRule_Override */
    public static final String COLUMNNAME_PriorityRule_Override = "PriorityRule_Override";

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
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Bewegte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyMoved (java.math.BigDecimal QtyMoved);

	/**
	 * Get Bewegte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMoved();

    /** Column definition for QtyMoved */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMoved = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMoved", null);
    /** Column name QtyMoved */
    public static final String COLUMNNAME_QtyMoved = "QtyMoved";

	/**
	 * Set Qty Moved (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyMovedWithIssues (java.math.BigDecimal QtyMovedWithIssues);

	/**
	 * Get Qty Moved (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMovedWithIssues();

    /** Column definition for QtyMovedWithIssues */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedWithIssues = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyMovedWithIssues", null);
    /** Column name QtyMovedWithIssues */
    public static final String COLUMNNAME_QtyMovedWithIssues = "QtyMovedWithIssues";

	/**
	 * Set Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder);

	/**
	 * Get QtyOrderedOverUnder.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrderedOverUnder();

    /** Column definition for QtyOrderedOverUnder */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrderedOverUnder = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyOrderedOverUnder", null);
    /** Column name QtyOrderedOverUnder */
    public static final String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Menge zu bewegen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToMove (java.math.BigDecimal QtyToMove);

	/**
	 * Get Menge zu bewegen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToMove();

    /** Column definition for QtyToMove */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyToMove", null);
    /** Column name QtyToMove */
    public static final String COLUMNNAME_QtyToMove = "QtyToMove";

	/**
	 * Set Menge zu bewegen abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToMove_Override (java.math.BigDecimal QtyToMove_Override);

	/**
	 * Get Menge zu bewegen abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToMove_Override();

    /** Column definition for QtyToMove_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove_Override = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QtyToMove_Override", null);
    /** Column name QtyToMove_Override */
    public static final String COLUMNNAME_QtyToMove_Override = "QtyToMove_Override";

	/**
	 * Set Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityDiscountPercent();

    /** Column definition for QualityDiscountPercent */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityDiscountPercent = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QualityDiscountPercent", null);
    /** Column name QualityDiscountPercent */
    public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityNote (java.lang.String QualityNote);

	/**
	 * Get Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQualityNote();

    /** Column definition for QualityNote */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityNote = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "QualityNote", null);
    /** Column name QualityNote */
    public static final String COLUMNNAME_QualityNote = "QualityNote";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, Object>(I_M_ReceiptSchedule.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_AD_User>(I_M_ReceiptSchedule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
