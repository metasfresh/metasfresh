package org.compiere.model;


/** Generated Interface for C_Invoice_Reference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Reference 
{

    /** TableName=C_Invoice_Reference */
    public static final String Table_Name = "C_Invoice_Reference";

    /** AD_Table_ID=540834 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_Client>(I_C_Invoice_Reference.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_Org>(I_C_Invoice_Reference.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_C_Invoice>(I_C_Invoice_Reference.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set C_Invoice_Linked_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Linked_ID (int C_Invoice_Linked_ID);

	/**
	 * Get C_Invoice_Linked_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Linked_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice_Linked();

	public void setC_Invoice_Linked(org.compiere.model.I_C_Invoice C_Invoice_Linked);

    /** Column definition for C_Invoice_Linked_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_Linked_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_C_Invoice>(I_C_Invoice_Reference.class, "C_Invoice_Linked_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_Linked_ID */
    public static final String COLUMNNAME_C_Invoice_Linked_ID = "C_Invoice_Linked_ID";

	/**
	 * Set C_Invoice_Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Reference_ID (int C_Invoice_Reference_ID);

	/**
	 * Get C_Invoice_Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Reference_ID();

    /** Column definition for C_Invoice_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object> COLUMN_C_Invoice_Reference_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object>(I_C_Invoice_Reference.class, "C_Invoice_Reference_ID", null);
    /** Column name C_Invoice_Reference_ID */
    public static final String COLUMNNAME_C_Invoice_Reference_ID = "C_Invoice_Reference_ID";

	/**
	 * Set C_Invoice_Reference_Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Reference_Type (java.lang.String C_Invoice_Reference_Type);

	/**
	 * Get C_Invoice_Reference_Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_Invoice_Reference_Type();

    /** Column definition for C_Invoice_Reference_Type */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object> COLUMN_C_Invoice_Reference_Type = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object>(I_C_Invoice_Reference.class, "C_Invoice_Reference_Type", null);
    /** Column name C_Invoice_Reference_Type */
    public static final String COLUMNNAME_C_Invoice_Reference_Type = "C_Invoice_Reference_Type";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object>(I_C_Invoice_Reference.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_User>(I_C_Invoice_Reference.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object>(I_C_Invoice_Reference.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, Object>(I_C_Invoice_Reference.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Reference, org.compiere.model.I_AD_User>(I_C_Invoice_Reference.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
