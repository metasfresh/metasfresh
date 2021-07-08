package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PI_Version
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_PI_Version 
{

	String Table_Name = "M_HU_PI_Version";

//	/** AD_Table_ID=540510 */
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

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PI_Version.class, "Created", null);
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

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Description = new ModelColumn<>(I_M_HU_PI_Version.class, "Description", null);
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

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_HU_UnitType = new ModelColumn<>(I_M_HU_PI_Version.class, "HU_UnitType", null);
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

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PI_Version.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Current Valid Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCurrent (boolean IsCurrent);

	/**
	 * Get Current Valid Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCurrent();

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_IsCurrent = new ModelColumn<>(I_M_HU_PI_Version.class, "IsCurrent", null);
	String COLUMNNAME_IsCurrent = "IsCurrent";

	/**
	 * Set Packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID);

	/**
	 * Get Packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PackagingCode getM_HU_PackagingCode();

	void setM_HU_PackagingCode(@Nullable de.metas.handlingunits.model.I_M_HU_PackagingCode M_HU_PackagingCode);

	ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PackagingCode> COLUMN_M_HU_PackagingCode_ID = new ModelColumn<>(I_M_HU_PI_Version.class, "M_HU_PackagingCode_ID", de.metas.handlingunits.model.I_M_HU_PackagingCode.class);
	String COLUMNNAME_M_HU_PackagingCode_ID = "M_HU_PackagingCode_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_ID();

	de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI();

	void setM_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_HU_PI);

	ModelColumn<I_M_HU_PI_Version, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_HU_PI_ID = new ModelColumn<>(I_M_HU_PI_Version.class, "M_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Version_ID();

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_M_HU_PI_Version_ID = new ModelColumn<>(I_M_HU_PI_Version.class, "M_HU_PI_Version_ID", null);
	String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

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

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Name = new ModelColumn<>(I_M_HU_PI_Version.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PI_Version, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PI_Version.class, "Updated", null);
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
