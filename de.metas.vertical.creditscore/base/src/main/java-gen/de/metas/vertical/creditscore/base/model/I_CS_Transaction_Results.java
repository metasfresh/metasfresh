package de.metas.vertical.creditscore.base.model;


/** Generated Interface for CS_Transaction_Results
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CS_Transaction_Results 
{

    /** TableName=CS_Transaction_Results */
    public static final String Table_Name = "CS_Transaction_Results";

    /** AD_Table_ID=541194 */
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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_Client>(I_CS_Transaction_Results.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_Org>(I_CS_Transaction_Results.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_C_BPartner>(I_CS_Transaction_Results.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_C_Order>(I_CS_Transaction_Results.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_User>(I_CS_Transaction_Results.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CS_Transaction_Results.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCS_Transaction_Results_ID (int CS_Transaction_Results_ID);

	/**
	 * Get CS_Transaction_Results.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCS_Transaction_Results_ID();

    /** Column definition for CS_Transaction_Results_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_CS_Transaction_Results_ID = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "CS_Transaction_Results_ID", null);
    /** Column name CS_Transaction_Results_ID */
    public static final String COLUMNNAME_CS_Transaction_Results_ID = "CS_Transaction_Results_ID";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRequestEndTime (java.sql.Timestamp RequestEndTime);

	/**
	 * Get Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRequestEndTime();

    /** Column definition for RequestEndTime */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_RequestEndTime = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "RequestEndTime", null);
    /** Column name RequestEndTime */
    public static final String COLUMNNAME_RequestEndTime = "RequestEndTime";

	/**
	 * Set Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRequestStartTime (java.sql.Timestamp RequestStartTime);

	/**
	 * Get Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRequestStartTime();

    /** Column definition for RequestStartTime */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_RequestStartTime = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "RequestStartTime", null);
    /** Column name RequestStartTime */
    public static final String COLUMNNAME_RequestStartTime = "RequestStartTime";

	/**
	 * Set Antwort Code.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setResponseCode (java.math.BigDecimal ResponseCode);

	/**
	 * Get Antwort Code.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getResponseCode();

    /** Column definition for ResponseCode */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_ResponseCode = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "ResponseCode", null);
    /** Column name ResponseCode */
    public static final String COLUMNNAME_ResponseCode = "ResponseCode";

	/**
	 * Set Antwort Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setResponseCodeText (java.lang.String ResponseCodeText);

	/**
	 * Get Antwort Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseCodeText();

    /** Column definition for ResponseCodeText */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_ResponseCodeText = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "ResponseCodeText", null);
    /** Column name ResponseCodeText */
    public static final String COLUMNNAME_ResponseCodeText = "ResponseCodeText";

	/**
	 * Set Antwort Details.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseDetails (java.lang.String ResponseDetails);

	/**
	 * Get Antwort Details.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseDetails();

    /** Column definition for ResponseDetails */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_ResponseDetails = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "ResponseDetails", null);
    /** Column name ResponseDetails */
    public static final String COLUMNNAME_ResponseDetails = "ResponseDetails";

	/**
	 * Set Transaktionsreferenz .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTransactionCustomerId (java.lang.String TransactionCustomerId);

	/**
	 * Get Transaktionsreferenz .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionCustomerId();

    /** Column definition for TransactionCustomerId */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_TransactionCustomerId = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "TransactionCustomerId", null);
    /** Column name TransactionCustomerId */
    public static final String COLUMNNAME_TransactionCustomerId = "TransactionCustomerId";

	/**
	 * Set Transaktionsreferenz .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTransactionIdAPI (java.lang.String TransactionIdAPI);

	/**
	 * Get Transaktionsreferenz .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionIdAPI();

    /** Column definition for TransactionIdAPI */
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_TransactionIdAPI = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "TransactionIdAPI", null);
    /** Column name TransactionIdAPI */
    public static final String COLUMNNAME_TransactionIdAPI = "TransactionIdAPI";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, Object>(I_CS_Transaction_Results.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_CS_Transaction_Results, org.compiere.model.I_AD_User>(I_CS_Transaction_Results.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
