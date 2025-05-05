/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for I_Invoice_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_Invoice_Candidate 
{

	String Table_Name = "I_Invoice_Candidate";

//	/** AD_Table_ID=542207 */
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
	 * Set Responsible External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ExternalReference(@Nullable String AD_User_InCharge_ExternalReference);

	/**
	 * Get Responsible External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getAD_User_InCharge_ExternalReference();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_AD_User_InCharge_ExternalReference = new ModelColumn<>(I_I_Invoice_Candidate.class, "AD_User_InCharge_ExternalReference", null);
	String COLUMNNAME_AD_User_InCharge_ExternalReference = "AD_User_InCharge_ExternalReference";

	/**
	 * Set Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Bill Partner External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ExternalReference(@Nullable String Bill_BPartner_ExternalReference);

	/**
	 * Get Bill Partner External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getBill_BPartner_ExternalReference();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Bill_BPartner_ExternalReference = new ModelColumn<>(I_I_Invoice_Candidate.class, "Bill_BPartner_ExternalReference", null);
	String COLUMNNAME_Bill_BPartner_ExternalReference = "Bill_BPartner_ExternalReference";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill partner search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_Value (@Nullable String Bill_BPartner_Value);

	/**
	 * Get Bill partner search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getBill_BPartner_Value();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Bill_BPartner_Value = new ModelColumn<>(I_I_Invoice_Candidate.class, "Bill_BPartner_Value", null);
	String COLUMNNAME_Bill_BPartner_Value = "Bill_BPartner_Value";

	/**
	 * Set Bill Location External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ExternalReference(@Nullable String Bill_Location_ExternalReference);

	/**
	 * Get Bill Location External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getBill_Location_ExternalReference();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Bill_Location_ExternalReference = new ModelColumn<>(I_I_Invoice_Candidate.class, "Bill_Location_ExternalReference", null);
	String COLUMNNAME_Bill_Location_ExternalReference = "Bill_Location_ExternalReference";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Bill Contact External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ExternalReference(@Nullable String Bill_User_ExternalReference);

	/**
	 * Get Bill Contact External Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getBill_User_ExternalReference();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Bill_User_ExternalReference = new ModelColumn<>(I_I_Invoice_Candidate.class, "Bill_User_ExternalReference", null);
	String COLUMNNAME_Bill_User_ExternalReference = "Bill_User_ExternalReference";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Activity search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_Value (@Nullable String C_Activity_Value);

	/**
	 * Get Activity search key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getC_Activity_Value();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_C_Activity_Value = new ModelColumn<>(I_I_Invoice_Candidate.class, "C_Activity_Value", null);
	String COLUMNNAME_C_Activity_Value = "C_Activity_Value";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_Invoice_Candidate, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_Invoice_Candidate.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_Invoice_Candidate, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_Invoice_Candidate.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_I_Invoice_Candidate.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_DateOrdered = new ModelColumn<>(I_I_Invoice_Candidate.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Default Org Code.
	 * Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDefault_OrgCode (String Default_OrgCode);

	/**
	 * Get Default Org Code.
	 * Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getDefault_OrgCode();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Default_OrgCode = new ModelColumn<>(I_I_Invoice_Candidate.class, "Default_OrgCode", null);
	String COLUMNNAME_Default_OrgCode = "Default_OrgCode";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription(@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Description = new ModelColumn<>(I_I_Invoice_Candidate.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set End note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom(@Nullable String DescriptionBottom);

	/**
	 * Get End note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescriptionBottom();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_I_Invoice_Candidate.class, "DescriptionBottom", null);
	String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Discount = new ModelColumn<>(I_I_Invoice_Candidate.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocBaseType();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_DocBaseType = new ModelColumn<>(I_I_Invoice_Candidate.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocSubType (@Nullable String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocSubType();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_DocSubType = new ModelColumn<>(I_I_Invoice_Candidate.class, "DocSubType", null);
	String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalHeaderId (@Nullable String ExternalHeaderId);

	/**
	 * Get External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExternalHeaderId();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_I_Invoice_Candidate.class, "ExternalHeaderId", null);
	String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalLineId (@Nullable String ExternalLineId);

	/**
	 * Get External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExternalLineId();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_I_Invoice_Candidate.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem(@Nullable String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getExternalSystem();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_ExternalSystem = new ModelColumn<>(I_I_Invoice_Candidate.class, "ExternalSystem", null);
	String COLUMNNAME_ExternalSystem = "ExternalSystem";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getI_ErrorMsg();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_Invoice_Candidate.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Import - Invoice candiate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_Invoice_Candidate_ID (int I_Invoice_Candidate_ID);

	/**
	 * Get Import - Invoice candiate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_Invoice_Candidate_ID();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_I_Invoice_Candidate_ID = new ModelColumn<>(I_I_Invoice_Candidate.class, "I_Invoice_Candidate_ID", null);
	String COLUMNNAME_I_Invoice_Candidate_ID = "I_Invoice_Candidate_ID";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_Invoice_Candidate.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent(@Nullable String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getI_LineContent();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_Invoice_Candidate.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_Invoice_Candidate.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule(@Nullable String InvoiceRule);

	/**
	 * Get Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getInvoiceRule();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_I_Invoice_Candidate.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

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

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_I_Invoice_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_I_Invoice_Candidate.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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
	 * Set Product Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Value (String M_Product_Value);

	/**
	 * Get Product Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getM_Product_Value();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_M_Product_Value = new ModelColumn<>(I_I_Invoice_Candidate.class, "M_Product_Value", null);
	String COLUMNNAME_M_Product_Value = "M_Product_Value";

	/**
	 * Set Org Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrgCode (@Nullable String OrgCode);

	/**
	 * Get Org Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getOrgCode();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_OrgCode = new ModelColumn<>(I_I_Invoice_Candidate.class, "OrgCode", null);
	String COLUMNNAME_OrgCode = "OrgCode";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPOReference();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_POReference = new ModelColumn<>(I_I_Invoice_Candidate.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPresetDateInvoiced (@Nullable java.sql.Timestamp PresetDateInvoiced);

	/**
	 * Get Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPresetDateInvoiced();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_PresetDateInvoiced = new ModelColumn<>(I_I_Invoice_Candidate.class, "PresetDateInvoiced", null);
	String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Price.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice (@Nullable BigDecimal Price);

	/**
	 * Get Price.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrice();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Price = new ModelColumn<>(I_I_Invoice_Candidate.class, "Price", null);
	String COLUMNNAME_Price = "Price";

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

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_I_Invoice_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Processing = new ModelColumn<>(I_I_Invoice_Candidate.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (@Nullable BigDecimal QtyDelivered);

	/**
	 * Get Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_I_Invoice_Candidate.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_I_Invoice_Candidate.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_I_Invoice_Candidate.class, "Updated", null);
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

	/**
	 * Set UOM Code.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setX12DE355(@Nullable String X12DE355);

	/**
	 * Get UOM Code.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getX12DE355();

	ModelColumn<I_I_Invoice_Candidate, Object> COLUMN_X12DE355 = new ModelColumn<>(I_I_Invoice_Candidate.class, "X12DE355", null);
	String COLUMNNAME_X12DE355 = "X12DE355";
}
