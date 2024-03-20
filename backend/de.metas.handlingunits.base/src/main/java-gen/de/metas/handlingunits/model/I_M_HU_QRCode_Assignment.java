package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_QRCode_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_QRCode_Assignment 
{

	String Table_Name = "M_HU_QRCode_Assignment";

//	/** AD_Table_ID=542395 */
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

	ModelColumn<I_M_HU_QRCode_Assignment, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "Created", null);
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

	ModelColumn<I_M_HU_QRCode_Assignment, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	I_M_HU getM_HU();

	void setM_HU(I_M_HU M_HU);

	ModelColumn<I_M_HU_QRCode_Assignment, I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "M_HU_ID", I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set HU QR Code Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_QRCode_Assignment_ID (int M_HU_QRCode_Assignment_ID);

	/**
	 * Get HU QR Code Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_QRCode_Assignment_ID();

	ModelColumn<I_M_HU_QRCode_Assignment, Object> COLUMN_M_HU_QRCode_Assignment_ID = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "M_HU_QRCode_Assignment_ID", null);
	String COLUMNNAME_M_HU_QRCode_Assignment_ID = "M_HU_QRCode_Assignment_ID";

	/**
	 * Set HU QR Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_QRCode_ID (int M_HU_QRCode_ID);

	/**
	 * Get HU QR Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_QRCode_ID();

	I_M_HU_QRCode getM_HU_QRCode();

	void setM_HU_QRCode(I_M_HU_QRCode M_HU_QRCode);

	ModelColumn<I_M_HU_QRCode_Assignment, I_M_HU_QRCode> COLUMN_M_HU_QRCode_ID = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "M_HU_QRCode_ID", I_M_HU_QRCode.class);
	String COLUMNNAME_M_HU_QRCode_ID = "M_HU_QRCode_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_QRCode_Assignment, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_QRCode_Assignment.class, "Updated", null);
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
