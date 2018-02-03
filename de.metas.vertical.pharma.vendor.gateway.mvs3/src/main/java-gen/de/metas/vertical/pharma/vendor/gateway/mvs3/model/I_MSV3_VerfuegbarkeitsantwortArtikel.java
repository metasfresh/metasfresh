package de.metas.vertical.pharma.vendor.gateway.mvs3.model;


/** Generated Interface for MSV3_VerfuegbarkeitsantwortArtikel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_VerfuegbarkeitsantwortArtikel 
{

    /** TableName=MSV3_VerfuegbarkeitsantwortArtikel */
    public static final String Table_Name = "MSV3_VerfuegbarkeitsantwortArtikel";

    /** AD_Table_ID=540907 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_Client>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_Org>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_C_OrderLine>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_ID();

    /** Column definition for C_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "C_PurchaseCandidate_ID", null);
    /** Column name C_PurchaseCandidate_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set AnfrageMenge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_AnfrageMenge (int MSV3_AnfrageMenge);

	/**
	 * Get AnfrageMenge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_AnfrageMenge();

    /** Column definition for MSV3_AnfrageMenge */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_MSV3_AnfrageMenge = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "MSV3_AnfrageMenge", null);
    /** Column name MSV3_AnfrageMenge */
    public static final String COLUMNNAME_MSV3_AnfrageMenge = "MSV3_AnfrageMenge";

	/**
	 * Set AnfragePzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_AnfragePzn (java.lang.String MSV3_AnfragePzn);

	/**
	 * Get AnfragePzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_AnfragePzn();

    /** Column definition for MSV3_AnfragePzn */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_MSV3_AnfragePzn = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "MSV3_AnfragePzn", null);
    /** Column name MSV3_AnfragePzn */
    public static final String COLUMNNAME_MSV3_AnfragePzn = "MSV3_AnfragePzn";

	/**
	 * Set MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID (int MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID);

	/**
	 * Get MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID();

	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort getMSV3_VerfuegbarkeitsanfrageEinzelneAntwort();

	public void setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort MSV3_VerfuegbarkeitsanfrageEinzelneAntwort);

    /** Column definition for MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort> COLUMN_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID", de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class);
    /** Column name MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID = "MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID";

	/**
	 * Set VerfuegbarkeitsantwortArtikel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitsantwortArtikel_ID (int MSV3_VerfuegbarkeitsantwortArtikel_ID);

	/**
	 * Get VerfuegbarkeitsantwortArtikel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitsantwortArtikel_ID();

    /** Column definition for MSV3_VerfuegbarkeitsantwortArtikel_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_MSV3_VerfuegbarkeitsantwortArtikel_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "MSV3_VerfuegbarkeitsantwortArtikel_ID", null);
    /** Column name MSV3_VerfuegbarkeitsantwortArtikel_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID = "MSV3_VerfuegbarkeitsantwortArtikel_ID";

	/**
	 * Set VerfuegbarkeitSubstitution.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitSubstitution_ID (int MSV3_VerfuegbarkeitSubstitution_ID);

	/**
	 * Get VerfuegbarkeitSubstitution.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitSubstitution_ID();

	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution getMSV3_VerfuegbarkeitSubstitution();

	public void setMSV3_VerfuegbarkeitSubstitution(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution MSV3_VerfuegbarkeitSubstitution);

    /** Column definition for MSV3_VerfuegbarkeitSubstitution_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution> COLUMN_MSV3_VerfuegbarkeitSubstitution_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "MSV3_VerfuegbarkeitSubstitution_ID", de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution.class);
    /** Column name MSV3_VerfuegbarkeitSubstitution_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID = "MSV3_VerfuegbarkeitSubstitution_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, Object>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitsantwortArtikel.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
