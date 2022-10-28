/*
 * #%L
 * de.metas.banking.base
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

package org.adempiere.banking.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BankStatement_Import_File_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BankStatement_Import_File_Log 
{

	String Table_Name = "C_BankStatement_Import_File_Log";

//	/** AD_Table_ID=542251 */
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
	 * Set Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Bank Statement Import-File.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_Import_File_ID (int C_BankStatement_Import_File_ID);

	/**
	 * Get Bank Statement Import-File.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_Import_File_ID();

	I_C_BankStatement_Import_File getC_BankStatement_Import_File();

	void setC_BankStatement_Import_File(I_C_BankStatement_Import_File C_BankStatement_Import_File);

	ModelColumn<I_C_BankStatement_Import_File_Log, I_C_BankStatement_Import_File> COLUMN_C_BankStatement_Import_File_ID = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "C_BankStatement_Import_File_ID", I_C_BankStatement_Import_File.class);
	String COLUMNNAME_C_BankStatement_Import_File_ID = "C_BankStatement_Import_File_ID";

	/**
	 * Set C_BankStatement_Import_File_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_Import_File_Log_ID (int C_BankStatement_Import_File_Log_ID);

	/**
	 * Get C_BankStatement_Import_File_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_Import_File_Log_ID();

	ModelColumn<I_C_BankStatement_Import_File_Log, Object> COLUMN_C_BankStatement_Import_File_Log_ID = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "C_BankStatement_Import_File_Log_ID", null);
	String COLUMNNAME_C_BankStatement_Import_File_Log_ID = "C_BankStatement_Import_File_Log_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BankStatement_Import_File_Log, Object> COLUMN_Created = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "Created", null);
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

	ModelColumn<I_C_BankStatement_Import_File_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Message Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogmessage (@Nullable java.lang.String Logmessage);

	/**
	 * Get Message Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLogmessage();

	ModelColumn<I_C_BankStatement_Import_File_Log, Object> COLUMN_Logmessage = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "Logmessage", null);
	String COLUMNNAME_Logmessage = "Logmessage";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BankStatement_Import_File_Log, Object> COLUMN_Updated = new ModelColumn<>(I_C_BankStatement_Import_File_Log.class, "Updated", null);
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
