package de.metas.dimension.model;


/** Generated Interface for DIM_Dimension_Spec
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DIM_Dimension_Spec 
{

    /** TableName=DIM_Dimension_Spec */
    public static final String Table_Name = "DIM_Dimension_Spec";

    /** AD_Table_ID=540660 */
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_Client>(I_DIM_Dimension_Spec.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_Org>(I_DIM_Dimension_Spec.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_User>(I_DIM_Dimension_Spec.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Dimensionsspezifikation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_ID (int DIM_Dimension_Spec_ID);

	/**
	 * Get Dimensionsspezifikation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_ID();

    /** Column definition for DIM_Dimension_Spec_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_DIM_Dimension_Spec_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "DIM_Dimension_Spec_ID", null);
    /** Column name DIM_Dimension_Spec_ID */
    public static final String COLUMNNAME_DIM_Dimension_Spec_ID = "DIM_Dimension_Spec_ID";

	/**
	 * Set Dimensionstyp.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Type_ID (int DIM_Dimension_Type_ID);

	/**
	 * Get Dimensionstyp.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Type_ID();

	public de.metas.dimension.model.I_DIM_Dimension_Type getDIM_Dimension_Type();

	public void setDIM_Dimension_Type(de.metas.dimension.model.I_DIM_Dimension_Type DIM_Dimension_Type);

    /** Column definition for DIM_Dimension_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, de.metas.dimension.model.I_DIM_Dimension_Type> COLUMN_DIM_Dimension_Type_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, de.metas.dimension.model.I_DIM_Dimension_Type>(I_DIM_Dimension_Spec.class, "DIM_Dimension_Type_ID", de.metas.dimension.model.I_DIM_Dimension_Type.class);
    /** Column name DIM_Dimension_Type_ID */
    public static final String COLUMNNAME_DIM_Dimension_Type_ID = "DIM_Dimension_Type_ID";

	/**
	 * Set DIM_Dimension_Type_InternalName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDIM_Dimension_Type_InternalName (java.lang.String DIM_Dimension_Type_InternalName);

	/**
	 * Get DIM_Dimension_Type_InternalName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getDIM_Dimension_Type_InternalName();

    /** Column definition for DIM_Dimension_Type_InternalName */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_DIM_Dimension_Type_InternalName = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "DIM_Dimension_Type_InternalName", null);
    /** Column name DIM_Dimension_Type_InternalName */
    public static final String COLUMNNAME_DIM_Dimension_Type_InternalName = "DIM_Dimension_Type_InternalName";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set inkl. "leer"-Eintrag.
	 * Legt fest, ob die Dimension einen dezidierten "Leer" Eintrag enthalten soll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIncludeEmpty (boolean IsIncludeEmpty);

	/**
	 * Get inkl. "leer"-Eintrag.
	 * Legt fest, ob die Dimension einen dezidierten "Leer" Eintrag enthalten soll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIncludeEmpty();

    /** Column definition for IsIncludeEmpty */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_IsIncludeEmpty = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "IsIncludeEmpty", null);
    /** Column name IsIncludeEmpty */
    public static final String COLUMNNAME_IsIncludeEmpty = "IsIncludeEmpty";

	/**
	 * Set inkl. "sonstige"-Eintrag.
	 * Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIncludeOtherGroup (boolean IsIncludeOtherGroup);

	/**
	 * Get inkl. "sonstige"-Eintrag.
	 * Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIncludeOtherGroup();

    /** Column definition for IsIncludeOtherGroup */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_IsIncludeOtherGroup = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "IsIncludeOtherGroup", null);
    /** Column name IsIncludeOtherGroup */
    public static final String COLUMNNAME_IsIncludeOtherGroup = "IsIncludeOtherGroup";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, Object>(I_DIM_Dimension_Spec.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec, org.compiere.model.I_AD_User>(I_DIM_Dimension_Spec.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
