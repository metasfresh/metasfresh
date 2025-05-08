package org.eevolution.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for PP_Order_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_Candidate 
{

	String Table_Name = "PP_Order_Candidate";

//	/** AD_Table_ID=541913 */
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
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
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

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Candidate.class, "Created", null);
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date Promised.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDatePromised();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_DatePromised = new ModelColumn<>(I_PP_Order_Candidate.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateStartSchedule();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_DateStartSchedule = new ModelColumn<>(I_PP_Order_Candidate.class, "DateStartSchedule", null);
	String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_IsClosed = new ModelColumn<>(I_PP_Order_Candidate.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Maturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMaturing (boolean IsMaturing);

	/**
	 * Get Maturing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMaturing();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_IsMaturing = new ModelColumn<>(I_PP_Order_Candidate.class, "IsMaturing", null);
	String COLUMNNAME_IsMaturing = "IsMaturing";

	/**
	 * Set Simulated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulated (boolean IsSimulated);

	/**
	 * Get Simulated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulated();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_IsSimulated = new ModelColumn<>(I_PP_Order_Candidate.class, "IsSimulated", null);
	String COLUMNNAME_IsSimulated = "IsSimulated";

	/**
	 * Set Issue HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssue_HU_ID (int Issue_HU_ID);

	/**
	 * Get Issue HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIssue_HU_ID();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_Issue_HU_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "Issue_HU_ID", null);
	String COLUMNNAME_Issue_HU_ID = "Issue_HU_ID";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Maturing Configuration .
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_ID (int M_Maturing_Configuration_ID);

	/**
	 * Get Maturing Configuration .
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_ID();

	@Nullable org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration();

	void setM_Maturing_Configuration(@Nullable org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration);

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_M_Maturing_Configuration> COLUMN_M_Maturing_Configuration_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "M_Maturing_Configuration_ID", org.compiere.model.I_M_Maturing_Configuration.class);
	String COLUMNNAME_M_Maturing_Configuration_ID = "M_Maturing_Configuration_ID";

	/**
	 * Set Maturing Products Allocation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Maturing_Configuration_Line_ID (int M_Maturing_Configuration_Line_ID);

	/**
	 * Get Maturing Products Allocation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Maturing_Configuration_Line_ID();

	@Nullable org.compiere.model.I_M_Maturing_Configuration_Line getM_Maturing_Configuration_Line();

	void setM_Maturing_Configuration_Line(@Nullable org.compiere.model.I_M_Maturing_Configuration_Line M_Maturing_Configuration_Line);

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_M_Maturing_Configuration_Line> COLUMN_M_Maturing_Configuration_Line_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "M_Maturing_Configuration_Line_ID", org.compiere.model.I_M_Maturing_Configuration_Line.class);
	String COLUMNNAME_M_Maturing_Configuration_Line_ID = "M_Maturing_Configuration_Line_ID";

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
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Number of Resources to Process.
	 * Required number of resources to process the the open qty
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setNumberOfResources_ToProcess (int NumberOfResources_ToProcess);

	/**
	 * Get Number of Resources to Process.
	 * Required number of resources to process the the open qty
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getNumberOfResources_ToProcess();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_NumberOfResources_ToProcess = new ModelColumn<>(I_PP_Order_Candidate.class, "NumberOfResources_ToProcess", null);
	String COLUMNNAME_NumberOfResources_ToProcess = "NumberOfResources_ToProcess";

	/**
	 * Set Manufacturing candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Candidate_ID (int PP_Order_Candidate_ID);

	/**
	 * Get Manufacturing candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Candidate_ID();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_PP_Order_Candidate_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "PP_Order_Candidate_ID", null);
	String COLUMNNAME_PP_Order_Candidate_ID = "PP_Order_Candidate_ID";

	/**
	 * Set BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_PP_Order_Candidate, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_Planning_ID();

	String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

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

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Order_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (@Nullable BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_QtyEntered = new ModelColumn<>(I_PP_Order_Candidate.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Quantity Processed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyProcessed (@Nullable BigDecimal QtyProcessed);

	/**
	 * Get Quantity Processed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyProcessed();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_QtyProcessed = new ModelColumn<>(I_PP_Order_Candidate.class, "QtyProcessed", null);
	String COLUMNNAME_QtyProcessed = "QtyProcessed";

	/**
	 * Set Quantity To Process.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToProcess (@Nullable BigDecimal QtyToProcess);

	/**
	 * Get Quantity To Process.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToProcess();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_QtyToProcess = new ModelColumn<>(I_PP_Order_Candidate.class, "QtyToProcess", null);
	String COLUMNNAME_QtyToProcess = "QtyToProcess";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_SeqNo = new ModelColumn<>(I_PP_Order_Candidate.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Candidate.class, "Updated", null);
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
	 * Set Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkStation_ID (int WorkStation_ID);

	/**
	 * Get Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWorkStation_ID();

	@Nullable org.compiere.model.I_S_Resource getWorkStation();

	void setWorkStation(@Nullable org.compiere.model.I_S_Resource WorkStation);

	ModelColumn<I_PP_Order_Candidate, org.compiere.model.I_S_Resource> COLUMN_WorkStation_ID = new ModelColumn<>(I_PP_Order_Candidate.class, "WorkStation_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_WorkStation_ID = "WorkStation_ID";
}
