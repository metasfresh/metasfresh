package de.metas.handlingunits.model;


/** Generated Interface for M_HU_BestBefore_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_BestBefore_V 
{

    /** TableName=M_HU_BestBefore_V */
    public static final String Table_Name = "M_HU_BestBefore_V";

    /** AD_Table_ID=540942 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_Client>(I_M_HU_BestBefore_V.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_Org>(I_M_HU_BestBefore_V.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_User>(I_M_HU_BestBefore_V.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Min. Garantie-Tage.
	 * Mindestanzahl Garantie-Tage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGuaranteeDaysMin (int GuaranteeDaysMin);

	/**
	 * Get Min. Garantie-Tage.
	 * Mindestanzahl Garantie-Tage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGuaranteeDaysMin();

    /** Column definition for GuaranteeDaysMin */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_GuaranteeDaysMin = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "GuaranteeDaysMin", null);
    /** Column name GuaranteeDaysMin */
    public static final String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

	/**
	 * Set Best Before Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_BestBeforeDate (java.sql.Timestamp HU_BestBeforeDate);

	/**
	 * Get Best Before Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getHU_BestBeforeDate();

    /** Column definition for HU_BestBeforeDate */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_BestBeforeDate = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "HU_BestBeforeDate", null);
    /** Column name HU_BestBeforeDate */
    public static final String COLUMNNAME_HU_BestBeforeDate = "HU_BestBeforeDate";

	/**
	 * Set Expired.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_Expired (java.lang.String HU_Expired);

	/**
	 * Get Expired.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHU_Expired();

    /** Column definition for HU_Expired */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_Expired = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "HU_Expired", null);
    /** Column name HU_Expired */
    public static final String COLUMNNAME_HU_Expired = "HU_Expired";

	/**
	 * Set Expiring Warning Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_ExpiredWarnDate (java.sql.Timestamp HU_ExpiredWarnDate);

	/**
	 * Get Expiring Warning Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getHU_ExpiredWarnDate();

    /** Column definition for HU_ExpiredWarnDate */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_ExpiredWarnDate = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "HU_ExpiredWarnDate", null);
    /** Column name HU_ExpiredWarnDate */
    public static final String COLUMNNAME_HU_ExpiredWarnDate = "HU_ExpiredWarnDate";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, de.metas.handlingunits.model.I_M_HU>(I_M_HU_BestBefore_V.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, Object>(I_M_HU_BestBefore_V.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_BestBefore_V, org.compiere.model.I_AD_User>(I_M_HU_BestBefore_V.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
