package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_NetworkDistributionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_NetworkDistributionLine 
{

	String Table_Name = "DD_NetworkDistributionLine";

//	/** AD_Table_ID=53061 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Created = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "Created", null);
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
	 * Set Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_AllowPush (boolean DD_AllowPush);

	/**
	 * Get Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDD_AllowPush();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_DD_AllowPush = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "DD_AllowPush", null);
	String COLUMNNAME_DD_AllowPush = "DD_AllowPush";

	/**
	 * Set Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID);

	/**
	 * Get Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistribution_ID();

	org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution();

	void setDD_NetworkDistribution(org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution);

	ModelColumn<I_DD_NetworkDistributionLine, org.eevolution.model.I_DD_NetworkDistribution> COLUMN_DD_NetworkDistribution_ID = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "DD_NetworkDistribution_ID", org.eevolution.model.I_DD_NetworkDistribution.class);
	String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistributionLine_ID();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_DD_NetworkDistributionLine_ID = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "DD_NetworkDistributionLine_ID", null);
	String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

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

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsKeepTargetPlant (boolean IsKeepTargetPlant);

	/**
	 * Get Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isKeepTargetPlant();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_IsKeepTargetPlant = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "IsKeepTargetPlant", null);
	String COLUMNNAME_IsKeepTargetPlant = "IsKeepTargetPlant";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_DD_NetworkDistributionLine, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_WarehouseSource_ID();

	String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercent (BigDecimal Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercent();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Percent = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "Percent", null);
	String COLUMNNAME_Percent = "Percent";

	/**
	 * Set Relative Priority.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityNo (int PriorityNo);

	/**
	 * Get Relative Priority.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPriorityNo();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_PriorityNo = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "PriorityNo", null);
	String COLUMNNAME_PriorityNo = "PriorityNo";

	/**
	 * Set Transfer Time.
	 * Transfer Time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransfertTime (@Nullable BigDecimal TransfertTime);

	/**
	 * Get Transfer Time.
	 * Transfer Time
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTransfertTime();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_TransfertTime = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "TransfertTime", null);
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

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_Updated = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_ValidFrom = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_DD_NetworkDistributionLine, Object> COLUMN_ValidTo = new ModelColumn<>(I_DD_NetworkDistributionLine.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
