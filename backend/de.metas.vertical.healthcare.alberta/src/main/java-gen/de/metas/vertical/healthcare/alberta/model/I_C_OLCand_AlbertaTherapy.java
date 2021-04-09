/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OLCand_AlbertaTherapy
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OLCand_AlbertaTherapy
{

	String Table_Name = "C_OLCand_AlbertaTherapy";

	//	/** AD_Table_ID=541607 */
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
	 * Set Order line Alberta therapy.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_AlbertaTherapy_ID (int C_OLCand_AlbertaTherapy_ID);

	/**
	 * Get Order line Alberta therapy.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_AlbertaTherapy_ID();

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_C_OLCand_AlbertaTherapy_ID = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "C_OLCand_AlbertaTherapy_ID", null);
	String COLUMNNAME_C_OLCand_AlbertaTherapy_ID = "C_OLCand_AlbertaTherapy_ID";

	/**
	 * Set Orderline Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_ID (int C_OLCand_ID);

	/**
	 * Get Orderline Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_ID();

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_C_OLCand_ID = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "C_OLCand_ID", null);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "Created", null);
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

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Therapy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTherapy (String Therapy);

	/**
	 * Get Therapy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getTherapy();

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_Therapy = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "Therapy", null);
	String COLUMNNAME_Therapy = "Therapy";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCand_AlbertaTherapy, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCand_AlbertaTherapy.class, "Updated", null);
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