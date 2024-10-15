package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for LeichMehl_PluFile_ConfigGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_LeichMehl_PluFile_ConfigGroup 
{

	String Table_Name = "LeichMehl_PluFile_ConfigGroup";

//	/** AD_Table_ID=542378 */
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

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_Created = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "Created", null);
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

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_Description = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "Description", null);
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

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_IsActive = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PLU File Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLeichMehl_PluFile_ConfigGroup_ID (int LeichMehl_PluFile_ConfigGroup_ID);

	/**
	 * Get PLU File Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLeichMehl_PluFile_ConfigGroup_ID();

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_LeichMehl_PluFile_ConfigGroup_ID = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "LeichMehl_PluFile_ConfigGroup_ID", null);
	String COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID = "LeichMehl_PluFile_ConfigGroup_ID";

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

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_Name = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "Name", null);
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

	ModelColumn<I_LeichMehl_PluFile_ConfigGroup, Object> COLUMN_Updated = new ModelColumn<>(I_LeichMehl_PluFile_ConfigGroup.class, "Updated", null);
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
