package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_UserProfile_DD_CaptionItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_UserProfile_DD_CaptionItem 
{

	String Table_Name = "MobileUI_UserProfile_DD_CaptionItem";

//	/** AD_Table_ID=542557 */
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

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "Created", null);
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
	 * Set Field name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFieldName (java.lang.String FieldName);

	/**
	 * Get Field name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFieldName();

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_FieldName = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "FieldName", null);
	String COLUMNNAME_FieldName = "FieldName";

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

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Caption.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_DD_CaptionItem_ID (int MobileUI_UserProfile_DD_CaptionItem_ID);

	/**
	 * Get Caption.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_DD_CaptionItem_ID();

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_MobileUI_UserProfile_DD_CaptionItem_ID = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "MobileUI_UserProfile_DD_CaptionItem_ID", null);
	String COLUMNNAME_MobileUI_UserProfile_DD_CaptionItem_ID = "MobileUI_UserProfile_DD_CaptionItem_ID";

	/**
	 * Set Mobile Distribution Profile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_DD_ID (int MobileUI_UserProfile_DD_ID);

	/**
	 * Get Mobile Distribution Profile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_DD_ID();

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, org.compiere.model.I_MobileUI_UserProfile_DD> COLUMN_MobileUI_UserProfile_DD_ID = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "MobileUI_UserProfile_DD_ID", org.compiere.model.I_MobileUI_UserProfile_DD.class);
	String COLUMNNAME_MobileUI_UserProfile_DD_ID = "MobileUI_UserProfile_DD_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_SeqNo = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileUI_UserProfile_DD_CaptionItem, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_UserProfile_DD_CaptionItem.class, "Updated", null);
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
