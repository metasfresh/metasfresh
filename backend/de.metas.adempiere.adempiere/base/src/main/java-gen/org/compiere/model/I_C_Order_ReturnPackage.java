package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Order_ReturnPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Order_ReturnPackage 
{

	String Table_Name = "C_Order_ReturnPackage";

//	/** AD_Table_ID=542618 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	ModelColumn<I_C_Order_ReturnPackage, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Order_ReturnPackage.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Return Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ReturnPackage_ID (int C_Order_ReturnPackage_ID);

	/**
	 * Get Return Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ReturnPackage_ID();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_C_Order_ReturnPackage_ID = new ModelColumn<>(I_C_Order_ReturnPackage.class, "C_Order_ReturnPackage_ID", null);
	String COLUMNNAME_C_Order_ReturnPackage_ID = "C_Order_ReturnPackage_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_Created = new ModelColumn<>(I_C_Order_ReturnPackage.class, "Created", null);
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

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Order_ReturnPackage.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Pallet.
	 * Pallet type of the return package (EUR or H1).
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPalletType (@Nullable java.lang.String PalletType);

	/**
	 * Get Pallet.
	 * Pallet type of the return package (EUR or H1).
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPalletType();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_PalletType = new ModelColumn<>(I_C_Order_ReturnPackage.class, "PalletType", null);
	String COLUMNNAME_PalletType = "PalletType";

	/**
	 * Set delivered.
	 * Delivered quantity of the return package.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredLU (@Nullable BigDecimal QtyDeliveredLU);

	/**
	 * Get delivered.
	 * Delivered quantity of the return package.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredLU();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_QtyDeliveredLU = new ModelColumn<>(I_C_Order_ReturnPackage.class, "QtyDeliveredLU", null);
	String COLUMNNAME_QtyDeliveredLU = "QtyDeliveredLU";

	/**
	 * Set returned.
	 * Returned quantity of the return package.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReturnedLU (@Nullable BigDecimal QtyReturnedLU);

	/**
	 * Get returned.
	 * Returned quantity of the return package.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReturnedLU();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_QtyReturnedLU = new ModelColumn<>(I_C_Order_ReturnPackage.class, "QtyReturnedLU", null);
	String COLUMNNAME_QtyReturnedLU = "QtyReturnedLU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Order_ReturnPackage, Object> COLUMN_Updated = new ModelColumn<>(I_C_Order_ReturnPackage.class, "Updated", null);
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
