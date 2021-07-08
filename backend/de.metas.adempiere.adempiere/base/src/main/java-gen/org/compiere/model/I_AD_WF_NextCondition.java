package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_NextCondition
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_NextCondition 
{

	String Table_Name = "AD_WF_NextCondition";

//	/** AD_Table_ID=706 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_WF_NextCondition, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_WF_NextCondition.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
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
	 * Set Transition Condition.
	 * Workflow Node Transition Condition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_NextCondition_ID (int AD_WF_NextCondition_ID);

	/**
	 * Get Transition Condition.
	 * Workflow Node Transition Condition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_NextCondition_ID();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_AD_WF_NextCondition_ID = new ModelColumn<>(I_AD_WF_NextCondition.class, "AD_WF_NextCondition_ID", null);
	String COLUMNNAME_AD_WF_NextCondition_ID = "AD_WF_NextCondition_ID";

	/**
	 * Set Node Transition.
	 * Workflow Node Transition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_NodeNext_ID (int AD_WF_NodeNext_ID);

	/**
	 * Get Node Transition.
	 * Workflow Node Transition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_NodeNext_ID();

	String COLUMNNAME_AD_WF_NodeNext_ID = "AD_WF_NodeNext_ID";

	/**
	 * Set And/Or.
	 * Logical operation: AND or OR
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAndOr (java.lang.String AndOr);

	/**
	 * Get And/Or.
	 * Logical operation: AND or OR
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAndOr();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_AndOr = new ModelColumn<>(I_AD_WF_NextCondition.class, "AndOr", null);
	String COLUMNNAME_AndOr = "AndOr";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_NextCondition.class, "Created", null);
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

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Description = new ModelColumn<>(I_AD_WF_NextCondition.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_WF_NextCondition.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

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

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_NextCondition.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Arbeitsvorgang .
	 * Compare Operation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOperation (java.lang.String Operation);

	/**
	 * Get Arbeitsvorgang .
	 * Compare Operation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOperation();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Operation = new ModelColumn<>(I_AD_WF_NextCondition.class, "Operation", null);
	String COLUMNNAME_Operation = "Operation";

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

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_WF_NextCondition.class, "SeqNo", null);
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

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_NextCondition.class, "Updated", null);
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

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Value = new ModelColumn<>(I_AD_WF_NextCondition.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Value To.
	 * Value To
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue2 (@Nullable java.lang.String Value2);

	/**
	 * Get Value To.
	 * Value To
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue2();

	ModelColumn<I_AD_WF_NextCondition, Object> COLUMN_Value2 = new ModelColumn<>(I_AD_WF_NextCondition.class, "Value2", null);
	String COLUMNNAME_Value2 = "Value2";
}
