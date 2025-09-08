package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileConfiguration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileConfiguration 
{

	String Table_Name = "MobileConfiguration";

//	/** AD_Table_ID=542388 */
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

	ModelColumn<I_MobileConfiguration, Object> COLUMN_Created = new ModelColumn<>(I_MobileConfiguration.class, "Created", null);
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
	 * Set Default Authentication Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDefaultAuthenticationMethod (java.lang.String DefaultAuthenticationMethod);

	/**
	 * Get Default Authentication Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDefaultAuthenticationMethod();

	ModelColumn<I_MobileConfiguration, Object> COLUMN_DefaultAuthenticationMethod = new ModelColumn<>(I_MobileConfiguration.class, "DefaultAuthenticationMethod", null);
	String COLUMNNAME_DefaultAuthenticationMethod = "DefaultAuthenticationMethod";

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

	ModelColumn<I_MobileConfiguration, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileConfiguration.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Mobile Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileConfiguration_ID (int MobileConfiguration_ID);

	/**
	 * Get Mobile Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileConfiguration_ID();

	ModelColumn<I_MobileConfiguration, Object> COLUMN_MobileConfiguration_ID = new ModelColumn<>(I_MobileConfiguration.class, "MobileConfiguration_ID", null);
	String COLUMNNAME_MobileConfiguration_ID = "MobileConfiguration_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileConfiguration, Object> COLUMN_Updated = new ModelColumn<>(I_MobileConfiguration.class, "Updated", null);
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
