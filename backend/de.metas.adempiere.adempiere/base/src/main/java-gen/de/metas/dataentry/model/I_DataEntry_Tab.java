package de.metas.dataentry.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DataEntry_Tab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DataEntry_Tab 
{

	String Table_Name = "DataEntry_Tab";

//	/** AD_Table_ID=541165 */
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
	 * Set Available in API.
	 * Specifies whether this field is available to external applications via metasfresh API.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAvailableInAPI (boolean AvailableInAPI);

	/**
	 * Get Available in API.
	 * Specifies whether this field is available to external applications via metasfresh API.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAvailableInAPI();

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_AvailableInAPI = new ModelColumn<>(I_DataEntry_Tab.class, "AvailableInAPI", null);
	String COLUMNNAME_AvailableInAPI = "AvailableInAPI";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_Created = new ModelColumn<>(I_DataEntry_Tab.class, "Created", null);
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
	 * Set Data entry tab.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDataEntry_Tab_ID (int DataEntry_Tab_ID);

	/**
	 * Get Data entry tab.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDataEntry_Tab_ID();

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_DataEntry_Tab_ID = new ModelColumn<>(I_DataEntry_Tab.class, "DataEntry_Tab_ID", null);
	String COLUMNNAME_DataEntry_Tab_ID = "DataEntry_Tab_ID";

	/**
	 * Set Eingabefenster.
	 * Bestehendes Fenster, das um diese Eingabegruppe erweitert werden soll
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDataEntry_TargetWindow_ID (int DataEntry_TargetWindow_ID);

	/**
	 * Get Eingabefenster.
	 * Bestehendes Fenster, das um diese Eingabegruppe erweitert werden soll
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDataEntry_TargetWindow_ID();

	org.compiere.model.I_AD_Window getDataEntry_TargetWindow();

	void setDataEntry_TargetWindow(org.compiere.model.I_AD_Window DataEntry_TargetWindow);

	ModelColumn<I_DataEntry_Tab, org.compiere.model.I_AD_Window> COLUMN_DataEntry_TargetWindow_ID = new ModelColumn<>(I_DataEntry_Tab.class, "DataEntry_TargetWindow_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_DataEntry_TargetWindow_ID = "DataEntry_TargetWindow_ID";

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

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_Description = new ModelColumn<>(I_DataEntry_Tab.class, "Description", null);
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

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_IsActive = new ModelColumn<>(I_DataEntry_Tab.class, "IsActive", null);
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

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_Name = new ModelColumn<>(I_DataEntry_Tab.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_SeqNo = new ModelColumn<>(I_DataEntry_Tab.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Registername.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTabName (java.lang.String TabName);

	/**
	 * Get Registername.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTabName();

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_TabName = new ModelColumn<>(I_DataEntry_Tab.class, "TabName", null);
	String COLUMNNAME_TabName = "TabName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DataEntry_Tab, Object> COLUMN_Updated = new ModelColumn<>(I_DataEntry_Tab.class, "Updated", null);
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
