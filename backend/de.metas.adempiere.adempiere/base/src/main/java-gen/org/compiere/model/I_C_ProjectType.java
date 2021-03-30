package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ProjectType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ProjectType 
{

	String Table_Name = "C_ProjectType";

//	/** AD_Table_ID=575 */
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
	 * Set Projekt-Nummerfolge.
	 * Nummernfolge für Projekt-Suchschlüssel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Sequence_ProjectValue_ID (int AD_Sequence_ProjectValue_ID);

	/**
	 * Get Projekt-Nummerfolge.
	 * Nummernfolge für Projekt-Suchschlüssel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Sequence_ProjectValue_ID();

	@Nullable org.compiere.model.I_AD_Sequence getAD_Sequence_ProjectValue();

	void setAD_Sequence_ProjectValue(@Nullable org.compiere.model.I_AD_Sequence AD_Sequence_ProjectValue);

	ModelColumn<I_C_ProjectType, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ProjectValue_ID = new ModelColumn<>(I_C_ProjectType.class, "AD_Sequence_ProjectValue_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_AD_Sequence_ProjectValue_ID = "AD_Sequence_ProjectValue_ID";

	/**
	 * Set Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectType_ID (int C_ProjectType_ID);

	/**
	 * Get Project Type.
	 * Set Project Type and for Service Projects copy Phases and Tasks of Project Type into Project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectType_ID();

	ModelColumn<I_C_ProjectType, Object> COLUMN_C_ProjectType_ID = new ModelColumn<>(I_C_ProjectType.class, "C_ProjectType_ID", null);
	String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ProjectType, Object> COLUMN_Created = new ModelColumn<>(I_C_ProjectType.class, "Created", null);
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

	ModelColumn<I_C_ProjectType, Object> COLUMN_Description = new ModelColumn<>(I_C_ProjectType.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_C_ProjectType, Object> COLUMN_Help = new ModelColumn<>(I_C_ProjectType.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_C_ProjectType, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ProjectType.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_ProjectType, Object> COLUMN_Name = new ModelColumn<>(I_C_ProjectType.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectCategory (java.lang.String ProjectCategory);

	/**
	 * Get Project Category.
	 * Project Category
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjectCategory();

	ModelColumn<I_C_ProjectType, Object> COLUMN_ProjectCategory = new ModelColumn<>(I_C_ProjectType.class, "ProjectCategory", null);
	String COLUMNNAME_ProjectCategory = "ProjectCategory";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ProjectType, Object> COLUMN_Updated = new ModelColumn<>(I_C_ProjectType.class, "Updated", null);
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
