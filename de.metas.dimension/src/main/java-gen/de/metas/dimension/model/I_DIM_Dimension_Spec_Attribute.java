package de.metas.dimension.model;


/** Generated Interface for DIM_Dimension_Spec_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DIM_Dimension_Spec_Attribute 
{

    /** TableName=DIM_Dimension_Spec_Attribute */
    public static final String Table_Name = "DIM_Dimension_Spec_Attribute";

    /** AD_Table_ID=540662 */
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_Client>(I_DIM_Dimension_Spec_Attribute.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_Org>(I_DIM_Dimension_Spec_Attribute.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeValueType (java.lang.String AttributeValueType);

	/**
	 * Get Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeValueType();

    /** Column definition for AttributeValueType */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_AttributeValueType = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "AttributeValueType", null);
    /** Column name AttributeValueType */
    public static final String COLUMNNAME_AttributeValueType = "AttributeValueType";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_User>(I_DIM_Dimension_Spec_Attribute.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dimensionsspezifikation (Merkmal).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_Attribute_ID (int DIM_Dimension_Spec_Attribute_ID);

	/**
	 * Get Dimensionsspezifikation (Merkmal).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_Attribute_ID();

    /** Column definition for DIM_Dimension_Spec_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_DIM_Dimension_Spec_Attribute_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "DIM_Dimension_Spec_Attribute_ID", null);
    /** Column name DIM_Dimension_Spec_Attribute_ID */
    public static final String COLUMNNAME_DIM_Dimension_Spec_Attribute_ID = "DIM_Dimension_Spec_Attribute_ID";

	/**
	 * Set Dimensionsspezifikation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDIM_Dimension_Spec_ID (int DIM_Dimension_Spec_ID);

	/**
	 * Get Dimensionsspezifikation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDIM_Dimension_Spec_ID();

	public de.metas.dimension.model.I_DIM_Dimension_Spec getDIM_Dimension_Spec();

	public void setDIM_Dimension_Spec(de.metas.dimension.model.I_DIM_Dimension_Spec DIM_Dimension_Spec);

    /** Column definition for DIM_Dimension_Spec_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, de.metas.dimension.model.I_DIM_Dimension_Spec> COLUMN_DIM_Dimension_Spec_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, de.metas.dimension.model.I_DIM_Dimension_Spec>(I_DIM_Dimension_Spec_Attribute.class, "DIM_Dimension_Spec_ID", de.metas.dimension.model.I_DIM_Dimension_Spec.class);
    /** Column name DIM_Dimension_Spec_ID */
    public static final String COLUMNNAME_DIM_Dimension_Spec_ID = "DIM_Dimension_Spec_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Alle Attributwerte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIncludeAllAttributeValues (boolean IsIncludeAllAttributeValues);

	/**
	 * Get Alle Attributwerte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIncludeAllAttributeValues();

    /** Column definition for IsIncludeAllAttributeValues */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_IsIncludeAllAttributeValues = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "IsIncludeAllAttributeValues", null);
    /** Column name IsIncludeAllAttributeValues */
    public static final String COLUMNNAME_IsIncludeAllAttributeValues = "IsIncludeAllAttributeValues";

	/**
	 * Set Merkmalswert-Zusammenfassung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValueAggregate (boolean IsValueAggregate);

	/**
	 * Get Merkmalswert-Zusammenfassung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValueAggregate();

    /** Column definition for IsValueAggregate */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_IsValueAggregate = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "IsValueAggregate", null);
    /** Column name IsValueAggregate */
    public static final String COLUMNNAME_IsValueAggregate = "IsValueAggregate";

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

	public org.compiere.model.I_M_Attribute getM_Attribute();

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_M_Attribute>(I_DIM_Dimension_Spec_Attribute.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_AD_User>(I_DIM_Dimension_Spec_Attribute.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gruppenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueAggregateName (java.lang.String ValueAggregateName);

	/**
	 * Get Gruppenname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValueAggregateName();

    /** Column definition for ValueAggregateName */
    public static final org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object> COLUMN_ValueAggregateName = new org.adempiere.model.ModelColumn<I_DIM_Dimension_Spec_Attribute, Object>(I_DIM_Dimension_Spec_Attribute.class, "ValueAggregateName", null);
    /** Column name ValueAggregateName */
    public static final String COLUMNNAME_ValueAggregateName = "ValueAggregateName";
}
