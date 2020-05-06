package org.compiere.model;


/** Generated Interface for M_Product_Certificate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product_Certificate 
{

    /** TableName=M_Product_Certificate */
    public static final String Table_Name = "M_Product_Certificate";

    /** AD_Table_ID=541164 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_Client>(I_M_Product_Certificate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_Org>(I_M_Product_Certificate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object>(I_M_Product_Certificate.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_User>(I_M_Product_Certificate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object>(I_M_Product_Certificate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Label-Zerti-Stelle.
	 * Zertifizierungsstelle
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLabelCertificationAuthority (java.lang.String LabelCertificationAuthority);

	/**
	 * Get Label-Zerti-Stelle.
	 * Zertifizierungsstelle
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLabelCertificationAuthority();

    /** Column definition for LabelCertificationAuthority */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object> COLUMN_LabelCertificationAuthority = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object>(I_M_Product_Certificate.class, "LabelCertificationAuthority", null);
    /** Column name LabelCertificationAuthority */
    public static final String COLUMNNAME_LabelCertificationAuthority = "LabelCertificationAuthority";

	/**
	 * Set Label List.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Label_ID (int M_Label_ID);

	/**
	 * Get Label List.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Label_ID();

	public org.compiere.model.I_M_Label getM_Label();

	public void setM_Label(org.compiere.model.I_M_Label M_Label);

    /** Column definition for M_Label_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_M_Label> COLUMN_M_Label_ID = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_M_Label>(I_M_Product_Certificate.class, "M_Label_ID", org.compiere.model.I_M_Label.class);
    /** Column name M_Label_ID */
    public static final String COLUMNNAME_M_Label_ID = "M_Label_ID";

	/**
	 * Set Label_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Certificate_ID (int M_Product_Certificate_ID);

	/**
	 * Get Label_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Certificate_ID();

    /** Column definition for M_Product_Certificate_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object> COLUMN_M_Product_Certificate_ID = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object>(I_M_Product_Certificate.class, "M_Product_Certificate_ID", null);
    /** Column name M_Product_Certificate_ID */
    public static final String COLUMNNAME_M_Product_Certificate_ID = "M_Product_Certificate_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_M_Product>(I_M_Product_Certificate.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, Object>(I_M_Product_Certificate.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Certificate, org.compiere.model.I_AD_User>(I_M_Product_Certificate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
