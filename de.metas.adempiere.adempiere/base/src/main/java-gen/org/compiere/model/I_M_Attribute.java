package org.compiere.model;


/** Generated Interface for M_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Attribute 
{

    /** TableName=M_Attribute */
    public static final String Table_Name = "M_Attribute";

    /** AD_Table_ID=562 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Client>(I_M_Attribute.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Java Klasse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get Java Klasse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_JavaClass_ID();

	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass();

	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass);

    /** Column definition for AD_JavaClass_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, de.metas.javaclasses.model.I_AD_JavaClass> COLUMN_AD_JavaClass_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, de.metas.javaclasses.model.I_AD_JavaClass>(I_M_Attribute.class, "AD_JavaClass_ID", de.metas.javaclasses.model.I_AD_JavaClass.class);
    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Org>(I_M_Attribute.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Dynamische Validierung.
	 * Regel für die  dynamische Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Dynamische Validierung.
	 * Regel für die  dynamische Validierung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Val_Rule_ID();

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

    /** Column definition for AD_Val_Rule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Val_Rule>(I_M_Attribute.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAttributeValueType (java.lang.String AttributeValueType);

	/**
	 * Get Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeValueType();

    /** Column definition for AttributeValueType */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_AttributeValueType = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "AttributeValueType", null);
    /** Column name AttributeValueType */
    public static final String COLUMNNAME_AttributeValueType = "AttributeValueType";

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

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_C_UOM>(I_M_Attribute.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_User>(I_M_Attribute.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auf Belegen auszuweisen.
	 * Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAttrDocumentRelevant (boolean IsAttrDocumentRelevant);

	/**
	 * Get Auf Belegen auszuweisen.
	 * Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAttrDocumentRelevant();

    /** Column definition for IsAttrDocumentRelevant */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsAttrDocumentRelevant = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsAttrDocumentRelevant", null);
    /** Column name IsAttrDocumentRelevant */
    public static final String COLUMNNAME_IsAttrDocumentRelevant = "IsAttrDocumentRelevant";

	/**
	 * Set Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInstanceAttribute();

    /** Column definition for IsInstanceAttribute */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsInstanceAttribute = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsInstanceAttribute", null);
    /** Column name IsInstanceAttribute */
    public static final String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMandatory (boolean IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set isPricingRelevant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPricingRelevant (boolean IsPricingRelevant);

	/**
	 * Get isPricingRelevant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPricingRelevant();

    /** Column definition for IsPricingRelevant */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsPricingRelevant = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsPricingRelevant", null);
    /** Column name IsPricingRelevant */
    public static final String COLUMNNAME_IsPricingRelevant = "IsPricingRelevant";

	/**
	 * Set IsReadOnlyValues.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadOnlyValues (boolean IsReadOnlyValues);

	/**
	 * Get IsReadOnlyValues.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadOnlyValues();

    /** Column definition for IsReadOnlyValues */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_IsReadOnlyValues = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "IsReadOnlyValues", null);
    /** Column name IsReadOnlyValues */
    public static final String COLUMNNAME_IsReadOnlyValues = "IsReadOnlyValues";

	/**
	 * Set Merkmal.
	 * Product Attribute
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Product Attribute
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "M_Attribute_ID", null);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmal-Suche.
	 * Common Search Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSearch_ID (int M_AttributeSearch_ID);

	/**
	 * Get Merkmal-Suche.
	 * Common Search Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSearch_ID();

	public org.compiere.model.I_M_AttributeSearch getM_AttributeSearch();

	public void setM_AttributeSearch(org.compiere.model.I_M_AttributeSearch M_AttributeSearch);

    /** Column definition for M_AttributeSearch_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_M_AttributeSearch> COLUMN_M_AttributeSearch_ID = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_M_AttributeSearch>(I_M_Attribute.class, "M_AttributeSearch_ID", org.compiere.model.I_M_AttributeSearch.class);
    /** Column name M_AttributeSearch_ID */
    public static final String COLUMNNAME_M_AttributeSearch_ID = "M_AttributeSearch_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Attribute, org.compiere.model.I_AD_User>(I_M_Attribute.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Max. Wert.
	 * Maximum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueMax (java.math.BigDecimal ValueMax);

	/**
	 * Get Max. Wert.
	 * Maximum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getValueMax();

    /** Column definition for ValueMax */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_ValueMax = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "ValueMax", null);
    /** Column name ValueMax */
    public static final String COLUMNNAME_ValueMax = "ValueMax";

	/**
	 * Set Min. Wert.
	 * Minimum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueMin (java.math.BigDecimal ValueMin);

	/**
	 * Get Min. Wert.
	 * Minimum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getValueMin();

    /** Column definition for ValueMin */
    public static final org.adempiere.model.ModelColumn<I_M_Attribute, Object> COLUMN_ValueMin = new org.adempiere.model.ModelColumn<I_M_Attribute, Object>(I_M_Attribute.class, "ValueMin", null);
    /** Column name ValueMin */
    public static final String COLUMNNAME_ValueMin = "ValueMin";
}
