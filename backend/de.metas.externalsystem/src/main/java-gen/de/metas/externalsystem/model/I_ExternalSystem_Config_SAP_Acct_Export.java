/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

/** Generated Interface for ExternalSystem_Config_SAP_Acct_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_SAP_Acct_Export 
{

	String Table_Name = "ExternalSystem_Config_SAP_Acct_Export";

//	/** AD_Table_ID=542316 */
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
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "Created", null);
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
	 * Set SAP Config - Accouting Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_Acct_Export_ID (int ExternalSystem_Config_SAP_Acct_Export_ID);

	/**
	 * Get SAP Config - Accouting Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_Acct_Export_ID();

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, Object> COLUMN_ExternalSystem_Config_SAP_Acct_Export_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "ExternalSystem_Config_SAP_Acct_Export_ID", null);
	String COLUMNNAME_ExternalSystem_Config_SAP_Acct_Export_ID = "ExternalSystem_Config_SAP_Acct_Export_ID";

	/**
	 * Set ExternalSystem_Config_SAP.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_ID (int ExternalSystem_Config_SAP_ID);

	/**
	 * Get ExternalSystem_Config_SAP.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config_SAP getExternalSystem_Config_SAP();

	void setExternalSystem_Config_SAP(de.metas.externalsystem.model.I_ExternalSystem_Config_SAP ExternalSystem_Config_SAP);

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP> COLUMN_ExternalSystem_Config_SAP_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "ExternalSystem_Config_SAP_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class);
	String COLUMNNAME_ExternalSystem_Config_SAP_ID = "ExternalSystem_Config_SAP_ID";

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

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_SAP_Acct_Export, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_SAP_Acct_Export.class, "Updated", null);
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
