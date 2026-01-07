package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Weighting_Spec
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Weighting_Spec 
{

	String Table_Name = "PP_Weighting_Spec";

//	/** AD_Table_ID=542256 */
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

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_Created = new ModelColumn<>(I_PP_Weighting_Spec.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_Description = new ModelColumn<>(I_PP_Weighting_Spec.class, "Description", null);
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

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Weighting_Spec.class, "IsActive", null);
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

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_Name = new ModelColumn<>(I_PP_Weighting_Spec.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Weighting Specifications.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Weighting_Spec_ID (int PP_Weighting_Spec_ID);

	/**
	 * Get Weighting Specifications.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Weighting_Spec_ID();

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_PP_Weighting_Spec_ID = new ModelColumn<>(I_PP_Weighting_Spec.class, "PP_Weighting_Spec_ID", null);
	String COLUMNNAME_PP_Weighting_Spec_ID = "PP_Weighting_Spec_ID";

	/**
	 * Set Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTolerance_Perc (BigDecimal Tolerance_Perc);

	/**
	 * Get Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTolerance_Perc();

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_Tolerance_Perc = new ModelColumn<>(I_PP_Weighting_Spec.class, "Tolerance_Perc", null);
	String COLUMNNAME_Tolerance_Perc = "Tolerance_Perc";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Weighting_Spec.class, "Updated", null);
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

	/**
	 * Set Required Weight Checks.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWeightChecksRequired (int WeightChecksRequired);

	/**
	 * Get Required Weight Checks.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWeightChecksRequired();

	ModelColumn<I_PP_Weighting_Spec, Object> COLUMN_WeightChecksRequired = new ModelColumn<>(I_PP_Weighting_Spec.class, "WeightChecksRequired", null);
	String COLUMNNAME_WeightChecksRequired = "WeightChecksRequired";
}
