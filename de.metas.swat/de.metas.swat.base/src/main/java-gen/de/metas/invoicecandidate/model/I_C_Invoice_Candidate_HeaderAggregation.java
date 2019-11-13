package de.metas.invoicecandidate.model;


/** Generated Interface for C_Invoice_Candidate_HeaderAggregation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Candidate_HeaderAggregation 
{

    /** TableName=C_Invoice_Candidate_HeaderAggregation */
    public static final String Table_Name = "C_Invoice_Candidate_HeaderAggregation";

    /** AD_Table_ID=540648 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_Client>(I_C_Invoice_Candidate_HeaderAggregation.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_Org>(I_C_Invoice_Candidate_HeaderAggregation.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_C_BPartner>(I_C_Invoice_Candidate_HeaderAggregation.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Abrechnungsgruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID);

	/**
	 * Get Abrechnungsgruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_HeaderAggregation_ID();

    /** Column definition for C_Invoice_Candidate_HeaderAggregation_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_C_Invoice_Candidate_HeaderAggregation_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "C_Invoice_Candidate_HeaderAggregation_ID", null);
    /** Column name C_Invoice_Candidate_HeaderAggregation_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID = "C_Invoice_Candidate_HeaderAggregation_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_HeaderAggregation.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey();

    /** Column definition for HeaderAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_HeaderAggregationKey = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "HeaderAggregationKey", null);
    /** Column name HeaderAggregationKey */
    public static final String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID);

	/**
	 * Get Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getHeaderAggregationKeyBuilder_ID();

    /** Column definition for HeaderAggregationKeyBuilder_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_HeaderAggregationKeyBuilder_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "HeaderAggregationKeyBuilder_ID", null);
    /** Column name HeaderAggregationKeyBuilder_ID */
    public static final String COLUMNNAME_HeaderAggregationKeyBuilder_ID = "HeaderAggregationKeyBuilder_ID";

	/**
	 * Set Abrechnungsgruppe.
	 * Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoicingGroupNo (int InvoicingGroupNo);

	/**
	 * Get Abrechnungsgruppe.
	 * Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInvoicingGroupNo();

    /** Column definition for InvoicingGroupNo */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_InvoicingGroupNo = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "InvoicingGroupNo", null);
    /** Column name InvoicingGroupNo */
    public static final String COLUMNNAME_InvoicingGroupNo = "InvoicingGroupNo";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object>(I_C_Invoice_Candidate_HeaderAggregation.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_HeaderAggregation.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
