package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_ViewSource_Column
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_ViewSource_Column 
{

	String Table_Name = "AD_ViewSource_Column";

//	/** AD_Table_ID=542320 */
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_ViewSource_Column, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_ViewSource_Column.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
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
	 * Set View Source Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_ViewSource_Column_ID (int AD_ViewSource_Column_ID);

	/**
	 * Get View Source Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_ViewSource_Column_ID();

	ModelColumn<I_AD_ViewSource_Column, Object> COLUMN_AD_ViewSource_Column_ID = new ModelColumn<>(I_AD_ViewSource_Column.class, "AD_ViewSource_Column_ID", null);
	String COLUMNNAME_AD_ViewSource_Column_ID = "AD_ViewSource_Column_ID";

	/**
	 * Set View Source.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_ViewSource_ID (int AD_ViewSource_ID);

	/**
	 * Get View Source.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_ViewSource_ID();

	org.compiere.model.I_AD_ViewSource getAD_ViewSource();

	void setAD_ViewSource(org.compiere.model.I_AD_ViewSource AD_ViewSource);

	ModelColumn<I_AD_ViewSource_Column, org.compiere.model.I_AD_ViewSource> COLUMN_AD_ViewSource_ID = new ModelColumn<>(I_AD_ViewSource_Column.class, "AD_ViewSource_ID", org.compiere.model.I_AD_ViewSource.class);
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

	ModelColumn<I_AD_ViewSource_Column, Object> COLUMN_Created = new ModelColumn<>(I_AD_ViewSource_Column.class, "Created", null);
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

	ModelColumn<I_AD_ViewSource_Column, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_ViewSource_Column.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_AD_ViewSource_Column, Object> COLUMN_TechnicalNote = new ModelColumn<>(I_AD_ViewSource_Column.class, "TechnicalNote", null);
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

	ModelColumn<I_AD_ViewSource_Column, Object> COLUMN_Updated = new ModelColumn<>(I_AD_ViewSource_Column.class, "Updated", null);
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
