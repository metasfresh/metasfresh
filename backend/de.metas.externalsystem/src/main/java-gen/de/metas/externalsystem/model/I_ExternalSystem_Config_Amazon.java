/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_Amazon
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Amazon 
{

	String Table_Name = "ExternalSystem_Config_Amazon";

//	/** AD_Table_ID=542249 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Access Key ID.
	 * Access Key ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccessKeyId (java.lang.String AccessKeyId);

	/**
	 * Get Access Key ID.
	 * Access Key ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccessKeyId();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_AccessKeyId = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "AccessKeyId", null);
	String COLUMNNAME_AccessKeyId = "AccessKeyId";

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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Base Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBasePath (java.lang.String BasePath);

	/**
	 * Get Base Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBasePath();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_BasePath = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "BasePath", null);
	String COLUMNNAME_BasePath = "BasePath";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClientID (java.lang.String ClientID);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClientID();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_ClientID = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "ClientID", null);
	String COLUMNNAME_ClientID = "ClientID";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClientSecret (java.lang.String ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClientSecret();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_ClientSecret = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "ClientSecret", null);
	String COLUMNNAME_ClientSecret = "ClientSecret";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set External system config Amazon.
	 * External system config Amazon
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Amazon_ID (int ExternalSystem_Config_Amazon_ID);

	/**
	 * Get External system config Amazon.
	 * External system config Amazon
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Amazon_ID();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_ExternalSystem_Config_Amazon_ID = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "ExternalSystem_Config_Amazon_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Amazon_ID = "ExternalSystem_Config_Amazon_ID";

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

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "ExternalSystem_Config_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (java.lang.String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Debug protocol.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDebugProtocol (boolean IsDebugProtocol);

	/**
	 * Get Debug protocol.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDebugProtocol();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_IsDebugProtocol = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "IsDebugProtocol", null);
	String COLUMNNAME_IsDebugProtocol = "IsDebugProtocol";

	/**
	 * Set LWA Endpoint.
	 * LWA authentication server URI
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLWAEndpoint (java.lang.String LWAEndpoint);

	/**
	 * Get LWA Endpoint.
	 * LWA authentication server URI
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLWAEndpoint();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_LWAEndpoint = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "LWAEndpoint", null);
	String COLUMNNAME_LWAEndpoint = "LWAEndpoint";

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

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_Name = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Refresh Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRefreshToken (@Nullable java.lang.String RefreshToken);

	/**
	 * Get Refresh Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRefreshToken();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_RefreshToken = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "RefreshToken", null);
	String COLUMNNAME_RefreshToken = "RefreshToken";

	/**
	 * Set Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRegionName (@Nullable java.lang.String RegionName);

	/**
	 * Get Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRegionName();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_RegionName = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set RoleArn.
	 * The ARN of the IAM role
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRoleArn (java.lang.String RoleArn);

	/**
	 * Get RoleArn.
	 * The ARN of the IAM role
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRoleArn();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_RoleArn = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "RoleArn", null);
	String COLUMNNAME_RoleArn = "RoleArn";

	/**
	 * Set Secret Key.
	 * AWS secret access key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSecretKey (@Nullable java.lang.String SecretKey);

	/**
	 * Get Secret Key.
	 * AWS secret access key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSecretKey();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_SecretKey = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "SecretKey", null);
	String COLUMNNAME_SecretKey = "SecretKey";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Amazon, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Amazon.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
