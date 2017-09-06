package de.metas.flatrate.model;


/** Generated Interface for C_Invoice_Clearing_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Clearing_Alloc 
{

    /** TableName=C_Invoice_Clearing_Alloc */
    public static final String Table_Name = "C_Invoice_Clearing_Alloc";

    /** AD_Table_ID=540314 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_Client>(I_C_Invoice_Clearing_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_Org>(I_C_Invoice_Clearing_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Abrechnungssatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID);

	/**
	 * Get Abrechnungssatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_DataEntry_ID();

	public de.metas.flatrate.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry();

	public void setC_Flatrate_DataEntry(de.metas.flatrate.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry);

    /** Column definition for C_Flatrate_DataEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.flatrate.model.I_C_Flatrate_DataEntry> COLUMN_C_Flatrate_DataEntry_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.flatrate.model.I_C_Flatrate_DataEntry>(I_C_Invoice_Clearing_Alloc.class, "C_Flatrate_DataEntry_ID", de.metas.flatrate.model.I_C_Flatrate_DataEntry.class);
    /** Column name C_Flatrate_DataEntry_ID */
    public static final String COLUMNNAME_C_Flatrate_DataEntry_ID = "C_Flatrate_DataEntry_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term();

	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term);

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.flatrate.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.flatrate.model.I_C_Flatrate_Term>(I_C_Invoice_Clearing_Alloc.class, "C_Flatrate_Term_ID", de.metas.flatrate.model.I_C_Flatrate_Term.class);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Zu verrechnender Rechn-Kand..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Cand_ToClear_ID (int C_Invoice_Cand_ToClear_ID);

	/**
	 * Get Zu verrechnender Rechn-Kand..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Cand_ToClear_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Cand_ToClear();

	public void setC_Invoice_Cand_ToClear(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Cand_ToClear);

    /** Column definition for C_Invoice_Cand_ToClear_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Cand_ToClear_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_Invoice_Clearing_Alloc.class, "C_Invoice_Cand_ToClear_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Cand_ToClear_ID */
    public static final String COLUMNNAME_C_Invoice_Cand_ToClear_ID = "C_Invoice_Cand_ToClear_ID";

	/**
	 * Set Rechnungskandidat.
	 * Eindeutige Identifikationsnummer eines Rechnungskandidaten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 * Eindeutige Identifikationsnummer eines Rechnungskandidaten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_Invoice_Clearing_Alloc.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Rechnungskanditad - Verrechnung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Clearing_Alloc_ID (int C_Invoice_Clearing_Alloc_ID);

	/**
	 * Get Rechnungskanditad - Verrechnung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Clearing_Alloc_ID();

    /** Column definition for C_Invoice_Clearing_Alloc_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object> COLUMN_C_Invoice_Clearing_Alloc_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object>(I_C_Invoice_Clearing_Alloc.class, "C_Invoice_Clearing_Alloc_ID", null);
    /** Column name C_Invoice_Clearing_Alloc_ID */
    public static final String COLUMNNAME_C_Invoice_Clearing_Alloc_ID = "C_Invoice_Clearing_Alloc_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object>(I_C_Invoice_Clearing_Alloc.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_User>(I_C_Invoice_Clearing_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object>(I_C_Invoice_Clearing_Alloc.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, Object>(I_C_Invoice_Clearing_Alloc.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Clearing_Alloc, org.compiere.model.I_AD_User>(I_C_Invoice_Clearing_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
