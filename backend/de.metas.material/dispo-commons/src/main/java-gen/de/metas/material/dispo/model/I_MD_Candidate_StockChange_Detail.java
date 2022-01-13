package de.metas.material.dispo.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for MD_Candidate_StockChange_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Candidate_StockChange_Detail
{

	String Table_Name = "MD_Candidate_StockChange_Detail";

	//	/** AD_Table_ID=541724 */
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

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "Created", null);
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
	 * Set Timestamp.
	 * Time when the event was fired in metasfresh.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEventDateMaterialDispo (java.sql.Timestamp EventDateMaterialDispo);

	/**
	 * Get Timestamp.
	 * Time when the event was fired in metasfresh.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getEventDateMaterialDispo();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_EventDateMaterialDispo = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "EventDateMaterialDispo", null);
	String COLUMNNAME_EventDateMaterialDispo = "EventDateMaterialDispo";

	/**
	 * Set Stock Control Purchase.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID);

	/**
	 * Get Stock Control Purchase.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFresh_QtyOnHand_ID();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_Fresh_QtyOnHand_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "Fresh_QtyOnHand_ID", null);
	String COLUMNNAME_Fresh_QtyOnHand_ID = "Fresh_QtyOnHand_ID";

	/**
	 * Set Stock Control Purchase Record.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFresh_QtyOnHand_Line_ID (int Fresh_QtyOnHand_Line_ID);

	/**
	 * Get Stock Control Purchase Record.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFresh_QtyOnHand_Line_ID();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_Fresh_QtyOnHand_Line_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "Fresh_QtyOnHand_Line_ID", null);
	String COLUMNNAME_Fresh_QtyOnHand_Line_ID = "Fresh_QtyOnHand_Line_ID";

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

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reverted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReverted (boolean IsReverted);

	/**
	 * Get Reverted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReverted();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_IsReverted = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "IsReverted", null);
	String COLUMNNAME_IsReverted = "IsReverted";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	@Nullable org.compiere.model.I_M_Inventory getM_Inventory();

	void setM_Inventory(@Nullable org.compiere.model.I_M_Inventory M_Inventory);

	ModelColumn<I_MD_Candidate_StockChange_Detail, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_ID();

	@Nullable org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	void setM_InventoryLine(@Nullable org.compiere.model.I_M_InventoryLine M_InventoryLine);

	ModelColumn<I_MD_Candidate_StockChange_Detail, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
	String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	@Nullable de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	void setMD_Candidate(@Nullable de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

	ModelColumn<I_MD_Candidate_StockChange_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Change of stock.
	 * Inventory or stock estimation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_StockChange_Detail_ID (int MD_Candidate_StockChange_Detail_ID);

	/**
	 * Get Change of stock.
	 * Inventory or stock estimation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_StockChange_Detail_ID();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_MD_Candidate_StockChange_Detail_ID = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "MD_Candidate_StockChange_Detail_ID", null);
	String COLUMNNAME_MD_Candidate_StockChange_Detail_ID = "MD_Candidate_StockChange_Detail_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Candidate_StockChange_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate_StockChange_Detail.class, "Updated", null);
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
