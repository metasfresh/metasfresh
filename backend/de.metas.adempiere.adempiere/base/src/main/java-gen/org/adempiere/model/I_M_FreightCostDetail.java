/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_FreightCostDetail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_FreightCostDetail 
{

	String Table_Name = "M_FreightCostDetail";

//	/** AD_Table_ID=540005 */
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
	 * Set Country Area.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CountryArea_ID (int C_CountryArea_ID);

	/**
	 * Get Country Area.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CountryArea_ID();

	@Nullable org.compiere.model.I_C_CountryArea getC_CountryArea();

	void setC_CountryArea(@Nullable org.compiere.model.I_C_CountryArea C_CountryArea);

	ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_C_CountryArea> COLUMN_C_CountryArea_ID = new ModelColumn<>(I_M_FreightCostDetail.class, "C_CountryArea_ID", org.compiere.model.I_C_CountryArea.class);
	String COLUMNNAME_C_CountryArea_ID = "C_CountryArea_ID";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_M_FreightCostDetail.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_Created = new ModelColumn<>(I_M_FreightCostDetail.class, "Created", null);
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
	 * Set Frachtbetrag.
	 * Frachtbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Frachtbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFreightAmt();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_FreightAmt = new ModelColumn<>(I_M_FreightCostDetail.class, "FreightAmt", null);
	String COLUMNNAME_FreightAmt = "FreightAmt";

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

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_IsActive = new ModelColumn<>(I_M_FreightCostDetail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Frachtkostendetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_FreightCostDetail_ID (int M_FreightCostDetail_ID);

	/**
	 * Get Frachtkostendetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_FreightCostDetail_ID();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_M_FreightCostDetail_ID = new ModelColumn<>(I_M_FreightCostDetail.class, "M_FreightCostDetail_ID", null);
	String COLUMNNAME_M_FreightCostDetail_ID = "M_FreightCostDetail_ID";

	/**
	 * Set Freight Cost Shipper.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_FreightCostShipper_ID (int M_FreightCostShipper_ID);

	/**
	 * Get Freight Cost Shipper.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_FreightCostShipper_ID();

	org.adempiere.model.I_M_FreightCostShipper getM_FreightCostShipper();

	void setM_FreightCostShipper(org.adempiere.model.I_M_FreightCostShipper M_FreightCostShipper);

	ModelColumn<I_M_FreightCostDetail, org.adempiere.model.I_M_FreightCostShipper> COLUMN_M_FreightCostShipper_ID = new ModelColumn<>(I_M_FreightCostDetail.class, "M_FreightCostShipper_ID", org.adempiere.model.I_M_FreightCostShipper.class);
	String COLUMNNAME_M_FreightCostShipper_ID = "M_FreightCostShipper_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_FreightCostDetail.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Lieferwert.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShipmentValueAmt (BigDecimal ShipmentValueAmt);

	/**
	 * Get Lieferwert.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getShipmentValueAmt();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_ShipmentValueAmt = new ModelColumn<>(I_M_FreightCostDetail.class, "ShipmentValueAmt", null);
	String COLUMNNAME_ShipmentValueAmt = "ShipmentValueAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_FreightCostDetail, Object> COLUMN_Updated = new ModelColumn<>(I_M_FreightCostDetail.class, "Updated", null);
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
