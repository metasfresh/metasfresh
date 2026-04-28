/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.dispo.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for MD_Candidate_QtyDetails
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Candidate_QtyDetails 
{

	String Table_Name = "MD_Candidate_QtyDetails";

//	/** AD_Table_ID=542503 */
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

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "Created", null);
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
	 * Set Detail disposition candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDetail_MD_Candidate_ID (int Detail_MD_Candidate_ID);

	/**
	 * Get Detail disposition candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDetail_MD_Candidate_ID();

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_Detail_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "Detail_MD_Candidate_ID", null);
	String COLUMNNAME_Detail_MD_Candidate_ID = "Detail_MD_Candidate_ID";

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

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

	ModelColumn<I_MD_Candidate_QtyDetails, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set MD_Candidate_QtyDetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_QtyDetails_ID (int MD_Candidate_QtyDetails_ID);

	/**
	 * Get MD_Candidate_QtyDetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_QtyDetails_ID();

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_MD_Candidate_QtyDetails_ID = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "MD_Candidate_QtyDetails_ID", null);
	String COLUMNNAME_MD_Candidate_QtyDetails_ID = "MD_Candidate_QtyDetails_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_Qty = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Stock disposition candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStock_MD_Candidate_ID (int Stock_MD_Candidate_ID);

	/**
	 * Get Stock disposition candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getStock_MD_Candidate_ID();

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_Stock_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "Stock_MD_Candidate_ID", null);
	String COLUMNNAME_Stock_MD_Candidate_ID = "Stock_MD_Candidate_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Candidate_QtyDetails, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate_QtyDetails.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this record
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
