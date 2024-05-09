package de.metas.contracts.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ModCntr_Module
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Module 
{

	String Table_Name = "ModCntr_Module";

//	/** AD_Table_ID=542340 */
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

	ModelColumn<I_ModCntr_Module, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Module.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDescription();

	ModelColumn<I_ModCntr_Module, Object> COLUMN_Description = new ModelColumn<>(I_ModCntr_Module.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Invoice Line Group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicingGroup (java.lang.String InvoicingGroup);

	/**
	 * Get Invoice Line Group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoicingGroup();

	ModelColumn<I_ModCntr_Module, Object> COLUMN_InvoicingGroup = new ModelColumn<>(I_ModCntr_Module.class, "InvoicingGroup", null);
	String COLUMNNAME_InvoicingGroup = "InvoicingGroup";

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

	ModelColumn<I_ModCntr_Module, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Module.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Modules.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	ModelColumn<I_ModCntr_Module, Object> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_ModCntr_Module.class, "ModCntr_Module_ID", null);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";

	/**
	 * Set Modular Contract Settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Settings_ID (int ModCntr_Settings_ID);

	/**
	 * Get Modular Contract Settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Settings_ID();

	de.metas.contracts.model.I_ModCntr_Settings getModCntr_Settings();

	void setModCntr_Settings(de.metas.contracts.model.I_ModCntr_Settings ModCntr_Settings);

	ModelColumn<I_ModCntr_Module, de.metas.contracts.model.I_ModCntr_Settings> COLUMN_ModCntr_Settings_ID = new ModelColumn<>(I_ModCntr_Module.class, "ModCntr_Settings_ID", de.metas.contracts.model.I_ModCntr_Settings.class);
	String COLUMNNAME_ModCntr_Settings_ID = "ModCntr_Settings_ID";

	/**
	 * Set Computing Method.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Type_ID (int ModCntr_Type_ID);

	/**
	 * Get Computing Method.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Type_ID();

	de.metas.contracts.model.I_ModCntr_Type getModCntr_Type();

	void setModCntr_Type(de.metas.contracts.model.I_ModCntr_Type ModCntr_Type);

	ModelColumn<I_ModCntr_Module, de.metas.contracts.model.I_ModCntr_Type> COLUMN_ModCntr_Type_ID = new ModelColumn<>(I_ModCntr_Module.class, "ModCntr_Type_ID", de.metas.contracts.model.I_ModCntr_Type.class);
	String COLUMNNAME_ModCntr_Type_ID = "ModCntr_Type_ID";

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
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_ModCntr_Module, Object> COLUMN_Name = new ModelColumn<>(I_ModCntr_Module.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_ModCntr_Module, Object> COLUMN_Processed = new ModelColumn<>(I_ModCntr_Module.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

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

	ModelColumn<I_ModCntr_Module, Object> COLUMN_SeqNo = new ModelColumn<>(I_ModCntr_Module.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Module, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Module.class, "Updated", null);
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
