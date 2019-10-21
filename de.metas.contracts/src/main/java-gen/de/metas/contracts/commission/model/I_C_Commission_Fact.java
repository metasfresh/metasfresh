package de.metas.contracts.commission.model;


/** Generated Interface for C_Commission_Fact
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Commission_Fact 
{

    /** TableName=C_Commission_Fact */
    public static final String Table_Name = "C_Commission_Fact";

    /** AD_Table_ID=541407 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set C_Commission_Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Fact_ID (int C_Commission_Fact_ID);

	/**
	 * Get C_Commission_Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Fact_ID();

    /** Column definition for C_Commission_Fact_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_C_Commission_Fact_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "C_Commission_Fact_ID", null);
    /** Column name C_Commission_Fact_ID */
    public static final String COLUMNNAME_C_Commission_Fact_ID = "C_Commission_Fact_ID";

	/**
	 * Set C_Commission_Share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Share_ID (int C_Commission_Share_ID);

	/**
	 * Get C_Commission_Share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Share_ID();

	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share();

	public void setC_Commission_Share(de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share);

    /** Column definition for C_Commission_Share_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, de.metas.contracts.commission.model.I_C_Commission_Share> COLUMN_C_Commission_Share_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, de.metas.contracts.commission.model.I_C_Commission_Share>(I_C_Commission_Fact.class, "C_Commission_Share_ID", de.metas.contracts.commission.model.I_C_Commission_Share.class);
    /** Column name C_Commission_Share_ID */
    public static final String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

	/**
	 * Set Provisionsabrechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Commission_ID (int C_Invoice_Candidate_Commission_ID);

	/**
	 * Get Provisionsabrechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Commission_ID();

    /** Column definition for C_Invoice_Candidate_Commission_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_C_Invoice_Candidate_Commission_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "C_Invoice_Candidate_Commission_ID", null);
    /** Column name C_Invoice_Candidate_Commission_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Commission_ID = "C_Invoice_Candidate_Commission_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommission_Fact_State (java.lang.String Commission_Fact_State);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCommission_Fact_State();

    /** Column definition for Commission_Fact_State */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_Commission_Fact_State = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "Commission_Fact_State", null);
    /** Column name Commission_Fact_State */
    public static final String COLUMNNAME_Commission_Fact_State = "Commission_Fact_State";

	/**
	 * Set Zeitstempel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommissionFactTimestamp (java.lang.String CommissionFactTimestamp);

	/**
	 * Get Zeitstempel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCommissionFactTimestamp();

    /** Column definition for CommissionFactTimestamp */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_CommissionFactTimestamp = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "CommissionFactTimestamp", null);
    /** Column name CommissionFactTimestamp */
    public static final String COLUMNNAME_CommissionFactTimestamp = "CommissionFactTimestamp";

	/**
	 * Set Provisionspunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommissionPoints (java.math.BigDecimal CommissionPoints);

	/**
	 * Get Provisionspunkte.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCommissionPoints();

    /** Column definition for CommissionPoints */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_CommissionPoints = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "CommissionPoints", null);
    /** Column name CommissionPoints */
    public static final String COLUMNNAME_CommissionPoints = "CommissionPoints";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Commission_Fact, Object>(I_C_Commission_Fact.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
