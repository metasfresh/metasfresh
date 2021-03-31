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

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_OLCand_AlbertaOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OLCand_AlbertaOrder 
{

	String Table_Name = "C_OLCand_AlbertaOrder";

//	/** AD_Table_ID=541609 */
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
	 * Set Annotation.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAnnotation (@Nullable java.lang.String Annotation);

	/**
	 * Get Annotation.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAnnotation();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_Annotation = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "Annotation", null);
	String COLUMNNAME_Annotation = "Annotation";

	/**
	 * Set Doctor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Doctor_BPartner_ID (int C_Doctor_BPartner_ID);

	/**
	 * Get Doctor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Doctor_BPartner_ID();

	String COLUMNNAME_C_Doctor_BPartner_ID = "C_Doctor_BPartner_ID";

	/**
	 * Set Order line Alberta order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_AlbertaOrder_ID (int C_OLCand_AlbertaOrder_ID);

	/**
	 * Get Order line Alberta order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_AlbertaOrder_ID();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_C_OLCand_AlbertaOrder_ID = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "C_OLCand_AlbertaOrder_ID", null);
	String COLUMNNAME_C_OLCand_AlbertaOrder_ID = "C_OLCand_AlbertaOrder_ID";

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

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_C_OLCand_ID = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "C_OLCand_ID", null);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Set Pharmacy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Pharmacy_BPartner_ID (int C_Pharmacy_BPartner_ID);

	/**
	 * Get Pharmacy.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Pharmacy_BPartner_ID();

	String COLUMNNAME_C_Pharmacy_BPartner_ID = "C_Pharmacy_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "Created", null);
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
	 * Set Creation date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreationDate (@Nullable java.sql.Timestamp CreationDate);

	/**
	 * Get Creation date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreationDate();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_CreationDate = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "CreationDate", null);
	String COLUMNNAME_CreationDate = "CreationDate";

	/**
	 * Set Day of delivery.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDayOfDelivery (@Nullable BigDecimal DayOfDelivery);

	/**
	 * Get Day of delivery.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDayOfDelivery();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_DayOfDelivery = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "DayOfDelivery", null);
	String COLUMNNAME_DayOfDelivery = "DayOfDelivery";

	/**
	 * Set Duration amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationAmount (@Nullable BigDecimal DurationAmount);

	/**
	 * Get Duration amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDurationAmount();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_DurationAmount = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "DurationAmount", null);
	String COLUMNNAME_DurationAmount = "DurationAmount";

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

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_EndDate = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "EndDate", null);
	String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set Externally updated at.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternallyUpdatedAt (@Nullable java.sql.Timestamp ExternallyUpdatedAt);

	/**
	 * Get Externally updated at.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getExternallyUpdatedAt();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_ExternallyUpdatedAt = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "ExternallyUpdatedAt", null);
	String COLUMNNAME_ExternallyUpdatedAt = "ExternallyUpdatedAt";

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

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsArchived (boolean IsArchived);

	/**
	 * Get Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isArchived();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsArchived = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsArchived", null);
	String COLUMNNAME_IsArchived = "IsArchived";

	/**
	 * Set Initial care.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInitialCare (boolean IsInitialCare);

	/**
	 * Get Initial care.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInitialCare();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsInitialCare = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsInitialCare", null);
	String COLUMNNAME_IsInitialCare = "IsInitialCare";

	/**
	 * Set Private sale.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrivateSale (boolean IsPrivateSale);

	/**
	 * Get Private sale.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrivateSale();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsPrivateSale = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsPrivateSale", null);
	String COLUMNNAME_IsPrivateSale = "IsPrivateSale";

	/**
	 * Set Rental equipment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRentalEquipment (boolean IsRentalEquipment);

	/**
	 * Get Rental equipment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRentalEquipment();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsRentalEquipment = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsRentalEquipment", null);
	String COLUMNNAME_IsRentalEquipment = "IsRentalEquipment";

	/**
	 * Set Series order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSeriesOrder (boolean IsSeriesOrder);

	/**
	 * Get Series order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSeriesOrder();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_IsSeriesOrder = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "IsSeriesOrder", null);
	String COLUMNNAME_IsSeriesOrder = "IsSeriesOrder";

	/**
	 * Set Next delivery.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNextDelivery (@Nullable java.sql.Timestamp NextDelivery);

	/**
	 * Get Next delivery.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getNextDelivery();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_NextDelivery = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "NextDelivery", null);
	String COLUMNNAME_NextDelivery = "NextDelivery";

	/**
	 * Set Root Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRootId (@Nullable java.lang.String RootId);

	/**
	 * Get Root Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRootId();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_RootId = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "RootId", null);
	String COLUMNNAME_RootId = "RootId";

	/**
	 * Set Sales line.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesLineId (@Nullable java.lang.String SalesLineId);

	/**
	 * Get Sales line.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSalesLineId();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_SalesLineId = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "SalesLineId", null);
	String COLUMNNAME_SalesLineId = "SalesLineId";

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

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_StartDate = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "StartDate", null);
	String COLUMNNAME_StartDate = "StartDate";

	/**
	 * Set Time period.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimePeriod (@Nullable BigDecimal TimePeriod);

	/**
	 * Get Time period.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTimePeriod();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_TimePeriod = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "TimePeriod", null);
	String COLUMNNAME_TimePeriod = "TimePeriod";

	/**
	 * Set Unit.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnit (@Nullable java.lang.String Unit);

	/**
	 * Get Unit.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUnit();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_Unit = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "Unit", null);
	String COLUMNNAME_Unit = "Unit";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCand_AlbertaOrder, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCand_AlbertaOrder.class, "Updated", null);
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
