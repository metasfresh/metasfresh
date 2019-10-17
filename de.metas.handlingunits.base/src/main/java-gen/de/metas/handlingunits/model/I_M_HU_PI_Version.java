package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PI_Version
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PI_Version 
{

    /** TableName=M_HU_PI_Version */
    public static final String Table_Name = "M_HU_PI_Version";

    /** AD_Table_ID=540510 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_Client>(I_M_HU_PI_Version.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_Org>(I_M_HU_PI_Version.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_User>(I_M_HU_PI_Version.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Handling Unit Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_UnitType (java.lang.String HU_UnitType);

	/**
	 * Get Handling Unit Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHU_UnitType();

    /** Column definition for HU_UnitType */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_HU_UnitType = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "HU_UnitType", null);
    /** Column name HU_UnitType */
    public static final String COLUMNNAME_HU_UnitType = "HU_UnitType";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aktuell gültige Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCurrent (boolean IsCurrent);

	/**
	 * Get Aktuell gültige Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCurrent();

    /** Column definition for IsCurrent */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_IsCurrent = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "IsCurrent", null);
    /** Column name IsCurrent */
    public static final String COLUMNNAME_IsCurrent = "IsCurrent";

	/**
	 * Set Verpackungscode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID);

	/**
	 * Get Verpackungscode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_ID();

	public de.metas.handlingunits.model.I_M_HU_PackagingCode getM_HU_PackagingCode();

	public void setM_HU_PackagingCode(de.metas.handlingunits.model.I_M_HU_PackagingCode M_HU_PackagingCode);

    /** Column definition for M_HU_PackagingCode_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PackagingCode> COLUMN_M_HU_PackagingCode_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PackagingCode>(I_M_HU_PI_Version.class, "M_HU_PackagingCode_ID", de.metas.handlingunits.model.I_M_HU_PackagingCode.class);
    /** Column name M_HU_PackagingCode_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_ID = "M_HU_PackagingCode_ID";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_ID();

	public de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI();

	public void setM_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_HU_PI);

    /** Column definition for M_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PI>(I_M_HU_PI_Version.class, "M_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
    /** Column name M_HU_PI_ID */
    public static final String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Version_ID();

    /** Column definition for M_HU_PI_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_M_HU_PI_Version_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "M_HU_PI_Version_ID", null);
    /** Column name M_HU_PI_Version_ID */
    public static final String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, Object>(I_M_HU_PI_Version.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PI_Version, org.compiere.model.I_AD_User>(I_M_HU_PI_Version.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
