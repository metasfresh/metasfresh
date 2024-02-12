package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Alberta_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Alberta_Order 
{

	String Table_Name = "Alberta_Order";

//	/** AD_Table_ID=541896 */
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
	 * Set Alberta order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAlberta_Order_ID (int Alberta_Order_ID);

	/**
	 * Get Alberta order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAlberta_Order_ID();

	ModelColumn<I_Alberta_Order, Object> COLUMN_Alberta_Order_ID = new ModelColumn<>(I_Alberta_Order.class, "Alberta_Order_ID", null);
	String COLUMNNAME_Alberta_Order_ID = "Alberta_Order_ID";

	/**
	 * Set Annotation.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAnnotation (@Nullable String Annotation);

	/**
	 * Get Annotation.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getAnnotation();

	ModelColumn<I_Alberta_Order, Object> COLUMN_Annotation = new ModelColumn<>(I_Alberta_Order.class, "Annotation", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_Created = new ModelColumn<>(I_Alberta_Order.class, "Created", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_CreationDate = new ModelColumn<>(I_Alberta_Order.class, "CreationDate", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_DayOfDelivery = new ModelColumn<>(I_Alberta_Order.class, "DayOfDelivery", null);
	String COLUMNNAME_DayOfDelivery = "DayOfDelivery";

	/**
	 * Set Delivery info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryInfo (@Nullable String DeliveryInfo);

	/**
	 * Get Delivery info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDeliveryInfo();

	ModelColumn<I_Alberta_Order, Object> COLUMN_DeliveryInfo = new ModelColumn<>(I_Alberta_Order.class, "DeliveryInfo", null);
	String COLUMNNAME_DeliveryInfo = "DeliveryInfo";

	/**
	 * Set DeliveryNote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryNote (@Nullable String DeliveryNote);

	/**
	 * Get DeliveryNote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDeliveryNote();

	ModelColumn<I_Alberta_Order, Object> COLUMN_DeliveryNote = new ModelColumn<>(I_Alberta_Order.class, "DeliveryNote", null);
	String COLUMNNAME_DeliveryNote = "DeliveryNote";

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

	ModelColumn<I_Alberta_Order, Object> COLUMN_EndDate = new ModelColumn<>(I_Alberta_Order.class, "EndDate", null);
	String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalId (String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getExternalId();

	ModelColumn<I_Alberta_Order, Object> COLUMN_ExternalId = new ModelColumn<>(I_Alberta_Order.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_Alberta_Order, Object> COLUMN_ExternallyUpdatedAt = new ModelColumn<>(I_Alberta_Order.class, "ExternallyUpdatedAt", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_IsActive = new ModelColumn<>(I_Alberta_Order.class, "IsActive", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_IsArchived = new ModelColumn<>(I_Alberta_Order.class, "IsArchived", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_IsInitialCare = new ModelColumn<>(I_Alberta_Order.class, "IsInitialCare", null);
	String COLUMNNAME_IsInitialCare = "IsInitialCare";

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

	ModelColumn<I_Alberta_Order, Object> COLUMN_IsSeriesOrder = new ModelColumn<>(I_Alberta_Order.class, "IsSeriesOrder", null);
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

	ModelColumn<I_Alberta_Order, Object> COLUMN_NextDelivery = new ModelColumn<>(I_Alberta_Order.class, "NextDelivery", null);
	String COLUMNNAME_NextDelivery = "NextDelivery";

	/**
	 * Set Root Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRootId (@Nullable String RootId);

	/**
	 * Get Root Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getRootId();

	ModelColumn<I_Alberta_Order, Object> COLUMN_RootId = new ModelColumn<>(I_Alberta_Order.class, "RootId", null);
	String COLUMNNAME_RootId = "RootId";

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

	ModelColumn<I_Alberta_Order, Object> COLUMN_StartDate = new ModelColumn<>(I_Alberta_Order.class, "StartDate", null);
	String COLUMNNAME_StartDate = "StartDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Alberta_Order, Object> COLUMN_Updated = new ModelColumn<>(I_Alberta_Order.class, "Updated", null);
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
