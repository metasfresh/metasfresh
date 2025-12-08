package de.metas.picking.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PickingProfile_PickingJobConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PickingProfile_PickingJobConfig 
{

	String Table_Name = "PickingProfile_PickingJobConfig";

//	/** AD_Table_ID=542390 */
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

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_Created = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "Created", null);
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
	 * Set Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFormatPattern (@Nullable java.lang.String FormatPattern);

	/**
	 * Get Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFormatPattern();

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_FormatPattern = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "FormatPattern", null);
	String COLUMNNAME_FormatPattern = "FormatPattern";

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

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Display in detailed view.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayInDetailed (boolean IsDisplayInDetailed);

	/**
	 * Get Display in detailed view.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayInDetailed();

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_IsDisplayInDetailed = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "IsDisplayInDetailed", null);
	String COLUMNNAME_IsDisplayInDetailed = "IsDisplayInDetailed";

	/**
	 * Set Display in summary.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayInSummary (boolean IsDisplayInSummary);

	/**
	 * Get Display in summary.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayInSummary();

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_IsDisplayInSummary = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "IsDisplayInSummary", null);
	String COLUMNNAME_IsDisplayInSummary = "IsDisplayInSummary";

	/**
	 * Set Mobile UI Picking Profile.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_Picking_ID (int MobileUI_UserProfile_Picking_ID);

	/**
	 * Get Mobile UI Picking Profile.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_Picking_ID();

	org.compiere.model.I_MobileUI_UserProfile_Picking getMobileUI_UserProfile_Picking();

	void setMobileUI_UserProfile_Picking(org.compiere.model.I_MobileUI_UserProfile_Picking MobileUI_UserProfile_Picking);

	ModelColumn<I_PickingProfile_PickingJobConfig, org.compiere.model.I_MobileUI_UserProfile_Picking> COLUMN_MobileUI_UserProfile_Picking_ID = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "MobileUI_UserProfile_Picking_ID", org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	String COLUMNNAME_MobileUI_UserProfile_Picking_ID = "MobileUI_UserProfile_Picking_ID";

	/**
	 * Set Field.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickingJobField (java.lang.String PickingJobField);

	/**
	 * Get Field.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPickingJobField();

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_PickingJobField = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "PickingJobField", null);
	String COLUMNNAME_PickingJobField = "PickingJobField";

	/**
	 * Set Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickingProfile_PickingJobConfig_ID (int PickingProfile_PickingJobConfig_ID);

	/**
	 * Get Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickingProfile_PickingJobConfig_ID();

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_PickingProfile_PickingJobConfig_ID = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "PickingProfile_PickingJobConfig_ID", null);
	String COLUMNNAME_PickingProfile_PickingJobConfig_ID = "PickingProfile_PickingJobConfig_ID";

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

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_SeqNo = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "SeqNo", null);
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

	ModelColumn<I_PickingProfile_PickingJobConfig, Object> COLUMN_Updated = new ModelColumn<>(I_PickingProfile_PickingJobConfig.class, "Updated", null);
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
