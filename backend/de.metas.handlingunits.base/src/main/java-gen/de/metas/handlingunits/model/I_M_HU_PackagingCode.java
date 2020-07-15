package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PackagingCode
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PackagingCode 
{

    /** TableName=M_HU_PackagingCode */
    public static final String Table_Name = "M_HU_PackagingCode";

    /** AD_Table_ID=541423 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_HU_UnitType = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "HU_UnitType", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verpackungscode.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID);

	/**
	 * Get Verpackungscode.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_ID();

    /** Column definition for M_HU_PackagingCode_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_M_HU_PackagingCode_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "M_HU_PackagingCode_ID", null);
    /** Column name M_HU_PackagingCode_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_ID = "M_HU_PackagingCode_ID";

	/**
	 * Set Verpackungscode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPackagingCode (java.lang.String PackagingCode);

	/**
	 * Get Verpackungscode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackagingCode();

    /** Column definition for PackagingCode */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_PackagingCode = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "PackagingCode", null);
    /** Column name PackagingCode */
    public static final String COLUMNNAME_PackagingCode = "PackagingCode";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PackagingCode, Object>(I_M_HU_PackagingCode.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
