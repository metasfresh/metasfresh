/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Doc_Outbound_Log_PostFinance_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_Outbound_Log_PostFinance_Log
{

	String Table_Name = "C_Doc_Outbound_Log_PostFinance_Log";

	//	/** AD_Table_ID=542402 */
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
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
	 * Set Outbound Document Log ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID);

	/**
	 * Get Outbound Document Log ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Log_ID();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_C_Doc_Outbound_Log_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "C_Doc_Outbound_Log_ID", null);
	String COLUMNNAME_C_Doc_Outbound_Log_ID = "C_Doc_Outbound_Log_ID";

	/**
	 * Set PostFinance Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Log_PostFinance_Log_ID (int C_Doc_Outbound_Log_PostFinance_Log_ID);

	/**
	 * Get PostFinance Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Log_PostFinance_Log_ID();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_C_Doc_Outbound_Log_PostFinance_Log_ID = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "C_Doc_Outbound_Log_PostFinance_Log_ID", null);
	String COLUMNNAME_C_Doc_Outbound_Log_PostFinance_Log_ID = "C_Doc_Outbound_Log_PostFinance_Log_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "Created", null);
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

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_IsError = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMsgText (java.lang.String MsgText);

	/**
	 * Get Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMsgText();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_MsgText = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "MsgText", null);
	String COLUMNNAME_MsgText = "MsgText";

	/**
	 * Set PostFinance Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Transaction_Id (@Nullable java.lang.String PostFinance_Transaction_Id);

	/**
	 * Get PostFinance Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostFinance_Transaction_Id();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_PostFinance_Transaction_Id = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "PostFinance_Transaction_Id", null);
	String COLUMNNAME_PostFinance_Transaction_Id = "PostFinance_Transaction_Id";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Doc_Outbound_Log_PostFinance_Log, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_Outbound_Log_PostFinance_Log.class, "Updated", null);
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
