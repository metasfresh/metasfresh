package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Attribute 
{

	String Table_Name = "M_Attribute";

//	/** AD_Table_ID=562 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Java Class.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get Java Class.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_JavaClass_ID();

	ModelColumn<I_M_Attribute, Object> COLUMN_AD_JavaClass_ID = new ModelColumn<>(I_M_Attribute.class, "AD_JavaClass_ID", null);
	String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	void setAD_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

	ModelColumn<I_M_Attribute, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_M_Attribute.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAttributeValueType (java.lang.String AttributeValueType);

	/**
	 * Get Attribute Value Type.
	 * Type of Attribute Value
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAttributeValueType();

	ModelColumn<I_M_Attribute, Object> COLUMN_AttributeValueType = new ModelColumn<>(I_M_Attribute.class, "AttributeValueType", null);
	String COLUMNNAME_AttributeValueType = "AttributeValueType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_M_Attribute.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Attribute, Object> COLUMN_Description = new ModelColumn<>(I_M_Attribute.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Description Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionPattern (@Nullable java.lang.String DescriptionPattern);

	/**
	 * Get Description Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionPattern();

	ModelColumn<I_M_Attribute, Object> COLUMN_DescriptionPattern = new ModelColumn<>(I_M_Attribute.class, "DescriptionPattern", null);
	String COLUMNNAME_DescriptionPattern = "DescriptionPattern";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Always Updateable.
	 * The column's field is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAlwaysUpdateable (boolean IsAlwaysUpdateable);

	/**
	 * Get Always Updateable.
	 * The column's field is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAlwaysUpdateable();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsAlwaysUpdateable = new ModelColumn<>(I_M_Attribute.class, "IsAlwaysUpdateable", null);
	String COLUMNNAME_IsAlwaysUpdateable = "IsAlwaysUpdateable";

	/**
	 * Set Auf Belegen auszuweisen.
	 * Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAttrDocumentRelevant (boolean IsAttrDocumentRelevant);

	/**
	 * Get Auf Belegen auszuweisen.
	 * Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAttrDocumentRelevant();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsAttrDocumentRelevant = new ModelColumn<>(I_M_Attribute.class, "IsAttrDocumentRelevant", null);
	String COLUMNNAME_IsAttrDocumentRelevant = "IsAttrDocumentRelevant";

	/**
	 * Set High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHighVolume (boolean IsHighVolume);

	/**
	 * Get High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHighVolume();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsHighVolume = new ModelColumn<>(I_M_Attribute.class, "IsHighVolume", null);
	String COLUMNNAME_IsHighVolume = "IsHighVolume";

	/**
	 * Set Instance Attribute.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instance Attribute.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInstanceAttribute();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsInstanceAttribute = new ModelColumn<>(I_M_Attribute.class, "IsInstanceAttribute", null);
	String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (boolean IsMandatory);

	/**
	 * Get mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMandatory();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsMandatory = new ModelColumn<>(I_M_Attribute.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set isPricingRelevant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPricingRelevant (boolean IsPricingRelevant);

	/**
	 * Get isPricingRelevant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPricingRelevant();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsPricingRelevant = new ModelColumn<>(I_M_Attribute.class, "IsPricingRelevant", null);
	String COLUMNNAME_IsPricingRelevant = "IsPricingRelevant";

	/**
	 * Set Printed in document.
	 * If ticked, the attribute will be printed on all referenced documents.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrintedInDocument (boolean IsPrintedInDocument);

	/**
	 * Get Printed in document.
	 * If ticked, the attribute will be printed on all referenced documents.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrintedInDocument();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsPrintedInDocument = new ModelColumn<>(I_M_Attribute.class, "IsPrintedInDocument", null);
	String COLUMNNAME_IsPrintedInDocument = "IsPrintedInDocument";

	/**
	 * Set Read Only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnlyValues (boolean IsReadOnlyValues);

	/**
	 * Get Read Only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnlyValues();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsReadOnlyValues = new ModelColumn<>(I_M_Attribute.class, "IsReadOnlyValues", null);
	String COLUMNNAME_IsReadOnlyValues = "IsReadOnlyValues";

	/**
	 * Set Ist HU-Bestandsrelevant.
	 * Is used to do attibute matching between HU storage attributes and order line attributes (ASIs).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStorageRelevant (boolean IsStorageRelevant);

	/**
	 * Get Ist HU-Bestandsrelevant.
	 * Is used to do attibute matching between HU storage attributes and order line attributes (ASIs).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStorageRelevant();

	ModelColumn<I_M_Attribute, Object> COLUMN_IsStorageRelevant = new ModelColumn<>(I_M_Attribute.class, "IsStorageRelevant", null);
	String COLUMNNAME_IsStorageRelevant = "IsStorageRelevant";

	/**
	 * Set Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	ModelColumn<I_M_Attribute, Object> COLUMN_M_Attribute_ID = new ModelColumn<>(I_M_Attribute.class, "M_Attribute_ID", null);
	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Attribute Search.
	 * Common Search Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSearch_ID (int M_AttributeSearch_ID);

	/**
	 * Get Attribute Search.
	 * Common Search Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSearch_ID();

	@Nullable org.compiere.model.I_M_AttributeSearch getM_AttributeSearch();

	void setM_AttributeSearch(@Nullable org.compiere.model.I_M_AttributeSearch M_AttributeSearch);

	ModelColumn<I_M_Attribute, org.compiere.model.I_M_AttributeSearch> COLUMN_M_AttributeSearch_ID = new ModelColumn<>(I_M_Attribute.class, "M_AttributeSearch_ID", org.compiere.model.I_M_AttributeSearch.class);
	String COLUMNNAME_M_AttributeSearch_ID = "M_AttributeSearch_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_M_Attribute, Object> COLUMN_Name = new ModelColumn<>(I_M_Attribute.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Print Value Override.
	 * The value that we print on documents if is filled out
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintValue_Override (@Nullable java.lang.String PrintValue_Override);

	/**
	 * Get Print Value Override.
	 * The value that we print on documents if is filled out
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrintValue_Override();

	ModelColumn<I_M_Attribute, Object> COLUMN_PrintValue_Override = new ModelColumn<>(I_M_Attribute.class, "PrintValue_Override", null);
	String COLUMNNAME_PrintValue_Override = "PrintValue_Override";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_M_Attribute.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_M_Attribute, Object> COLUMN_Value = new ModelColumn<>(I_M_Attribute.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Max. Value.
	 * Maximum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueMax (@Nullable BigDecimal ValueMax);

	/**
	 * Get Max. Value.
	 * Maximum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getValueMax();

	ModelColumn<I_M_Attribute, Object> COLUMN_ValueMax = new ModelColumn<>(I_M_Attribute.class, "ValueMax", null);
	String COLUMNNAME_ValueMax = "ValueMax";

	/**
	 * Set Min. Value.
	 * Minimum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueMin (@Nullable BigDecimal ValueMin);

	/**
	 * Get Min. Value.
	 * Minimum Value for a field
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getValueMin();

	ModelColumn<I_M_Attribute, Object> COLUMN_ValueMin = new ModelColumn<>(I_M_Attribute.class, "ValueMin", null);
	String COLUMNNAME_ValueMin = "ValueMin";
}
