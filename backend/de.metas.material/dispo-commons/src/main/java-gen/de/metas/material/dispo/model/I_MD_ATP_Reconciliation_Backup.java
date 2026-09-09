package de.metas.material.dispo.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MD_ATP_Reconciliation_Backup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_ATP_Reconciliation_Backup 
{

	String Table_Name = "MD_ATP_Reconciliation_Backup";

//	/** AD_Table_ID=542645 */
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

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_Created = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "Created", null);
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
	 * Set Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateProjected (java.sql.Timestamp DateProjected);

	/**
	 * Get Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateProjected();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_DateProjected = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "DateProjected", null);
	String COLUMNNAME_DateProjected = "DateProjected";

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

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MD_ATP_Reconciliation_Backup.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_ATP_Reconciliation_Backup_ID (int MD_ATP_Reconciliation_Backup_ID);

	/**
	 * Get MD_ATP_Reconciliation_Backup.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_ATP_Reconciliation_Backup_ID();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_MD_ATP_Reconciliation_Backup_ID = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "MD_ATP_Reconciliation_Backup_ID", null);
	String COLUMNNAME_MD_ATP_Reconciliation_Backup_ID = "MD_ATP_Reconciliation_Backup_ID";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Qty after change.
	 * The candidate quantity after the ATP reconciliation changed it.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyAfter (BigDecimal QtyAfter);

	/**
	 * Get Qty after change.
	 * The candidate quantity after the ATP reconciliation changed it.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyAfter();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_QtyAfter = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "QtyAfter", null);
	String COLUMNNAME_QtyAfter = "QtyAfter";

	/**
	 * Set Qty before change.
	 * The candidate quantity immediately before the ATP reconciliation changed it.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBefore (@Nullable BigDecimal QtyBefore);

	/**
	 * Get Qty before change.
	 * The candidate quantity immediately before the ATP reconciliation changed it.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBefore();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_QtyBefore = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "QtyBefore", null);
	String COLUMNNAME_QtyBefore = "QtyBefore";

	/**
	 * Set Reconciliation run id.
	 * Unique identifier grouping every row a single ATP reconciliation run backed up.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReconciliationRunUUID (java.lang.String ReconciliationRunUUID);

	/**
	 * Get Reconciliation run id.
	 * Unique identifier grouping every row a single ATP reconciliation run backed up.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReconciliationRunUUID();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_ReconciliationRunUUID = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "ReconciliationRunUUID", null);
	String COLUMNNAME_ReconciliationRunUUID = "ReconciliationRunUUID";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStorageAttributesKey (java.lang.String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStorageAttributesKey();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "StorageAttributesKey", null);
	String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_ATP_Reconciliation_Backup, Object> COLUMN_Updated = new ModelColumn<>(I_MD_ATP_Reconciliation_Backup.class, "Updated", null);
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
