package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Repair_CostCollector
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Repair_CostCollector 
{

	String Table_Name = "C_Project_Repair_CostCollector";

//	/** AD_Table_ID=541564 */
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
	 * Set Service/Repair Project Expenses.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Repair_CostCollector_ID (int C_Project_Repair_CostCollector_ID);

	/**
	 * Get Service/Repair Project Expenses.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Repair_CostCollector_ID();

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_C_Project_Repair_CostCollector_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "C_Project_Repair_CostCollector_ID", null);
	String COLUMNNAME_C_Project_Repair_CostCollector_ID = "C_Project_Repair_CostCollector_ID";

	/**
	 * Set Project Repair Task.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_Repair_Task_ID (int C_Project_Repair_Task_ID);

	/**
	 * Get Project Repair Task.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_Repair_Task_ID();

	@Nullable de.metas.servicerepair.repository.model.I_C_Project_Repair_Task getC_Project_Repair_Task();

	void setC_Project_Repair_Task(@Nullable de.metas.servicerepair.repository.model.I_C_Project_Repair_Task C_Project_Repair_Task);

	ModelColumn<I_C_Project_Repair_CostCollector, de.metas.servicerepair.repository.model.I_C_Project_Repair_Task> COLUMN_C_Project_Repair_Task_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "C_Project_Repair_Task_ID", de.metas.servicerepair.repository.model.I_C_Project_Repair_Task.class);
	String COLUMNNAME_C_Project_Repair_Task_ID = "C_Project_Repair_Task_ID";

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

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "Created", null);
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
	 * Set From Repair Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFrom_Rapair_Order_ID (int From_Rapair_Order_ID);

	/**
	 * Get From Repair Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFrom_Rapair_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getFrom_Rapair_Order();

	void setFrom_Rapair_Order(@Nullable org.eevolution.model.I_PP_Order From_Rapair_Order);

	ModelColumn<I_C_Project_Repair_CostCollector, org.eevolution.model.I_PP_Order> COLUMN_From_Rapair_Order_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "From_Rapair_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_From_Rapair_Order_ID = "From_Rapair_Order_ID";

	/**
	 * Set From Repair Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFrom_Repair_Cost_Collector_ID (int From_Repair_Cost_Collector_ID);

	/**
	 * Get From Repair Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFrom_Repair_Cost_Collector_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getFrom_Repair_Cost_Collector();

	void setFrom_Repair_Cost_Collector(@Nullable org.eevolution.model.I_PP_Cost_Collector From_Repair_Cost_Collector);

	ModelColumn<I_C_Project_Repair_CostCollector, org.eevolution.model.I_PP_Cost_Collector> COLUMN_From_Repair_Cost_Collector_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "From_Repair_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_From_Repair_Cost_Collector_ID = "From_Repair_Cost_Collector_ID";

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

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_IsWarrantyCase = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "IsWarrantyCase", null);
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

	ModelColumn<I_C_Project_Repair_CostCollector, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
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
	 * Set Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyConsumed (BigDecimal QtyConsumed);

	/**
	 * Get Consumed Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyConsumed();

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_QtyConsumed = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "QtyConsumed", null);
	String COLUMNNAME_QtyConsumed = "QtyConsumed";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_QtyReserved = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Quotation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuotation_Order_ID (int Quotation_Order_ID);

	/**
	 * Get Quotation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQuotation_Order_ID();

	@Nullable org.compiere.model.I_C_Order getQuotation_Order();

	void setQuotation_Order(@Nullable org.compiere.model.I_C_Order Quotation_Order);

	ModelColumn<I_C_Project_Repair_CostCollector, org.compiere.model.I_C_Order> COLUMN_Quotation_Order_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "Quotation_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_Quotation_Order_ID = "Quotation_Order_ID";

	/**
	 * Set Quotation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuotation_OrderLine_ID (int Quotation_OrderLine_ID);

	/**
	 * Get Quotation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQuotation_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getQuotation_OrderLine();

	void setQuotation_OrderLine(@Nullable org.compiere.model.I_C_OrderLine Quotation_OrderLine);

	ModelColumn<I_C_Project_Repair_CostCollector, org.compiere.model.I_C_OrderLine> COLUMN_Quotation_OrderLine_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "Quotation_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_Quotation_OrderLine_ID = "Quotation_OrderLine_ID";

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

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_Type = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "Type", null);
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

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "Updated", null);
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
	 * Set CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVHU_ID (int VHU_ID);

	/**
	 * Get CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getVHU_ID();

	ModelColumn<I_C_Project_Repair_CostCollector, Object> COLUMN_VHU_ID = new ModelColumn<>(I_C_Project_Repair_CostCollector.class, "VHU_ID", null);
	String COLUMNNAME_VHU_ID = "VHU_ID";
}
