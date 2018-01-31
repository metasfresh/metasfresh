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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setApprovedBy_ID (int ApprovedBy_ID);

	/**
	 * Get Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
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
	 * Set Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.sql.Timestamp DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

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
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_C_BPartner_CreditLimit, Object>(I_C_BPartner_CreditLimit.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
