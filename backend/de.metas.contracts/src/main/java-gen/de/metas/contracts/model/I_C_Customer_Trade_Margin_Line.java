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

package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Customer_Trade_Margin_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Customer_Trade_Margin_Line 
{

	String Table_Name = "C_Customer_Trade_Margin_Line";

//	/** AD_Table_ID=541796 */
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
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Customer_ID();

	String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set C_Customer_Trade_Margin.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Customer_Trade_Margin_ID (int C_Customer_Trade_Margin_ID);

	/**
	 * Get C_Customer_Trade_Margin.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Customer_Trade_Margin_ID();

	de.metas.contracts.model.I_C_Customer_Trade_Margin getC_Customer_Trade_Margin();

	void setC_Customer_Trade_Margin(de.metas.contracts.model.I_C_Customer_Trade_Margin C_Customer_Trade_Margin);

	ModelColumn<I_C_Customer_Trade_Margin_Line, de.metas.contracts.model.I_C_Customer_Trade_Margin> COLUMN_C_Customer_Trade_Margin_ID = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "C_Customer_Trade_Margin_ID", de.metas.contracts.model.I_C_Customer_Trade_Margin.class);
	String COLUMNNAME_C_Customer_Trade_Margin_ID = "C_Customer_Trade_Margin_ID";

	/**
	 * Set C_Customer_Trade_Margin_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Customer_Trade_Margin_Line_ID (int C_Customer_Trade_Margin_Line_ID);

	/**
	 * Get C_Customer_Trade_Margin_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Customer_Trade_Margin_Line_ID();

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_C_Customer_Trade_Margin_Line_ID = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "C_Customer_Trade_Margin_Line_ID", null);
	String COLUMNNAME_C_Customer_Trade_Margin_Line_ID = "C_Customer_Trade_Margin_Line_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "Created", null);
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

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin (int Margin);

	/**
	 * Get Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMargin();

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_Margin = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "Margin", null);
	String COLUMNNAME_Margin = "Margin";

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

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "SeqNo", null);
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

	ModelColumn<I_C_Customer_Trade_Margin_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_Customer_Trade_Margin_Line.class, "Updated", null);
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
