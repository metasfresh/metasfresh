package de.metas.acct.model;


/** Generated Interface for C_VAT_Code
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_VAT_Code 
{

    /** TableName=C_VAT_Code */
    public static final String Table_Name = "C_VAT_Code";

    /** AD_Table_ID=540703 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_Client>(I_C_VAT_Code.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_Org>(I_C_VAT_Code.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Stammdaten für Buchhaltung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_C_AcctSchema>(I_C_VAT_Code.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_C_Tax>(I_C_VAT_Code.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_VAT_Code_ID (int C_VAT_Code_ID);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_VAT_Code_ID();

    /** Column definition for C_VAT_Code_ID */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_C_VAT_Code_ID = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "C_VAT_Code_ID", null);
    /** Column name C_VAT_Code_ID */
    public static final String COLUMNNAME_C_VAT_Code_ID = "C_VAT_Code_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_User>(I_C_VAT_Code.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (java.lang.String IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_VAT_Code, org.compiere.model.I_AD_User>(I_C_VAT_Code.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVATCode (java.lang.String VATCode);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVATCode();

    /** Column definition for VATCode */
    public static final org.adempiere.model.ModelColumn<I_C_VAT_Code, Object> COLUMN_VATCode = new org.adempiere.model.ModelColumn<I_C_VAT_Code, Object>(I_C_VAT_Code.class, "VATCode", null);
    /** Column name VATCode */
    public static final String COLUMNNAME_VATCode = "VATCode";
}
