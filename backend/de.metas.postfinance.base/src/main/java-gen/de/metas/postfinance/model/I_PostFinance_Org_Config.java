/*
 * #%L
 * de.metas.postfinance.base
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

/** Generated Interface for PostFinance_Org_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PostFinance_Org_Config 
{

	String Table_Name = "PostFinance_Org_Config";

//	/** AD_Table_ID=542386 */
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

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_Created = new ModelColumn<>(I_PostFinance_Org_Config.class, "Created", null);
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

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_PostFinance_Org_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Archive Data.
	 * Download already downloaded data from PostFinance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsArchiveData (boolean IsArchiveData);

	/**
	 * Get Archive Data.
	 * Download already downloaded data from PostFinance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isArchiveData();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_IsArchiveData = new ModelColumn<>(I_PostFinance_Org_Config.class, "IsArchiveData", null);
	String COLUMNNAME_IsArchiveData = "IsArchiveData";

	/**
	 * Set Testserver.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTestserver (boolean IsTestserver);

	/**
	 * Get Testserver.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTestserver();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_IsTestserver = new ModelColumn<>(I_PostFinance_Org_Config.class, "IsTestserver", null);
	String COLUMNNAME_IsTestserver = "IsTestserver";

	/**
	 * Set Use Paper Bill Service.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUsePaperBill (boolean IsUsePaperBill);

	/**
	 * Get Use Paper Bill Service.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUsePaperBill();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_IsUsePaperBill = new ModelColumn<>(I_PostFinance_Org_Config.class, "IsUsePaperBill", null);
	String COLUMNNAME_IsUsePaperBill = "IsUsePaperBill";

	/**
	 * Set PostFinance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Org_Config_ID (int PostFinance_Org_Config_ID);

	/**
	 * Get PostFinance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPostFinance_Org_Config_ID();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_PostFinance_Org_Config_ID = new ModelColumn<>(I_PostFinance_Org_Config.class, "PostFinance_Org_Config_ID", null);
	String COLUMNNAME_PostFinance_Org_Config_ID = "PostFinance_Org_Config_ID";

	/**
	 * Set Biller ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Sender_BillerId (java.lang.String PostFinance_Sender_BillerId);

	/**
	 * Get Biller ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostFinance_Sender_BillerId();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_PostFinance_Sender_BillerId = new ModelColumn<>(I_PostFinance_Org_Config.class, "PostFinance_Sender_BillerId", null);
	String COLUMNNAME_PostFinance_Sender_BillerId = "PostFinance_Sender_BillerId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PostFinance_Org_Config, Object> COLUMN_Updated = new ModelColumn<>(I_PostFinance_Org_Config.class, "Updated", null);
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
