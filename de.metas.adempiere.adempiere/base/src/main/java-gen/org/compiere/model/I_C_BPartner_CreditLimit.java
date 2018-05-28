package org.compiere.model;


/** Generated Interface for C_BPartner_CreditLimit
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_CreditLimit 
{

    /** TableName=C_BPartner_CreditLimit */
    public static final String Table_Name = "C_BPartner_CreditLimit";

    /** AD_Table_ID=540929 */
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_Client>(I_C_BPartner_CreditLimit.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_Org>(I_C_BPartner_CreditLimit.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Betrag in einer definierten Währung
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmount (java.math.BigDecimal Amount);

	/**
	 * Get Betrag.
	 * Betrag in einer definierten Währung
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmount();

    /** Column definition for Amount */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Amount = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "Amount", null);
    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setApprovedBy_ID (int ApprovedBy_ID);

	/**
	 * Get Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getApprovedBy_ID();

	public org.compiere.model.I_AD_User getApprovedBy();

	public void setApprovedBy(org.compiere.model.I_AD_User ApprovedBy);

    /** Column definition for ApprovedBy_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User> COLUMN_ApprovedBy_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User>(I_C_BPartner_CreditLimit.class, "ApprovedBy_ID", org.compiere.model.I_AD_User.class);
    /** Column name ApprovedBy_ID */
    public static final String COLUMNNAME_ApprovedBy_ID = "ApprovedBy_ID";

	/**
	 * Set Bussines Partner Credit Limit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_CreditLimit_ID (int C_BPartner_CreditLimit_ID);

	/**
	 * Get Bussines Partner Credit Limit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_CreditLimit_ID();

    /** Column definition for C_BPartner_CreditLimit_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_C_BPartner_CreditLimit_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "C_BPartner_CreditLimit_ID", null);
    /** Column name C_BPartner_CreditLimit_ID */
    public static final String COLUMNNAME_C_BPartner_CreditLimit_ID = "C_BPartner_CreditLimit_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_BPartner>(I_C_BPartner_CreditLimit.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Credit Limit Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CreditLimit_Type_ID (int C_CreditLimit_Type_ID);

	/**
	 * Get Credit Limit Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CreditLimit_Type_ID();

	public org.compiere.model.I_C_CreditLimit_Type getC_CreditLimit_Type();

	public void setC_CreditLimit_Type(org.compiere.model.I_C_CreditLimit_Type C_CreditLimit_Type);

    /** Column definition for C_CreditLimit_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_CreditLimit_Type> COLUMN_C_CreditLimit_Type_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_CreditLimit_Type>(I_C_BPartner_CreditLimit.class, "C_CreditLimit_Type_ID", org.compiere.model.I_C_CreditLimit_Type.class);
    /** Column name C_CreditLimit_Type_ID */
    public static final String COLUMNNAME_C_CreditLimit_Type_ID = "C_CreditLimit_Type_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	@Deprecated
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_Currency>(I_C_BPartner_CreditLimit.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User>(I_C_BPartner_CreditLimit.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum von.
	 * Startdatum eines Abschnittes
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFrom (java.sql.Timestamp DateFrom);

	/**
	 * Get Datum von.
	 * Startdatum eines Abschnittes
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFrom();

    /** Column definition for DateFrom */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_DateFrom = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "DateFrom", null);
    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Freigegeben.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_User>(I_C_BPartner_CreditLimit.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
