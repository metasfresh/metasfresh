package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Alberta_OrderedArticleLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Alberta_OrderedArticleLine 
{

	String Table_Name = "Alberta_OrderedArticleLine";

//	/** AD_Table_ID=541897 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAlberta_Order_ID (int Alberta_Order_ID);

	/**
	 * Get Alberta order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAlberta_Order_ID();

	I_Alberta_Order getAlberta_Order();

	void setAlberta_Order(I_Alberta_Order Alberta_Order);

	ModelColumn<I_Alberta_OrderedArticleLine, I_Alberta_Order> COLUMN_Alberta_Order_ID = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "Alberta_Order_ID", I_Alberta_Order.class);
	String COLUMNNAME_Alberta_Order_ID = "Alberta_Order_ID";

	/**
	 * Set Alberta ordered article line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAlberta_OrderedArticleLine_ID (int Alberta_OrderedArticleLine_ID);

	/**
	 * Get Alberta ordered article line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAlberta_OrderedArticleLine_ID();

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_Alberta_OrderedArticleLine_ID = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "Alberta_OrderedArticleLine_ID", null);
	String COLUMNNAME_Alberta_OrderedArticleLine_ID = "Alberta_OrderedArticleLine_ID";

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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_C_OLCand_ID = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "C_OLCand_ID", null);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_Created = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "Created", null);
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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_DurationAmount = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "DurationAmount", null);
	String COLUMNNAME_DurationAmount = "DurationAmount";

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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_ExternalId = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "ExternalId", null);
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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_ExternallyUpdatedAt = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "ExternallyUpdatedAt", null);
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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_IsActive = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_IsPrivateSale = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "IsPrivateSale", null);
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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_IsRentalEquipment = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "IsRentalEquipment", null);
	String COLUMNNAME_IsRentalEquipment = "IsRentalEquipment";

	/**
	 * Set Sales line.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesLineId (@Nullable String SalesLineId);

	/**
	 * Get Sales line.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSalesLineId();

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_SalesLineId = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "SalesLineId", null);
	String COLUMNNAME_SalesLineId = "SalesLineId";

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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_TimePeriod = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "TimePeriod", null);
	String COLUMNNAME_TimePeriod = "TimePeriod";

	/**
	 * Set Unit.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnit (@Nullable String Unit);

	/**
	 * Get Unit.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUnit();

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_Unit = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "Unit", null);
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

	ModelColumn<I_Alberta_OrderedArticleLine, Object> COLUMN_Updated = new ModelColumn<>(I_Alberta_OrderedArticleLine.class, "Updated", null);
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
