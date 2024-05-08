package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_OrderLine_Alternative
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_OrderLine_Alternative 
{

	String Table_Name = "DD_OrderLine_Alternative";

//	/** AD_Table_ID=540628 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_Created = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "Created", null);
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
	 * Set Distribution Order Line Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_Alternative_ID (int DD_OrderLine_Alternative_ID);

	/**
	 * Get Distribution Order Line Alternative.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_Alternative_ID();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_DD_OrderLine_Alternative_ID = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "DD_OrderLine_Alternative_ID", null);
	String COLUMNNAME_DD_OrderLine_Alternative_ID = "DD_OrderLine_Alternative_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_DD_OrderLine_Alternative, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_DD_OrderLine_Alternative, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty In Transit.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyInTransit (BigDecimal QtyInTransit);

	/**
	 * Get Qty In Transit.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInTransit();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_QtyInTransit = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "QtyInTransit", null);
	String COLUMNNAME_QtyInTransit = "QtyInTransit";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DD_OrderLine_Alternative, Object> COLUMN_Updated = new ModelColumn<>(I_DD_OrderLine_Alternative.class, "Updated", null);
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
