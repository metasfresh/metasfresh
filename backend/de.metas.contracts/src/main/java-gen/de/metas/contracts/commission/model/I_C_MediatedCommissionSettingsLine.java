/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.commission.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for C_MediatedCommissionSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_MediatedCommissionSettingsLine 
{

	String Table_Name = "C_MediatedCommissionSettingsLine";

//	/** AD_Table_ID=541808 */
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
	 * Set Mediated commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettings_ID (int C_MediatedCommissionSettings_ID);

	/**
	 * Get Mediated commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettings_ID();

	I_C_MediatedCommissionSettings getC_MediatedCommissionSettings();

	void setC_MediatedCommissionSettings(I_C_MediatedCommissionSettings C_MediatedCommissionSettings);

	ModelColumn<I_C_MediatedCommissionSettingsLine, I_C_MediatedCommissionSettings> COLUMN_C_MediatedCommissionSettings_ID = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "C_MediatedCommissionSettings_ID", I_C_MediatedCommissionSettings.class);
	String COLUMNNAME_C_MediatedCommissionSettings_ID = "C_MediatedCommissionSettings_ID";

	/**
	 * Set C_MediatedCommissionSettingsLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettingsLine_ID (int C_MediatedCommissionSettingsLine_ID);

	/**
	 * Get C_MediatedCommissionSettingsLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettingsLine_ID();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_C_MediatedCommissionSettingsLine_ID = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "C_MediatedCommissionSettingsLine_ID", null);
	String COLUMNNAME_C_MediatedCommissionSettingsLine_ID = "C_MediatedCommissionSettingsLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_Created = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "Created", null);
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

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set % of base points.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercentOfBasePoints (BigDecimal PercentOfBasePoints);

	/**
	 * Get % of base points.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentOfBasePoints();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_PercentOfBasePoints = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "PercentOfBasePoints", null);
	String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "Updated", null);
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
