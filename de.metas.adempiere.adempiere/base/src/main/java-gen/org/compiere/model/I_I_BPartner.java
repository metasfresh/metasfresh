package org.compiere.model;


/** Generated Interface for I_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_BPartner 
{

    /** TableName=I_BPartner */
    public static final String Table_Name = "I_BPartner";

    /** AD_Table_ID=533 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Client>(I_I_BPartner.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Org>(I_I_BPartner.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

    /** Column definition for AD_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_PrintFormat>(I_I_BPartner.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Benutzer ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ExternalId (java.lang.String AD_User_ExternalId);

	/**
	 * Get Benutzer ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_User_ExternalId();

    /** Column definition for AD_User_ExternalId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_ExternalId = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_User_ExternalId", null);
    /** Column name AD_User_ExternalId */
    public static final String COLUMNNAME_AD_User_ExternalId = "AD_User_ExternalId";

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
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set AD_User_Memo1.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Memo1 (java.lang.String AD_User_Memo1);

	/**
	 * Get AD_User_Memo1.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_User_Memo1();

    /** Column definition for AD_User_Memo1 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo1 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_User_Memo1", null);
    /** Column name AD_User_Memo1 */
    public static final String COLUMNNAME_AD_User_Memo1 = "AD_User_Memo1";

	/**
	 * Set AD_User_Memo2.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Memo2 (java.lang.String AD_User_Memo2);

	/**
	 * Get AD_User_Memo2.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_User_Memo2();

    /** Column definition for AD_User_Memo2 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_User_Memo2", null);
    /** Column name AD_User_Memo2 */
    public static final String COLUMNNAME_AD_User_Memo2 = "AD_User_Memo2";

	/**
	 * Set AD_User_Memo3.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Memo3 (java.lang.String AD_User_Memo3);

	/**
	 * Get AD_User_Memo3.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_User_Memo3();

    /** Column definition for AD_User_Memo3 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo3 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_User_Memo3", null);
    /** Column name AD_User_Memo3 */
    public static final String COLUMNNAME_AD_User_Memo3 = "AD_User_Memo3";

	/**
	 * Set AD_User_Memo4.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Memo4 (java.lang.String AD_User_Memo4);

	/**
	 * Get AD_User_Memo4.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_User_Memo4();

    /** Column definition for AD_User_Memo4 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo4 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AD_User_Memo4", null);
    /** Column name AD_User_Memo4 */
    public static final String COLUMNNAME_AD_User_Memo4 = "AD_User_Memo4";

	/**
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress2 (java.lang.String Address2);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress2();

    /** Column definition for Address2 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address2", null);
    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Adresszeile 3.
	 * Adresszeilee 3 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress3 (java.lang.String Address3);

	/**
	 * Get Adresszeile 3.
	 * Adresszeilee 3 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress3();

    /** Column definition for Address3 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address3 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address3", null);
    /** Column name Address3 */
    public static final String COLUMNNAME_Address3 = "Address3";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress4 (java.lang.String Address4);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress4();

    /** Column definition for Address4 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address4 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address4", null);
    /** Column name Address4 */
    public static final String COLUMNNAME_Address4 = "Address4";

	/**
	 * Set Aggregation Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAggregationName (java.lang.String AggregationName);

	/**
	 * Get Aggregation Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAggregationName();

    /** Column definition for AggregationName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_AggregationName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "AggregationName", null);
    /** Column name AggregationName */
    public static final String COLUMNNAME_AggregationName = "AggregationName";

	/**
	 * Set Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBirthday (java.sql.Timestamp Birthday);

	/**
	 * Get Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getBirthday();

    /** Column definition for Birthday */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Birthday = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Birthday", null);
    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPContactGreeting (java.lang.String BPContactGreeting);

	/**
	 * Get Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPContactGreeting();

    /** Column definition for BPContactGreeting */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_BPContactGreeting = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "BPContactGreeting", null);
    /** Column name BPContactGreeting */
    public static final String COLUMNNAME_BPContactGreeting = "BPContactGreeting";

	/**
	 * Set Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPValue (java.lang.String BPValue);

	/**
	 * Get Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPValue();

    /** Column definition for BPValue */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_BPValue = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "BPValue", null);
    /** Column name BPValue */
    public static final String COLUMNNAME_BPValue = "BPValue";

	/**
	 * Set Aggregation Definition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Aggregation_ID (int C_Aggregation_ID);

	/**
	 * Get Aggregation Definition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Aggregation_ID();

    /** Column definition for C_Aggregation_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_C_Aggregation_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "C_Aggregation_ID", null);
    /** Column name C_Aggregation_ID */
    public static final String COLUMNNAME_C_Aggregation_ID = "C_Aggregation_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_BankAccount>(I_I_BPartner.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_Group>(I_I_BPartner.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Geschäftspartner - Druck - Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_PrintFormat_ID (int C_BP_PrintFormat_ID);

	/**
	 * Get Geschäftspartner - Druck - Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_PrintFormat_ID();

	public org.compiere.model.I_C_BP_PrintFormat getC_BP_PrintFormat();

	public void setC_BP_PrintFormat(org.compiere.model.I_C_BP_PrintFormat C_BP_PrintFormat);

    /** Column definition for C_BP_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_PrintFormat> COLUMN_C_BP_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_PrintFormat>(I_I_BPartner.class, "C_BP_PrintFormat_ID", org.compiere.model.I_C_BP_PrintFormat.class);
    /** Column name C_BP_PrintFormat_ID */
    public static final String COLUMNNAME_C_BP_PrintFormat_ID = "C_BP_PrintFormat_ID";

	/**
	 * Set Geschaftspartner ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ExternalId (java.lang.String C_BPartner_ExternalId);

	/**
	 * Get Geschaftspartner ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_BPartner_ExternalId();

    /** Column definition for C_BPartner_ExternalId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_ExternalId = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "C_BPartner_ExternalId", null);
    /** Column name C_BPartner_ExternalId */
    public static final String COLUMNNAME_C_BPartner_ExternalId = "C_BPartner_ExternalId";

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
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner>(I_I_BPartner.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ExternalId (java.lang.String C_BPartner_Location_ExternalId);

	/**
	 * Get Standort ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_BPartner_Location_ExternalId();

    /** Column definition for C_BPartner_Location_ExternalId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_Location_ExternalId = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "C_BPartner_Location_ExternalId", null);
    /** Column name C_BPartner_Location_ExternalId */
    public static final String COLUMNNAME_C_BPartner_Location_ExternalId = "C_BPartner_Location_ExternalId";

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
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner_Location>(I_I_BPartner.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set C_BPartner_Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Memo (java.lang.String C_BPartner_Memo);

	/**
	 * Get C_BPartner_Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_BPartner_Memo();

    /** Column definition for C_BPartner_Memo */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_Memo = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "C_BPartner_Memo", null);
    /** Column name C_BPartner_Memo */
    public static final String COLUMNNAME_C_BPartner_Memo = "C_BPartner_Memo";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Country>(I_I_BPartner.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_DataImport>(I_I_BPartner.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting();

	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting);

    /** Column definition for C_Greeting_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Greeting> COLUMN_C_Greeting_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Greeting>(I_I_BPartner.class, "C_Greeting_ID", org.compiere.model.I_C_Greeting.class);
    /** Column name C_Greeting_ID */
    public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceSchedule_ID();

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

    /** Column definition for C_InvoiceSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_InvoiceSchedule>(I_I_BPartner.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set Position.
	 * Position in der Firma
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Position in der Firma
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Job_ID();

	public org.compiere.model.I_C_Job getC_Job();

	public void setC_Job(org.compiere.model.I_C_Job C_Job);

    /** Column definition for C_Job_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Job> COLUMN_C_Job_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Job>(I_I_BPartner.class, "C_Job_ID", org.compiere.model.I_C_Job.class);
    /** Column name C_Job_ID */
    public static final String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/**
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region();

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column definition for C_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Region>(I_I_BPartner.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set Ort.
	 * Identifies a City
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCity (java.lang.String City);

	/**
	 * Get Ort.
	 * Identifies a City
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/**
	 * Set Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setComments (java.lang.String Comments);

	/**
	 * Get Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getComments();

    /** Column definition for Comments */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Comments = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Comments", null);
    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/**
	 * Set Firmenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCompanyname (java.lang.String Companyname);

	/**
	 * Get Firmenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCompanyname();

    /** Column definition for Companyname */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Companyname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Companyname", null);
    /** Column name Companyname */
    public static final String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Kontakt-Beschreibung.
	 * Description of Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContactDescription (java.lang.String ContactDescription);

	/**
	 * Get Kontakt-Beschreibung.
	 * Description of Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContactDescription();

    /** Column definition for ContactDescription */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ContactDescription = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ContactDescription", null);
    /** Column name ContactDescription */
    public static final String COLUMNNAME_ContactDescription = "ContactDescription";

	/**
	 * Set Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContactName (java.lang.String ContactName);

	/**
	 * Get Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContactName();

    /** Column definition for ContactName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ContactName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ContactName", null);
    /** Column name ContactName */
    public static final String COLUMNNAME_ContactName = "ContactName";

	/**
	 * Set ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountryCode (java.lang.String CountryCode);

	/**
	 * Get ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryCode();

    /** Column definition for CountryCode */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountryName (java.lang.String CountryName);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryName();

    /** Column definition for CountryName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CountryName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CountryName", null);
    /** Column name CountryName */
    public static final String COLUMNNAME_CountryName = "CountryName";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditLimit (java.math.BigDecimal CreditLimit);

	/**
	 * Get Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCreditLimit();

    /** Column definition for CreditLimit */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CreditLimit = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CreditLimit", null);
    /** Column name CreditLimit */
    public static final String COLUMNNAME_CreditLimit = "CreditLimit";

	/**
	 * Set Credit limit 2.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditLimit2 (java.math.BigDecimal CreditLimit2);

	/**
	 * Get Credit limit 2.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCreditLimit2();

    /** Column definition for CreditLimit2 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CreditLimit2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CreditLimit2", null);
    /** Column name CreditLimit2 */
    public static final String COLUMNNAME_CreditLimit2 = "CreditLimit2";

	/**
	 * Set Kreditoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditorId (int CreditorId);

	/**
	 * Get Kreditoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreditorId();

    /** Column definition for CreditorId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CreditorId = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CreditorId", null);
    /** Column name CreditorId */
    public static final String COLUMNNAME_CreditorId = "CreditorId";

	/**
	 * Set Eigene-Kd. Nr. .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomerNoAtVendor (java.lang.String CustomerNoAtVendor);

	/**
	 * Get Eigene-Kd. Nr. .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomerNoAtVendor();

    /** Column definition for CustomerNoAtVendor */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CustomerNoAtVendor = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CustomerNoAtVendor", null);
    /** Column name CustomerNoAtVendor */
    public static final String COLUMNNAME_CustomerNoAtVendor = "CustomerNoAtVendor";

	/**
	 * Set Debitoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDebtorId (int DebtorId);

	/**
	 * Get Debitoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDebtorId();

    /** Column definition for DebtorId */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_DebtorId = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "DebtorId", null);
    /** Column name DebtorId */
    public static final String COLUMNNAME_DebtorId = "DebtorId";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDUNS (java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDUNS();

    /** Column definition for DUNS */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_DUNS = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "DUNS", null);
    /** Column name DUNS */
    public static final String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFax (java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFax();

    /** Column definition for Fax */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Fax", null);
    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstname (java.lang.String Firstname);

	/**
	 * Get Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFirstname();

    /** Column definition for Firstname */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Firstname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Firstname", null);
    /** Column name Firstname */
    public static final String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Erster Verkauf.
	 * Datum des Ersten Verkaufs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstSale (java.sql.Timestamp FirstSale);

	/**
	 * Get Erster Verkauf.
	 * Datum des Ersten Verkaufs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getFirstSale();

    /** Column definition for FirstSale */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_FirstSale = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "FirstSale", null);
    /** Column name FirstSale */
    public static final String COLUMNNAME_FirstSale = "FirstSale";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGLN (java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";

	/**
	 * Set Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGlobalID (java.lang.String GlobalID);

	/**
	 * Get Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGlobalID();

    /** Column definition for GlobalID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_GlobalID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "GlobalID", null);
    /** Column name GlobalID */
    public static final String COLUMNNAME_GlobalID = "GlobalID";

	/**
	 * Set Gruppen-Schlüssel.
	 * Business Partner Group Key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupValue (java.lang.String GroupValue);

	/**
	 * Get Gruppen-Schlüssel.
	 * Business Partner Group Key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupValue();

    /** Column definition for GroupValue */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_GroupValue = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "GroupValue", null);
    /** Column name GroupValue */
    public static final String COLUMNNAME_GroupValue = "GroupValue";

	/**
	 * Set Import - Geschäftspartner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_BPartner_ID (int I_BPartner_ID);

	/**
	 * Get Import - Geschäftspartner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_BPartner_ID();

    /** Column definition for I_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_BPartner_ID", null);
    /** Column name I_BPartner_ID */
    public static final String COLUMNNAME_I_BPartner_ID = "I_BPartner_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIBAN();

    /** Column definition for IBAN */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IBAN = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IBAN", null);
    /** Column name IBAN */
    public static final String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set Interessengebiet.
	 * Name of the Interest Area
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInterestAreaName (java.lang.String InterestAreaName);

	/**
	 * Get Interessengebiet.
	 * Name of the Interest Area
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInterestAreaName();

    /** Column definition for InterestAreaName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_InterestAreaName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "InterestAreaName", null);
    /** Column name InterestAreaName */
    public static final String COLUMNNAME_InterestAreaName = "InterestAreaName";

	/**
	 * Set Status Terminplan.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceSchedule (java.lang.String InvoiceSchedule);

	/**
	 * Get Status Terminplan.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceSchedule();

    /** Column definition for InvoiceSchedule */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_InvoiceSchedule = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "InvoiceSchedule", null);
    /** Column name InvoiceSchedule */
    public static final String COLUMNNAME_InvoiceSchedule = "InvoiceSchedule";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Active Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActiveStatus (boolean IsActiveStatus);

	/**
	 * Get Active Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActiveStatus();

    /** Column definition for IsActiveStatus */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsActiveStatus = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsActiveStatus", null);
    /** Column name IsActiveStatus */
    public static final String COLUMNNAME_IsActiveStatus = "IsActiveStatus";

	/**
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillTo();

    /** Column definition for IsBillTo */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsBillTo = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsBillTo", null);
    /** Column name IsBillTo */
    public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Rechnungskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillToContact_Default (boolean IsBillToContact_Default);

	/**
	 * Get Rechnungskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillToContact_Default();

    /** Column definition for IsBillToContact_Default */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsBillToContact_Default = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsBillToContact_Default", null);
    /** Column name IsBillToContact_Default */
    public static final String COLUMNNAME_IsBillToContact_Default = "IsBillToContact_Default";

	/**
	 * Set Rechnung Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillToDefault (boolean IsBillToDefault);

	/**
	 * Get Rechnung Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillToDefault();

    /** Column definition for IsBillToDefault */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsBillToDefault = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsBillToDefault", null);
    /** Column name IsBillToDefault */
    public static final String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/**
	 * Set Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCustomer();

    /** Column definition for IsCustomer */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsCustomer", null);
    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Standard-Ansprechpartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultContact (boolean IsDefaultContact);

	/**
	 * Get Standard-Ansprechpartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultContact();

    /** Column definition for IsDefaultContact */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsDefaultContact = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsDefaultContact", null);
    /** Column name IsDefaultContact */
    public static final String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	/**
	 * Set Employee.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsEmployee (boolean IsEmployee);

	/**
	 * Get Employee.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isEmployee();

    /** Column definition for IsEmployee */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsEmployee = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsEmployee", null);
    /** Column name IsEmployee */
    public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/**
	 * Set SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSEPASigned (boolean IsSEPASigned);

	/**
	 * Get SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSEPASigned();

    /** Column definition for IsSEPASigned */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsSEPASigned = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsSEPASigned", null);
    /** Column name IsSEPASigned */
    public static final String COLUMNNAME_IsSEPASigned = "IsSEPASigned";

	/**
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipTo();

    /** Column definition for IsShipTo */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsShipTo = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsShipTo", null);
    /** Column name IsShipTo */
    public static final String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set Lieferkontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipToContact_Default (boolean IsShipToContact_Default);

	/**
	 * Get Lieferkontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipToContact_Default();

    /** Column definition for IsShipToContact_Default */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsShipToContact_Default = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsShipToContact_Default", null);
    /** Column name IsShipToContact_Default */
    public static final String COLUMNNAME_IsShipToContact_Default = "IsShipToContact_Default";

	/**
	 * Set Liefer Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipToDefault (boolean IsShipToDefault);

	/**
	 * Get Liefer Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipToDefault();

    /** Column definition for IsShipToDefault */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsShipToDefault = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsShipToDefault", null);
    /** Column name IsShipToDefault */
    public static final String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/**
	 * Set Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsVendor (boolean IsVendor);

	/**
	 * Get Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isVendor();

    /** Column definition for IsVendor */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsVendor = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsVendor", null);
    /** Column name IsVendor */
    public static final String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set JobName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJobName (java.lang.String JobName);

	/**
	 * Get JobName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJobName();

    /** Column definition for JobName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_JobName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "JobName", null);
    /** Column name JobName */
    public static final String COLUMNNAME_JobName = "JobName";

	/**
	 * Set Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastname (java.lang.String Lastname);

	/**
	 * Get Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastname();

    /** Column definition for Lastname */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Lastname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Lastname", null);
    /** Column name Lastname */
    public static final String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLeadTimeOffset (int LeadTimeOffset);

	/**
	 * Get Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLeadTimeOffset();

    /** Column definition for LeadTimeOffset */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_LeadTimeOffset = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "LeadTimeOffset", null);
    /** Column name LeadTimeOffset */
    public static final String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_PricingSystem>(I_I_BPartner.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_Shipper>(I_I_BPartner.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Memo_Delivery.
	 * Memo Lieferung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMemo_Delivery (java.lang.String Memo_Delivery);

	/**
	 * Get Memo_Delivery.
	 * Memo Lieferung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMemo_Delivery();

    /** Column definition for Memo_Delivery */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Memo_Delivery = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Memo_Delivery", null);
    /** Column name Memo_Delivery */
    public static final String COLUMNNAME_Memo_Delivery = "Memo_Delivery";

	/**
	 * Set Memo_Invoicing.
	 * Memo Abrechnung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMemo_Invoicing (java.lang.String Memo_Invoicing);

	/**
	 * Get Memo_Invoicing.
	 * Memo Abrechnung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMemo_Invoicing();

    /** Column definition for Memo_Invoicing */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Memo_Invoicing = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Memo_Invoicing", null);
    /** Column name Memo_Invoicing */
    public static final String COLUMNNAME_Memo_Invoicing = "Memo_Invoicing";

	/**
	 * Set NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNAICS (java.lang.String NAICS);

	/**
	 * Get NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNAICS();

    /** Column definition for NAICS */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_NAICS = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "NAICS", null);
    /** Column name NAICS */
    public static final String COLUMNNAME_NAICS = "NAICS";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName3 (java.lang.String Name3);

	/**
	 * Get Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName3();

    /** Column definition for Name3 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Name3 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Name3", null);
    /** Column name Name3 */
    public static final String COLUMNNAME_Name3 = "Name3";

	/**
	 * Set Organisations-Schlüssel.
	 * Suchschlüssel der Organisation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgValue (java.lang.String OrgValue);

	/**
	 * Get Organisations-Schlüssel.
	 * Suchschlüssel der Organisation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrgValue();

    /** Column definition for OrgValue */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_OrgValue = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "OrgValue", null);
    /** Column name OrgValue */
    public static final String COLUMNNAME_OrgValue = "OrgValue";

	/**
	 * Set Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * Wie die Rechnung bezahlt wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Zahlungsweise.
	 * Möglichkeiten der Bezahlung einer Bestellung
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentRulePO (java.lang.String PaymentRulePO);

	/**
	 * Get Zahlungsweise.
	 * Möglichkeiten der Bezahlung einer Bestellung
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRulePO();

    /** Column definition for PaymentRulePO */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PaymentRulePO = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PaymentRulePO", null);
    /** Column name PaymentRulePO */
    public static final String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/**
	 * Set Zahlungskondition.
	 * Zahlungskondition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentTerm (java.lang.String PaymentTerm);

	/**
	 * Get Zahlungskondition.
	 * Zahlungskondition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentTerm();

    /** Column definition for PaymentTerm */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PaymentTerm = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PaymentTerm", null);
    /** Column name PaymentTerm */
    public static final String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/**
	 * Set Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone (java.lang.String Phone);

	/**
	 * Get Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Mobil.
	 * Alternative Mobile Telefonnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone2 (java.lang.String Phone2);

	/**
	 * Get Mobil.
	 * Alternative Mobile Telefonnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone2();

    /** Column definition for Phone2 */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Phone2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Phone2", null);
    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set Zahlungskondition.
	 * Zahlungskondition für die Bestellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get Zahlungskondition.
	 * Zahlungskondition für die Bestellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm();

	public void setPO_PaymentTerm(org.compiere.model.I_C_PaymentTerm PO_PaymentTerm);

    /** Column definition for PO_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_PaymentTerm> COLUMN_PO_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_PaymentTerm>(I_I_BPartner.class, "PO_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name PO_PaymentTerm_ID */
    public static final String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/**
	 * Set Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem();

	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem);

    /** Column definition for PO_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_PricingSystem> COLUMN_PO_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_M_PricingSystem>(I_I_BPartner.class, "PO_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name PO_PricingSystem_ID */
    public static final String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

	/**
	 * Set PO_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PricingSystem_Value (java.lang.String PO_PricingSystem_Value);

	/**
	 * Get PO_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPO_PricingSystem_Value();

    /** Column definition for PO_PricingSystem_Value */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PO_PricingSystem_Value = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PO_PricingSystem_Value", null);
    /** Column name PO_PricingSystem_Value */
    public static final String COLUMNNAME_PO_PricingSystem_Value = "PO_PricingSystem_Value";

	/**
	 * Set Postfach.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOBox (java.lang.String POBox);

	/**
	 * Get Postfach.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOBox();

    /** Column definition for POBox */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_POBox = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "POBox", null);
    /** Column name POBox */
    public static final String COLUMNNAME_POBox = "POBox";

	/**
	 * Set PLZ.
	 * Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal (java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Postal", null);
    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal_Add (java.lang.String Postal_Add);

	/**
	 * Get -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal_Add();

    /** Column definition for Postal_Add */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Postal_Add = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Postal_Add", null);
    /** Column name Postal_Add */
    public static final String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPricingSystem_Value (java.lang.String PricingSystem_Value);

	/**
	 * Get PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPricingSystem_Value();

    /** Column definition for PricingSystem_Value */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PricingSystem_Value = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PricingSystem_Value", null);
    /** Column name PricingSystem_Value */
    public static final String COLUMNNAME_PricingSystem_Value = "PricingSystem_Value";

	/**
	 * Set PrintFormat_Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintFormat_Name (java.lang.String PrintFormat_Name);

	/**
	 * Get PrintFormat_Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintFormat_Name();

    /** Column definition for PrintFormat_Name */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_PrintFormat_Name = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "PrintFormat_Name", null);
    /** Column name PrintFormat_Name */
    public static final String COLUMNNAME_PrintFormat_Name = "PrintFormat_Name";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Qualification .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualification (java.lang.String Qualification);

	/**
	 * Get Qualification .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQualification();

    /** Column definition for Qualification */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Qualification = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Qualification", null);
    /** Column name Qualification */
    public static final String COLUMNNAME_Qualification = "Qualification";

	/**
	 * Set Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_InterestArea_ID (int R_InterestArea_ID);

	/**
	 * Get Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_InterestArea_ID();

	public org.compiere.model.I_R_InterestArea getR_InterestArea();

	public void setR_InterestArea(org.compiere.model.I_R_InterestArea R_InterestArea);

    /** Column definition for R_InterestArea_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_R_InterestArea> COLUMN_R_InterestArea_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_R_InterestArea>(I_I_BPartner.class, "R_InterestArea_ID", org.compiere.model.I_R_InterestArea.class);
    /** Column name R_InterestArea_ID */
    public static final String COLUMNNAME_R_InterestArea_ID = "R_InterestArea_ID";

	/**
	 * Set Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRegionName (java.lang.String RegionName);

	/**
	 * Get Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRegionName();

    /** Column definition for RegionName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_RegionName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "RegionName", null);
    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Statistik Gruppe.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesgroup (java.lang.String Salesgroup);

	/**
	 * Get Statistik Gruppe.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSalesgroup();

    /** Column definition for Salesgroup */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Salesgroup = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Salesgroup", null);
    /** Column name Salesgroup */
    public static final String COLUMNNAME_Salesgroup = "Salesgroup";

	/**
	 * Set Mindesthaltbarkeit Tage.
	 * Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfLifeMinDays (int ShelfLifeMinDays);

	/**
	 * Get Mindesthaltbarkeit Tage.
	 * Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfLifeMinDays();

    /** Column definition for ShelfLifeMinDays */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ShelfLifeMinDays = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ShelfLifeMinDays", null);
    /** Column name ShelfLifeMinDays */
    public static final String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/**
	 * Set Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShipperName (java.lang.String ShipperName);

	/**
	 * Get Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShipperName();

    /** Column definition for ShipperName */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ShipperName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ShipperName", null);
    /** Column name ShipperName */
    public static final String COLUMNNAME_ShipperName = "ShipperName";

	/**
	 * Set Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShortDescription (java.lang.String ShortDescription);

	/**
	 * Get Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShortDescription();

    /** Column definition for ShortDescription */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ShortDescription = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ShortDescription", null);
    /** Column name ShortDescription */
    public static final String COLUMNNAME_ShortDescription = "ShortDescription";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSwiftCode (java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwiftCode();

    /** Column definition for SwiftCode */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_SwiftCode = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "SwiftCode", null);
    /** Column name SwiftCode */
    public static final String COLUMNNAME_SwiftCode = "SwiftCode";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTaxID (java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTaxID();

    /** Column definition for TaxID */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_TaxID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "TaxID", null);
    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTitle (java.lang.String Title);

	/**
	 * Get Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTitle();

    /** Column definition for Title */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Title = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Title", null);
    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL (java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL();

    /** Column definition for URL */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_URL = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "URL", null);
    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/**
	 * Set Lieferanten Kategorie.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorCategory (java.lang.String VendorCategory);

	/**
	 * Get Lieferanten Kategorie.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorCategory();

    /** Column definition for VendorCategory */
    public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_VendorCategory = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "VendorCategory", null);
    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";
}
