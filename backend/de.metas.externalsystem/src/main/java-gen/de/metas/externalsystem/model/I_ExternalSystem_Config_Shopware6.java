/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_Shopware6
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Shopware6 
{

	String Table_Name = "ExternalSystem_Config_Shopware6";

//	/** AD_Table_ID=541585 */
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
	 * Set Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseURL (java.lang.String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBaseURL();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Set Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClient_Id (java.lang.String Client_Id);

	/**
	 * Get Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClient_Id();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Client_Id = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Client_Id", null);
	String COLUMNNAME_Client_Id = "Client_Id";

	/**
	 * Set Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClient_Secret (java.lang.String Client_Secret);

	/**
	 * Get Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClient_Secret();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Client_Secret = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Client_Secret", null);
	String COLUMNNAME_Client_Secret = "Client_Secret";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Created", null);
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

	de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_Shopware6, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_Shopware6.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Shopware6_ID (int ExternalSystem_Config_Shopware6_ID);

	/**
	 * Get ExternalSystem_Config_Shopware6.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Shopware6_ID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_ExternalSystem_Config_Shopware6_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystem_Config_Shopware6_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Shopware6_ID = "ExternalSystem_Config_Shopware6_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (@Nullable java.lang.String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Customer JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the permanent customer-ID can be found. IMPORTANT: if set, then orders without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathConstantBPartnerID (@Nullable java.lang.String JSONPathConstantBPartnerID);

	/**
	 * Get Customer JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the permanent customer-ID can be found. IMPORTANT: if set, then orders without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathConstantBPartnerID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathConstantBPartnerID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathConstantBPartnerID", null);
	String COLUMNNAME_JSONPathConstantBPartnerID = "JSONPathConstantBPartnerID";

	/**
	 * Set Address JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware address the permanent address-ID can be found. IMPORTANT: if set, then addresses without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathConstantBPartnerLocationID (@Nullable java.lang.String JSONPathConstantBPartnerLocationID);

	/**
	 * Get Address JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware address the permanent address-ID can be found. IMPORTANT: if set, then addresses without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathConstantBPartnerLocationID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathConstantBPartnerLocationID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathConstantBPartnerLocationID", null);
	String COLUMNNAME_JSONPathConstantBPartnerLocationID = "JSONPathConstantBPartnerLocationID";

	/**
	 * Set Sales rep JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the sales rep can be found.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathSalesRepID (@Nullable java.lang.String JSONPathSalesRepID);

	/**
	 * Get Sales rep JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the sales rep can be found.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathSalesRepID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathSalesRepID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathSalesRepID", null);
	String COLUMNNAME_JSONPathSalesRepID = "JSONPathSalesRepID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Updated", null);
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
