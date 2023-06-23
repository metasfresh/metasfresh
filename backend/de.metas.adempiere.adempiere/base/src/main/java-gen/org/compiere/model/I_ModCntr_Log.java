package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Log 
{

	String Table_Name = "ModCntr_Log";

//	/** AD_Table_ID=542338 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_ModCntr_Log.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_ModCntr_Log.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Collection Point.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCollectionPoint_BPartner_ID (int CollectionPoint_BPartner_ID);

	/**
	 * Get Collection Point.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCollectionPoint_BPartner_ID();

	String COLUMNNAME_CollectionPoint_BPartner_ID = "CollectionPoint_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Log.class, "Created", null);
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
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateTrx (@Nullable java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateTrx();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_DateTrx = new ModelColumn<>(I_ModCntr_Log.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Description = new ModelColumn<>(I_ModCntr_Log.class, "Description", null);
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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_ModCntr_Log.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Contract Module Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_ID (int ModCntr_Log_ID);

	/**
	 * Get Contract Module Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Log_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_ModCntr_Log_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Log_ID", null);
	String COLUMNNAME_ModCntr_Log_ID = "ModCntr_Log_ID";

	/**
	 * Set Contract Module Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Type_ID (int ModCntr_Type_ID);

	/**
	 * Get Contract Module Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Type_ID();

	@Nullable org.compiere.model.I_ModCntr_Type getModCntr_Type();

	void setModCntr_Type(@Nullable org.compiere.model.I_ModCntr_Type ModCntr_Type);

	ModelColumn<I_ModCntr_Log, org.compiere.model.I_ModCntr_Type> COLUMN_ModCntr_Type_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Type_ID", org.compiere.model.I_ModCntr_Type.class);
	String COLUMNNAME_ModCntr_Type_ID = "ModCntr_Type_ID";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Processed = new ModelColumn<>(I_ModCntr_Log.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Producer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProducer_BPartner_ID (int Producer_BPartner_ID);

	/**
	 * Get Producer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getProducer_BPartner_ID();

	String COLUMNNAME_Producer_BPartner_ID = "Producer_BPartner_ID";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_ModCntr_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Log.class, "Updated", null);
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
