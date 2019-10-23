package org.compiere.model;


/** Generated Interface for C_CountryArea_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_CountryArea_Assign 
{

    /** TableName=C_CountryArea_Assign */
    public static final String Table_Name = "C_CountryArea_Assign";

    /** AD_Table_ID=540384 */
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_Client>(I_C_CountryArea_Assign.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_Org>(I_C_CountryArea_Assign.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_C_Country>(I_C_CountryArea_Assign.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Country area assign.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CountryArea_Assign_ID (int C_CountryArea_Assign_ID);

	/**
	 * Get Country area assign.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CountryArea_Assign_ID();

    /** Column definition for C_CountryArea_Assign_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_C_CountryArea_Assign_ID = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "C_CountryArea_Assign_ID", null);
    /** Column name C_CountryArea_Assign_ID */
    public static final String COLUMNNAME_C_CountryArea_Assign_ID = "C_CountryArea_Assign_ID";

	/**
	 * Set Country Area.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CountryArea_ID (int C_CountryArea_ID);

	/**
	 * Get Country Area.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CountryArea_ID();

	public org.compiere.model.I_C_CountryArea getC_CountryArea();

	public void setC_CountryArea(org.compiere.model.I_C_CountryArea C_CountryArea);

    /** Column definition for C_CountryArea_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_C_CountryArea> COLUMN_C_CountryArea_ID = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_C_CountryArea>(I_C_CountryArea_Assign.class, "C_CountryArea_ID", org.compiere.model.I_C_CountryArea.class);
    /** Column name C_CountryArea_ID */
    public static final String COLUMNNAME_C_CountryArea_ID = "C_CountryArea_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_User>(I_C_CountryArea_Assign.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, org.compiere.model.I_AD_User>(I_C_CountryArea_Assign.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set GÃ¼ltig ab.
	 * GÃ¼ltig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get GÃ¼ltig ab.
	 * GÃ¼ltig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set GÃ¼ltig bis.
	 * GÃ¼ltig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get GÃ¼ltig bis.
	 * GÃ¼ltig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_C_CountryArea_Assign, Object>(I_C_CountryArea_Assign.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
