package de.metas.vertical.pharma.vendor.gateway.mvs3.model;


/** Generated Interface for MSV3_BestellungPosition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_BestellungPosition 
{

    /** TableName=MSV3_BestellungPosition */
    public static final String Table_Name = "MSV3_BestellungPosition";

    /** AD_Table_ID=540918 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_Client>(I_MSV3_BestellungPosition.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_Org>(I_MSV3_BestellungPosition.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_User>(I_MSV3_BestellungPosition.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MSV3_BestellungAuftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAuftrag_ID (int MSV3_BestellungAuftrag_ID);

	/**
	 * Get MSV3_BestellungAuftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAuftrag_ID();

	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag getMSV3_BestellungAuftrag();

	public void setMSV3_BestellungAuftrag(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag MSV3_BestellungAuftrag);

    /** Column definition for MSV3_BestellungAuftrag_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag> COLUMN_MSV3_BestellungAuftrag_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag>(I_MSV3_BestellungPosition.class, "MSV3_BestellungAuftrag_ID", de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag.class);
    /** Column name MSV3_BestellungAuftrag_ID */
    public static final String COLUMNNAME_MSV3_BestellungAuftrag_ID = "MSV3_BestellungAuftrag_ID";

	/**
	 * Set MSV3_BestellungPosition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungPosition_ID (int MSV3_BestellungPosition_ID);

	/**
	 * Get MSV3_BestellungPosition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungPosition_ID();

    /** Column definition for MSV3_BestellungPosition_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_MSV3_BestellungPosition_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "MSV3_BestellungPosition_ID", null);
    /** Column name MSV3_BestellungPosition_ID */
    public static final String COLUMNNAME_MSV3_BestellungPosition_ID = "MSV3_BestellungPosition_ID";

	/**
	 * Set Liefervorgabe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Liefervorgabe (java.lang.String MSV3_Liefervorgabe);

	/**
	 * Get Liefervorgabe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Liefervorgabe();

    /** Column definition for MSV3_Liefervorgabe */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_MSV3_Liefervorgabe = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "MSV3_Liefervorgabe", null);
    /** Column name MSV3_Liefervorgabe */
    public static final String COLUMNNAME_MSV3_Liefervorgabe = "MSV3_Liefervorgabe";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_MSV3_Menge = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "MSV3_Menge", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_MSV3_Pzn = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "MSV3_Pzn", null);
    /** Column name MSV3_Pzn */
    public static final String COLUMNNAME_MSV3_Pzn = "MSV3_Pzn";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, Object>(I_MSV3_BestellungPosition.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungPosition, org.compiere.model.I_AD_User>(I_MSV3_BestellungPosition.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
