package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_ComponentGenerator_Param
 *  @author metasfresh (generated) 
 */
public interface I_PP_ComponentGenerator_Param 
{

	String Table_Name = "PP_ComponentGenerator_Param";

//	/** AD_Table_ID=541555 */
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

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_Created = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "Created", null);
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

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_Name = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParamValue (@Nullable java.lang.String ParamValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getParamValue();

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_ParamValue = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "ParamValue", null);
	String COLUMNNAME_ParamValue = "ParamValue";

	/**
	 * Set PP_ComponentGenerator.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_ComponentGenerator_ID (int PP_ComponentGenerator_ID);

	/**
	 * Get PP_ComponentGenerator.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_ComponentGenerator_ID();

	org.compiere.model.I_PP_ComponentGenerator getPP_ComponentGenerator();

	void setPP_ComponentGenerator(org.compiere.model.I_PP_ComponentGenerator PP_ComponentGenerator);

	ModelColumn<I_PP_ComponentGenerator_Param, org.compiere.model.I_PP_ComponentGenerator> COLUMN_PP_ComponentGenerator_ID = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "PP_ComponentGenerator_ID", org.compiere.model.I_PP_ComponentGenerator.class);
	String COLUMNNAME_PP_ComponentGenerator_ID = "PP_ComponentGenerator_ID";

	/**
	 * Set PP_ComponentGenerator_Param.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_ComponentGenerator_Param_ID (int PP_ComponentGenerator_Param_ID);

	/**
	 * Get PP_ComponentGenerator_Param.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_ComponentGenerator_Param_ID();

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_PP_ComponentGenerator_Param_ID = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "PP_ComponentGenerator_Param_ID", null);
	String COLUMNNAME_PP_ComponentGenerator_Param_ID = "PP_ComponentGenerator_Param_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_ComponentGenerator_Param, Object> COLUMN_Updated = new ModelColumn<>(I_PP_ComponentGenerator_Param.class, "Updated", null);
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
