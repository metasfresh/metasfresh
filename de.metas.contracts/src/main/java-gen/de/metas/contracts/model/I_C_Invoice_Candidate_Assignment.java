package de.metas.contracts.model;


/** Generated Interface for C_Invoice_Candidate_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Candidate_Assignment 
{

    /** TableName=C_Invoice_Candidate_Assignment */
    public static final String Table_Name = "C_Invoice_Candidate_Assignment";

    /** AD_Table_ID=540981 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_Client>(I_C_Invoice_Candidate_Assignment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_Org>(I_C_Invoice_Candidate_Assignment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Zugeordneter Betrag.
	 * Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssignedAmount (java.math.BigDecimal AssignedAmount);

	/**
	 * Get Zugeordneter Betrag.
	 * Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAssignedAmount();

    /** Column definition for AssignedAmount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_AssignedAmount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "AssignedAmount", null);
    /** Column name AssignedAmount */
    public static final String COLUMNNAME_AssignedAmount = "AssignedAmount";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, de.metas.contracts.model.I_C_Flatrate_Term>(I_C_Invoice_Candidate_Assignment.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Zugeordneter Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Assigned_ID (int C_Invoice_Candidate_Assigned_ID);

	/**
	 * Get Zugeordneter Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Assigned_ID();

    /** Column definition for C_Invoice_Candidate_Assigned_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_C_Invoice_Candidate_Assigned_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "C_Invoice_Candidate_Assigned_ID", null);
    /** Column name C_Invoice_Candidate_Assigned_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Assigned_ID = "C_Invoice_Candidate_Assigned_ID";

	/**
	 * Set C_Invoice_Candidate_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Assignment_ID (int C_Invoice_Candidate_Assignment_ID);

	/**
	 * Get C_Invoice_Candidate_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Assignment_ID();

    /** Column definition for C_Invoice_Candidate_Assignment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_C_Invoice_Candidate_Assignment_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "C_Invoice_Candidate_Assignment_ID", null);
    /** Column name C_Invoice_Candidate_Assignment_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Assignment_ID = "C_Invoice_Candidate_Assignment_ID";

	/**
	 * Set Vertrag-Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Term_ID (int C_Invoice_Candidate_Term_ID);

	/**
	 * Get Vertrag-Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Term_ID();

    /** Column definition for C_Invoice_Candidate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_C_Invoice_Candidate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "C_Invoice_Candidate_Term_ID", null);
    /** Column name C_Invoice_Candidate_Term_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Term_ID = "C_Invoice_Candidate_Term_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_Assignment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, Object>(I_C_Invoice_Candidate_Assignment.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_Assignment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
