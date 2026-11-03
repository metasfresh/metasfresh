package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_Line_Carrier_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Line_Carrier_Service 
{

	String Table_Name = "M_Picking_Job_Line_Carrier_Service";

//	/** AD_Table_ID=542619 */
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
	 * Set Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCarrier_Service_ID (int Carrier_Service_ID);

	/**
	 * Get Carrier Service Catalog.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCarrier_Service_ID();

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, org.compiere.model.I_Carrier_Service> COLUMN_Carrier_Service_ID = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "Carrier_Service_ID", org.compiere.model.I_Carrier_Service.class);
	String COLUMNNAME_Carrier_Service_ID = "Carrier_Service_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "Created", null);
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

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Carrier Service Assignment (Picking Line).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Line_Carrier_Service_ID (int M_Picking_Job_Line_Carrier_Service_ID);

	/**
	 * Get Carrier Service Assignment (Picking Line).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Line_Carrier_Service_ID();

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, Object> COLUMN_M_Picking_Job_Line_Carrier_Service_ID = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "M_Picking_Job_Line_Carrier_Service_ID", null);
	String COLUMNNAME_M_Picking_Job_Line_Carrier_Service_ID = "M_Picking_Job_Line_Carrier_Service_ID";

	/**
	 * Set Picking Job Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Line_ID (int M_Picking_Job_Line_ID);

	/**
	 * Get Picking Job Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Line_ID();

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, de.metas.handlingunits.model.I_M_Picking_Job_Line> COLUMN_M_Picking_Job_Line_ID = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "M_Picking_Job_Line_ID", de.metas.handlingunits.model.I_M_Picking_Job_Line.class);
	String COLUMNNAME_M_Picking_Job_Line_ID = "M_Picking_Job_Line_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Line_Carrier_Service, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Line_Carrier_Service.class, "Updated", null);
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
