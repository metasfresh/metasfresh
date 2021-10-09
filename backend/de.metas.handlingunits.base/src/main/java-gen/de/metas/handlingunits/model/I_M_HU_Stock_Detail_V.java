package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Stock_Detail_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Stock_Detail_V 
{

	String Table_Name = "M_HU_Stock_Detail_V";

//	/** AD_Table_ID=541008 */
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
	 * Set Suchschlüssel.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValue (@Nullable java.lang.String AttributeValue);

	/**
	 * Get Suchschlüssel.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValue();

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_AttributeValue = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "AttributeValue", null);
	String COLUMNNAME_AttributeValue = "AttributeValue";

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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "Created", null);
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

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_HUStatus = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "HUStatus", null);
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

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Attribute_ID (int M_HU_Attribute_ID);

	/**
	 * Get Handling Unit Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Attribute_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute();

	void setM_HU_Attribute(@Nullable de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute);

	ModelColumn<I_M_HU_Stock_Detail_V, de.metas.handlingunits.model.I_M_HU_Attribute> COLUMN_M_HU_Attribute_ID = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "M_HU_Attribute_ID", de.metas.handlingunits.model.I_M_HU_Attribute.class);
	String COLUMNNAME_M_HU_Attribute_ID = "M_HU_Attribute_ID";

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

	ModelColumn<I_M_HU_Stock_Detail_V, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Units Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Storage_ID (int M_HU_Storage_ID);

	/**
	 * Get Handling Units Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Storage_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Storage getM_HU_Storage();

	void setM_HU_Storage(@Nullable de.metas.handlingunits.model.I_M_HU_Storage M_HU_Storage);

	ModelColumn<I_M_HU_Stock_Detail_V, de.metas.handlingunits.model.I_M_HU_Storage> COLUMN_M_HU_Storage_ID = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "M_HU_Storage_ID", de.metas.handlingunits.model.I_M_HU_Storage.class);
	String COLUMNNAME_M_HU_Storage_ID = "M_HU_Storage_ID";

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

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Stock_Detail_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Stock_Detail_V.class, "Updated", null);
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
