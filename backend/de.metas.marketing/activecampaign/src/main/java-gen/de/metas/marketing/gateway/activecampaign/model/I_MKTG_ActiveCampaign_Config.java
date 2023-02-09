/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.gateway.activecampaign.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MKTG_ActiveCampaign_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MKTG_ActiveCampaign_Config 
{

	String Table_Name = "MKTG_ActiveCampaign_Config";

//	/** AD_Table_ID=542274 */
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
	 * Set API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApiKey (java.lang.String ApiKey);

	/**
	 * Get API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApiKey();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_ApiKey = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "ApiKey", null);
	String COLUMNNAME_ApiKey = "ApiKey";

	/**
	 * Set Base-URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseURL (java.lang.String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBaseURL();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_BaseURL = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_Created = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "Created", null);
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

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MKTG_ActiveCampaign_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMKTG_ActiveCampaign_Config_ID (int MKTG_ActiveCampaign_Config_ID);

	/**
	 * Get MKTG_ActiveCampaign_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMKTG_ActiveCampaign_Config_ID();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_MKTG_ActiveCampaign_Config_ID = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "MKTG_ActiveCampaign_Config_ID", null);
	String COLUMNNAME_MKTG_ActiveCampaign_Config_ID = "MKTG_ActiveCampaign_Config_ID";

	/**
	 * Set Marketing Platform.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMKTG_Platform_ID (int MKTG_Platform_ID);

	/**
	 * Get Marketing Platform.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMKTG_Platform_ID();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_MKTG_Platform_ID = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "MKTG_Platform_ID", null);
	String COLUMNNAME_MKTG_Platform_ID = "MKTG_Platform_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MKTG_ActiveCampaign_Config, Object> COLUMN_Updated = new ModelColumn<>(I_MKTG_ActiveCampaign_Config.class, "Updated", null);
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
