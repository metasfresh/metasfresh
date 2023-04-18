package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_HazardSymbol
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HazardSymbol 
{

	String Table_Name = "M_HazardSymbol";

//	/** AD_Table_ID=541962 */
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
	 * Set Image.
	 * Image or Icon
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Image.
	 * Image or Icon
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Image_ID();

	@Nullable org.compiere.model.I_AD_Image getAD_Image();

	void setAD_Image(@Nullable org.compiere.model.I_AD_Image AD_Image);

	ModelColumn<I_M_HazardSymbol, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new ModelColumn<>(I_M_HazardSymbol.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

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

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_Created = new ModelColumn<>(I_M_HazardSymbol.class, "Created", null);
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

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HazardSymbol.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Hazard Symbol.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HazardSymbol_ID (int M_HazardSymbol_ID);

	/**
	 * Get Hazard Symbol.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HazardSymbol_ID();

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_M_HazardSymbol_ID = new ModelColumn<>(I_M_HazardSymbol.class, "M_HazardSymbol_ID", null);
	String COLUMNNAME_M_HazardSymbol_ID = "M_HazardSymbol_ID";

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

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_Name = new ModelColumn<>(I_M_HazardSymbol.class, "Name", null);
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

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_Updated = new ModelColumn<>(I_M_HazardSymbol.class, "Updated", null);
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

	ModelColumn<I_M_HazardSymbol, Object> COLUMN_Value = new ModelColumn<>(I_M_HazardSymbol.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
