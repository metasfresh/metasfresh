package org.compiere.model;


/** Generated Interface for M_Product_Category
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product_Category 
{

    /** TableName=M_Product_Category */
    public static final String Table_Name = "M_Product_Category";

    /** AD_Table_ID=209 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getA_Asset_Group_ID();

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	public void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group);

    /** Column definition for A_Asset_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_A_Asset_Group>(I_M_Product_Category.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_Client>(I_M_Product_Category.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_Org>(I_M_Product_Category.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/**
	 * Get Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintColor_ID();

	public org.compiere.model.I_AD_PrintColor getAD_PrintColor();

	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor);

    /** Column definition for AD_PrintColor_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_PrintColor> COLUMN_AD_PrintColor_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_PrintColor>(I_M_Product_Category.class, "AD_PrintColor_ID", org.compiere.model.I_AD_PrintColor.class);
    /** Column name AD_PrintColor_ID */
    public static final String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/**
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CompensationGroup_Schema_ID();

	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	public void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

    /** Column definition for C_CompensationGroup_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, de.metas.order.model.I_C_CompensationGroup_Schema>(I_M_Product_Category.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
    /** Column name C_CompensationGroup_Schema_ID */
    public static final String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_C_DocType>(I_M_Product_Category.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_User>(I_M_Product_Category.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_GuaranteeDaysMin = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "GuaranteeDaysMin", null);
    /** Column name GuaranteeDaysMin */
    public static final String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Verpackungsmaterial.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Verpackungsmaterial.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackagingMaterial();

    /** Column definition for IsPackagingMaterial */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_IsPackagingMaterial = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "IsPackagingMaterial", null);
    /** Column name IsPackagingMaterial */
    public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSet_ID();

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

    /** Column definition for M_AttributeSet_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_M_AttributeSet>(I_M_Product_Category.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "M_Product_Category_ID", null);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Parent Product Category.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_Parent_ID (int M_Product_Category_Parent_ID);

	/**
	 * Get Parent Product Category.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_Parent_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category_Parent();

	public void setM_Product_Category_Parent(org.compiere.model.I_M_Product_Category M_Product_Category_Parent);

    /** Column definition for M_Product_Category_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_Parent_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_M_Product_Category>(I_M_Product_Category.class, "M_Product_Category_Parent_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_Parent_ID */
    public static final String COLUMNNAME_M_Product_Category_Parent_ID = "M_Product_Category_Parent_ID";

	/**
	 * Set Materialfluß.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMMPolicy (java.lang.String MMPolicy);

	/**
	 * Get Materialfluß.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMMPolicy();

    /** Column definition for MMPolicy */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_MMPolicy = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "MMPolicy", null);
    /** Column name MMPolicy */
    public static final String COLUMNNAME_MMPolicy = "MMPolicy";

	/**
	 * Set MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set DB1 %.
	 * Project's planned margin as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPlannedMargin (java.math.BigDecimal PlannedMargin);

	/**
	 * Get DB1 %.
	 * Project's planned margin as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPlannedMargin();

    /** Column definition for PlannedMargin */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_PlannedMargin = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "PlannedMargin", null);
    /** Column name PlannedMargin */
    public static final String COLUMNNAME_PlannedMargin = "PlannedMargin";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Category, org.compiere.model.I_AD_User>(I_M_Product_Category.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_Product_Category, Object>(I_M_Product_Category.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
