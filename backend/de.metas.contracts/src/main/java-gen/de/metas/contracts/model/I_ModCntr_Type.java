package de.metas.contracts.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;
import org.compiere.model.I_C_AcctSchema_Element;

/** Generated Interface for ModCntr_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Type 
{

	String Table_Name = "ModCntr_Type";

//	/** AD_Table_ID=542337 */
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
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(@Nullable org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_C_AcctSchema_Element, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_C_AcctSchema_Element.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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

	ModelColumn<I_ModCntr_Type, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Type.class, "Created", null);
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
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_ModCntr_Type, Object> COLUMN_Description = new ModelColumn<>(I_ModCntr_Type.class, "Description", null);
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

	ModelColumn<I_ModCntr_Type, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Type.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Computing Method.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Type_ID (int ModCntr_Type_ID);

	/**
	 * Get Computing Method.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Type_ID();

	ModelColumn<I_ModCntr_Type, Object> COLUMN_ModCntr_Type_ID = new ModelColumn<>(I_ModCntr_Type.class, "ModCntr_Type_ID", null);
	String COLUMNNAME_ModCntr_Type_ID = "ModCntr_Type_ID";

	/**
	 * Set Modular Contract Handler Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModularContractHandlerType (java.lang.String ModularContractHandlerType);

	/**
	 * Get Modular Contract Handler Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getModularContractHandlerType();

	ModelColumn<I_ModCntr_Type, Object> COLUMN_ModularContractHandlerType = new ModelColumn<>(I_ModCntr_Type.class, "ModularContractHandlerType", null);
	String COLUMNNAME_ModularContractHandlerType = "ModularContractHandlerType";

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

	ModelColumn<I_ModCntr_Type, Object> COLUMN_Name = new ModelColumn<>(I_ModCntr_Type.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Type, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Type.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_ModCntr_Type, Object> COLUMN_Value = new ModelColumn<>(I_ModCntr_Type.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
