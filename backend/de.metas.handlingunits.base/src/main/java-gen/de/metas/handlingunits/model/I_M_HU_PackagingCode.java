package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PackagingCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_PackagingCode 
{

	String Table_Name = "M_HU_PackagingCode";

//	/** AD_Table_ID=541423 */
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

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PackagingCode.class, "Created", null);
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

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Description = new ModelColumn<>(I_M_HU_PackagingCode.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set HU Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_UnitType (@Nullable java.lang.String HU_UnitType);

	/**
	 * Get HU Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHU_UnitType();

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_HU_UnitType = new ModelColumn<>(I_M_HU_PackagingCode.class, "HU_UnitType", null);
	String COLUMNNAME_HU_UnitType = "HU_UnitType";

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

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PackagingCode.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Packaging code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID);

	/**
	 * Get Packaging code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_ID();

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_M_HU_PackagingCode_ID = new ModelColumn<>(I_M_HU_PackagingCode.class, "M_HU_PackagingCode_ID", null);
	String COLUMNNAME_M_HU_PackagingCode_ID = "M_HU_PackagingCode_ID";

	/**
	 * Set Packaging code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPackagingCode (java.lang.String PackagingCode);

	/**
	 * Get Packaging code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPackagingCode();

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_PackagingCode = new ModelColumn<>(I_M_HU_PackagingCode.class, "PackagingCode", null);
	String COLUMNNAME_PackagingCode = "PackagingCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PackagingCode, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PackagingCode.class, "Updated", null);
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
