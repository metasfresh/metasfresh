package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_ViewSource
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_ViewSource 
{

	String Table_Name = "AD_ViewSource";

//	/** AD_Table_ID=542319 */
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
	 * Set View Source.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_ViewSource_ID (int AD_ViewSource_ID);

	/**
	 * Get View Source.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_ViewSource_ID();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_AD_ViewSource_ID = new ModelColumn<>(I_AD_ViewSource.class, "AD_ViewSource_ID", null);
	String COLUMNNAME_AD_ViewSource_ID = "AD_ViewSource_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_Created = new ModelColumn<>(I_AD_ViewSource.class, "Created", null);
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

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_ViewSource.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invalidate on After Change.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidateOnAfterChange (boolean IsInvalidateOnAfterChange);

	/**
	 * Get Invalidate on After Change.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidateOnAfterChange();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsInvalidateOnAfterChange = new ModelColumn<>(I_AD_ViewSource.class, "IsInvalidateOnAfterChange", null);
	String COLUMNNAME_IsInvalidateOnAfterChange = "IsInvalidateOnAfterChange";

	/**
	 * Set Invalidate on After Delete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidateOnAfterDelete (boolean IsInvalidateOnAfterDelete);

	/**
	 * Get Invalidate on After Delete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidateOnAfterDelete();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsInvalidateOnAfterDelete = new ModelColumn<>(I_AD_ViewSource.class, "IsInvalidateOnAfterDelete", null);
	String COLUMNNAME_IsInvalidateOnAfterDelete = "IsInvalidateOnAfterDelete";

	/**
	 * Set Invalidate on After New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidateOnAfterNew (boolean IsInvalidateOnAfterNew);

	/**
	 * Get Invalidate on After New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidateOnAfterNew();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsInvalidateOnAfterNew = new ModelColumn<>(I_AD_ViewSource.class, "IsInvalidateOnAfterNew", null);
	String COLUMNNAME_IsInvalidateOnAfterNew = "IsInvalidateOnAfterNew";

	/**
	 * Set Invalidate on Before Change.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidateOnBeforeChange (boolean IsInvalidateOnBeforeChange);

	/**
	 * Get Invalidate on Before Change.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidateOnBeforeChange();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsInvalidateOnBeforeChange = new ModelColumn<>(I_AD_ViewSource.class, "IsInvalidateOnBeforeChange", null);
	String COLUMNNAME_IsInvalidateOnBeforeChange = "IsInvalidateOnBeforeChange";

	/**
	 * Set Invalidate on Before New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidateOnBeforeNew (boolean IsInvalidateOnBeforeNew);

	/**
	 * Get Invalidate on Before New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidateOnBeforeNew();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_IsInvalidateOnBeforeNew = new ModelColumn<>(I_AD_ViewSource.class, "IsInvalidateOnBeforeNew", null);
	String COLUMNNAME_IsInvalidateOnBeforeNew = "IsInvalidateOnBeforeNew";

	/**
	 * Set Parent Link column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setParent_LinkColumn_ID (int Parent_LinkColumn_ID);

	/**
	 * Get Parent Link column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getParent_LinkColumn_ID();

	org.compiere.model.I_AD_Column getParent_LinkColumn();

	void setParent_LinkColumn(org.compiere.model.I_AD_Column Parent_LinkColumn);

	ModelColumn<I_AD_ViewSource, org.compiere.model.I_AD_Column> COLUMN_Parent_LinkColumn_ID = new ModelColumn<>(I_AD_ViewSource.class, "Parent_LinkColumn_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_Parent_LinkColumn_ID = "Parent_LinkColumn_ID";

	/**
	 * Set Source Link column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_LinkColumn_ID (int Source_LinkColumn_ID);

	/**
	 * Get Source Link column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_LinkColumn_ID();

	org.compiere.model.I_AD_Column getSource_LinkColumn();

	void setSource_LinkColumn(org.compiere.model.I_AD_Column Source_LinkColumn);

	ModelColumn<I_AD_ViewSource, org.compiere.model.I_AD_Column> COLUMN_Source_LinkColumn_ID = new ModelColumn<>(I_AD_ViewSource.class, "Source_LinkColumn_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_Source_LinkColumn_ID = "Source_LinkColumn_ID";

	/**
	 * Set Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_Table_ID (int Source_Table_ID);

	/**
	 * Get Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_Table_ID();

	String COLUMNNAME_Source_Table_ID = "Source_Table_ID";

	/**
	 * Set TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTechnicalNote (@Nullable java.lang.String TechnicalNote);

	/**
	 * Get TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTechnicalNote();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_TechnicalNote = new ModelColumn<>(I_AD_ViewSource.class, "TechnicalNote", null);
	String COLUMNNAME_TechnicalNote = "TechnicalNote";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_ViewSource, Object> COLUMN_Updated = new ModelColumn<>(I_AD_ViewSource.class, "Updated", null);
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
