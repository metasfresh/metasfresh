package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PI_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PI_Attribute 
{

    /** TableName=M_HU_PI_Attribute */
    public static final String Table_Name = "M_HU_PI_Attribute";

    /** AD_Table_ID=540507 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_Client>(I_M_HU_PI_Attribute.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_Org>(I_M_HU_PI_Attribute.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Aggregation Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAggregationStrategy_JavaClass_ID (int AggregationStrategy_JavaClass_ID);

	/**
	 * Get Aggregation Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAggregationStrategy_JavaClass_ID();

    /** Column definition for AggregationStrategy_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_AggregationStrategy_JavaClass_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "AggregationStrategy_JavaClass_ID", null);
    /** Column name AggregationStrategy_JavaClass_ID */
    public static final String COLUMNNAME_AggregationStrategy_JavaClass_ID = "AggregationStrategy_JavaClass_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

//	public org.compiere.model.I_C_UOM getC_UOM();
//
//	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_C_UOM>(I_M_HU_PI_Attribute.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_User>(I_M_HU_PI_Attribute.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set HU Transfer Attribute Strategy.
	 * Strategy used for transferring an attribute from a source HU.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHU_TansferStrategy_JavaClass_ID (int HU_TansferStrategy_JavaClass_ID);

	/**
	 * Get HU Transfer Attribute Strategy.
	 * Strategy used for transferring an attribute from a source HU.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHU_TansferStrategy_JavaClass_ID();

    /** Column definition for HU_TansferStrategy_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_HU_TansferStrategy_JavaClass_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "HU_TansferStrategy_JavaClass_ID", null);
    /** Column name HU_TansferStrategy_JavaClass_ID */
    public static final String COLUMNNAME_HU_TansferStrategy_JavaClass_ID = "HU_TansferStrategy_JavaClass_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Instanz Merkmal.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instanz Merkmal.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInstanceAttribute();

    /** Column definition for IsInstanceAttribute */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsInstanceAttribute = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsInstanceAttribute", null);
    /** Column name IsInstanceAttribute */
    public static final String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMandatory (boolean IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set OnlyIfInProductAttributeSet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOnlyIfInProductAttributeSet (boolean IsOnlyIfInProductAttributeSet);

	/**
	 * Get OnlyIfInProductAttributeSet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnlyIfInProductAttributeSet();

    /** Column definition for IsOnlyIfInProductAttributeSet */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsOnlyIfInProductAttributeSet = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsOnlyIfInProductAttributeSet", null);
    /** Column name IsOnlyIfInProductAttributeSet */
    public static final String COLUMNNAME_IsOnlyIfInProductAttributeSet = "IsOnlyIfInProductAttributeSet";

	/**
	 * Set Schreibgeschützt.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadOnly();

    /** Column definition for IsReadOnly */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

//	public org.compiere.model.I_M_Attribute getM_Attribute();
//
//	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_M_Attribute>(I_M_HU_PI_Attribute.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID);

	/**
	 * Get Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Attribute_ID();

    /** Column definition for M_HU_PI_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_M_HU_PI_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "M_HU_PI_Attribute_ID", null);
    /** Column name M_HU_PI_Attribute_ID */
    public static final String COLUMNNAME_M_HU_PI_Attribute_ID = "M_HU_PI_Attribute_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Version_ID();

//	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();
//
//	public void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

    /** Column definition for M_HU_PI_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, de.metas.handlingunits.model.I_M_HU_PI_Version>(I_M_HU_PI_Attribute.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
    /** Column name M_HU_PI_Version_ID */
    public static final String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Propagation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPropagationType (java.lang.String PropagationType);

	/**
	 * Get Propagation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPropagationType();

    /** Column definition for PropagationType */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_PropagationType = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "PropagationType", null);
    /** Column name PropagationType */
    public static final String COLUMNNAME_PropagationType = "PropagationType";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Splitter Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSplitterStrategy_JavaClass_ID (int SplitterStrategy_JavaClass_ID);

	/**
	 * Get Splitter Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSplitterStrategy_JavaClass_ID();

    /** Column definition for SplitterStrategy_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_SplitterStrategy_JavaClass_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "SplitterStrategy_JavaClass_ID", null);
    /** Column name SplitterStrategy_JavaClass_ID */
    public static final String COLUMNNAME_SplitterStrategy_JavaClass_ID = "SplitterStrategy_JavaClass_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, org.compiere.model.I_AD_User>(I_M_HU_PI_Attribute.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Use in ASI.
	 * If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseInASI (boolean UseInASI);

	/**
	 * Get Use in ASI.
	 * If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseInASI();

    /** Column definition for UseInASI */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_UseInASI = new org.adempiere.model.ModelColumn<I_M_HU_PI_Attribute, Object>(I_M_HU_PI_Attribute.class, "UseInASI", null);
    /** Column name UseInASI */
    public static final String COLUMNNAME_UseInASI = "UseInASI";
}
