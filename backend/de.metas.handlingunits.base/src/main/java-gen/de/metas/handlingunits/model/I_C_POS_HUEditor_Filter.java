package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_HUEditor_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_HUEditor_Filter 
{

	String Table_Name = "C_POS_HUEditor_Filter";

//	/** AD_Table_ID=540647 */
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
	 * Set Java Class.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/**
	 * Get Java Class.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_JavaClass_ID();

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_AD_JavaClass_ID = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "AD_JavaClass_ID", null);
	String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

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
	 * Set Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_ID();

	org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenzschlüssel.
	 * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Referenzschlüssel.
	 * Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_Value_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Value();

	void setAD_Reference_Value(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Value);

	ModelColumn<I_C_POS_HUEditor_Filter, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set POS HU Editor Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_HUEditor_Filter_ID (int C_POS_HUEditor_Filter_ID);

	/**
	 * Get POS HU Editor Filter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_HUEditor_Filter_ID();

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_C_POS_HUEditor_Filter_ID = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "C_POS_HUEditor_Filter_ID", null);
	String COLUMNNAME_C_POS_HUEditor_Filter_ID = "C_POS_HUEditor_Filter_ID";

	/**
	 * Set Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getColumnName();

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_ColumnName = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "ColumnName", null);
	String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "Created", null);
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

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_HUEditor_Filter, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_HUEditor_Filter.class, "Updated", null);
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
