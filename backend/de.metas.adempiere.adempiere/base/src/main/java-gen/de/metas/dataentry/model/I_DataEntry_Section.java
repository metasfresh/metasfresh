package de.metas.dataentry.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DataEntry_Section
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DataEntry_Section 
{

	String Table_Name = "DataEntry_Section";

//	/** AD_Table_ID=541179 */
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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_AvailableInAPI = new ModelColumn<>(I_DataEntry_Section.class, "AvailableInAPI", null);
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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_Created = new ModelColumn<>(I_DataEntry_Section.class, "Created", null);
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
	 * Set Section.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDataEntry_Section_ID (int DataEntry_Section_ID);

	/**
	 * Get Section.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDataEntry_Section_ID();

	ModelColumn<I_DataEntry_Section, Object> COLUMN_DataEntry_Section_ID = new ModelColumn<>(I_DataEntry_Section.class, "DataEntry_Section_ID", null);
	String COLUMNNAME_DataEntry_Section_ID = "DataEntry_Section_ID";

	/**
	 * Set Subtab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID);

	/**
	 * Get Subtab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDataEntry_SubTab_ID();

	de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab();

	void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab);

	ModelColumn<I_DataEntry_Section, de.metas.dataentry.model.I_DataEntry_SubTab> COLUMN_DataEntry_SubTab_ID = new ModelColumn<>(I_DataEntry_Section.class, "DataEntry_SubTab_ID", de.metas.dataentry.model.I_DataEntry_SubTab.class);
	String COLUMNNAME_DataEntry_SubTab_ID = "DataEntry_SubTab_ID";

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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_Description = new ModelColumn<>(I_DataEntry_Section.class, "Description", null);
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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_IsActive = new ModelColumn<>(I_DataEntry_Section.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Initially closed.
	 * Specifies whether the field group is initially open (field visible) or closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInitiallyClosed (boolean IsInitiallyClosed);

	/**
	 * Get Initially closed.
	 * Specifies whether the field group is initially open (field visible) or closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInitiallyClosed();

	ModelColumn<I_DataEntry_Section, Object> COLUMN_IsInitiallyClosed = new ModelColumn<>(I_DataEntry_Section.class, "IsInitiallyClosed", null);
	String COLUMNNAME_IsInitiallyClosed = "IsInitiallyClosed";

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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_Name = new ModelColumn<>(I_DataEntry_Section.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Sektionsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSectionName (@Nullable java.lang.String SectionName);

	/**
	 * Get Sektionsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSectionName();

	ModelColumn<I_DataEntry_Section, Object> COLUMN_SectionName = new ModelColumn<>(I_DataEntry_Section.class, "SectionName", null);
	String COLUMNNAME_SectionName = "SectionName";

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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_SeqNo = new ModelColumn<>(I_DataEntry_Section.class, "SeqNo", null);
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

	ModelColumn<I_DataEntry_Section, Object> COLUMN_Updated = new ModelColumn<>(I_DataEntry_Section.class, "Updated", null);
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
