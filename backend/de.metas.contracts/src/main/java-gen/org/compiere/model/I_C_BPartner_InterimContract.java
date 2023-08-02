/*
 * #%L
 * de.metas.contracts
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

package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_InterimContract
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_InterimContract 
{

	String Table_Name = "C_BPartner_InterimContract";

//	/** AD_Table_ID=542357 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_BPartner_InterimContract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_InterimContract_ID (int C_BPartner_InterimContract_ID);

	/**
	 * Get C_BPartner_InterimContract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_InterimContract_ID();

	ModelColumn<I_C_BPartner_InterimContract, Object> COLUMN_C_BPartner_InterimContract_ID = new ModelColumn<>(I_C_BPartner_InterimContract.class, "C_BPartner_InterimContract_ID", null);
	String COLUMNNAME_C_BPartner_InterimContract_ID = "C_BPartner_InterimContract_ID";

	/**
	 * Set Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Harvesting_Calendar_ID (int C_Harvesting_Calendar_ID);

	/**
	 * Get Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Harvesting_Calendar_ID();

	org.compiere.model.I_C_Year getC_Harvesting_Calendar();

	void setC_Harvesting_Calendar(org.compiere.model.I_C_Year C_Harvesting_Calendar);

	ModelColumn<I_C_BPartner_InterimContract, org.compiere.model.I_C_Year> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_C_BPartner_InterimContract.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_InterimContract, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_InterimContract.class, "Created", null);
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
	 * Set Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	@Nullable org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(@Nullable org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_C_BPartner_InterimContract, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_C_BPartner_InterimContract.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

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

	ModelColumn<I_C_BPartner_InterimContract, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_InterimContract.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsInterimContract.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInterimContract (boolean IsInterimContract);

	/**
	 * Get IsInterimContract.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInterimContract();

	ModelColumn<I_C_BPartner_InterimContract, Object> COLUMN_IsInterimContract = new ModelColumn<>(I_C_BPartner_InterimContract.class, "IsInterimContract", null);
	String COLUMNNAME_IsInterimContract = "IsInterimContract";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_InterimContract, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_InterimContract.class, "Updated", null);
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
