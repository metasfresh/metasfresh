package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_LUTU_Configuration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_LUTU_Configuration 
{

	String Table_Name = "M_HU_LUTU_Configuration";

//	/** AD_Table_ID=540605 */
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
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUStatus (@Nullable java.lang.String HUStatus);

	/**
	 * Get Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUStatus();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_HUStatus = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "HUStatus", null);
	String COLUMNNAME_HUStatus = "HUStatus";

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

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Unendliche CU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInfiniteQtyCU (boolean IsInfiniteQtyCU);

	/**
	 * Get Unendliche CU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInfiniteQtyCU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyCU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyCU", null);
	String COLUMNNAME_IsInfiniteQtyCU = "IsInfiniteQtyCU";

	/**
	 * Set Unendliche LU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInfiniteQtyLU (boolean IsInfiniteQtyLU);

	/**
	 * Get Unendliche LU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInfiniteQtyLU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyLU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyLU", null);
	String COLUMNNAME_IsInfiniteQtyLU = "IsInfiniteQtyLU";

	/**
	 * Set Unendliche TU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInfiniteQtyTU (boolean IsInfiniteQtyTU);

	/**
	 * Get Unendliche TU Menge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInfiniteQtyTU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_IsInfiniteQtyTU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "IsInfiniteQtyTU", null);
	String COLUMNNAME_IsInfiniteQtyTU = "IsInfiniteQtyTU";

	/**
	 * Set Packing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Packing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_LUTU_Configuration_ID();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_M_HU_LUTU_Configuration_ID = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "M_HU_LUTU_Configuration_ID", null);
	String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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
	 * Set Packvorschrift (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_LU_HU_PI_ID (int M_LU_HU_PI_ID);

	/**
	 * Get Packvorschrift (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_LU_HU_PI_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI getM_LU_HU_PI();

	void setM_LU_HU_PI(@Nullable de.metas.handlingunits.model.I_M_HU_PI M_LU_HU_PI);

	ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_LU_HU_PI_ID = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "M_LU_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";

	/**
	 * Set Packvorschrift Position (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_LU_HU_PI_Item_ID (int M_LU_HU_PI_Item_ID);

	/**
	 * Get Packvorschrift Position (LU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_LU_HU_PI_Item_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI_Item getM_LU_HU_PI_Item();

	void setM_LU_HU_PI_Item(@Nullable de.metas.handlingunits.model.I_M_HU_PI_Item M_LU_HU_PI_Item);

	ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_LU_HU_PI_Item_ID = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "M_LU_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	String COLUMNNAME_M_LU_HU_PI_Item_ID = "M_LU_HU_PI_Item_ID";

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
	 * Set Packing Instruction (TU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_TU_HU_PI_ID (int M_TU_HU_PI_ID);

	/**
	 * Get Packing Instruction (TU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_TU_HU_PI_ID();

	de.metas.handlingunits.model.I_M_HU_PI getM_TU_HU_PI();

	void setM_TU_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_TU_HU_PI);

	ModelColumn<I_M_HU_LUTU_Configuration, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_TU_HU_PI_ID = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "M_TU_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_M_TU_HU_PI_ID = "M_TU_HU_PI_ID";

	/**
	 * Set Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyCUsPerTU (BigDecimal QtyCUsPerTU);

	/**
	 * Get Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCUsPerTU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyCUsPerTU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "QtyCUsPerTU", null);
	String COLUMNNAME_QtyCUsPerTU = "QtyCUsPerTU";

	/**
	 * Set Number of LUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyLU (BigDecimal QtyLU);

	/**
	 * Get Number of LUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyLU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyLU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "QtyLU", null);
	String COLUMNNAME_QtyLU = "QtyLU";

	/**
	 * Set Number of TUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyTU (BigDecimal QtyTU);

	/**
	 * Get Number of TUs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyTU();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_QtyTU = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "QtyTU", null);
	String COLUMNNAME_QtyTU = "QtyTU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_LUTU_Configuration, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_LUTU_Configuration.class, "Updated", null);
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
