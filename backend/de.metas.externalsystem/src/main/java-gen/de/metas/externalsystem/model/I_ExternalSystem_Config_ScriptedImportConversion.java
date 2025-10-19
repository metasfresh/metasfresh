/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_ScriptedImportConversion
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_ScriptedImportConversion 
{

	String Table_Name = "ExternalSystem_Config_ScriptedImportConversion";

//	/** AD_Table_ID=542546 */
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
	 * Set User Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_Import_ID (int AD_User_Import_ID);

	/**
	 * Get User Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_Import_ID();

	String COLUMNNAME_AD_User_Import_ID = "AD_User_Import_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "Created", null);
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
	void setDescription (@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_Description = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Endpoint Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEndpointName (String EndpointName);

	/**
	 * Get Endpoint Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getEndpointName();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_EndpointName = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "EndpointName", null);
	String COLUMNNAME_EndpointName = "EndpointName";

	/**
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_ScriptedImportConversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ScriptedImportConversion_ID (int ExternalSystem_Config_ScriptedImportConversion_ID);

	/**
	 * Get ExternalSystem_Config_ScriptedImportConversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ScriptedImportConversion_ID();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_ExternalSystem_Config_ScriptedImportConversion_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "ExternalSystem_Config_ScriptedImportConversion_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ScriptedImportConversion_ID = "ExternalSystem_Config_ScriptedImportConversion_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

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

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Script Identifier.
	 * Name of the JavaScript-file that shall be executed by External Systems
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setScriptIdentifier (String ScriptIdentifier);

	/**
	 * Get Script Identifier.
	 * Name of the JavaScript-file that shall be executed by External Systems
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getScriptIdentifier();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_ScriptIdentifier = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "ScriptIdentifier", null);
	String COLUMNNAME_ScriptIdentifier = "ScriptIdentifier";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_ScriptedImportConversion, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_ScriptedImportConversion.class, "Updated", null);
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
