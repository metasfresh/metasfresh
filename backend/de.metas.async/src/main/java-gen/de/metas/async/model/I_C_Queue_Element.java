package de.metas.async.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Queue_Element
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Queue_Element 
{

	String Table_Name = "C_Queue_Element";

//	/** AD_Table_ID=540426 */
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Element Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Queue_Element_ID (int C_Queue_Element_ID);

	/**
	 * Get Element Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Queue_Element_ID();

	ModelColumn<I_C_Queue_Element, Object> COLUMN_C_Queue_Element_ID = new ModelColumn<>(I_C_Queue_Element.class, "C_Queue_Element_ID", null);
	String COLUMNNAME_C_Queue_Element_ID = "C_Queue_Element_ID";

	/**
	 * Set Asynchronous WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get Asynchronous WorkPackage Queue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Queue_WorkPackage_ID();

	I_C_Queue_WorkPackage getC_Queue_WorkPackage();

	void setC_Queue_WorkPackage(I_C_Queue_WorkPackage C_Queue_WorkPackage);

	ModelColumn<I_C_Queue_Element, I_C_Queue_WorkPackage> COLUMN_C_Queue_WorkPackage_ID = new ModelColumn<>(I_C_Queue_Element.class, "C_Queue_WorkPackage_ID", I_C_Queue_WorkPackage.class);
	String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

	/**
	 * Set Ältestes nicht verarb. Vorgänger-Paket.
	 * Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Queue_Workpackage_Preceeding_ID (int C_Queue_Workpackage_Preceeding_ID);

	/**
	 * Get Ältestes nicht verarb. Vorgänger-Paket.
	 * Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_Queue_Workpackage_Preceeding_ID();

	@Deprecated
	@Nullable I_C_Queue_WorkPackage getC_Queue_Workpackage_Preceeding();

	@Deprecated
	void setC_Queue_Workpackage_Preceeding(@Nullable I_C_Queue_WorkPackage C_Queue_Workpackage_Preceeding);

	ModelColumn<I_C_Queue_Element, I_C_Queue_WorkPackage> COLUMN_C_Queue_Workpackage_Preceeding_ID = new ModelColumn<>(I_C_Queue_Element.class, "C_Queue_Workpackage_Preceeding_ID", I_C_Queue_WorkPackage.class);
	String COLUMNNAME_C_Queue_Workpackage_Preceeding_ID = "C_Queue_Workpackage_Preceeding_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Queue_Element, Object> COLUMN_Created = new ModelColumn<>(I_C_Queue_Element.class, "Created", null);
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

	ModelColumn<I_C_Queue_Element, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Queue_Element.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_Queue_Element, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Queue_Element.class, "Record_ID", null);
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

	ModelColumn<I_C_Queue_Element, Object> COLUMN_Updated = new ModelColumn<>(I_C_Queue_Element.class, "Updated", null);
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
