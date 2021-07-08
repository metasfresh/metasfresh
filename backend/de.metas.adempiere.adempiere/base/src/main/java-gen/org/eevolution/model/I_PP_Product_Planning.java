package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Product_Planning
 *  @author metasfresh (generated) 
 */
public interface I_PP_Product_Planning 
{

	String Table_Name = "PP_Product_Planning";

//	/** AD_Table_ID=53020 */
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
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_Created = new ModelColumn<>(I_PP_Product_Planning.class, "Created", null);
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
	 * Set Network Distribution.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID);

	/**
	 * Get Network Distribution.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistribution_ID();

	@Nullable org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution();

	void setDD_NetworkDistribution(@Nullable org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution);

	ModelColumn<I_PP_Product_Planning, org.eevolution.model.I_DD_NetworkDistribution> COLUMN_DD_NetworkDistribution_ID = new ModelColumn<>(I_PP_Product_Planning.class, "DD_NetworkDistribution_ID", org.eevolution.model.I_DD_NetworkDistribution.class);
	String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryTime_Promised (@Nullable BigDecimal DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDeliveryTime_Promised();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_DeliveryTime_Promised = new ModelColumn<>(I_PP_Product_Planning.class, "DeliveryTime_Promised", null);
	String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

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

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Product_Planning.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attributabhängig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAttributeDependant (boolean IsAttributeDependant);

	/**
	 * Get Attributabhängig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAttributeDependant();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsAttributeDependant = new ModelColumn<>(I_PP_Product_Planning.class, "IsAttributeDependant", null);
	String COLUMNNAME_IsAttributeDependant = "IsAttributeDependant";

	/**
	 * Set Create Plan.
	 * Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreatePlan (boolean IsCreatePlan);

	/**
	 * Get Create Plan.
	 * Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreatePlan();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsCreatePlan = new ModelColumn<>(I_PP_Product_Planning.class, "IsCreatePlan", null);
	String COLUMNNAME_IsCreatePlan = "IsCreatePlan";

	/**
	 * Set Complete Document.
	 * Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDocComplete (boolean IsDocComplete);

	/**
	 * Get Complete Document.
	 * Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDocComplete();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsDocComplete = new ModelColumn<>(I_PP_Product_Planning.class, "IsDocComplete", null);
	String COLUMNNAME_IsDocComplete = "IsDocComplete";

	/**
	 * Set Wird produziert.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsManufactured (@Nullable java.lang.String IsManufactured);

	/**
	 * Get Wird produziert.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsManufactured();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsManufactured = new ModelColumn<>(I_PP_Product_Planning.class, "IsManufactured", null);
	String COLUMNNAME_IsManufactured = "IsManufactured";

	/**
	 * Set Is MPS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMPS (boolean IsMPS);

	/**
	 * Get Is MPS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMPS();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsMPS = new ModelColumn<>(I_PP_Product_Planning.class, "IsMPS", null);
	String COLUMNNAME_IsMPS = "IsMPS";

	/**
	 * Set Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible);

	/**
	 * Get Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickDirectlyIfFeasible();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsPickDirectlyIfFeasible = new ModelColumn<>(I_PP_Product_Planning.class, "IsPickDirectlyIfFeasible", null);
	String COLUMNNAME_IsPickDirectlyIfFeasible = "IsPickDirectlyIfFeasible";

	/**
	 * Set Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickingOrder (boolean IsPickingOrder);

	/**
	 * Get Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickingOrder();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsPickingOrder = new ModelColumn<>(I_PP_Product_Planning.class, "IsPickingOrder", null);
	String COLUMNNAME_IsPickingOrder = "IsPickingOrder";

	/**
	 * Set Eingekauft.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPurchased (@Nullable java.lang.String IsPurchased);

	/**
	 * Get Eingekauft.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsPurchased();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsPurchased = new ModelColumn<>(I_PP_Product_Planning.class, "IsPurchased", null);
	String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Trading Product.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTraded (@Nullable java.lang.String IsTraded);

	/**
	 * Get Trading Product.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsTraded();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsTraded = new ModelColumn<>(I_PP_Product_Planning.class, "IsTraded", null);
	String COLUMNNAME_IsTraded = "IsTraded";

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

	ModelColumn<I_PP_Product_Planning, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Product_Planning.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Max. quantity per manufactoring order.
	 * Optional;
 steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMaxManufacturedQtyPerOrder (@Nullable BigDecimal MaxManufacturedQtyPerOrder);

	/**
	 * Get Max. quantity per manufactoring order.
	 * Optional;
 steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMaxManufacturedQtyPerOrder();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_MaxManufacturedQtyPerOrder = new ModelColumn<>(I_PP_Product_Planning.class, "MaxManufacturedQtyPerOrder", null);
	String COLUMNNAME_MaxManufacturedQtyPerOrder = "MaxManufacturedQtyPerOrder";

	/**
	 * Set Unit of measurement.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMaxManufacturedQtyPerOrder_UOM_ID (int MaxManufacturedQtyPerOrder_UOM_ID);

	/**
	 * Get Unit of measurement.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMaxManufacturedQtyPerOrder_UOM_ID();

	String COLUMNNAME_MaxManufacturedQtyPerOrder_UOM_ID = "MaxManufacturedQtyPerOrder_UOM_ID";

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
	 * Set Product Planning Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_PlanningSchema_ID (int M_Product_PlanningSchema_ID);

	/**
	 * Get Product Planning Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_PlanningSchema_ID();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_M_Product_PlanningSchema_ID = new ModelColumn<>(I_PP_Product_Planning.class, "M_Product_PlanningSchema_ID", null);
	String COLUMNNAME_M_Product_PlanningSchema_ID = "M_Product_PlanningSchema_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set OnMaterialReceiptWithDestWarehouse.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnMaterialReceiptWithDestWarehouse (@Nullable java.lang.String OnMaterialReceiptWithDestWarehouse);

	/**
	 * Get OnMaterialReceiptWithDestWarehouse.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOnMaterialReceiptWithDestWarehouse();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_OnMaterialReceiptWithDestWarehouse = new ModelColumn<>(I_PP_Product_Planning.class, "OnMaterialReceiptWithDestWarehouse", null);
	String COLUMNNAME_OnMaterialReceiptWithDestWarehouse = "OnMaterialReceiptWithDestWarehouse";

	/**
	 * Set Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlanner_ID (int Planner_ID);

	/**
	 * Get Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPlanner_ID();

	String COLUMNNAME_Planner_ID = "Planner_ID";

	/**
	 * Set BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(@Nullable org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_PP_Product_Planning, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_PP_Product_Planning.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set Product Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Product_Planning_ID();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_PP_Product_Planning_ID = new ModelColumn<>(I_PP_Product_Planning.class, "PP_Product_Planning_ID", null);
	String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_SeqNo = new ModelColumn<>(I_PP_Product_Planning.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	@Nullable org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(@Nullable org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_PP_Product_Planning, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_PP_Product_Planning.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStorageAttributesKey (@Nullable java.lang.String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStorageAttributesKey();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(I_PP_Product_Planning.class, "StorageAttributesKey", null);
	String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";

	/**
	 * Set Transfert Time.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransfertTime (@Nullable BigDecimal TransfertTime);

	/**
	 * Get Transfert Time.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTransfertTime();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_TransfertTime = new ModelColumn<>(I_PP_Product_Planning.class, "TransfertTime", null);
	String COLUMNNAME_TransfertTime = "TransfertTime";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Product_Planning.class, "Updated", null);
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
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkingTime (@Nullable BigDecimal WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWorkingTime();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_WorkingTime = new ModelColumn<>(I_PP_Product_Planning.class, "WorkingTime", null);
	String COLUMNNAME_WorkingTime = "WorkingTime";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setYield (int Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getYield();

	ModelColumn<I_PP_Product_Planning, Object> COLUMN_Yield = new ModelColumn<>(I_PP_Product_Planning.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";
}
