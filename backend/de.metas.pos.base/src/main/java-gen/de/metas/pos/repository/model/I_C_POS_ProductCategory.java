package de.metas.pos.repository.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_ProductCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_ProductCategory 
{

	String Table_Name = "C_POS_ProductCategory";

//	/** AD_Table_ID=542447 */
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

	ModelColumn<I_C_POS_ProductCategory, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new ModelColumn<>(I_C_POS_ProductCategory.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
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
	 * Set POS Product Category.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_ProductCategory_ID (int C_POS_ProductCategory_ID);

	/**
	 * Get POS Product Category.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_ProductCategory_ID();

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_C_POS_ProductCategory_ID = new ModelColumn<>(I_C_POS_ProductCategory.class, "C_POS_ProductCategory_ID", null);
	String COLUMNNAME_C_POS_ProductCategory_ID = "C_POS_ProductCategory_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_ProductCategory.class, "Created", null);
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
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_Description = new ModelColumn<>(I_C_POS_ProductCategory.class, "Description", null);
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

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_ProductCategory.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_Name = new ModelColumn<>(I_C_POS_ProductCategory.class, "Name", null);
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

	ModelColumn<I_C_POS_ProductCategory, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_ProductCategory.class, "Updated", null);
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
