/*
 * #%L
 * de.metas.async
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

package de.metas.async.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Async_Batch_Milestone
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Async_Batch_Milestone 
{

	String Table_Name = "C_Async_Batch_Milestone";

//	/** AD_Table_ID=541758 */
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
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	de.metas.async.model.I_C_Async_Batch getC_Async_Batch();

	void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch);

	ModelColumn<I_C_Async_Batch_Milestone, de.metas.async.model.I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "C_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Async Batch Milestone.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_Milestone_ID (int C_Async_Batch_Milestone_ID);

	/**
	 * Get Async Batch Milestone.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_Milestone_ID();

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_C_Async_Batch_Milestone_ID = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "C_Async_Batch_Milestone_ID", null);
	String COLUMNNAME_C_Async_Batch_Milestone_ID = "C_Async_Batch_Milestone_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_Created = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "Created", null);
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

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_Name = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_Processed = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Async_Batch_Milestone, Object> COLUMN_Updated = new ModelColumn<>(I_C_Async_Batch_Milestone.class, "Updated", null);
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
