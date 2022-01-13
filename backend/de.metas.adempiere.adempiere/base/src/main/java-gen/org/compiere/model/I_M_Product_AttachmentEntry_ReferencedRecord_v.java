package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_Product_AttachmentEntry_ReferencedRecord_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_AttachmentEntry_ReferencedRecord_v 
{

	String Table_Name = "M_Product_AttachmentEntry_ReferencedRecord_v";

//	/** AD_Table_ID=541733 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_AttachmentEntry_ID();

	@Nullable org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry();

	void setAD_AttachmentEntry(@Nullable org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry);

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, org.compiere.model.I_AD_AttachmentEntry> COLUMN_AD_AttachmentEntry_ID = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "AD_AttachmentEntry_ID", org.compiere.model.I_AD_AttachmentEntry.class);
	String COLUMNNAME_AD_AttachmentEntry_ID = "AD_AttachmentEntry_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Binary Data.
	 * Binary Data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBinaryData (@Nullable java.lang.String BinaryData);

	/**
	 * Get Binary Data.
	 * Binary Data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBinaryData();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_BinaryData = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "BinaryData", null);
	String COLUMNNAME_BinaryData = "BinaryData";

	/**
	 * Set Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContentType (@Nullable java.lang.String ContentType);

	/**
	 * Get Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContentType();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_ContentType = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "ContentType", null);
	String COLUMNNAME_ContentType = "ContentType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_Description = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName (@Nullable java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFileName();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_FileName = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

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

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Attachments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_AttachmentEntry_ReferencedRecord_v_ID (int M_Product_AttachmentEntry_ReferencedRecord_v_ID);

	/**
	 * Get Product Attachments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_AttachmentEntry_ReferencedRecord_v_ID();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_M_Product_AttachmentEntry_ReferencedRecord_v_ID = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "M_Product_AttachmentEntry_ReferencedRecord_v_ID", null);
	String COLUMNNAME_M_Product_AttachmentEntry_ReferencedRecord_v_ID = "M_Product_AttachmentEntry_ReferencedRecord_v_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setType (boolean Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isType();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_Type = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_M_Product_AttachmentEntry_ReferencedRecord_v, Object> COLUMN_URL = new ModelColumn<>(I_M_Product_AttachmentEntry_ReferencedRecord_v.class, "URL", null);
	String COLUMNNAME_URL = "URL";
}
