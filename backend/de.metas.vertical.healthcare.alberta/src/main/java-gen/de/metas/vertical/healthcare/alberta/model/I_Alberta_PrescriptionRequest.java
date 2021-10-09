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

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Alberta_PrescriptionRequest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Alberta_PrescriptionRequest 
{

	String Table_Name = "Alberta_PrescriptionRequest";

//	/** AD_Table_ID=541622 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Accounting months.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountingMonths (@Nullable java.lang.String AccountingMonths);

	/**
	 * Get Accounting months.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountingMonths();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_AccountingMonths = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "AccountingMonths", null);
	String COLUMNNAME_AccountingMonths = "AccountingMonths";

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
	 * Set Alberta Prescription Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAlberta_PrescriptionRequest_ID (int Alberta_PrescriptionRequest_ID);

	/**
	 * Get Alberta Prescription Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAlberta_PrescriptionRequest_ID();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_Alberta_PrescriptionRequest_ID = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "Alberta_PrescriptionRequest_ID", null);
	String COLUMNNAME_Alberta_PrescriptionRequest_ID = "Alberta_PrescriptionRequest_ID";

	/**
	 * Set Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setArchived (boolean Archived);

	/**
	 * Get Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isArchived();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_Archived = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "Archived", null);
	String COLUMNNAME_Archived = "Archived";

	/**
	 * Set Doctor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Doctor_ID (int C_BPartner_Doctor_ID);

	/**
	 * Get Doctor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Doctor_ID();

	String COLUMNNAME_C_BPartner_Doctor_ID = "C_BPartner_Doctor_ID";

	/**
	 * Set Patient.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Patient_ID (int C_BPartner_Patient_ID);

	/**
	 * Get Patient.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Patient_ID();

	String COLUMNNAME_C_BPartner_Patient_ID = "C_BPartner_Patient_ID";

	/**
	 * Set Pharmacy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Pharmacy_ID (int C_BPartner_Pharmacy_ID);

	/**
	 * Get Pharmacy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Pharmacy_ID();

	String COLUMNNAME_C_BPartner_Pharmacy_ID = "C_BPartner_Pharmacy_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_Alberta_PrescriptionRequest, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_Created = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "Created", null);
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
	 * Set Contract End.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEndDate (@Nullable java.sql.Timestamp EndDate);

	/**
	 * Get Contract End.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEndDate();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_EndDate = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "EndDate", null);
	String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_ExternalId = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalOrderId (@Nullable java.lang.String ExternalOrderId);

	/**
	 * Get External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalOrderId();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_ExternalOrderId = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "ExternalOrderId", null);
	String COLUMNNAME_ExternalOrderId = "ExternalOrderId";

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

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_IsActive = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_Note = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Birthday.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPatientBirthday (@Nullable java.sql.Timestamp PatientBirthday);

	/**
	 * Get Birthday.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPatientBirthday();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_PatientBirthday = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "PatientBirthday", null);
	String COLUMNNAME_PatientBirthday = "PatientBirthday";

	/**
	 * Set Prescription request created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrescriptionRequestCreatedAt (@Nullable java.sql.Timestamp PrescriptionRequestCreatedAt);

	/**
	 * Get Prescription request created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPrescriptionRequestCreatedAt();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_PrescriptionRequestCreatedAt = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "PrescriptionRequestCreatedAt", null);
	String COLUMNNAME_PrescriptionRequestCreatedAt = "PrescriptionRequestCreatedAt";

	/**
	 * Set Prescription request created by.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrescriptionRequestCreatedBy (int PrescriptionRequestCreatedBy);

	/**
	 * Get Prescription request created by.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrescriptionRequestCreatedBy();

	String COLUMNNAME_PrescriptionRequestCreatedBy = "PrescriptionRequestCreatedBy";

	/**
	 * Set Prescription request timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrescriptionRequestTimestamp (@Nullable java.sql.Timestamp PrescriptionRequestTimestamp);

	/**
	 * Get Prescription request timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPrescriptionRequestTimestamp();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_PrescriptionRequestTimestamp = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "PrescriptionRequestTimestamp", null);
	String COLUMNNAME_PrescriptionRequestTimestamp = "PrescriptionRequestTimestamp";

	/**
	 * Set Prescription request updated.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrescriptionRequestUpdateAt (@Nullable java.sql.Timestamp PrescriptionRequestUpdateAt);

	/**
	 * Get Prescription request updated.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPrescriptionRequestUpdateAt();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_PrescriptionRequestUpdateAt = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "PrescriptionRequestUpdateAt", null);
	String COLUMNNAME_PrescriptionRequestUpdateAt = "PrescriptionRequestUpdateAt";

	/**
	 * Set PrescriptionType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrescriptionType (@Nullable java.lang.String PrescriptionType);

	/**
	 * Get PrescriptionType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrescriptionType();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_PrescriptionType = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "PrescriptionType", null);
	String COLUMNNAME_PrescriptionType = "PrescriptionType";

	/**
	 * Set Start Date.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartDate (@Nullable java.sql.Timestamp StartDate);

	/**
	 * Get Start Date.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getStartDate();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_StartDate = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "StartDate", null);
	String COLUMNNAME_StartDate = "StartDate";

	/**
	 * Set Therapy Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTherapyTypeIds (@Nullable java.lang.String TherapyTypeIds);

	/**
	 * Get Therapy Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTherapyTypeIds();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_TherapyTypeIds = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "TherapyTypeIds", null);
	String COLUMNNAME_TherapyTypeIds = "TherapyTypeIds";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Alberta_PrescriptionRequest, Object> COLUMN_Updated = new ModelColumn<>(I_Alberta_PrescriptionRequest.class, "Updated", null);
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
