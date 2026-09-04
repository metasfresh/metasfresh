package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for RV_ReceiptLogistics
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_ReceiptLogistics 
{

	String Table_Name = "RV_ReceiptLogistics";

//	/** AD_Table_ID=542644 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setATA (@Nullable java.sql.Timestamp ATA);

	/**
	 * Get ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getATA();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_ATA = new ModelColumn<>(I_RV_ReceiptLogistics.class, "ATA", null);
	String COLUMNNAME_ATA = "ATA";

	/**
	 * Set ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setATD (@Nullable java.sql.Timestamp ATD);

	/**
	 * Get ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getATD();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_ATD = new ModelColumn<>(I_RV_ReceiptLogistics.class, "ATD", null);
	String COLUMNNAME_ATD = "ATD";

	/**
	 * Set KW.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCalendarWeek (@Nullable BigDecimal CalendarWeek);

	/**
	 * Get KW.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCalendarWeek();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_CalendarWeek = new ModelColumn<>(I_RV_ReceiptLogistics.class, "CalendarWeek", null);
	String COLUMNNAME_CalendarWeek = "CalendarWeek";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Container No.
	 * Number of the container
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContainerNo (@Nullable java.lang.String ContainerNo);

	/**
	 * Get Container No.
	 * Number of the container
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContainerNo();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_ContainerNo = new ModelColumn<>(I_RV_ReceiptLogistics.class, "ContainerNo", null);
	String COLUMNNAME_ContainerNo = "ContainerNo";

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

	ModelColumn<I_RV_ReceiptLogistics, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_RV_ReceiptLogistics.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_Created = new ModelColumn<>(I_RV_ReceiptLogistics.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_CreatedBy = new ModelColumn<>(I_RV_ReceiptLogistics.class, "CreatedBy", null);
	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised_Effective (@Nullable java.sql.Timestamp DatePromised_Effective);

	/**
	 * Get Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised_Effective();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_DatePromised_Effective = new ModelColumn<>(I_RV_ReceiptLogistics.class, "DatePromised_Effective", null);
	String COLUMNNAME_DatePromised_Effective = "DatePromised_Effective";

	/**
	 * Set ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setETA (@Nullable java.sql.Timestamp ETA);

	/**
	 * Get ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getETA();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_ETA = new ModelColumn<>(I_RV_ReceiptLogistics.class, "ETA", null);
	String COLUMNNAME_ETA = "ETA";

	/**
	 * Set ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setETD (@Nullable java.sql.Timestamp ETD);

	/**
	 * Get ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getETD();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_ETD = new ModelColumn<>(I_RV_ReceiptLogistics.class, "ETD", null);
	String COLUMNNAME_ETD = "ETD";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_IsActive = new ModelColumn<>(I_RV_ReceiptLogistics.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Planned.
	 * Indicates whether the row is backed by a delivery planning (planned) or only by a receipt schedule with no delivery planning (unplanned).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPlanned (boolean IsPlanned);

	/**
	 * Get Planned.
	 * Indicates whether the row is backed by a delivery planning (planned) or only by a receipt schedule with no delivery planning (unplanned).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPlanned();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_IsPlanned = new ModelColumn<>(I_RV_ReceiptLogistics.class, "IsPlanned", null);
	String COLUMNNAME_IsPlanned = "IsPlanned";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	ModelColumn<I_RV_ReceiptLogistics, org.compiere.model.I_M_Delivery_Planning> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_RV_ReceiptLogistics.class, "M_Delivery_Planning_ID", org.compiere.model.I_M_Delivery_Planning.class);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ReceiptSchedule_ID();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_M_ReceiptSchedule_ID = new ModelColumn<>(I_RV_ReceiptLogistics.class, "M_ReceiptSchedule_ID", null);
	String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_POReference = new ModelColumn<>(I_RV_ReceiptLogistics.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (@Nullable BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_RV_ReceiptLogistics.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Receipt Logistics.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRV_ReceiptLogistics_ID (int RV_ReceiptLogistics_ID);

	/**
	 * Get Receipt Logistics.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRV_ReceiptLogistics_ID();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_RV_ReceiptLogistics_ID = new ModelColumn<>(I_RV_ReceiptLogistics.class, "RV_ReceiptLogistics_ID", null);
	String COLUMNNAME_RV_ReceiptLogistics_ID = "RV_ReceiptLogistics_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_Updated = new ModelColumn<>(I_RV_ReceiptLogistics.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	ModelColumn<I_RV_ReceiptLogistics, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_RV_ReceiptLogistics.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
