/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

/** Generated Interface for PickingProfile_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PickingProfile_Filter 
{

	String Table_Name = "PickingProfile_Filter";

//	/** AD_Table_ID=542389 */
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

	ModelColumn<I_PickingProfile_Filter, Object> COLUMN_Created = new ModelColumn<>(I_PickingProfile_Filter.class, "Created", null);
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
	 * Set Filter Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFilterType (java.lang.String FilterType);

	/**
	 * Get Filter Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFilterType();

	ModelColumn<I_PickingProfile_Filter, Object> COLUMN_FilterType = new ModelColumn<>(I_PickingProfile_Filter.class, "FilterType", null);
	String COLUMNNAME_FilterType = "FilterType";

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

	ModelColumn<I_PickingProfile_Filter, Object> COLUMN_IsActive = new ModelColumn<>(I_PickingProfile_Filter.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_PickingProfile_Filter, org.compiere.model.I_MobileUI_UserProfile_Picking> COLUMN_MobileUI_UserProfile_Picking_ID = new ModelColumn<>(I_PickingProfile_Filter.class, "MobileUI_UserProfile_Picking_ID", org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	String COLUMNNAME_MobileUI_UserProfile_Picking_ID = "MobileUI_UserProfile_Picking_ID";

	/**
	 * Set Picking Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickingProfile_Filter_ID (int PickingProfile_Filter_ID);

	/**
	 * Get Picking Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickingProfile_Filter_ID();

	ModelColumn<I_PickingProfile_Filter, Object> COLUMN_PickingProfile_Filter_ID = new ModelColumn<>(I_PickingProfile_Filter.class, "PickingProfile_Filter_ID", null);
	String COLUMNNAME_PickingProfile_Filter_ID = "PickingProfile_Filter_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PickingProfile_Filter, Object> COLUMN_Updated = new ModelColumn<>(I_PickingProfile_Filter.class, "Updated", null);
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
