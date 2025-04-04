/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.picking.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for PickingProfile_PickingJobLineConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PickingProfile_PickingJobLineConfig 
{

	String Table_Name = "PickingProfile_PickingJobLineConfig";

//	/** AD_Table_ID=542472 */
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

	ModelColumn<I_PickingProfile_PickingJobLineConfig, Object> COLUMN_Created = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "Created", null);
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

	ModelColumn<I_PickingProfile_PickingJobLineConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Show Last Picked Best Before Date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowLastPickedBestBeforeDate (boolean IsShowLastPickedBestBeforeDate);

	/**
	 * Get Show Last Picked Best Before Date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowLastPickedBestBeforeDate();

	ModelColumn<I_PickingProfile_PickingJobLineConfig, Object> COLUMN_IsShowLastPickedBestBeforeDate = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "IsShowLastPickedBestBeforeDate", null);
	String COLUMNNAME_IsShowLastPickedBestBeforeDate = "IsShowLastPickedBestBeforeDate";

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

	ModelColumn<I_PickingProfile_PickingJobLineConfig, org.compiere.model.I_MobileUI_UserProfile_Picking> COLUMN_MobileUI_UserProfile_Picking_ID = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "MobileUI_UserProfile_Picking_ID", org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	String COLUMNNAME_MobileUI_UserProfile_Picking_ID = "MobileUI_UserProfile_Picking_ID";

	/**
	 * Set Line Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickingProfile_PickingJobLineConfig_ID (int PickingProfile_PickingJobLineConfig_ID);

	/**
	 * Get Line Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickingProfile_PickingJobLineConfig_ID();

	ModelColumn<I_PickingProfile_PickingJobLineConfig, Object> COLUMN_PickingProfile_PickingJobLineConfig_ID = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "PickingProfile_PickingJobLineConfig_ID", null);
	String COLUMNNAME_PickingProfile_PickingJobLineConfig_ID = "PickingProfile_PickingJobLineConfig_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PickingProfile_PickingJobLineConfig, Object> COLUMN_Updated = new ModelColumn<>(I_PickingProfile_PickingJobLineConfig.class, "Updated", null);
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
