package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for RV_M_Material_Tracking_HU_Details
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_M_Material_Tracking_HU_Details 
{

	String Table_Name = "RV_M_Material_Tracking_HU_Details";

//	/** AD_Table_ID=540682 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Created = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "Created", null);
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

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUStatus (@Nullable java.lang.String HUStatus);

	/**
	 * Get Gebinde Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUStatus();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_HUStatus = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "HUStatus", null);
	String COLUMNNAME_HUStatus = "HUStatus";

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

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_IsActive = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Waschprobe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsQualityInspection (boolean IsQualityInspection);

	/**
	 * Get Waschprobe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQualityInspection();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_IsQualityInspection = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "IsQualityInspection", null);
	String COLUMNNAME_IsQualityInspection = "IsQualityInspection";

	/**
	 * Set Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLot (@Nullable java.lang.String Lot);

	/**
	 * Get Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLot();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Lot = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "Lot", null);
	String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set m_inout_receipt_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_Receipt_ID (int M_InOut_Receipt_ID);

	/**
	 * Get m_inout_receipt_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_Receipt_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut_Receipt();

	void setM_InOut_Receipt(@Nullable org.compiere.model.I_M_InOut M_InOut_Receipt);

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_InOut> COLUMN_M_InOut_Receipt_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "M_InOut_Receipt_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_Receipt_ID = "M_InOut_Receipt_ID";

	/**
	 * Set m_inout_shipment_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_Shipment_ID (int M_InOut_Shipment_ID);

	/**
	 * Get m_inout_shipment_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_Shipment_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut_Shipment();

	void setM_InOut_Shipment(@Nullable org.compiere.model.I_M_InOut M_InOut_Shipment);

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.compiere.model.I_M_InOut> COLUMN_M_InOut_Shipment_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "M_InOut_Shipment_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_Shipment_ID = "M_InOut_Shipment_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Material_Tracking_ID (int M_Material_Tracking_ID);

	/**
	 * Get Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Material_Tracking_ID();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_M_Material_Tracking_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "M_Material_Tracking_ID", null);
	String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

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
	 * Set pp_order_issue_docstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Issue_DocStatus (@Nullable java.lang.String PP_Order_Issue_DocStatus);

	/**
	 * Get pp_order_issue_docstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_Order_Issue_DocStatus();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_PP_Order_Issue_DocStatus = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Issue_DocStatus", null);
	String COLUMNNAME_PP_Order_Issue_DocStatus = "PP_Order_Issue_DocStatus";

	/**
	 * Set pp_order_issue_doctype_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Issue_DocType_ID (int PP_Order_Issue_DocType_ID);

	/**
	 * Get pp_order_issue_doctype_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Issue_DocType_ID();

	String COLUMNNAME_PP_Order_Issue_DocType_ID = "PP_Order_Issue_DocType_ID";

	/**
	 * Set Zugeteilt zu Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Issue_ID (int PP_Order_Issue_ID);

	/**
	 * Get Zugeteilt zu Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Issue_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order_Issue();

	void setPP_Order_Issue(@Nullable org.eevolution.model.I_PP_Order PP_Order_Issue);

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_Issue_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Issue_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_Issue_ID = "PP_Order_Issue_ID";

	/**
	 * Set pp_order_receipt_docstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Receipt_DocStatus (@Nullable java.lang.String PP_Order_Receipt_DocStatus);

	/**
	 * Get pp_order_receipt_docstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_Order_Receipt_DocStatus();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_PP_Order_Receipt_DocStatus = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Receipt_DocStatus", null);
	String COLUMNNAME_PP_Order_Receipt_DocStatus = "PP_Order_Receipt_DocStatus";

	/**
	 * Set pp_order_receipt_doctype_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Receipt_DocType_ID (int PP_Order_Receipt_DocType_ID);

	/**
	 * Get pp_order_receipt_doctype_id.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Receipt_DocType_ID();

	String COLUMNNAME_PP_Order_Receipt_DocType_ID = "PP_Order_Receipt_DocType_ID";

	/**
	 * Set Empf. aus Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Receipt_ID (int PP_Order_Receipt_ID);

	/**
	 * Get Empf. aus Prod.-Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Receipt_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order_Receipt();

	void setPP_Order_Receipt(@Nullable org.eevolution.model.I_PP_Order PP_Order_Receipt);

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_Receipt_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "PP_Order_Receipt_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_Receipt_ID = "PP_Order_Receipt_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Qty = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set qualityinspectioncycle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityInspectionCycle (@Nullable java.lang.String QualityInspectionCycle);

	/**
	 * Get qualityinspectioncycle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQualityInspectionCycle();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_QualityInspectionCycle = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "QualityInspectionCycle", null);
	String COLUMNNAME_QualityInspectionCycle = "QualityInspectionCycle";

	/**
	 * Set RV_M_Material_Tracking_HU_Details_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRV_M_Material_Tracking_HU_Details_ID (int RV_M_Material_Tracking_HU_Details_ID);

	/**
	 * Get RV_M_Material_Tracking_HU_Details_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRV_M_Material_Tracking_HU_Details_ID();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_RV_M_Material_Tracking_HU_Details_ID = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "RV_M_Material_Tracking_HU_Details_ID", null);
	String COLUMNNAME_RV_M_Material_Tracking_HU_Details_ID = "RV_M_Material_Tracking_HU_Details_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_RV_M_Material_Tracking_HU_Details, Object> COLUMN_Updated = new ModelColumn<>(I_RV_M_Material_Tracking_HU_Details.class, "Updated", null);
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

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
