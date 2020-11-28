package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel 
{

    /** TableName=MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel */
    public static final String Table_Name = "MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel";

    /** AD_Table_ID=540920 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_Client>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_Org>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_C_OrderLine>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "C_PurchaseCandidate_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bedarf.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Bedarf (java.lang.String MSV3_Bedarf);

	/**
	 * Get Bedarf.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Bedarf();

    /** Column definition for MSV3_Bedarf */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_MSV3_Bedarf = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "MSV3_Bedarf", null);
    /** Column name MSV3_Bedarf */
    public static final String COLUMNNAME_MSV3_Bedarf = "MSV3_Bedarf";

	/**
	 * Set MSV3_Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Menge (int MSV3_Menge);

	/**
	 * Get MSV3_Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Menge();

    /** Column definition for MSV3_Menge */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_MSV3_Menge = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "MSV3_Menge", null);
    /** Column name MSV3_Menge */
    public static final String COLUMNNAME_MSV3_Menge = "MSV3_Menge";

	/**
	 * Set MSV3_Pzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Pzn (java.lang.String MSV3_Pzn);

	/**
	 * Get MSV3_Pzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Pzn();

    /** Column definition for MSV3_Pzn */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_MSV3_Pzn = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "MSV3_Pzn", null);
    /** Column name MSV3_Pzn */
    public static final String COLUMNNAME_MSV3_Pzn = "MSV3_Pzn";

	/**
	 * Set MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID (int MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID);

	/**
	 * Get MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID();

    /** Column definition for MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID", null);
    /** Column name MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID = "MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID";

	/**
	 * Set MSV3_VerfuegbarkeitsanfrageEinzelne.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitsanfrageEinzelne_ID (int MSV3_VerfuegbarkeitsanfrageEinzelne_ID);

	/**
	 * Get MSV3_VerfuegbarkeitsanfrageEinzelne.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitsanfrageEinzelne_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne getMSV3_VerfuegbarkeitsanfrageEinzelne();

	public void setMSV3_VerfuegbarkeitsanfrageEinzelne(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne MSV3_VerfuegbarkeitsanfrageEinzelne);

    /** Column definition for MSV3_VerfuegbarkeitsanfrageEinzelne_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne> COLUMN_MSV3_VerfuegbarkeitsanfrageEinzelne_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "MSV3_VerfuegbarkeitsanfrageEinzelne_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne.class);
    /** Column name MSV3_VerfuegbarkeitsanfrageEinzelne_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID = "MSV3_VerfuegbarkeitsanfrageEinzelne_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, Object>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
