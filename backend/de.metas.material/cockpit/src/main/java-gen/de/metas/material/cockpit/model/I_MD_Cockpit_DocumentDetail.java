package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Cockpit_DocumentDetail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Cockpit_DocumentDetail 
{

	String Table_Name = "MD_Cockpit_DocumentDetail";

//	/** AD_Table_ID=540893 */
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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

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

	ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "Created", null);
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
	 * Set Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID);

	/**
	 * Get Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubscriptionProgress_ID();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_C_SubscriptionProgress_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "C_SubscriptionProgress_ID", null);
	String COLUMNNAME_C_SubscriptionProgress_ID = "C_SubscriptionProgress_ID";

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

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set DocumentDetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Cockpit_DocumentDetail_ID (int MD_Cockpit_DocumentDetail_ID);

	/**
	 * Get DocumentDetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Cockpit_DocumentDetail_ID();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_MD_Cockpit_DocumentDetail_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "MD_Cockpit_DocumentDetail_ID", null);
	String COLUMNNAME_MD_Cockpit_DocumentDetail_ID = "MD_Cockpit_DocumentDetail_ID";

	/**
	 * Set Material Cockpit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Cockpit_ID (int MD_Cockpit_ID);

	/**
	 * Get Material Cockpit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Cockpit_ID();

	de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit();

	void setMD_Cockpit(de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit);

	ModelColumn<I_MD_Cockpit_DocumentDetail, de.metas.material.cockpit.model.I_MD_Cockpit> COLUMN_MD_Cockpit_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "MD_Cockpit_ID", de.metas.material.cockpit.model.I_MD_Cockpit.class);
	String COLUMNNAME_MD_Cockpit_ID = "MD_Cockpit_ID";

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

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_M_ReceiptSchedule_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "M_ReceiptSchedule_ID", null);
	String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

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

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (@Nullable BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_QtyReserved = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Cockpit_DocumentDetail.class, "Updated", null);
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
