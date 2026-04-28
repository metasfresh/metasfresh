package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Order_Weighting_RunCheck
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_Weighting_RunCheck 
{

	String Table_Name = "PP_Order_Weighting_RunCheck";

//	/** AD_Table_ID=542255 */
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

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "Created", null);
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
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

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_Description = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "Description", null);
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

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tolerance Excheeded.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsToleranceExceeded (boolean IsToleranceExceeded);

	/**
	 * Get Tolerance Excheeded.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isToleranceExceeded();

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_IsToleranceExceeded = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "IsToleranceExceeded", null);
	String COLUMNNAME_IsToleranceExceeded = "IsToleranceExceeded";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_Line = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Wighting Check.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Weighting_RunCheck_ID (int PP_Order_Weighting_RunCheck_ID);

	/**
	 * Get Wighting Check.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Weighting_RunCheck_ID();

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_PP_Order_Weighting_RunCheck_ID = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "PP_Order_Weighting_RunCheck_ID", null);
	String COLUMNNAME_PP_Order_Weighting_RunCheck_ID = "PP_Order_Weighting_RunCheck_ID";

	/**
	 * Set Weighting Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Weighting_Run_ID (int PP_Order_Weighting_Run_ID);

	/**
	 * Get Weighting Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Weighting_Run_ID();

	org.eevolution.model.I_PP_Order_Weighting_Run getPP_Order_Weighting_Run();

	void setPP_Order_Weighting_Run(org.eevolution.model.I_PP_Order_Weighting_Run PP_Order_Weighting_Run);

	ModelColumn<I_PP_Order_Weighting_RunCheck, org.eevolution.model.I_PP_Order_Weighting_Run> COLUMN_PP_Order_Weighting_Run_ID = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "PP_Order_Weighting_Run_ID", org.eevolution.model.I_PP_Order_Weighting_Run.class);
	String COLUMNNAME_PP_Order_Weighting_Run_ID = "PP_Order_Weighting_Run_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "Updated", null);
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
	 * Set Weight.
	 * Weight of a product
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWeight (BigDecimal Weight);

	/**
	 * Get Weight.
	 * Weight of a product
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getWeight();

	ModelColumn<I_PP_Order_Weighting_RunCheck, Object> COLUMN_Weight = new ModelColumn<>(I_PP_Order_Weighting_RunCheck.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";
}
