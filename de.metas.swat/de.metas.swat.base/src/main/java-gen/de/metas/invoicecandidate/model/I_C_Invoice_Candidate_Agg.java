package de.metas.invoicecandidate.model;


/** Generated Interface for C_Invoice_Candidate_Agg
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Candidate_Agg 
{

    /** TableName=C_Invoice_Candidate_Agg */
    public static final String Table_Name = "C_Invoice_Candidate_Agg";

    /** AD_Table_ID=540322 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_Client>(I_C_Invoice_Candidate_Agg.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_Org>(I_C_Invoice_Candidate_Agg.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_C_BPartner>(I_C_Invoice_Candidate_Agg.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID);

	/**
	 * Get Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Agg_ID();

    /** Column definition for C_Invoice_Candidate_Agg_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_C_Invoice_Candidate_Agg_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "C_Invoice_Candidate_Agg_ID", null);
    /** Column name C_Invoice_Candidate_Agg_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Agg_ID = "C_Invoice_Candidate_Agg_ID";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClassname (java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassname();

    /** Column definition for Classname */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "Classname", null);
    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_Agg.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductGroup_ID (int M_ProductGroup_ID);

	/**
	 * Get Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ProductGroup_ID();

	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup();

	public void setM_ProductGroup(de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup);

    /** Column definition for M_ProductGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, de.metas.invoicecandidate.model.I_M_ProductGroup> COLUMN_M_ProductGroup_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, de.metas.invoicecandidate.model.I_M_ProductGroup>(I_C_Invoice_Candidate_Agg.class, "M_ProductGroup_ID", de.metas.invoicecandidate.model.I_M_ProductGroup.class);
    /** Column name M_ProductGroup_ID */
    public static final String COLUMNNAME_M_ProductGroup_ID = "M_ProductGroup_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, Object>(I_C_Invoice_Candidate_Agg.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Agg, org.compiere.model.I_AD_User>(I_C_Invoice_Candidate_Agg.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
