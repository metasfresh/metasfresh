package de.metas.dimension.model;


/** Generated Interface for DIM_Dimension_Spec_AttributeValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DIM_Dimension_Spec_AttributeValue 
{

    /** TableName=DIM_Dimension_Spec_AttributeValue */
    public static final String Table_Name = "DIM_Dimension_Spec_AttributeValue";

    /** AD_Table_ID=540663 */
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object>(I_DIM_Dimension_Spec_AttributeValue.class, "Created", null);
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
	 * Set Dimensionsspezifikation (Merkmal).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_Attribute_ID (int DIM_Dimension_Spec_Attribute_ID);

	/**
	 * Get Dimensionsspezifikation (Merkmal).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_Attribute_ID();

	public de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute getDIM_Dimension_Spec_Attribute();

	public void setDIM_Dimension_Spec_Attribute(de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute DIM_Dimension_Spec_Attribute);

    /** Column definition for DIM_Dimension_Spec_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute> COLUMN_DIM_Dimension_Spec_Attribute_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute>(I_DIM_Dimension_Spec_AttributeValue.class, "DIM_Dimension_Spec_Attribute_ID", de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute.class);
    /** Column name DIM_Dimension_Spec_Attribute_ID */
    public static final String COLUMNNAME_DIM_Dimension_Spec_Attribute_ID = "DIM_Dimension_Spec_Attribute_ID";

	/**
	 * Set Dimensionsattributwert.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_AttributeValue_ID (int DIM_Dimension_Spec_AttributeValue_ID);

	/**
	 * Get Dimensionsattributwert.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_AttributeValue_ID();

    /** Column definition for DIM_Dimension_Spec_AttributeValue_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object> COLUMN_DIM_Dimension_Spec_AttributeValue_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object>(I_DIM_Dimension_Spec_AttributeValue.class, "DIM_Dimension_Spec_AttributeValue_ID", null);
    /** Column name DIM_Dimension_Spec_AttributeValue_ID */
    public static final String COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID = "DIM_Dimension_Spec_AttributeValue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object>(I_DIM_Dimension_Spec_AttributeValue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_AttributeValue, Object>(I_DIM_Dimension_Spec_AttributeValue.class, "Updated", null);
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
