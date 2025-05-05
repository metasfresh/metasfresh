package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_MailConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_MailConfig 
{

	String Table_Name = "AD_MailConfig";

//	/** AD_Table_ID=540241 */
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
	 * Set Mail Box.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_MailBox_ID (int AD_MailBox_ID);

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_MailBox_ID();

	org.compiere.model.I_AD_MailBox getAD_MailBox();

	void setAD_MailBox(org.compiere.model.I_AD_MailBox AD_MailBox);

	ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_MailBox> COLUMN_AD_MailBox_ID = new ModelColumn<>(I_AD_MailConfig.class, "AD_MailBox_ID", org.compiere.model.I_AD_MailBox.class);
	String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

	/**
	 * Set Mail Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_MailConfig_ID (int AD_MailConfig_ID);

	/**
	 * Get Mail Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_MailConfig_ID();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_AD_MailConfig_ID = new ModelColumn<>(I_AD_MailConfig.class, "AD_MailConfig_ID", null);
	String COLUMNNAME_AD_MailConfig_ID = "AD_MailConfig_ID";

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
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_MailConfig, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_MailConfig.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Column User To.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setColumnUserTo (@Nullable java.lang.String ColumnUserTo);

	/**
	 * Get Column User To.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getColumnUserTo();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_ColumnUserTo = new ModelColumn<>(I_AD_MailConfig.class, "ColumnUserTo", null);
	String COLUMNNAME_ColumnUserTo = "ColumnUserTo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_Created = new ModelColumn<>(I_AD_MailConfig.class, "Created", null);
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
	 * Set Custom Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomType (@Nullable java.lang.String CustomType);

	/**
	 * Get Custom Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomType();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_CustomType = new ModelColumn<>(I_AD_MailConfig.class, "CustomType", null);
	String COLUMNNAME_CustomType = "CustomType";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable java.lang.String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocBaseType();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_DocBaseType = new ModelColumn<>(I_AD_MailConfig.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocSubType (@Nullable java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocSubType();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_DocSubType = new ModelColumn<>(I_AD_MailConfig.class, "DocSubType", null);
	String COLUMNNAME_DocSubType = "DocSubType";

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

	ModelColumn<I_AD_MailConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_MailConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_MailConfig, Object> COLUMN_Updated = new ModelColumn<>(I_AD_MailConfig.class, "Updated", null);
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
