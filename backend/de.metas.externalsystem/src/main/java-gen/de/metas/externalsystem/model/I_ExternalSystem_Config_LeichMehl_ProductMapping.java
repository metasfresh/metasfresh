/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_LeichMehl_ProductMapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_LeichMehl_ProductMapping 
{

	String Table_Name = "ExternalSystem_Config_LeichMehl_ProductMapping";

//	/** AD_Table_ID=542172 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "Created", null);
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
	 * Set LeichUndMehl.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_LeichMehl_ID (int ExternalSystem_Config_LeichMehl_ID);

	/**
	 * Get LeichUndMehl.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_LeichMehl_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl getExternalSystem_Config_LeichMehl();

	void setExternalSystem_Config_LeichMehl(de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl ExternalSystem_Config_LeichMehl);

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl> COLUMN_ExternalSystem_Config_LeichMehl_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "ExternalSystem_Config_LeichMehl_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl.class);
	String COLUMNNAME_ExternalSystem_Config_LeichMehl_ID = "ExternalSystem_Config_LeichMehl_ID";

	/**
	 * Set ExternalSystem_Config_LeichMehl_ProductMapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_LeichMehl_ProductMapping_ID (int ExternalSystem_Config_LeichMehl_ProductMapping_ID);

	/**
	 * Get ExternalSystem_Config_LeichMehl_ProductMapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_LeichMehl_ProductMapping_ID();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_ExternalSystem_Config_LeichMehl_ProductMapping_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "ExternalSystem_Config_LeichMehl_ProductMapping_ID", null);
	String COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID = "ExternalSystem_Config_LeichMehl_ProductMapping_ID";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set PLU_File.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPLU_File (java.lang.String PLU_File);

	/**
	 * Get PLU_File.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPLU_File();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_PLU_File = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "PLU_File", null);
	String COLUMNNAME_PLU_File = "PLU_File";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_SeqNo = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "SeqNo", null);
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

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "Updated", null);
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
