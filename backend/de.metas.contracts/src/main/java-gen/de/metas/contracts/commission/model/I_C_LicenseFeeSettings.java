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

/** Generated Interface for C_LicenseFeeSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_LicenseFeeSettings 
{

	String Table_Name = "C_LicenseFeeSettings";

//	/** AD_Table_ID=541946 */
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
	 * Set License fee settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_LicenseFeeSettings_ID (int C_LicenseFeeSettings_ID);

	/**
	 * Get License fee settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_LicenseFeeSettings_ID();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_C_LicenseFeeSettings_ID = new ModelColumn<>(I_C_LicenseFeeSettings.class, "C_LicenseFeeSettings_ID", null);
	String COLUMNNAME_C_LicenseFeeSettings_ID = "C_LicenseFeeSettings_ID";

	/**
	 * Set Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Product_ID (int Commission_Product_ID);

	/**
	 * Get Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommission_Product_ID();

	String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_Created = new ModelColumn<>(I_C_LicenseFeeSettings.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_Description = new ModelColumn<>(I_C_LicenseFeeSettings.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_IsActive = new ModelColumn<>(I_C_LicenseFeeSettings.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_Name = new ModelColumn<>(I_C_LicenseFeeSettings.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsPrecision (int PointsPrecision);

	/**
	 * Get Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPointsPrecision();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_PointsPrecision = new ModelColumn<>(I_C_LicenseFeeSettings.class, "PointsPrecision", null);
	String COLUMNNAME_PointsPrecision = "PointsPrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_LicenseFeeSettings, Object> COLUMN_Updated = new ModelColumn<>(I_C_LicenseFeeSettings.class, "Updated", null);
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
