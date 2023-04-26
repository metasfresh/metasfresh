package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for I_DeliveryPlanning_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_DeliveryPlanning_Data 
{

	String Table_Name = "I_DeliveryPlanning_Data";

//	/** AD_Table_ID=542290 */
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

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_Created = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "Created", null);
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
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName (@Nullable java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFileName();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_FileName = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

	/**
	 * Set Delivery Planning Data Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_DeliveryPlanning_Data_ID (int I_DeliveryPlanning_Data_ID);

	/**
	 * Get Delivery Planning Data Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_DeliveryPlanning_Data_ID();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_I_DeliveryPlanning_Data_ID = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "I_DeliveryPlanning_Data_ID", null);
	String COLUMNNAME_I_DeliveryPlanning_Data_ID = "I_DeliveryPlanning_Data_ID";

	/**
	 * Set Imported timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImported (@Nullable java.sql.Timestamp Imported);

	/**
	 * Get Imported timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getImported();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_Imported = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "Imported", null);
	String COLUMNNAME_Imported = "Imported";

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

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_IsActive = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadyForProcessing (boolean IsReadyForProcessing);

	/**
	 * Get Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadyForProcessing();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_IsReadyForProcessing = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "IsReadyForProcessing", null);
	String COLUMNNAME_IsReadyForProcessing = "IsReadyForProcessing";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_I_DeliveryPlanning_Data, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_Processed = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_I_DeliveryPlanning_Data, Object> COLUMN_Updated = new ModelColumn<>(I_I_DeliveryPlanning_Data.class, "Updated", null);
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
