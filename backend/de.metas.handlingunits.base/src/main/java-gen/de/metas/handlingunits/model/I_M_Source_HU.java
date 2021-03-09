package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Source_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Source_HU 
{

	String Table_Name = "M_Source_HU";

//	/** AD_Table_ID=540835 */
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

	ModelColumn<I_M_Source_HU, Object> COLUMN_Created = new ModelColumn<>(I_M_Source_HU.class, "Created", null);
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

	ModelColumn<I_M_Source_HU, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Source_HU.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_Source_HU, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_Source_HU.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Source-HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Source_HU_ID (int M_Source_HU_ID);

	/**
	 * Get Source-HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Source_HU_ID();

	ModelColumn<I_M_Source_HU, Object> COLUMN_M_Source_HU_ID = new ModelColumn<>(I_M_Source_HU.class, "M_Source_HU_ID", null);
	String COLUMNNAME_M_Source_HU_ID = "M_Source_HU_ID";

	/**
	 * Set PreDestroy_Snapshot_UUID.
	 * Snapshot einer HU vor ihrer Zerstörung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreDestroy_Snapshot_UUID (@Nullable java.lang.String PreDestroy_Snapshot_UUID);

	/**
	 * Get PreDestroy_Snapshot_UUID.
	 * Snapshot einer HU vor ihrer Zerstörung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPreDestroy_Snapshot_UUID();

	ModelColumn<I_M_Source_HU, Object> COLUMN_PreDestroy_Snapshot_UUID = new ModelColumn<>(I_M_Source_HU.class, "PreDestroy_Snapshot_UUID", null);
	String COLUMNNAME_PreDestroy_Snapshot_UUID = "PreDestroy_Snapshot_UUID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Source_HU, Object> COLUMN_Updated = new ModelColumn<>(I_M_Source_HU.class, "Updated", null);
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
