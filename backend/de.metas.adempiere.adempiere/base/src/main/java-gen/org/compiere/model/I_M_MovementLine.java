package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_MovementLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_MovementLine 
{

	String Table_Name = "M_MovementLine";

//	/** AD_Table_ID=324 */
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
	 * Set Activity From.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ActivityFrom_ID (int C_ActivityFrom_ID);

	/**
	 * Get Activity From.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ActivityFrom_ID();

	String COLUMNNAME_C_ActivityFrom_ID = "C_ActivityFrom_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Qty Confirmed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConfirmedQty (@Nullable BigDecimal ConfirmedQty);

	/**
	 * Get Qty Confirmed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getConfirmedQty();

	ModelColumn<I_M_MovementLine, Object> COLUMN_ConfirmedQty = new ModelColumn<>(I_M_MovementLine.class, "ConfirmedQty", null);
	String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Created = new ModelColumn<>(I_M_MovementLine.class, "Created", null);
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_Alternative_ID (int DD_OrderLine_Alternative_ID);

	/**
	 * Get Distribution Order Line Alternative.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_Alternative_ID();

	@Nullable org.eevolution.model.I_DD_OrderLine_Alternative getDD_OrderLine_Alternative();

	void setDD_OrderLine_Alternative(@Nullable org.eevolution.model.I_DD_OrderLine_Alternative DD_OrderLine_Alternative);

	ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine_Alternative> COLUMN_DD_OrderLine_Alternative_ID = new ModelColumn<>(I_M_MovementLine.class, "DD_OrderLine_Alternative_ID", org.eevolution.model.I_DD_OrderLine_Alternative.class);
	String COLUMNNAME_DD_OrderLine_Alternative_ID = "DD_OrderLine_Alternative_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	@Nullable org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(@Nullable org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_M_MovementLine, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_M_MovementLine.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Description = new ModelColumn<>(I_M_MovementLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_MovementLine, Object> COLUMN_IsActive = new ModelColumn<>(I_M_MovementLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Line = new ModelColumn<>(I_M_MovementLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_MovementLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstanceTo_ID (int M_AttributeSetInstanceTo_ID);

	/**
	 * Get Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstanceTo_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstanceTo();

	void setM_AttributeSetInstanceTo(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstanceTo);

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstanceTo_ID = new ModelColumn<>(I_M_MovementLine.class, "M_AttributeSetInstanceTo_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstanceTo_ID = "M_AttributeSetInstanceTo_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_MovementLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Locator to.
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_LocatorTo_ID (int M_LocatorTo_ID);

	/**
	 * Get Locator to.
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_LocatorTo_ID();

	String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";

	/**
	 * Set Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Movement_ID (int M_Movement_ID);

	/**
	 * Get Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Movement_ID();

	org.compiere.model.I_M_Movement getM_Movement();

	void setM_Movement(org.compiere.model.I_M_Movement M_Movement);

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_Movement> COLUMN_M_Movement_ID = new ModelColumn<>(I_M_MovementLine.class, "M_Movement_ID", org.compiere.model.I_M_Movement.class);
	String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/**
	 * Set Move Line.
	 * Inventory Move document Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_MovementLine_ID (int M_MovementLine_ID);

	/**
	 * Get Move Line.
	 * Inventory Move document Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_MovementLine_ID();

	ModelColumn<I_M_MovementLine, Object> COLUMN_M_MovementLine_ID = new ModelColumn<>(I_M_MovementLine.class, "M_MovementLine_ID", null);
	String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementQty (BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMovementQty();

	ModelColumn<I_M_MovementLine, Object> COLUMN_MovementQty = new ModelColumn<>(I_M_MovementLine.class, "MovementQty", null);
	String COLUMNNAME_MovementQty = "MovementQty";

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
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_MovementLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Processed = new ModelColumn<>(I_M_MovementLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversalLine_ID();

	@Nullable org.compiere.model.I_M_MovementLine getReversalLine();

	void setReversalLine(@Nullable org.compiere.model.I_M_MovementLine ReversalLine);

	ModelColumn<I_M_MovementLine, org.compiere.model.I_M_MovementLine> COLUMN_ReversalLine_ID = new ModelColumn<>(I_M_MovementLine.class, "ReversalLine_ID", org.compiere.model.I_M_MovementLine.class);
	String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/**
	 * Set Qty Scrapped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScrappedQty (@Nullable BigDecimal ScrappedQty);

	/**
	 * Get Qty Scrapped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getScrappedQty();

	ModelColumn<I_M_MovementLine, Object> COLUMN_ScrappedQty = new ModelColumn<>(I_M_MovementLine.class, "ScrappedQty", null);
	String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/**
	 * Set Target Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTargetQty (BigDecimal TargetQty);

	/**
	 * Get Target Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTargetQty();

	ModelColumn<I_M_MovementLine, Object> COLUMN_TargetQty = new ModelColumn<>(I_M_MovementLine.class, "TargetQty", null);
	String COLUMNNAME_TargetQty = "TargetQty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Updated = new ModelColumn<>(I_M_MovementLine.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getValue();

	ModelColumn<I_M_MovementLine, Object> COLUMN_Value = new ModelColumn<>(I_M_MovementLine.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
