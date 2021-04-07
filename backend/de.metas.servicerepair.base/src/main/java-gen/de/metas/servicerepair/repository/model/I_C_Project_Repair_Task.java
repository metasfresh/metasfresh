package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Repair_Task
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Repair_Task 
{

	String Table_Name = "C_Project_Repair_Task";

//	/** AD_Table_ID=541563 */
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
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Repair Task.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Repair_Task_ID (int C_Project_Repair_Task_ID);

	/**
	 * Get Project Repair Task.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Repair_Task_ID();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_C_Project_Repair_Task_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "C_Project_Repair_Task_ID", null);
	String COLUMNNAME_C_Project_Repair_Task_ID = "C_Project_Repair_Task_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Repair_Task.class, "Created", null);
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
	 * Set Customer Return.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerReturn_InOut_ID (int CustomerReturn_InOut_ID);

	/**
	 * Get Customer Return.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCustomerReturn_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getCustomerReturn_InOut();

	void setCustomerReturn_InOut(@Nullable org.compiere.model.I_M_InOut CustomerReturn_InOut);

	ModelColumn<I_C_Project_Repair_Task, org.compiere.model.I_M_InOut> COLUMN_CustomerReturn_InOut_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "CustomerReturn_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_CustomerReturn_InOut_ID = "CustomerReturn_InOut_ID";

	/**
	 * Set Customer Return Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerReturn_InOutLine_ID (int CustomerReturn_InOutLine_ID);

	/**
	 * Get Customer Return Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCustomerReturn_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getCustomerReturn_InOutLine();

	void setCustomerReturn_InOutLine(@Nullable org.compiere.model.I_M_InOutLine CustomerReturn_InOutLine);

	ModelColumn<I_C_Project_Repair_Task, org.compiere.model.I_M_InOutLine> COLUMN_CustomerReturn_InOutLine_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "CustomerReturn_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_CustomerReturn_InOutLine_ID = "CustomerReturn_InOutLine_ID";

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

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Repair_Task.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Repair Order Done.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRepairOrderDone (boolean IsRepairOrderDone);

	/**
	 * Get Repair Order Done.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRepairOrderDone();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_IsRepairOrderDone = new ModelColumn<>(I_C_Project_Repair_Task.class, "IsRepairOrderDone", null);
	String COLUMNNAME_IsRepairOrderDone = "IsRepairOrderDone";

	/**
	 * Set Warranty Case.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWarrantyCase (boolean IsWarrantyCase);

	/**
	 * Get Warranty Case.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWarrantyCase();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_IsWarrantyCase = new ModelColumn<>(I_C_Project_Repair_Task.class, "IsWarrantyCase", null);
	String COLUMNNAME_IsWarrantyCase = "IsWarrantyCase";

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

	ModelColumn<I_C_Project_Repair_Task, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * Set Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyConsumed (@Nullable BigDecimal QtyConsumed);

	/**
	 * Get Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyConsumed();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_QtyConsumed = new ModelColumn<>(I_C_Project_Repair_Task.class, "QtyConsumed", null);
	String COLUMNNAME_QtyConsumed = "QtyConsumed";

	/**
	 * Set Qty Required.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyRequired (@Nullable BigDecimal QtyRequired);

	/**
	 * Get Qty Required.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyRequired();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_QtyRequired = new ModelColumn<>(I_C_Project_Repair_Task.class, "QtyRequired", null);
	String COLUMNNAME_QtyRequired = "QtyRequired";

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

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_QtyReserved = new ModelColumn<>(I_C_Project_Repair_Task.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Repair Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRepair_Order_ID (int Repair_Order_ID);

	/**
	 * Get Repair Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRepair_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getRepair_Order();

	void setRepair_Order(@Nullable org.eevolution.model.I_PP_Order Repair_Order);

	ModelColumn<I_C_Project_Repair_Task, org.eevolution.model.I_PP_Order> COLUMN_Repair_Order_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "Repair_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_Repair_Order_ID = "Repair_Order_ID";

	/**
	 * Set Repair CU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRepair_VHU_ID (int Repair_VHU_ID);

	/**
	 * Get Repair CU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRepair_VHU_ID();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_Repair_VHU_ID = new ModelColumn<>(I_C_Project_Repair_Task.class, "Repair_VHU_ID", null);
	String COLUMNNAME_Repair_VHU_ID = "Repair_VHU_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_Status = new ModelColumn<>(I_C_Project_Repair_Task.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_Type = new ModelColumn<>(I_C_Project_Repair_Task.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_Repair_Task, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Repair_Task.class, "Updated", null);
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
