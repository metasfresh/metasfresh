package de.metas.contracts.commission.model;


/** Generated Interface for C_Commission_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Commission_Instance 
{

    /** TableName=C_Commission_Instance */
    public static final String Table_Name = "C_Commission_Instance";

    /** AD_Table_ID=541405 */
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
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set C_Commission_Instance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID);

	/**
	 * Get C_Commission_Instance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Instance_ID();

    /** Column definition for C_Commission_Instance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_C_Commission_Instance_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "C_Commission_Instance_ID", null);
    /** Column name C_Commission_Instance_ID */
    public static final String COLUMNNAME_C_Commission_Instance_ID = "C_Commission_Instance_ID";

	/**
	 * Set Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "C_Invoice_Candidate_ID", null);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, org.compiere.model.I_C_Order>(I_C_Commission_Instance.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MostRecentTriggerTimestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMostRecentTriggerTimestamp (java.sql.Timestamp MostRecentTriggerTimestamp);

	/**
	 * Get MostRecentTriggerTimestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMostRecentTriggerTimestamp();

    /** Column definition for MostRecentTriggerTimestamp */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_MostRecentTriggerTimestamp = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "MostRecentTriggerTimestamp", null);
    /** Column name MostRecentTriggerTimestamp */
    public static final String COLUMNNAME_MostRecentTriggerTimestamp = "MostRecentTriggerTimestamp";

	/**
	 * Set Prognostizierte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Forecasted (java.math.BigDecimal PointsBase_Forecasted);

	/**
	 * Get Prognostizierte Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Forecasted();

    /** Column definition for PointsBase_Forecasted */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_PointsBase_Forecasted = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "PointsBase_Forecasted", null);
    /** Column name PointsBase_Forecasted */
    public static final String COLUMNNAME_PointsBase_Forecasted = "PointsBase_Forecasted";

	/**
	 * Set Abzurechn. Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Invoiceable (java.math.BigDecimal PointsBase_Invoiceable);

	/**
	 * Get Abzurechn. Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Invoiceable();

    /** Column definition for PointsBase_Invoiceable */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_PointsBase_Invoiceable = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "PointsBase_Invoiceable", null);
    /** Column name PointsBase_Invoiceable */
    public static final String COLUMNNAME_PointsBase_Invoiceable = "PointsBase_Invoiceable";

	/**
	 * Set Abgerechn. Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsBase_Invoiced (java.math.BigDecimal PointsBase_Invoiced);

	/**
	 * Get Abgerechn. Basispunktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsBase_Invoiced();

    /** Column definition for PointsBase_Invoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_PointsBase_Invoiced = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "PointsBase_Invoiced", null);
    /** Column name PointsBase_Invoiced */
    public static final String COLUMNNAME_PointsBase_Invoiced = "PointsBase_Invoiced";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Commission_Instance, Object>(I_C_Commission_Instance.class, "Updated", null);
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
