package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Record_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Record_Attribute 
{

	String Table_Name = "Record_Attribute";

//	/** AD_Table_ID=542454 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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

	ModelColumn<I_Record_Attribute, Object> COLUMN_AttributeValueType = new ModelColumn<>(I_Record_Attribute.class, "AttributeValueType", null);
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

	ModelColumn<I_Record_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_Record_Attribute.class, "Created", null);
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

	ModelColumn<I_Record_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_Record_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Included Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_IncludedTab_ID (int M_AttributeSet_IncludedTab_ID);

	/**
	 * Get Included Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_IncludedTab_ID();

	org.compiere.model.I_M_AttributeSet_IncludedTab getM_AttributeSet_IncludedTab();

	void setM_AttributeSet_IncludedTab(org.compiere.model.I_M_AttributeSet_IncludedTab M_AttributeSet_IncludedTab);

	ModelColumn<I_Record_Attribute, org.compiere.model.I_M_AttributeSet_IncludedTab> COLUMN_M_AttributeSet_IncludedTab_ID = new ModelColumn<>(I_Record_Attribute.class, "M_AttributeSet_IncludedTab_ID", org.compiere.model.I_M_AttributeSet_IncludedTab.class);
	String COLUMNNAME_M_AttributeSet_IncludedTab_ID = "M_AttributeSet_IncludedTab_ID";

	/**
	 * Set Product Attribute Value.
	 * Product Attribute Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Product Attribute Value.
	 * Product Attribute Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeValue_ID();

	String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/**
	 * Set Record Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_Attribute_ID (int Record_Attribute_ID);

	/**
	 * Get Record Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_Attribute_ID();

	ModelColumn<I_Record_Attribute, Object> COLUMN_Record_Attribute_ID = new ModelColumn<>(I_Record_Attribute.class, "Record_Attribute_ID", null);
	String COLUMNNAME_Record_Attribute_ID = "Record_Attribute_ID";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_Record_Attribute, Object> COLUMN_Record_ID = new ModelColumn<>(I_Record_Attribute.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Record_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_Record_Attribute.class, "Updated", null);
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
	 * Set Date Value.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueDate (@Nullable java.sql.Timestamp ValueDate);

	/**
	 * Get Date Value.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValueDate();

	ModelColumn<I_Record_Attribute, Object> COLUMN_ValueDate = new ModelColumn<>(I_Record_Attribute.class, "ValueDate", null);
	String COLUMNNAME_ValueDate = "ValueDate";

	/**
	 * Set Numeric Value.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueNumber (@Nullable BigDecimal ValueNumber);

	/**
	 * Get Numeric Value.
	 * Numeric Value
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getValueNumber();

	ModelColumn<I_Record_Attribute, Object> COLUMN_ValueNumber = new ModelColumn<>(I_Record_Attribute.class, "ValueNumber", null);
	String COLUMNNAME_ValueNumber = "ValueNumber";

	/**
	 * Set String Value.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValueString (@Nullable java.lang.String ValueString);

	/**
	 * Get String Value.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValueString();

	ModelColumn<I_Record_Attribute, Object> COLUMN_ValueString = new ModelColumn<>(I_Record_Attribute.class, "ValueString", null);
	String COLUMNNAME_ValueString = "ValueString";
}
