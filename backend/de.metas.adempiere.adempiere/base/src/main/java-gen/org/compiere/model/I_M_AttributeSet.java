package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_AttributeSet
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_AttributeSet 
{

	String Table_Name = "M_AttributeSet";

//	/** AD_Table_ID=560 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_AttributeSet, Object> COLUMN_Created = new ModelColumn<>(I_M_AttributeSet.class, "Created", null);
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

	ModelColumn<I_M_AttributeSet, Object> COLUMN_Description = new ModelColumn<>(I_M_AttributeSet.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_AttributeSet, Object> COLUMN_IsActive = new ModelColumn<>(I_M_AttributeSet.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInstanceAttribute();

	ModelColumn<I_M_AttributeSet, Object> COLUMN_IsInstanceAttribute = new ModelColumn<>(I_M_AttributeSet.class, "IsInstanceAttribute", null);
	String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set Mandatory Type.
	 * The specification of a Product Attribute Instance is mandatory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMandatoryType (java.lang.String MandatoryType);

	/**
	 * Get Mandatory Type.
	 * The specification of a Product Attribute Instance is mandatory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMandatoryType();

	ModelColumn<I_M_AttributeSet, Object> COLUMN_MandatoryType = new ModelColumn<>(I_M_AttributeSet.class, "MandatoryType", null);
	String COLUMNNAME_MandatoryType = "MandatoryType";

	/**
	 * Set Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	ModelColumn<I_M_AttributeSet, Object> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_M_AttributeSet.class, "M_AttributeSet_ID", null);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

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

	ModelColumn<I_M_AttributeSet, Object> COLUMN_Name = new ModelColumn<>(I_M_AttributeSet.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_AttributeSet, Object> COLUMN_Updated = new ModelColumn<>(I_M_AttributeSet.class, "Updated", null);
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
}
