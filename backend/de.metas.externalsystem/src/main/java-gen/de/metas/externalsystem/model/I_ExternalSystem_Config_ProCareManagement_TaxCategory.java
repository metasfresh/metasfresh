/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_ProCareManagement_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_ProCareManagement_TaxCategory 
{

	String Table_Name = "ExternalSystem_Config_ProCareManagement_TaxCategory";

//	/** AD_Table_ID=542404 */
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "Created", null);
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
	 * Set Pro Care Management.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ProCareManagement_ID (int ExternalSystem_Config_ProCareManagement_ID);

	/**
	 * Get Pro Care Management.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ProCareManagement_ID();

	I_ExternalSystem_Config_ProCareManagement getExternalSystem_Config_ProCareManagement();

	void setExternalSystem_Config_ProCareManagement(I_ExternalSystem_Config_ProCareManagement ExternalSystem_Config_ProCareManagement);

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, I_ExternalSystem_Config_ProCareManagement> COLUMN_ExternalSystem_Config_ProCareManagement_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "ExternalSystem_Config_ProCareManagement_ID", I_ExternalSystem_Config_ProCareManagement.class);
	String COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID = "ExternalSystem_Config_ProCareManagement_ID";

	/**
	 * Set Steuerkategorie-Mapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ProCareManagement_TaxCategory_ID (int ExternalSystem_Config_ProCareManagement_TaxCategory_ID);

	/**
	 * Get Steuerkategorie-Mapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ProCareManagement_TaxCategory_ID();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, Object> COLUMN_ExternalSystem_Config_ProCareManagement_TaxCategory_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "ExternalSystem_Config_ProCareManagement_TaxCategory_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ProCareManagement_TaxCategory_ID = "ExternalSystem_Config_ProCareManagement_TaxCategory_ID";

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

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tax Rates.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxRates (String TaxRates);

	/**
	 * Get Tax Rates.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getTaxRates();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, Object> COLUMN_TaxRates = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "TaxRates", null);
	String COLUMNNAME_TaxRates = "TaxRates";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_TaxCategory, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class, "Updated", null);
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
