package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for M_QtyReservation
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_M_QtyReservation
{
	String Table_Name = "M_QtyReservation";

	/** AD_Table_ID=542583 */
	int Table_ID = MTable.getTable_ID(Table_Name);

	/**
	 * Get Client.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Organisation.
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributesKey(@Nullable String AttributesKey);

	/**
	 * Get AttributesKey (technical).
	 */
	@Nullable
	String getAttributesKey();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_AttributesKey = new ModelColumn<>(I_M_QtyReservation.class, "AttributesKey", null);
	String COLUMNNAME_AttributesKey = "AttributesKey";

	/**
	 * Set Vendor BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Vendor_ID(int C_BPartner_Vendor_ID);

	/**
	 * Get Vendor BPartner.
	 */
	int getC_BPartner_Vendor_ID();

	String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Set Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID(int C_OrderLine_ID);

	/**
	 * Get Order Line.
	 */
	int getC_OrderLine_ID();

	ModelColumn<I_M_QtyReservation, I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_QtyReservation.class, "C_OrderLine_ID", I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set UOM.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID(int C_UOM_ID);

	/**
	 * Get UOM.
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Created.
	 */
	Timestamp getCreated();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_Created = new ModelColumn<>(I_M_QtyReservation.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get CreatedBy.
	 */
	int getCreatedBy();

	ModelColumn<I_M_QtyReservation, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new ModelColumn<>(I_M_QtyReservation.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date Promised.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised(@Nullable Timestamp DatePromised);

	/**
	 * Get Date Promised.
	 */
	@Nullable
	Timestamp getDatePromised();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_DatePromised = new ModelColumn<>(I_M_QtyReservation.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Active.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 */
	boolean isActive();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_IsActive = new ModelColumn<>(I_M_QtyReservation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID(int M_Product_ID);

	/**
	 * Get Product.
	 */
	int getM_Product_ID();

	ModelColumn<I_M_QtyReservation, I_M_Product> COLUMN_M_Product_ID = new ModelColumn<>(I_M_QtyReservation.class, "M_Product_ID", I_M_Product.class);
	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Qty Reservation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_QtyReservation_ID(int M_QtyReservation_ID);

	/**
	 * Get Qty Reservation.
	 */
	int getM_QtyReservation_ID();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_M_QtyReservation_ID = new ModelColumn<>(I_M_QtyReservation.class, "M_QtyReservation_ID", null);
	String COLUMNNAME_M_QtyReservation_ID = "M_QtyReservation_ID";

	/**
	 * Set Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID(int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 */
	int getM_Warehouse_ID();

	ModelColumn<I_M_QtyReservation, I_M_Warehouse> COLUMN_M_Warehouse_ID = new ModelColumn<>(I_M_QtyReservation.class, "M_Warehouse_ID", I_M_Warehouse.class);
	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Qty (CU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty(BigDecimal Qty);

	/**
	 * Get Qty (CU).
	 */
	BigDecimal getQty();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_Qty = new ModelColumn<>(I_M_QtyReservation.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set QtyTU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyTU(BigDecimal QtyTU);

	/**
	 * Get QtyTU.
	 */
	BigDecimal getQtyTU();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_QtyTU = new ModelColumn<>(I_M_QtyReservation.class, "QtyTU", null);
	String COLUMNNAME_QtyTU = "QtyTU";

	/**
	 * Set Supply Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSupplyType(String SupplyType);

	/**
	 * Get Supply Type.
	 */
	String getSupplyType();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_SupplyType = new ModelColumn<>(I_M_QtyReservation.class, "SupplyType", null);
	String COLUMNNAME_SupplyType = "SupplyType";

	/**
	 * Get Updated.
	 */
	Timestamp getUpdated();

	ModelColumn<I_M_QtyReservation, Object> COLUMN_Updated = new ModelColumn<>(I_M_QtyReservation.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get UpdatedBy.
	 */
	int getUpdatedBy();

	ModelColumn<I_M_QtyReservation, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new ModelColumn<>(I_M_QtyReservation.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
