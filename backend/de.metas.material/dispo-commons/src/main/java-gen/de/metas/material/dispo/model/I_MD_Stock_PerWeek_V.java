package de.metas.material.dispo.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MD_Stock_PerWeek_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Stock_PerWeek_V 
{

	String Table_Name = "MD_Stock_PerWeek_V";

//	/** AD_Table_ID=542612 */
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
	 * Set Stock per week (ID).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Stock_PerWeek_V_ID (int MD_Stock_PerWeek_V_ID);

	/**
	 * Get Stock per week (ID).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Stock_PerWeek_V_ID();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_MD_Stock_PerWeek_V_ID = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "MD_Stock_PerWeek_V_ID", null);
	String COLUMNNAME_MD_Stock_PerWeek_V_ID = "MD_Stock_PerWeek_V_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Available Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyATP (@Nullable BigDecimal QtyATP);

	/**
	 * Get Available Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyATP();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_QtyATP = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "QtyATP", null);
	String COLUMNNAME_QtyATP = "QtyATP";

	/**
	 * Set ATP Begin.
	 * Available-to-Promise quantity at the beginning of the week. Reflects all material streams.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyATPBegin (@Nullable BigDecimal QtyATPBegin);

	/**
	 * Get ATP Begin.
	 * Available-to-Promise quantity at the beginning of the week. Reflects all material streams.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyATPBegin();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_QtyATPBegin = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "QtyATPBegin", null);
	String COLUMNNAME_QtyATPBegin = "QtyATPBegin";

	/**
	 * Set Expected Receipts.
	 * Expected inbound quantity in this week.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyExpectedReceipts (@Nullable BigDecimal QtyExpectedReceipts);

	/**
	 * Get Expected Receipts.
	 * Expected inbound quantity in this week.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyExpectedReceipts();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_QtyExpectedReceipts = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "QtyExpectedReceipts", null);
	String COLUMNNAME_QtyExpectedReceipts = "QtyExpectedReceipts";

	/**
	 * Set Expected Shipments.
	 * Expected outbound quantity in this week.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyExpectedShipments (@Nullable BigDecimal QtyExpectedShipments);

	/**
	 * Get Expected Shipments.
	 * Expected outbound quantity in this week.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyExpectedShipments();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_QtyExpectedShipments = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "QtyExpectedShipments", null);
	String COLUMNNAME_QtyExpectedShipments = "QtyExpectedShipments";

	/**
	 * Set Week start.
	 * Monday of the ISO calendar week.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWeekStartDate (java.sql.Timestamp WeekStartDate);

	/**
	 * Get Week start.
	 * Monday of the ISO calendar week.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getWeekStartDate();

	ModelColumn<I_MD_Stock_PerWeek_V, Object> COLUMN_WeekStartDate = new ModelColumn<>(I_MD_Stock_PerWeek_V.class, "WeekStartDate", null);
	String COLUMNNAME_WeekStartDate = "WeekStartDate";
}
