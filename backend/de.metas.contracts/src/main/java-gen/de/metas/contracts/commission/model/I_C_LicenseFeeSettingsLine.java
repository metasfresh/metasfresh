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

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_LicenseFeeSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_LicenseFeeSettingsLine 
{

	String Table_Name = "C_LicenseFeeSettingsLine";

//	/** AD_Table_ID=541947 */
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
	 * Set Sales rep group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_Match_ID (int C_BP_Group_Match_ID);

	/**
	 * Get Sales rep group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_Match_ID();

	@Nullable org.compiere.model.I_C_BP_Group getC_BP_Group_Match();

	void setC_BP_Group_Match(@Nullable org.compiere.model.I_C_BP_Group C_BP_Group_Match);

	ModelColumn<I_C_LicenseFeeSettingsLine, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_Match_ID = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "C_BP_Group_Match_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_Match_ID = "C_BP_Group_Match_ID";

	/**
	 * Set License fee settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_LicenseFeeSettings_ID (int C_LicenseFeeSettings_ID);

	/**
	 * Get License fee settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_LicenseFeeSettings_ID();

	de.metas.contracts.commission.model.I_C_LicenseFeeSettings getC_LicenseFeeSettings();

	void setC_LicenseFeeSettings(de.metas.contracts.commission.model.I_C_LicenseFeeSettings C_LicenseFeeSettings);

	ModelColumn<I_C_LicenseFeeSettingsLine, de.metas.contracts.commission.model.I_C_LicenseFeeSettings> COLUMN_C_LicenseFeeSettings_ID = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "C_LicenseFeeSettings_ID", de.metas.contracts.commission.model.I_C_LicenseFeeSettings.class);
	String COLUMNNAME_C_LicenseFeeSettings_ID = "C_LicenseFeeSettings_ID";

	/**
	 * Set License fee detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_LicenseFeeSettingsLine_ID (int C_LicenseFeeSettingsLine_ID);

	/**
	 * Get License fee detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_LicenseFeeSettingsLine_ID();

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_C_LicenseFeeSettingsLine_ID = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "C_LicenseFeeSettingsLine_ID", null);
	String COLUMNNAME_C_LicenseFeeSettingsLine_ID = "C_LicenseFeeSettingsLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_Created = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "Created", null);
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

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_PercentOfBasePoints = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "PercentOfBasePoints", null);
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

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "SeqNo", null);
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

	ModelColumn<I_C_LicenseFeeSettingsLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_LicenseFeeSettingsLine.class, "Updated", null);
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
